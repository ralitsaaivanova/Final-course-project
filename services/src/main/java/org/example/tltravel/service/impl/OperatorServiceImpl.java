package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.OperatorEntity;
import org.example.tltravel.entities.ReservationEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.OperatorRepository;
import org.example.tltravel.repositories.ReservationRepository;
import org.example.tltravel.service.IOperatorService;
import org.example.tltravel.view.in.OperatorInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.OperatorOutView;
import org.example.tltravel.view.out.ReservationOutView;
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
public class OperatorServiceImpl implements IOperatorService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Optional<OperatorOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<OperatorEntity> entity = operatorRepository.findByIdAndIsActive(id);

            Optional<OperatorOutView> res = modelMapper.map(entity,new TypeToken<Optional<OperatorOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<OperatorOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<OperatorEntity> entities = operatorRepository.findAllActive(pageable);
            List<OperatorOutView> operatorOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<AgentOutView>>(){}.getType());

            Page<OperatorOutView> res = new PageImpl<>(operatorOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public OperatorOutView addOne(OperatorInView operator) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: Body: {}", logId,operator);
        try{
            OperatorEntity entity = modelMapper.map(operator,new TypeToken<OperatorEntity>(){}.getType());
            entity.setActive(true);
            entity = operatorRepository.save(entity);
            OperatorOutView res = modelMapper.map(entity,new TypeToken<OperatorOutView>(){}.getType());
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

            OperatorEntity operatorEntity = operatorRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!operatorEntity.getActive()){
                throw new TLEntityNotActive(operatorEntity.toString());
            }
            operatorEntity.setActive(false);
            operatorRepository.save(operatorEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public OperatorOutView update(Long id, OperatorInView operator) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,operator);

        try{
            OperatorEntity entity = operatorRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            OperatorEntity toSave = modelMapper.map(operator,new TypeToken<OperatorEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            operatorRepository.save(toSave);

            OperatorOutView res= modelMapper.map(entity,new TypeToken<OperatorOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }

    @Override
    public Page<ReservationOutView> getAllReservations(Long id, Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllReservations start", logId);
        log.debug("{} : getAllReservations start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);

        try{
            OperatorEntity entity = operatorRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<ReservationEntity> entities = reservationRepository.findAllByOperatorIdActive(id,pageable);
            List<ReservationOutView> reservationOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<ReservationOutView>>(){}.getType());

            Page<ReservationOutView> page = new PageImpl<>(reservationOutViews,pageable,entities.getTotalElements());
            return page;

        }catch (Exception e){
            log.error("{}: getAllReservations error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllReservations finished", logId);
        }
    }
}
