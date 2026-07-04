package com.example.usersapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usersapi.model.Booking;
import com.example.usersapi.model.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
    List<Booking> findByUserId(UUID userId);
    
    List<Booking> findByHotelId(UUID hotelId);
    
    List<Booking> findByRoomId(UUID roomId);
    
    List<Booking> findByRoomIdAndStatusNot(UUID roomId, BookingStatus status);
    
    List<Booking> findByUserIdAndStatus(UUID userId, BookingStatus status);
    
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND b.status != 'CANCELLED' " +
           "AND ((b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate))")
    List<Booking> findByRoomIdAndCheckInCheckOutConflict(
            @Param("roomId") UUID roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);
}
