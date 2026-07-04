package com.example.usersapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usersapi.dto.RoomCreateRequest;
import com.example.usersapi.exception.HotelNotFoundException;
import com.example.usersapi.exception.RoomNotFoundException;
import com.example.usersapi.exception.UnauthorizedAccessException;
import com.example.usersapi.model.Hotel;
import com.example.usersapi.model.Role;
import com.example.usersapi.model.Room;
import com.example.usersapi.model.RoomType;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.HotelRepository;
import com.example.usersapi.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public Room createRoom(RoomCreateRequest request, User currentUser) {
        // Verify hotel exists and user has permission
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        if (!hotel.getOwnerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedAccessException("You don't have permission to add rooms to this hotel");
        }

        Room room = new Room(
                UUID.randomUUID(),
                request.getHotelId(),
                request.getRoomNumber(),
                RoomType.valueOf(request.getRoomType().toUpperCase()),
                request.getCapacity(),
                request.getBasePrice()
        );
        room.setDescription(request.getDescription());
        room.setAmenities(request.getAmenities());

        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Room findById(UUID id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Room> findByHotelId(UUID hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public List<Room> findAvailableRooms(UUID hotelId, LocalDate checkInDate, LocalDate checkOutDate,
                                          Integer capacity, BigDecimal maxPrice) {
        return roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate, capacity, maxPrice);
    }

    @Transactional
    public Room updateRoom(UUID roomId, RoomCreateRequest request, User currentUser) {
        Room room = findById(roomId);
        Hotel hotel = hotelRepository.findById(room.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        // Check authorization
        if (!hotel.getOwnerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedAccessException("You don't have permission to edit this room");
        }

        if (request.getRoomNumber() != null) {
            room.setRoomNumber(request.getRoomNumber());
        }
        if (request.getRoomType() != null) {
            room.setRoomType(RoomType.valueOf(request.getRoomType().toUpperCase()));
        }
        if (request.getCapacity() != null) {
            room.setCapacity(request.getCapacity());
        }
        if (request.getBasePrice() != null) {
            room.setBasePrice(request.getBasePrice());
        }
        if (request.getDescription() != null) {
            room.setDescription(request.getDescription());
        }
        if (request.getAmenities() != null) {
            room.setAmenities(request.getAmenities());
        }

        room.setUpdatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(UUID roomId, User currentUser) {
        Room room = findById(roomId);
        Hotel hotel = hotelRepository.findById(room.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        // Check authorization
        if (!hotel.getOwnerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedAccessException("You don't have permission to delete this room");
        }

        roomRepository.deleteById(roomId);
    }
}
