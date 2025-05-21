package org.example.tltravel.repositories;

import org.example.tltravel.entities.PaymentChannelEntity;
import org.example.tltravel.entities.ReservationPaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationPaymentRepository extends JpaRepository<ReservationPaymentEntity,Long> {
    @Query("SELECT  rp from RESERVATIONPAYMENT rp WHERE rp.isActive=true ")
    Page<ReservationPaymentEntity> findAllActive(Pageable pageable);

    @Query("SELECT rp from RESERVATIONPAYMENT rp WHERE rp.id = :id AND rp.isActive=true")
    Optional<ReservationPaymentEntity> findByIdAndIsActive(@Param("id")Long id);
}
