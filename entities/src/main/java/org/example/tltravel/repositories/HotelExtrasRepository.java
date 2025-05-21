package org.example.tltravel.repositories;

import org.example.tltravel.compositekeys.HotelExtrasId;
import org.example.tltravel.entities.HotelExtrasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelExtrasRepository extends JpaRepository<HotelExtrasEntity, HotelExtrasId> {
}
