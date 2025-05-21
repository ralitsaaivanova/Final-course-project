package org.example.tltravel.service;

import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface IHotelService {

    Optional<HotelOutView> getById(Long id) throws TLEntityNotFound;
    Page<HotelOutView> getAll(Pageable pageable) throws TLEntityNotFound;
    HotelOutView addOne(HotelInView hotel);
    void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive;
    HotelOutView update(Long id,HotelInView hotel) throws TLEntityNotFound;

    List<ExtrasOutView> getAllExtras(Long id) throws TLEntityNotFound;
    Page<HotelOutView> getAllByExtras(List<Long> id,Pageable pageable) throws TLEntityNotFound;

    void addExtraToHotel(Long hotelId, Long extrasId) throws TLEntityNotFound, TLEntityNotActive;
    void removeExtraToHotel(Long hotelId, Long extrasId) throws TLEntityNotFound, TLEntityNotActive;

    List<HotelPhotoOutView> getAllPhotos(Long id) throws TLEntityNotFound;
    Page<HotelRoomOutView> getAllHotelRooms(Long id, Pageable pageable) throws TLEntityNotFound;
    Page<ReservationOutView> getAllReservations(Long id, Pageable pageable) throws TLEntityNotFound;

    List<FeedingTypeOutView> getAllFeedingTypes(Long id) throws TLEntityNotFound;
    Page<HotelOutView> getAllByFeedingType(List<Long> id,Pageable pageable) throws TLEntityNotFound;

    void addFeedingTypeToHotel(Long hotelId, Long feedingTypeId) throws TLEntityNotFound, TLEntityNotActive;

    void removeFeedingTypeFromHotel(Long hotelId, Long feedingTypeId) throws TLEntityNotFound, TLEntityNotActive;


}
