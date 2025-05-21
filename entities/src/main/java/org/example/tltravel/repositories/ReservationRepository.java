package org.example.tltravel.repositories;

import org.example.tltravel.entities.ExtrasEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {

    @Query("SELECT  r from RESERVATION r WHERE r.isActive=true ")
    Page<ReservationEntity> findAllActive(Pageable pageable);
    @Query("SELECT r from RESERVATION r WHERE r.id = :id AND r.isActive=true")
    Optional<ReservationEntity> findByIdAndIsActive(@Param("id")Long id);

    @Query("SELECT  h from RESERVATION h WHERE h.isActive=true and h.operator_id=:operatorId ")
    Page<ReservationEntity> findAllByOperatorIdActive(@Param("operatorId") Long operatorId,Pageable pageable);

    @Query("SELECT  r from RESERVATION r WHERE r.isActive=true and r.client_id=:clientId ")
    Page<ReservationEntity> findAllByClientIdActive(@Param("clientId") Long clientId,Pageable pageable);

    @Query("SELECT  r from RESERVATION r " +
            "JOIN HOTEL h ON h.id = r.hotel_id " +
            "WHERE r.isActive=true AND r.hotel_id=:hotelId ")
    Page<ReservationEntity> findAllByHotelIdActive(@Param("hotelId") Long hotelId,Pageable pageable);



}
