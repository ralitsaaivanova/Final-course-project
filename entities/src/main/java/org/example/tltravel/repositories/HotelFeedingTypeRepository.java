package org.example.tltravel.repositories;


import org.example.tltravel.compositekeys.HotelFeedingTypeId;
import org.example.tltravel.entities.HotelFeedingTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelFeedingTypeRepository extends JpaRepository<HotelFeedingTypeEntity, HotelFeedingTypeId> {
}
