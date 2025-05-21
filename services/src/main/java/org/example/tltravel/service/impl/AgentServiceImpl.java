package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.AgentRepository;
import org.example.tltravel.repositories.HotelRepository;
import org.example.tltravel.service.IAgentService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.out.AgentOutView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentServiceImpl implements IAgentService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<AgentOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<AgentEntity> entity = agentRepository.findByIdAndIsActive(id);
            /*modelMapper.map(entity.get(), HotelOutView.class);*/

            Optional<AgentOutView> res = modelMapper.map(entity,new TypeToken<Optional<AgentOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<AgentOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<AgentEntity> entities = agentRepository.findAllActive(pageable);
            List<AgentOutView> agentOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<AgentOutView>>(){}.getType());

            Page<AgentOutView> res = new PageImpl<>(agentOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }

    }

    @Override
    public AgentOutView addOne(AgentInView body) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,body);
        try{
            AgentEntity entity = modelMapper.map(body,new TypeToken<AgentEntity>(){}.getType());
            entity.setActive(true);
            entity = agentRepository.save(entity);
            AgentOutView res = modelMapper.map(entity,new TypeToken<AgentOutView>(){}.getType());
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

            AgentEntity agentEntity = agentRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!agentEntity.getActive()){
                throw new TLEntityNotActive(agentEntity.toString());
            }
            agentEntity.setActive(false);
            agentRepository.save(agentEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public AgentOutView update(Long id, AgentInView agent) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,agent);

        try{
            AgentEntity entity = agentRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            AgentEntity toSave = modelMapper.map(agent,new TypeToken<AgentEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            agentRepository.save(toSave);

            AgentOutView res= modelMapper.map(entity,new TypeToken<AgentOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }

    @Override
    public Page<HotelOutView> getAllHotels(Long id, Pageable pageable) throws TLEntityNotFound {

        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllHotels start", logId);
        log.debug("{} : getAllHotels start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);

        try{
            //проверяваме дали съществува агент
            AgentEntity entity = agentRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<HotelEntity> entities = hotelRepository.findAllByPartnerIdActive(id,pageable);
            List<HotelOutView> hotelOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<HotelOutView>>(){}.getType());

            //филтър за хотелите, които са активни

            Page<HotelOutView> page = new PageImpl<>(hotelOutViews,pageable,entities.getTotalElements());
            return page;

        }catch (Exception e){
            log.error("{}: getAllHotels error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllHotels finished", logId);
        }

    }
}
