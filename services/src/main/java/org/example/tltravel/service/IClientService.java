package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.ClientInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.ClientOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IClientService {
    Optional<ClientOutView> getById(Long id) throws TLEntityNotFound;
    Page<ClientOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    ClientOutView addOne(ClientInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    ClientOutView update(Long id,ClientInView hotel) throws TLEntityNotFound;
    Page<ReservationOutView> getAllReservations(Long id, Pageable pageable) throws TLEntityNotFound;
}
