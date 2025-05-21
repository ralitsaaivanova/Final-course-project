package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.PaymentChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannelEntity,Long> {
    @Query("SELECT  pc from PAYMENTCHANNEL pc WHERE pc.isActive=true ")
    Page<PaymentChannelEntity> findAllActive(Pageable pageable);

    @Query("SELECT pc from PAYMENTCHANNEL pc WHERE pc.id = :id AND pc.isActive=true")
    Optional<PaymentChannelEntity> findByIdAndIsActive(@Param("id")Long id);
}
