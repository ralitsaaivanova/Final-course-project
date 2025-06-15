package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.LocationEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.AgentRepository;
import org.example.tltravel.repositories.HotelRepository;
import org.example.tltravel.repositories.LocationRepository;
import org.example.tltravel.service.ILocationService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.LocationInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.LocationOutView;
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
public class LocationServiceImpl implements ILocationService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<LocationOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<LocationEntity> entity = locationRepository.findById(id);

            Optional<LocationOutView> res = modelMapper.map(entity,new TypeToken<Optional<LocationOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<LocationOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAllLocations start", logId);
        try{
            Page<LocationEntity> entities = locationRepository.findAllActive(pageable);
            List<LocationOutView> locationOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<LocationOutView>>(){}.getType());

            Page<LocationOutView> res = new PageImpl<>(locationOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAllLocations error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllLocations finished", logId);
        }

    }

    @Override
    public LocationOutView addOne(LocationInView body) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,body);
        try{
            LocationEntity entity = modelMapper.map(body,new TypeToken<LocationEntity>(){}.getType());
            entity.setActive(true);
            entity = locationRepository.save(entity);
            LocationOutView res = modelMapper.map(entity,new TypeToken<LocationOutView>(){}.getType());
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

            LocationEntity locationEntity = locationRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!locationEntity.getActive()){
                throw new TLEntityNotActive(locationEntity.toString());
            }
            locationEntity.setActive(false);
            locationRepository.save(locationEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public LocationOutView update(Long id, LocationInView location) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,location);

        try{
            LocationEntity entity = locationRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            LocationEntity toSave = modelMapper.map(location,new TypeToken<LocationEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            locationRepository.save(toSave);

            LocationOutView res= modelMapper.map(toSave,new TypeToken<LocationOutView>(){}.getType());
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
            LocationEntity entity = locationRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            Page<HotelEntity> entities = hotelRepository.findAllByLocation(id,pageable);
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
