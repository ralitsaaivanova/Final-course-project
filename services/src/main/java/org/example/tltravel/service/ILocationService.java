package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.LocationInView;

import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.LocationOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ILocationService {

    Optional<LocationOutView> getById(Long id) throws TLEntityNotFound;
    Page<LocationOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    LocationOutView addOne(LocationInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    LocationOutView update(Long id,LocationInView hotel) throws TLEntityNotFound;
    Page<HotelOutView> getAllHotels(Long id, Pageable pageable) throws TLEntityNotFound;
}
