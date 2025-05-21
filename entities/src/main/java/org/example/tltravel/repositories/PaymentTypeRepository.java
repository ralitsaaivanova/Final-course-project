package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.PaymentTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity,Long> {
    @Query("SELECT  a from PAYMENTTYPE a WHERE a.isActive=true ")
    Page<PaymentTypeEntity> findAllActive(Pageable pageable);

    @Query("SELECT a from PAYMENTTYPE a WHERE a.id = :id AND a.isActive=true")
    Optional<PaymentTypeEntity> findByIdAndIsActive(@Param("id")Long id);
}
