package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.LocationInView;
import org.example.tltravel.view.in.ReservationInView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.LocationOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.example.tltravel.view.out.ReservationRoomOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IReservationService {
    Optional<ReservationOutView> getById(Long id) throws TLEntityNotFound;
    Page<ReservationOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    ReservationOutView addOne(ReservationInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    ReservationOutView update(Long id, ReservationInView hotel) throws TLEntityNotFound;

    Page<ReservationRoomOutView> getAllReservationRooms(Long id, Pageable pageable) throws TLEntityNotFound;
}
