package org.example.tltravel.repositories;

import org.example.tltravel.entities.HotelPhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelPhotosRepository extends JpaRepository<HotelPhotosEntity,Long> {


    @Query("SELECT p FROM HOTELPHOTOS p WHERE p.isActive=true")
    List<HotelPhotosEntity> findAllActive();

    @Query("SELECT p FROM HOTELPHOTOS p WHERE p.isActive=true and p.hotel_id=:id")
    List<HotelPhotosEntity> findAllByHotelAndIsActive(@Param("id")Long id);

    @Query("SELECT p FROM HOTELPHOTOS  p WHERE p.isActive=true and p.id=:id")
    Optional<HotelPhotosEntity> findByIdAndIsActive(@Param("id") Long id);

    @Query("UPDATE HOTELPHOTOS  p set p.isActive = false where p.id=:id")
    @Modifying
    Integer updatePhotoToUnActive(@Param("id") Long id);
}
