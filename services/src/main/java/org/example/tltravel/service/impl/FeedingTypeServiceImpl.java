package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.FeedingTypeEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.FeedingTypeRepository;
import org.example.tltravel.service.IFeedingTypeService;
import org.example.tltravel.view.in.FeedingTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.FeedingTypeOutView;
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
public class FeedingTypeServiceImpl implements IFeedingTypeService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private FeedingTypeRepository typeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<FeedingTypeOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<FeedingTypeEntity> entity = typeRepository.findByIdAndIsActive(id);

            Optional<FeedingTypeOutView> res = modelMapper.map(entity,new TypeToken<Optional<FeedingTypeOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<FeedingTypeOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<FeedingTypeEntity> entities = typeRepository.findAllActive(pageable);
            List<FeedingTypeOutView> feedingTypeOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<FeedingTypeOutView>>(){}.getType());

            Page<FeedingTypeOutView> res = new PageImpl<>(feedingTypeOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public FeedingTypeOutView addOne(FeedingTypeInView feedingType) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,feedingType);
        try{
            FeedingTypeEntity entity = modelMapper.map(feedingType,new TypeToken<FeedingTypeEntity>(){}.getType());
            entity.setActive(true);
            entity = typeRepository.save(entity);
            FeedingTypeOutView res = modelMapper.map(entity,new TypeToken<FeedingTypeOutView>(){}.getType());
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

            FeedingTypeEntity feedingTypeEntity = typeRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!feedingTypeEntity.getActive()){
                throw new TLEntityNotActive(feedingTypeEntity.toString());
            }
            feedingTypeEntity.setActive(false);
            typeRepository.save(feedingTypeEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public FeedingTypeOutView update(Long id, FeedingTypeInView feedingType) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,feedingType);

        try{
            FeedingTypeEntity entity = typeRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            FeedingTypeEntity toSave = modelMapper.map(feedingType,new TypeToken<FeedingTypeEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            typeRepository.save(toSave);

            FeedingTypeOutView res= modelMapper.map(toSave,new TypeToken<FeedingTypeOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
