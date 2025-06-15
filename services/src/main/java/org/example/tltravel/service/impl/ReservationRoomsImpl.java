package org.example.tltravel.service.impl;

import org.example.tltravel.entities.ReservationRoomsEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.ReservationRoomsRepository;
import org.example.tltravel.service.IReservationRoomsService;
import org.example.tltravel.view.in.ReservationRoomInView;
import org.example.tltravel.view.out.ReservationPaymentOutView;
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
public class ReservationRoomsImpl implements IReservationRoomsService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservationRoomsRepository reservationRoomsRepository;


    @Override
    public Optional<ReservationRoomOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<ReservationRoomsEntity> entity = reservationRoomsRepository.findByIdAndIsActive(id);
            Optional<ReservationRoomOutView> res = modelMapper.map(entity,new TypeToken<Optional<ReservationPaymentOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<ReservationRoomOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<ReservationRoomsEntity> entities = reservationRoomsRepository.findAllActive(pageable);
            List<ReservationRoomOutView> reservationRoomsOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<ReservationRoomOutView>>(){}.getType());

            Page<ReservationRoomOutView> res = new PageImpl<>(reservationRoomsOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }


    @Override
    public ReservationRoomOutView addOne(ReservationRoomInView reservationRoomInView) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,reservationRoomInView);
        try{
            ReservationRoomsEntity entity = modelMapper.map(reservationRoomInView,new TypeToken<ReservationRoomsEntity>(){}.getType());
            entity.setActive(true);
            entity = reservationRoomsRepository.save(entity);
            ReservationRoomOutView res = modelMapper.map(entity,new TypeToken<ReservationRoomOutView>(){}.getType());
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

            ReservationRoomsEntity reservationRoomsEntity = reservationRoomsRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!reservationRoomsEntity.getActive()){
                throw new TLEntityNotActive(reservationRoomsEntity.toString());
            }
            reservationRoomsEntity.setActive(false);
            reservationRoomsRepository.save(reservationRoomsEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public ReservationRoomOutView update(Long id, ReservationRoomInView reservationRoomInView) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,reservationRoomInView);

        try{
            ReservationRoomsEntity entity = reservationRoomsRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ReservationRoomsEntity toSave = modelMapper.map(reservationRoomInView,new TypeToken<ReservationRoomsEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            reservationRoomsRepository.save(toSave);

            ReservationRoomOutView res= modelMapper.map(toSave,new TypeToken<ReservationRoomOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
