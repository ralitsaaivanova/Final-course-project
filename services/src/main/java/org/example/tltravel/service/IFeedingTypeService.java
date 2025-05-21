package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.FeedingTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.FeedingTypeOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFeedingTypeService {

    Optional<FeedingTypeOutView> getById(Long id) throws TLEntityNotFound;
    Page<FeedingTypeOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    FeedingTypeOutView addOne(FeedingTypeInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    FeedingTypeOutView update(Long id, FeedingTypeInView hotel) throws TLEntityNotFound;
}
