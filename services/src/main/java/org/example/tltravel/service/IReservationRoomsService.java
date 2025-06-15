package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.ReservationRoomInView;
import org.example.tltravel.view.out.ReservationRoomOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IReservationRoomsService {
    Optional<ReservationRoomOutView> getById(Long id) throws TLEntityNotFound;
    Page<ReservationRoomOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    ReservationRoomOutView addOne(ReservationRoomInView reservationRoomInView);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    ReservationRoomOutView update(Long id, ReservationRoomInView reservationRoomInView) throws TLEntityNotFound;
}
