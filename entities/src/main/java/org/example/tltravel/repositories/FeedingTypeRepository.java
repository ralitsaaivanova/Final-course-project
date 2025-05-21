package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.FeedingTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedingTypeRepository extends JpaRepository<FeedingTypeEntity,Long> {
    @Query("SELECT  ft from FEEDINGTYPE ft WHERE ft.isActive=true ")
    Page<FeedingTypeEntity> findAllActive(Pageable pageable);

    @Query("SELECT ft from FEEDINGTYPE ft WHERE ft.id = :id AND ft.isActive=true")
    Optional<FeedingTypeEntity> findByIdAndIsActive(@Param("id")Long id);
}
