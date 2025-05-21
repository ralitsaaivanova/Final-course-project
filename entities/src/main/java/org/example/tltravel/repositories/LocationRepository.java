package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Query("SELECT  l from LOCATION l WHERE l.isActive=true ")
    Page<LocationEntity> findAllActive(Pageable pageable);





}
