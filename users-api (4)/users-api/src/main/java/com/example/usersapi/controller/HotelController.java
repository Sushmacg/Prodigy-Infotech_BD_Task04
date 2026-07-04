package com.example.usersapi.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.usersapi.dto.HotelCreateRequest;
import com.example.usersapi.dto.HotelResponse;
import com.example.usersapi.model.Hotel;
import com.example.usersapi.model.User;
import com.example.usersapi.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(
            @Valid @RequestBody HotelCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        Hotel hotel = hotelService.createHotel(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(HotelResponse.fromEntity(hotel));
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<Hotel> hotels = hotelService.findAll();
        return ResponseEntity.ok(hotels.stream()
                .map(HotelResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable UUID id) {
        Hotel hotel = hotelService.findById(id);
        return ResponseEntity.ok(HotelResponse.fromEntity(hotel));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCity(@PathVariable String city) {
        List<Hotel> hotels = hotelService.findByCity(city);
        return ResponseEntity.ok(hotels.stream()
                .map(HotelResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCountry(@PathVariable String country) {
        List<Hotel> hotels = hotelService.findByCountry(country);
        return ResponseEntity.ok(hotels.stream()
                .map(HotelResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<HotelResponse>> getHotelsByOwner(@PathVariable UUID ownerId) {
        List<Hotel> hotels = hotelService.findByOwnerId(ownerId);
        return ResponseEntity.ok(hotels.stream()
                .map(HotelResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my-hotels")
    public ResponseEntity<List<HotelResponse>> getMyHotels(
            @AuthenticationPrincipal User currentUser) {
        List<Hotel> hotels = hotelService.findByOwnerId(currentUser.getId());
        return ResponseEntity.ok(hotels.stream()
                .map(HotelResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(
            @PathVariable UUID id,
            @Valid @RequestBody HotelCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        Hotel hotel = hotelService.updateHotel(id, request, currentUser);
        return ResponseEntity.ok(HotelResponse.fromEntity(hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {
        hotelService.deleteHotel(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
