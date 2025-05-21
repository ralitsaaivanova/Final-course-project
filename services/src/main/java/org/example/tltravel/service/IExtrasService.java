package org.example.tltravel.service;


import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.ExtrasInView;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.ExtrasOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IExtrasService {

    Optional<ExtrasOutView> getById(Long id) throws TLEntityNotFound;
    Page<ExtrasOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    ExtrasOutView addOne(ExtrasInView extras);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    ExtrasOutView update(Long id, ExtrasInView hotel) throws TLEntityNotFound;
}
