package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IAgentService {

    Optional<AgentOutView> getById(Long id) throws TLEntityNotFound;
    Page<AgentOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    AgentOutView addOne(AgentInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    AgentOutView update(Long id,AgentInView hotel) throws TLEntityNotFound;
    Page<HotelOutView> getAllHotels(Long id,Pageable pageable) throws TLEntityNotFound;

}
