package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.PaymentChannelEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.PaymentChannelRepository;
import org.example.tltravel.service.IPaymentChannelService;
import org.example.tltravel.view.in.PaymentChannelInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.PaymentChannelOutView;
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
public class PaymentChannelServiceImpl implements IPaymentChannelService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentChannelRepository paymentChannelRepository;

    Logger log = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public Optional<PaymentChannelOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<PaymentChannelEntity> entity = paymentChannelRepository.findByIdAndIsActive(id);

            Optional<PaymentChannelOutView> res = modelMapper.map(entity,new TypeToken<Optional<PaymentChannelOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<PaymentChannelOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<PaymentChannelEntity> entities = paymentChannelRepository.findAllActive(pageable);
            List<PaymentChannelOutView> paymentChannelOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<PaymentChannelOutView>>(){}.getType());

            Page<PaymentChannelOutView> res = new PageImpl<>(paymentChannelOutViews,pageable,entities.getTotalElements());

            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public PaymentChannelOutView addOne(PaymentChannelInView paymentChannel) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,paymentChannel);
        try{
            PaymentChannelEntity entity = modelMapper.map(paymentChannel,new TypeToken<PaymentChannelEntity>(){}.getType());
            entity.setActive(true);
            entity = paymentChannelRepository.save(entity);
            PaymentChannelOutView res = modelMapper.map(entity,new TypeToken<PaymentChannelOutView>(){}.getType());
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

            PaymentChannelEntity paymentChannelEntity = paymentChannelRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!paymentChannelEntity.getActive()){
                throw new TLEntityNotActive(paymentChannelEntity.toString());
            }
            paymentChannelEntity.setActive(false);
            paymentChannelRepository.save(paymentChannelEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public PaymentChannelOutView update(Long id, PaymentChannelInView paymentChannel) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,paymentChannel);

        try{
            PaymentChannelEntity entity = paymentChannelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            PaymentChannelEntity toSave = modelMapper.map(paymentChannel,new TypeToken<PaymentChannelEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            paymentChannelRepository.save(toSave);

            PaymentChannelOutView res= modelMapper.map(toSave,new TypeToken<PaymentChannelOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
