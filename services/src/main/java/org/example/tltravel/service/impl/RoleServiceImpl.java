package org.example.tltravel.service.impl;

import org.example.tltravel.entities.AgentEntity;
import org.example.tltravel.entities.RoleEntity;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.RoleRepository;
import org.example.tltravel.service.IRoleService;
import org.example.tltravel.view.in.RoleInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.RoleOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
public class RoleServiceImpl implements IRoleService {

    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Page<RoleOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try {
            Page<RoleEntity> entities = this.roleRepository.findAll(pageable);

            List<RoleOutView> roleOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<RoleOutView>>() {
                    }.getType());

            Page<RoleOutView> res = new PageImpl<>(roleOutViews,pageable,entities.getTotalElements());;
            log.debug("{} : retrieved {} content", logId, entities.getSize());

            return res;

        } catch (Exception e) {
            log.error("{} getAll error", logId, e);
            throw e;
        } finally {
            log.info("{} : getAll finished", logId);
        }

    }


    @Override
    public Optional<RoleOutView> getById(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Optional<RoleEntity> entity = roleRepository.findById(id);

            Optional<RoleOutView> res = modelMapper.map(entity, new TypeToken<RoleOutView>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : getById error", logId, e);
            throw e;
        } finally {
            log.info("{} : getById finished", logId);
        }
    }

    @Override
    public RoleOutView addOne(RoleInView body) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, body);

        try {

            RoleEntity entity = modelMapper.map(body, new TypeToken<RoleEntity>() {
            }.getType());
            entity = roleRepository.save(entity);
            RoleOutView res = modelMapper.map(entity, new TypeToken<RoleOutView>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : addOne error", logId, e);
            throw e;
        } finally {
            log.info("{} : addOne finished", logId);
        }
    }

    @Override
    public void deleteOne(Long id) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            RoleEntity entity = roleRepository.findById(id).orElseThrow(() -> new TLEntityNotFound());
            roleRepository.delete(entity);

        } catch (Exception e) {
            log.error("{} : deleteById error", logId, e);
            throw e;
        } finally {
            log.info("{} : deleteById finished", logId);
        }
    }

    @Override
    public RoleOutView updateOne(Long id, RoleInView body) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : updateOne start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            RoleEntity entity = roleRepository.findById(id).orElseThrow(() -> new TLEntityNotFound());
            RoleEntity toSave = modelMapper.map(body, new TypeToken<RoleEntity>() {
            }.getType());
            toSave.setId(id);
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            System.out.println(toSave);
            toSave = roleRepository.save(toSave);

            RoleOutView res = modelMapper.map(toSave, new TypeToken<RoleOutView>() {
            }.getType());

            return res;

        } catch (Exception e) {
            log.error("{} : updateOne error", logId, e);
            throw e;
        } finally {
            log.info("{} : updateOne finished", logId);
        }
    }
}
