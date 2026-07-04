package com.example.usersapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usersapi.dto.BookingCreateRequest;
import com.example.usersapi.exception.BookingNotFoundException;
import com.example.usersapi.exception.RoomNotAvailableException;
import com.example.usersapi.exception.RoomNotFoundException;
import com.example.usersapi.exception.UnauthorizedAccessException;
import com.example.usersapi.model.Booking;
import com.example.usersapi.model.BookingStatus;
import com.example.usersapi.model.Room;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.BookingRepository;
import com.example.usersapi.repository.RoomRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Booking createBooking(BookingCreateRequest request, UUID userId) {
        // Verify room exists
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        // Validate dates
        if (request.getCheckOutDate().isBefore(request.getCheckInDate()) || 
            request.getCheckOutDate().isEqual(request.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        if (request.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        // Check if room is available for the requested dates
        List<Booking> conflictingBookings = bookingRepository.findByRoomIdAndCheckInCheckOutConflict(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (!conflictingBookings.isEmpty()) {
            throw new RoomNotAvailableException("Room is not available for the requested dates");
        }

        // Validate guest count
        if (request.getNumberOfGuests() > room.getCapacity()) {
            throw new IllegalArgumentException("Number of guests exceeds room capacity");
        }

        // Calculate total price
        long nights = request.getCheckOutDate().toEpochDay() - request.getCheckInDate().toEpochDay();
        BigDecimal totalPrice = room.getBasePrice().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking(
                UUID.randomUUID(),
                userId,
                request.getRoomId(),
                room.getHotelId(),
                request.getCheckInDate(),
                request.getCheckOutDate(),
                request.getNumberOfGuests(),
                totalPrice
        );
        booking.setSpecialRequests(request.getSpecialRequests());

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking findById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }

    @Transactional(readOnly = true)
    public List<Booking> findByUserId(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Booking> findByHotelId(UUID hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public List<Booking> findByRoomId(UUID roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Transactional
    public Booking cancelBooking(UUID bookingId, User currentUser) {
        Booking booking = findById(bookingId);

        // Check authorization - only user who made booking or ADMIN can cancel
        if (!booking.getUserId().equals(currentUser.getId()) && !currentUser.getRole().toString().equals("ADMIN")) {
            throw new UnauthorizedAccessException("You don't have permission to cancel this booking");
        }

        if (booking.getStatus().equals(BookingStatus.CANCELLED)) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(UUID roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> conflictingBookings = bookingRepository.findByRoomIdAndCheckInCheckOutConflict(
                roomId,
                checkInDate,
                checkOutDate
        );
        return conflictingBookings.isEmpty();
    }

    @Transactional
    public void completeBooking(UUID bookingId) {
        Booking booking = findById(bookingId);
        if (booking.getCheckOutDate().isBefore(LocalDate.now()) && 
            booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            booking.setStatus(BookingStatus.COMPLETED);
            bookingRepository.save(booking);
        }
    }
}
