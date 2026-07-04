package com.example.usersapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usersapi.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    List<Hotel> findByOwnerId(UUID ownerId);
    List<Hotel> findByCity(String city);
    List<Hotel> findByCountry(String country);
}
