package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.HotelRoomEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.HotelRoomRepository;
import org.example.tltravel.service.IHotelRoomService;
import org.example.tltravel.view.in.HotelRoomInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelRoomOutView;
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
public class HotelRoomServiceImpl implements IHotelRoomService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Override
    public Optional<HotelRoomOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<HotelRoomEntity> entity = hotelRoomRepository.findByIdAndIsActive(id);

            Optional<HotelRoomOutView> res = modelMapper.map(entity,new TypeToken<Optional<HotelRoomOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<HotelRoomOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<HotelRoomEntity> entities = hotelRoomRepository.findAllActive(pageable);
            List<HotelRoomOutView> hotelRoomOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<HotelRoomOutView>>(){}.getType());

            Page<HotelRoomOutView> res = new PageImpl<>(hotelRoomOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public HotelRoomOutView addOne(HotelRoomInView hotelRoom) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,hotelRoom);
        try{
            HotelRoomEntity entity = modelMapper.map(hotelRoom,new TypeToken<HotelRoomEntity>(){}.getType());
            entity.setActive(true);
            entity = hotelRoomRepository.save(entity);
            HotelRoomOutView res = modelMapper.map(entity,new TypeToken<HotelRoomOutView>(){}.getType());
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
            HotelRoomEntity hotelRoomEntity = hotelRoomRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!hotelRoomEntity.getActive()){
                throw new TLEntityNotActive(hotelRoomEntity.toString());
            }
            hotelRoomEntity.setActive(false);
            hotelRoomRepository.save(hotelRoomEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public HotelRoomOutView update(Long id, HotelRoomInView hotelRoom) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,hotelRoom);

        try{
            HotelRoomEntity entity = hotelRoomRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            HotelRoomEntity toSave = modelMapper.map(hotelRoom,new TypeToken<HotelRoomEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            hotelRoomRepository.save(toSave);

            HotelRoomOutView res= modelMapper.map(toSave,new TypeToken<HotelRoomOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: update error", logId,e);
            throw e;
        }finally {
            log.info("{}: update finished", logId);
        }
    }
}
