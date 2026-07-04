package com.example.usersapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usersapi.dto.HotelCreateRequest;
import com.example.usersapi.exception.HotelNotFoundException;
import com.example.usersapi.exception.UnauthorizedAccessException;
import com.example.usersapi.model.Hotel;
import com.example.usersapi.model.Role;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.HotelRepository;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public Hotel createHotel(HotelCreateRequest request, UUID ownerId) {
        Hotel hotel = new Hotel(
                UUID.randomUUID(),
                ownerId,
                request.getName(),
                request.getCity(),
                request.getCountry(),
                request.getAddress()
        );
        hotel.setDescription(request.getDescription());
        hotel.setEmail(request.getEmail());
        hotel.setPhone(request.getPhone());
        hotel.setStarRating(request.getStarRating());
        return hotelRepository.save(hotel);
    }

    @Transactional(readOnly = true)
    public Hotel findById(UUID id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hotel> findByCity(String city) {
        return hotelRepository.findByCity(city);
    }

    @Transactional(readOnly = true)
    public List<Hotel> findByCountry(String country) {
        return hotelRepository.findByCountry(country);
    }

    @Transactional(readOnly = true)
    public List<Hotel> findByOwnerId(UUID ownerId) {
        return hotelRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Hotel updateHotel(UUID hotelId, HotelCreateRequest request, User currentUser) {
        Hotel hotel = findById(hotelId);
        
        // Check authorization - only owner or ADMIN can edit
        if (!hotel.getOwnerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedAccessException("You don't have permission to edit this hotel");
        }

        if (request.getName() != null) {
            hotel.setName(request.getName());
        }
        if (request.getCity() != null) {
            hotel.setCity(request.getCity());
        }
        if (request.getCountry() != null) {
            hotel.setCountry(request.getCountry());
        }
        if (request.getAddress() != null) {
            hotel.setAddress(request.getAddress());
        }
        if (request.getDescription() != null) {
            hotel.setDescription(request.getDescription());
        }
        if (request.getEmail() != null) {
            hotel.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            hotel.setPhone(request.getPhone());
        }
        if (request.getStarRating() != null) {
            hotel.setStarRating(request.getStarRating());
        }

        hotel.setUpdatedAt(LocalDateTime.now());
        return hotelRepository.save(hotel);
    }

    @Transactional
    public void deleteHotel(UUID hotelId, User currentUser) {
        Hotel hotel = findById(hotelId);
        
        // Check authorization - only owner or ADMIN can delete
        if (!hotel.getOwnerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedAccessException("You don't have permission to delete this hotel");
        }

        hotelRepository.deleteById(hotelId);
    }
}
