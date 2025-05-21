package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.ClientEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.ReservationEntity;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.ClientRepository;
import org.example.tltravel.repositories.ReservationRepository;
import org.example.tltravel.service.IClientService;
import org.example.tltravel.view.in.ClientInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.ClientOutView;
import org.example.tltravel.view.out.HotelOutView;
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
public class ClientServiceimpl implements IClientService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Optional<ClientOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            Optional<ClientEntity> entity = clientRepository.findByIdAndIsActive(id);
            Optional<ClientOutView> res = modelMapper.map(entity,new TypeToken<Optional<ClientOutView>>(){}.getType());

            return res;
        }catch (Exception e){
            log.error("{}: getById error", logId,e);
            throw e;
        }finally {
            log.info("{}: getById finished", logId);
        }
    }

    @Override
    public Page<ClientOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<ClientEntity> entities = clientRepository.findAllActive(pageable);
            List<ClientOutView> clientOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<AgentOutView>>(){}.getType());

            Page<ClientOutView> res = new PageImpl<>(clientOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public ClientOutView addOne(ClientInView client) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getOne start", logId);
        log.debug("{} : params: Body: {}", logId,client);
        try{
            ClientEntity entity = modelMapper.map(client,new TypeToken<ClientEntity>(){}.getType());
            entity.setActive(true);
            entity = clientRepository.save(entity);
            ClientOutView res = modelMapper.map(entity,new TypeToken<ClientOutView>(){}.getType());
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

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(()->new TLEntityNotFound());
            if(!clientEntity.getActive()){
                throw new TLEntityNotActive(clientEntity.toString());
            }
            clientEntity.setActive(false);
            clientRepository.save(clientEntity);


        }catch (Exception e){
            log.error("{}: deleteOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: deleteOne finished", logId);
        }
    }

    @Override
    public ClientOutView update(Long id, ClientInView client) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : update start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: id: {}", logId,client);

        try{
            ClientEntity entity = clientRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            ClientEntity toSave = modelMapper.map(client,new TypeToken<ClientEntity>(){}.getType());

            toSave.setId(id);
            toSave.setActive(entity.getActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            clientRepository.save(toSave);

            ClientOutView res= modelMapper.map(entity,new TypeToken<ClientOutView>(){}.getType());
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
        log.info("{} : getAllHotels start", logId);
        log.debug("{} : getAllHotels start", logId);
        log.debug("{} : params: pageable {}", logId,pageable);

        try{
            //проверяваме дали съществува агент
            ClientEntity entity = clientRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            Page<ReservationEntity> entities = reservationRepository.findAllByClientIdActive(id,pageable);
            List<ReservationOutView> reservationOutViews = modelMapper.map(entities.getContent(),new TypeToken<List<ReservationOutView>>(){}.getType());

            //филтър за хотелите, които са активни

            Page<ReservationOutView> page = new PageImpl<>(reservationOutViews,pageable,entities.getTotalElements());
            return page;

        }catch (Exception e){
            log.error("{}: getAllHotels error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAllHotels finished", logId);
        }

    }
}
