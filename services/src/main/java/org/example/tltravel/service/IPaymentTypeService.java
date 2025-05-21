package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.PaymentTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.PaymentTypeOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPaymentTypeService {

    Optional<PaymentTypeOutView> getById(Long id) throws TLEntityNotFound;
    Page<PaymentTypeOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    PaymentTypeOutView addOne(PaymentTypeInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    PaymentTypeOutView update(Long id, PaymentTypeInView hotel) throws TLEntityNotFound;
}
