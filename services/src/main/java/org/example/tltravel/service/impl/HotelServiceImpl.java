package org.example.tltravel.service.impl;


import org.example.tltravel.compositekeys.HotelExtrasId;
import org.example.tltravel.compositekeys.HotelFeedingTypeId;
import org.example.tltravel.entities.*;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.*;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
//import java.util.logging.Logger;

@Service
public class HotelServiceImpl implements IHotelService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    //Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelPhotosRepository photosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExtrasRepository extrasRepository;
    @Autowired
    private HotelExtrasRepository hotelExtrasRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private FeedingTypeRepository feedingTypeRepository;
    @Autowired
    private HotelFeedingTypeRepository hotelFeedingTypeRepository;


    @Override
    public Optional<HotelOutView> getById(Long id) throws TLEntityNotFound {

        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<HotelEntity> entity = hotelRepository.findByIdAndIsActive(id);
            /*modelMapper.map(entity.get(), HotelOutView.class);*/

            Optional<HotelOutView> res = modelMapper.map(entity,new TypeToken<Optional<HotelOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }

    }

    @Override
    public Page<HotelOutView> getAll(Pageable pageable) {

        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<HotelEntity> entities = hotelRepository.findAllActive(pageable);

//            for (HotelEntity hotelEntity : entities) {
//                HotelOutView hotelOutView= modelMapper.map(hotelEntity, HotelOutView.class);
//                hotelOutViews.add(hotelOutView);
//            }

            List<HotelOutView> hotelOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<HotelOutView>>(){}.getType());

            Page<HotelOutView> res = new PageImpl<>(hotelOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }

    }

    @Override
    public HotelOutView addOne(HotelInView body) {

        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,body);
        try{
            HotelEntity entity = modelMapper.map(body,new TypeToken<HotelEntity>(){}.getType());
            entity.setActive(true);
            entity = hotelRepository.save(entity);
            HotelOutView res = modelMapper.map(entity,new TypeToken<HotelOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: getOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: getOne finished", logId);
        }
    }

    @Override
    public void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteOne start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            //hotelRepository.deleteById(id);

            HotelEntity hotelEntity = hotelRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!hotelEntity.getActive()){
                throw new TLEntityNotActive(hotelEntity.toString());
            }
            hotelEntity.setActive(false);
            hotelRepository.save(hotelEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public HotelOutView update(Long id, HotelInView hotel) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,hotel);

        try{
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            HotelEntity toSave = modelMapper.map(hotel,new TypeToken<HotelEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            hotelRepository.save(toSave);

            HotelOutView res= modelMapper.map(toSave,new TypeToken<HotelOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }

    }

    @Override
    public List<ExtrasOutView> getAllExtras(Long id) throws TLEntityNotFound {

        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllExtras start", logId);
        try{
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            //Всички ли са активни?
            List<ExtrasEntity> entities = hotelRepository.findAllByExtraIdAndActive(id);
            List<ExtrasOutView> res = modelMapper.map(entities,new TypeToken<List<ExtrasOutView>>(){}.getType());


            return res;

        }catch (Exception e){
            log.error("{}: getAllExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllExtras finished", logId);
        }
    }

    @Override
    public Page<HotelOutView> getAllByExtras(List<Long> ids,Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllByExtras start", logId);
        log.debug("{} : params: ids: {}", logId,ids);
        try{
            Page<HotelEntity> entities = hotelRepository.findAllByExtrasIdAndIsActive(ids,ids.size(),pageable);

            List<HotelOutView> hotelOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<HotelOutView>>(){}.getType());

            Page<HotelOutView> res = new PageImpl<>(hotelOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAllByExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllByExtras finished", logId);
        }
    }

    @Override
    public void addExtraToHotel(Long id, Long extraId) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addExtraToHotel start", logId);
        log.debug("{} : params: ids: {}", logId,id);
        log.debug("{} : params: extraIds: {}", logId,extraId);
        try{
            HotelEntity hotel = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ExtrasEntity extras = extrasRepository.findByIdAndIsActive(extraId).orElseThrow(()->new TLEntityNotFound());
            HotelExtrasId hotelExtrasId = new HotelExtrasId(id,extraId);
            Optional<HotelExtrasEntity> hotelExtrasEntity = hotelExtrasRepository.findById(hotelExtrasId);
            if(hotelExtrasEntity.isEmpty()){
                HotelExtrasEntity hotelExtras = new HotelExtrasEntity();
                hotelExtras.setHotel_id(id);
                hotelExtras.setExtras_id(extraId);
                hotelExtras.setActive(true);
                hotelExtrasRepository.save(hotelExtras);
            }else{
                HotelExtrasEntity hotelExtras = hotelExtrasEntity.get();
                hotelExtras.setActive(true);
                hotelExtrasRepository.save(hotelExtras);
            }


        }catch (Exception e){
            log.error("{}: addExtraToHotel error", logId,e);
            throw e;
        }finally {
            log.info("{}: addExtraToHotel finished", logId);
        }
    }

    @Override
    public void removeExtraToHotel(Long id, Long extrasId) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : removeExtraHotel start", logId);
        log.debug("{} : params: ids: {}", logId,id);
        log.debug("{} : params: extraIds: {}", logId,extrasId);
        try{
            HotelEntity hotel = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ExtrasEntity extras = extrasRepository.findByIdAndIsActive(extrasId).orElseThrow(()->new TLEntityNotFound());
            HotelExtrasId hotelExtras = new HotelExtrasId(id,extrasId);
            HotelExtrasEntity hotelExtrasEntity = hotelExtrasRepository.findById(hotelExtras).orElseThrow(()->new TLEntityNotFound());
            if(!hotelExtrasEntity.getActive()){
                throw new TLEntityNotActive();
            }

            hotelExtrasEntity.setActive(false);
            hotelExtrasRepository.save(hotelExtrasEntity);


        }catch (Exception e){
            log.error("{}: removeExtraHotel error", logId,e);
            throw e;
        }finally {
            log.info("{}: removeExtraHotel finished", logId);
        }
    }

    @Override
    public List<HotelPhotoOutView> getAllPhotos(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllPhotos start", logId);
        log.debug("{} : params : id: {}",logId,id);
        try{
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            //Всички ли са активни?
            List<HotelPhotosEntity> entities = photosRepository.findAllByHotelAndIsActive(id);
            List<HotelPhotoOutView> res = modelMapper.map(entities,new TypeToken<List<HotelPhotoOutView>>(){}.getType());


            return res;

        }catch (Exception e){
            log.error("{}: getAllPhotos error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllPhotos finished", logId);
        }
    }

    @Override
    public Page<HotelRoomOutView> getAllHotelRooms(Long id, Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllHotelRooms start", logId);
        log.debug("{} : getAllHotelRooms start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);

        try{
            //проверяваме дали съществува агент
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<HotelRoomEntity> entities = hotelRoomRepository.findAllRoomsByHotelIdActive(id,pageable);
            List<HotelRoomOutView> hotelRoomsOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<HotelRoomOutView>>(){}.getType());

            //филтър за хотелите, които са активни

            Page<HotelRoomOutView> page = new PageImpl<>(hotelRoomsOutViews,pageable,entities.getTotalElements());
            return page;

        }catch (Exception e){
            log.error("{}: getAllHotelRooms error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllHotelRooms finished", logId);
        }
    }

    @Override
    public Page<ReservationOutView> getAllReservations(Long id, Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllReservations start", logId);
        log.debug("{} : getAllReservations start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);

        try{
            //проверяваме дали съществува агент
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<ReservationEntity> entities = reservationRepository.findAllByHotelIdActive(id,pageable);
            List<ReservationOutView> hotelReservationsOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<ReservationOutView>>(){}.getType());

            //филтър за хотелите, които са активни

            Page<ReservationOutView> page = new PageImpl<>(hotelReservationsOutViews,pageable,entities.getTotalElements());
            return page;

        }catch (Exception e){
            log.error("{}: getAllReservations error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllReservations finished", logId);
        }
    }

    @Override
    public List<FeedingTypeOutView> getAllFeedingTypes(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllFeedingTypes start", logId);
        try{
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            //Всички ли са активни?
            List<FeedingTypeEntity> entities = hotelRepository.findAllByHotelIdAndActive(id);
            List<FeedingTypeOutView> res = modelMapper.map(entities,new TypeToken<List<FeedingTypeOutView>>(){}.getType());


            return res;

        }catch (Exception e){
            log.error("{}: getAllExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllExtras finished", logId);
        }
    }

    @Override
    public Page<HotelOutView> getAllByFeedingType(List<Long> ids, Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllByFeedingType start", logId);
        log.debug("{} : params: ids: {}", logId,ids);
        try{
            Page<HotelEntity> entities = hotelRepository.findAllByFeedingTypesIdAndIsActive(ids,ids.size(),pageable);

            List<HotelOutView> hotelOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<HotelOutView>>(){}.getType());

            Page<HotelOutView> res = new PageImpl<>(hotelOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAllByFeedingType error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllByFeedingType finished", logId);
        }
    }

    @Override
    public void addFeedingTypeToHotel(Long hotelId, Long feedingTypeId) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addExtraToHotel start", logId);
        log.debug("{} : params: ids: {}", logId,hotelId);
        log.debug("{} : params: extraIds: {}", logId,feedingTypeId);
        try{
            HotelEntity hotel = hotelRepository.findByIdAndIsActive(hotelId).orElseThrow(()->new TLEntityNotFound());
            FeedingTypeEntity extras = feedingTypeRepository.findByIdAndIsActive(feedingTypeId).orElseThrow(()->new TLEntityNotFound());
            HotelFeedingTypeId hotelFeedingTypeId = new HotelFeedingTypeId(hotelId,feedingTypeId);

            Optional<HotelFeedingTypeEntity> hotelFeedingTypeEntity = hotelFeedingTypeRepository.findById(hotelFeedingTypeId);
            if(hotelFeedingTypeEntity.isEmpty()){
                HotelFeedingTypeEntity hotelFeedingTypes = new HotelFeedingTypeEntity();
                hotelFeedingTypes.setHotel_id(hotelId);
                hotelFeedingTypes.setFeedingType_id(feedingTypeId);
                hotelFeedingTypes.setActive(true);

                hotelFeedingTypeRepository.save(hotelFeedingTypes);
            }else{
                HotelFeedingTypeEntity hotelFeedingType = hotelFeedingTypeEntity.get();
                hotelFeedingType.setActive(true);
                hotelFeedingTypeRepository.save(hotelFeedingType);
            }


        }catch (Exception e){
            log.error("{}: addFeedingTypeToHotel error", logId,e);
            throw e;
        }finally {
            log.info("{}: addFeedingTypeToHotel finished", logId);
        }
    }

    @Override
    public void removeFeedingTypeFromHotel(Long hotelId, Long feedingTypeId) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : removeFeedingTypeHotel start", logId);
        log.debug("{} : params: ids: {}", logId,hotelId);
        log.debug("{} : params: extraIds: {}", logId,feedingTypeId);
        try{
            HotelEntity hotel = hotelRepository.findByIdAndIsActive(hotelId).orElseThrow(()->new TLEntityNotFound());
            FeedingTypeEntity feedingType = feedingTypeRepository.findByIdAndIsActive(feedingTypeId).orElseThrow(()->new TLEntityNotFound());
            HotelFeedingTypeId hotelFeedingType = new HotelFeedingTypeId(hotelId,feedingTypeId);
            HotelFeedingTypeEntity hotelFeedingTypeEntity = hotelFeedingTypeRepository.findById(hotelFeedingType).orElseThrow(()->new TLEntityNotFound());

            if(!hotelFeedingTypeEntity.getActive()){
                throw new TLEntityNotActive();
            }

            hotelFeedingTypeEntity.setActive(false);

            hotelFeedingTypeRepository.save(hotelFeedingTypeEntity);


        }catch (Exception e){
            log.error("{}: removeFeedingTypeFromHotel error", logId,e);
            throw e;
        }finally {
            log.info("{}: removeFeedingTypeFromHotel finished", logId);
        }
    }


}
