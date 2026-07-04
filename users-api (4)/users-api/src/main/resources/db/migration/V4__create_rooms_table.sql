-- V4__create_rooms_table.sql
-- Create rooms table for hotel booking platform

CREATE TABLE rooms (
    id VARCHAR(36) PRIMARY KEY,
    hotel_id VARCHAR(36) NOT NULL,
    room_number VARCHAR(10) NOT NULL,
    room_type VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT true,
    description TEXT,
    amenities VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_hotel_id (hotel_id),
    INDEX idx_room_type (room_type),
    INDEX idx_availability (is_available),
    UNIQUE KEY uq_hotel_room_number (hotel_id, room_number),
    
    CONSTRAINT fk_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);
