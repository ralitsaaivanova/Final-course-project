package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.HotelRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity,Long> {
    @Query("SELECT h from HOTELROOM h WHERE h.id = :id AND h.isActive=true")
    Optional<HotelRoomEntity> findByIdAndIsActive(@Param("id")Long id);

    @Query("SELECT  h from HOTELROOM h WHERE h.isActive=true and h.hotel_id=:hotelId ")
    Page<HotelRoomEntity> findAllRoomsByHotelIdActive(@Param("hotelId") Long hotelId,Pageable pageable);

    @Query("SELECT  a from HOTELROOM a WHERE a.isActive=true ")
    Page<HotelRoomEntity> findAllActive(Pageable pageable);


}
