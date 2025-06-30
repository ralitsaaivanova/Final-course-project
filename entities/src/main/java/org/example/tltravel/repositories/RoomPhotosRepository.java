package org.example.tltravel.repositories;

import org.example.tltravel.entities.HotelPhotosEntity;
import org.example.tltravel.entities.RoomPhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomPhotosRepository extends JpaRepository<RoomPhotosEntity,Long> {

    @Query("SELECT p FROM ROOMPHOTOS p WHERE p.isActive=true and p.hotelRoomId=:id")
    List<RoomPhotosEntity> findAllByHotelAndIsActive(@Param("id")Long id);


    @Query("SELECT p FROM ROOMPHOTOS  p WHERE p.isActive=true and p.id=:id")
    Optional<RoomPhotosEntity> findByIdAndIsActive(@Param("id") Long id);

    @Query("UPDATE ROOMPHOTOS  p set p.isActive = false where p.id=:id")
    @Modifying
    Integer updatePhotoToUnActive(@Param("id") Long id);
}
