package com.example.usersapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.usersapi.model.Room;

public class RoomResponse {

    private UUID id;
    private UUID hotelId;
    private String roomNumber;
    private String roomType;
    private Integer capacity;
    private BigDecimal basePrice;
    private Boolean isAvailable;
    private String description;
    private String amenities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomResponse() {
    }

    public static RoomResponse fromEntity(Room room) {
        RoomResponse response = new RoomResponse();
        response.id = room.getId();
        response.hotelId = room.getHotelId();
        response.roomNumber = room.getRoomNumber();
        response.roomType = room.getRoomType().name();
        response.capacity = room.getCapacity();
        response.basePrice = room.getBasePrice();
        response.isAvailable = room.getIsAvailable();
        response.description = room.getDescription();
        response.amenities = room.getAmenities();
        response.createdAt = room.getCreatedAt();
        response.updatedAt = room.getUpdatedAt();
        return response;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
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
