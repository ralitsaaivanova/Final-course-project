package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.ReservationPaymentEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.ReservationPaymentRepository;
import org.example.tltravel.service.IReservationPaymentService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.ReservationPaymentInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.ReservationPaymentOutView;
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
public class ReservationPaymentServiceImpl implements IReservationPaymentService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservationPaymentRepository reservationPaymentRepository;


    @Override
    public Optional<ReservationPaymentOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<ReservationPaymentEntity> entity = reservationPaymentRepository.findByIdAndIsActive(id);
            Optional<ReservationPaymentOutView> res = modelMapper.map(entity,new TypeToken<Optional<ReservationPaymentOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<ReservationPaymentOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<ReservationPaymentEntity> entities = reservationPaymentRepository.findAllActive(pageable);
            List<ReservationPaymentOutView> reservationPaymentOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<ReservationPaymentOutView>>(){}.getType());

            Page<ReservationPaymentOutView> res = new PageImpl<>(reservationPaymentOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }


    @Override
    public ReservationPaymentOutView addOne(ReservationPaymentInView reservationPayment) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,reservationPayment);
        try{
            ReservationPaymentEntity entity = modelMapper.map(reservationPayment,new TypeToken<ReservationPaymentEntity>(){}.getType());
            entity.setActive(true);
            entity = reservationPaymentRepository.save(entity);
            ReservationPaymentOutView res = modelMapper.map(entity,new TypeToken<ReservationPaymentOutView>(){}.getType());
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

            ReservationPaymentEntity reservationPaymentEntity = reservationPaymentRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!reservationPaymentEntity.getActive()){
                throw new TLEntityNotActive(reservationPaymentEntity.toString());
            }
            reservationPaymentEntity.setActive(false);
            reservationPaymentRepository.save(reservationPaymentEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public ReservationPaymentOutView update(Long id, ReservationPaymentInView reservationPayment) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,reservationPayment);

        try{
            ReservationPaymentEntity entity = reservationPaymentRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ReservationPaymentEntity toSave = modelMapper.map(reservationPayment,new TypeToken<ReservationPaymentEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            reservationPaymentRepository.save(toSave);

            ReservationPaymentOutView res= modelMapper.map(entity,new TypeToken<ReservationPaymentOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
