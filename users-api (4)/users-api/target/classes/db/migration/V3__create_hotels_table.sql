-- V3__create_hotels_table.sql
-- Create hotels table for hotel booking platform

CREATE TABLE hotels (
    id VARCHAR(36) PRIMARY KEY,
    owner_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    description TEXT,
    email VARCHAR(100),
    phone VARCHAR(20),
    star_rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_owner_id (owner_id),
    INDEX idx_city (city),
    INDEX idx_country (country),
    
    CONSTRAINT fk_hotel_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);
