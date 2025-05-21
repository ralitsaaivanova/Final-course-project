package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
    @Query("SELECT  a from AGENT a WHERE a.isActive=true ")
    Page<AgentEntity> findAllActive(Pageable pageable);

    @Query("SELECT a from AGENT a WHERE a.id = :id AND a.isActive=true")
    Optional<AgentEntity> findByIdAndIsActive(@Param("id")Long id);
}
