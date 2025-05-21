package org.example.tltravel.service.impl;

import org.example.tltravel.entities.ExtrasEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;

import org.example.tltravel.repositories.ExtrasRepository;
import org.example.tltravel.service.IExtrasService;
import org.example.tltravel.view.in.ExtrasInView;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.ExtrasOutView;
import org.example.tltravel.view.out.HotelOutView;
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
public class ExtrasServiceImpl implements IExtrasService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ExtrasRepository extrasRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Optional<ExtrasOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<ExtrasEntity> entity = extrasRepository.findByIdAndIsActive(id);
            /*modelMapper.map(entity.get(), HotelOutView.class);*/

            Optional<ExtrasOutView> res = modelMapper.map(entity,new TypeToken<Optional<ExtrasOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<ExtrasOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllExtras start", logId);
        try{
            Page<ExtrasEntity> entities = extrasRepository.findAllActive(pageable);


            List<ExtrasOutView> extrasOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<ExtrasOutView>>(){}.getType());

            Page<ExtrasOutView> res = new PageImpl<>(extrasOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAllExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllExtras  finished", logId);
        }
    }

    @Override
    public ExtrasOutView addOne(ExtrasInView extras) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOneExtras start", logId);
        log.debug("{} : params: Body: {}", logId,extras);
        try{
            ExtrasEntity entity = modelMapper.map(extras,new TypeToken<ExtrasEntity>(){}.getType());
            entity.setActive(true);
            entity = extrasRepository.save(entity);
            ExtrasOutView res = modelMapper.map(entity,new TypeToken<ExtrasOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: getOneExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: getOneExtras finished", logId);
        }
    }

    @Override
    public void deleteOne(Long id) throws TLEntityNotFound, TLEntityNotActive {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteOneExtra start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{

            ExtrasEntity extrasEntity = extrasRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!extrasEntity.getActive()){
                throw new TLEntityNotActive(extrasEntity.toString());
            }
            extrasEntity.setActive(false);
            extrasRepository.save(extrasEntity);


        }catch (Exception e){
            log.error("{}: deleteOneExtra error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOneExtra finished", logId);
        }
    }

    @Override
    public ExtrasOutView update(Long id, ExtrasInView hotel) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : updateExtra  start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,hotel);

        try{
            ExtrasEntity entity = extrasRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ExtrasEntity toSave = modelMapper.map(hotel,new TypeToken<ExtrasEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            extrasRepository.save(toSave);

            ExtrasOutView res= modelMapper.map(entity,new TypeToken<ExtrasOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: updateExtras error", logId,e);
            throw e;
        }finally {
            log.info("{}: updateExtras finished", logId);
        }
    }
}
