package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.HotelRoomInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelRoomOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHotelRoomService {

    Optional<HotelRoomOutView> getById(Long id) throws TLEntityNotFound;
    Page<HotelRoomOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    HotelRoomOutView addOne(HotelRoomInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    HotelRoomOutView update(Long id, HotelRoomInView hotel) throws TLEntityNotFound;
}
