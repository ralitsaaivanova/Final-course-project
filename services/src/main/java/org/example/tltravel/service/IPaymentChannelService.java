package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.PaymentChannelInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.PaymentChannelOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPaymentChannelService {
    Optional<PaymentChannelOutView> getById(Long id) throws TLEntityNotFound;
    Page<PaymentChannelOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    PaymentChannelOutView addOne(PaymentChannelInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    PaymentChannelOutView update(Long id,PaymentChannelInView hotel) throws TLEntityNotFound;
    //Page<HotelOutView> getAllHotels(Long id, Pageable pageable) throws TLEntityNotFound;
}
