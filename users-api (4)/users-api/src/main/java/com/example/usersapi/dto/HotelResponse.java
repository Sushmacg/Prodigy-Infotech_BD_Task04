package com.example.usersapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.usersapi.model.Hotel;

public class HotelResponse {

    private UUID id;
    private UUID ownerId;
    private String name;
    private String city;
    private String country;
    private String address;
    private String description;
    private String email;
    private String phone;
    private Float starRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HotelResponse() {
    }

    public static HotelResponse fromEntity(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.id = hotel.getId();
        response.ownerId = hotel.getOwnerId();
        response.name = hotel.getName();
        response.city = hotel.getCity();
        response.country = hotel.getCountry();
        response.address = hotel.getAddress();
        response.description = hotel.getDescription();
        response.email = hotel.getEmail();
        response.phone = hotel.getPhone();
        response.starRating = hotel.getStarRating();
        response.createdAt = hotel.getCreatedAt();
        response.updatedAt = hotel.getUpdatedAt();
        return response;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getStarRating() {
        return starRating;
    }

    public void setStarRating(Float starRating) {
        this.starRating = starRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
