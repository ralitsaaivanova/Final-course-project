package org.example.tltravel.repositories;

import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    @Query("SELECT r from ROLE r WHERE r.name = :name ")
    Optional<RoleEntity> findByName(@Param("name")String name);
    List<RoleEntity> findAllByNameIn(Collection<String> roleNames);
}
