package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.ReservationRoomsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRoomsRepository extends JpaRepository<ReservationRoomsEntity,Long> {
    @Query("SELECT  r from RESERVATIONROOMS r "+
            "WHERE  r.reservation_id = :reservationId"
    )
    Page<ReservationRoomsEntity> findAllByReservationIdActive(@Param("reservationId") Long reservationId, Pageable pageable);


}
