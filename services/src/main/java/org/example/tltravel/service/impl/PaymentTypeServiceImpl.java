package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.PaymentTypeEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.PaymentTypeRepository;
import org.example.tltravel.service.IPaymentTypeService;
import org.example.tltravel.view.in.PaymentTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.PaymentTypeOutView;
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
public class PaymentTypeServiceImpl implements IPaymentTypeService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentTypeRepository typeRepository;

    @Override
    public Optional<PaymentTypeOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<PaymentTypeEntity> entity = typeRepository.findByIdAndIsActive(id);

            Optional<PaymentTypeOutView> res = modelMapper.map(entity,new TypeToken<Optional<PaymentTypeOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<PaymentTypeOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<PaymentTypeEntity> entities = typeRepository.findAllActive(pageable);
            List<PaymentTypeOutView> paymentTypeOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<PaymentTypeOutView>>(){}.getType());

            Page<PaymentTypeOutView> res = new PageImpl<>(paymentTypeOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }

    }

    @Override
    public PaymentTypeOutView addOne(PaymentTypeInView paymentType) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,paymentType);
        try{
            PaymentTypeEntity entity = modelMapper.map(paymentType,new TypeToken<PaymentTypeEntity>(){}.getType());
            entity.setActive(true);
            entity = typeRepository.save(entity);
            PaymentTypeOutView res = modelMapper.map(entity,new TypeToken<PaymentTypeOutView>(){}.getType());
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

            PaymentTypeEntity agentEntity = typeRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!agentEntity.getActive()){
                throw new TLEntityNotActive(agentEntity.toString());
            }
            agentEntity.setActive(false);
            typeRepository.save(agentEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public PaymentTypeOutView update(Long id, PaymentTypeInView paymentType) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,paymentType);

        try{
            PaymentTypeEntity entity = typeRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            PaymentTypeEntity toSave = modelMapper.map(paymentType,new TypeToken<PaymentTypeEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            typeRepository.save(toSave);

            PaymentTypeOutView res= modelMapper.map(entity,new TypeToken<PaymentTypeOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
