package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.OperatorInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.OperatorOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IOperatorService {
    Optional<OperatorOutView> getById(Long id) throws TLEntityNotFound;
    Page<OperatorOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    OperatorOutView addOne(OperatorInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    OperatorOutView update(Long id,OperatorInView hotel) throws TLEntityNotFound;
    Page<ReservationOutView> getAllReservations(Long id, Pageable pageable) throws TLEntityNotFound;
}
