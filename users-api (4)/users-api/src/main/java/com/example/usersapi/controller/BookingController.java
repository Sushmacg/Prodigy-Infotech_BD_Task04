package com.example.usersapi.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usersapi.dto.BookingCreateRequest;
import com.example.usersapi.dto.BookingResponse;
import com.example.usersapi.model.Booking;
import com.example.usersapi.model.User;
import com.example.usersapi.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        Booking booking = bookingService.createBooking(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(BookingResponse.fromEntity(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable UUID id) {
        Booking booking = bookingService.findById(id);
        return ResponseEntity.ok(BookingResponse.fromEntity(booking));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable UUID userId) {
        List<Booking> bookings = bookingService.findByUserId(userId);
        return ResponseEntity.ok(bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @AuthenticationPrincipal User currentUser) {
        List<Booking> bookings = bookingService.findByUserId(currentUser.getId());
        return ResponseEntity.ok(bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByHotel(@PathVariable UUID hotelId) {
        List<Booking> bookings = bookingService.findByHotelId(hotelId);
        return ResponseEntity.ok(bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByRoom(@PathVariable UUID roomId) {
        List<Booking> bookings = bookingService.findByRoomId(roomId);
        return ResponseEntity.ok(bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {
        Booking booking = bookingService.cancelBooking(id, currentUser);
        return ResponseEntity.ok(BookingResponse.fromEntity(booking));
    }

    @GetMapping("/availability/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam UUID roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {
        boolean available = bookingService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        return ResponseEntity.ok(available);
    }
}
