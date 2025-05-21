package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.OperatorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity,Long> {
    @Query("SELECT  o from OPERATOR o WHERE o.isActive=true ")
    Page<OperatorEntity> findAllActive(Pageable pageable);

    @Query("SELECT o from OPERATOR o WHERE o.id = :id AND o.isActive=true")
    Optional<OperatorEntity> findByIdAndIsActive(@Param("id")Long id);
}
