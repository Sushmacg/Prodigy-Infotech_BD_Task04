package com.example.usersapi.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usersapi.dto.RoomCreateRequest;
import com.example.usersapi.dto.RoomResponse;
import com.example.usersapi.model.Room;
import com.example.usersapi.model.User;
import com.example.usersapi.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody RoomCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        Room room = roomService.createRoom(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomResponse.fromEntity(room));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable UUID id) {
        Room room = roomService.findById(id);
        return ResponseEntity.ok(RoomResponse.fromEntity(room));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotel(@PathVariable UUID hotelId) {
        List<Room> rooms = roomService.findByHotelId(hotelId);
        return ResponseEntity.ok(rooms.stream()
                .map(RoomResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomResponse>> searchAvailableRooms(
            @RequestParam UUID hotelId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<Room> rooms = roomService.findAvailableRooms(hotelId, checkInDate, checkOutDate, capacity, maxPrice);
        return ResponseEntity.ok(rooms.stream()
                .map(RoomResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable UUID id,
            @Valid @RequestBody RoomCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        Room room = roomService.updateRoom(id, request, currentUser);
        return ResponseEntity.ok(RoomResponse.fromEntity(room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {
        roomService.deleteRoom(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
