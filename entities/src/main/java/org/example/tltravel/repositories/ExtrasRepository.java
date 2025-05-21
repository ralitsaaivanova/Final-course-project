package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.ExtrasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtrasRepository extends JpaRepository<ExtrasEntity,Long> {
    @Query("SELECT  e from EXTRAS e WHERE e.isActive=true ")
    Page<ExtrasEntity> findAllActive(Pageable pageable);

    @Query("SELECT e from EXTRAS e WHERE e.id = :id AND e.isActive=true")
    Optional<ExtrasEntity> findByIdAndIsActive(@Param("id")Long id);
}
