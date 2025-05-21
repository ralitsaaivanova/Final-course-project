package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.ReservationPaymentInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.ReservationPaymentOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IReservationPaymentService {


    Optional<ReservationPaymentOutView> getById(Long id) throws TLEntityNotFound;
    Page<ReservationPaymentOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    ReservationPaymentOutView addOne(ReservationPaymentInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    ReservationPaymentOutView update(Long id, ReservationPaymentInView hotel) throws TLEntityNotFound;
}
