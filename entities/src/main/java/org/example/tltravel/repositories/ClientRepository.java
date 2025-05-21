package org.example.tltravel.repositories;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("SELECT  c from CLIENT c WHERE c.isActive=true ")
    Page<ClientEntity> findAllActive(Pageable pageable);

    @Query("SELECT c from CLIENT c WHERE c.id = :id AND c.isActive=true")
    Optional<ClientEntity> findByIdAndIsActive(@Param("id")Long id);
}
