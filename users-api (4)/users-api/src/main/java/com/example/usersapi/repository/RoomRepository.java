package com.example.usersapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usersapi.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    
    List<Room> findByHotelId(UUID hotelId);
    
    List<Room> findByHotelIdAndIsAvailableTrue(UUID hotelId);
    
    @Query(value = "SELECT r FROM Room r WHERE r.hotelId = :hotelId AND r.isAvailable = true " +
            "AND r.capacity >= :capacity AND r.basePrice <= :maxPrice " +
            "AND r.id NOT IN (SELECT b.roomId FROM Booking b WHERE " +
            "(b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate) " +
            "AND b.status != 'CANCELLED')")
    List<Room> findAvailableRooms(@Param("hotelId") UUID hotelId,
                                   @Param("checkInDate") LocalDate checkInDate,
                                   @Param("checkOutDate") LocalDate checkOutDate,
                                   @Param("capacity") Integer capacity,
                                   @Param("maxPrice") java.math.BigDecimal maxPrice);
}
