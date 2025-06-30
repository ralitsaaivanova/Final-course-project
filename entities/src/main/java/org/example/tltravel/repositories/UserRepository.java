package org.example.tltravel.repositories;

import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT  u from USER u WHERE u.email = :email ")
    Optional<UserEntity> findByEmail(@Param("email")String email);

    @Query("SELECT  u from USER u WHERE u.isActive=true ")
    Page<UserEntity> findAllActive(Pageable pageable);

//    long countByRoles_Name(String roleName);
}
