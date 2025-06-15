package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.ReservationEntity;
import org.example.tltravel.entities.ReservationRoomsEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.HotelRoomRepository;
import org.example.tltravel.repositories.ReservationRepository;
import org.example.tltravel.repositories.ReservationRoomsRepository;
import org.example.tltravel.service.IReservationService;
import org.example.tltravel.view.in.ReservationInView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.example.tltravel.view.out.ReservationRoomOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements IReservationService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationRoomsRepository reservationRoomsRepository;

    @Override
    public Optional<ReservationOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<ReservationEntity> entity = reservationRepository.findByIdAndIsActive(id);
            Optional<ReservationOutView> res = modelMapper.map(entity,new TypeToken<Optional<ReservationOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<ReservationOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try {
            Page<ReservationEntity> entities = reservationRepository.findAllActive(pageable);

            List<ReservationOutView> reservationOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<ReservationOutView>>(){}.getType());

            Page<ReservationOutView> res = new PageImpl<>(reservationOutViews,pageable,entities.getTotalElements());
            return res;
        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }

    }

    @Override
    public ReservationOutView addOne(ReservationInView reservation) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: Body: {}", logId,reservation);
        try{
            ReservationEntity entity = modelMapper.map(reservation,new TypeToken<ReservationEntity>(){}.getType());
            entity.setActive(true);
            entity = reservationRepository.save(entity);
            ReservationOutView res = modelMapper.map(entity,new TypeToken<ReservationOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: addOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: addOne finished", logId);
        }
    }

    @Override
    public void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteOne start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            ReservationEntity reservationEntity = reservationRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!reservationEntity.getActive()){
                throw new TLEntityNotActive(reservationEntity.toString());
            }
            reservationEntity.setActive(false);
            reservationRepository.save(reservationEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public ReservationOutView update(Long id, ReservationInView reservation) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,reservation);

        try{
            ReservationEntity entity = reservationRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ReservationEntity toSave = modelMapper.map(reservation,new TypeToken<ReservationEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            reservationRepository.save(toSave);

            ReservationOutView res= modelMapper.map(toSave,new TypeToken<ReservationOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }

    @Override
    public Page<ReservationRoomOutView> getAllReservationRooms(Long id, Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllHotels start", logId);
        log.debug("{} : getAllHotels start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);
        try {
            ReservationEntity entity = reservationRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<ReservationRoomsEntity> entities = reservationRoomsRepository.findAllByReservationIdActive(id,pageable);
            List<ReservationRoomOutView> reservationRoomOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<ReservationRoomOutView>>(){}.getType());
            Page<ReservationRoomOutView> page = new PageImpl<>(reservationRoomOutViews,pageable,entities.getTotalElements());
            return page;
        }catch (Exception e){
            log.error("{}: getAllHotels error", logId,e);
            throw e;
       }finally {
            log.info("{}: getAllHotels finished", logId);
       }
    }

}
