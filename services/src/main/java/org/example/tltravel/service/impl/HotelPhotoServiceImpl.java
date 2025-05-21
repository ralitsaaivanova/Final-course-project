package org.example.tltravel.service.impl;

import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.HotelPhotosEntity;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.HotelPhotosRepository;
import org.example.tltravel.repositories.HotelRepository;
import org.example.tltravel.service.IHotelPhotosService;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class HotelPhotoServiceImpl implements IHotelPhotosService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelPhotosRepository hotelPhotosRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public HotelPhotoOutView uploadPhoto(Long id, MultipartFile file) throws TLEntityNotFound, IOException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : uploadPhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: file: {};{}", logId,file.getContentType(),file.getSize());

        try{
            HotelEntity entity = hotelRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            HotelPhotosEntity photo = new HotelPhotosEntity();
            photo.setHotel_id(id);

            Blob photoBlob = BlobProxy.generateProxy((file.getInputStream()), file.getSize());
            photo.setPhoto(photoBlob);
            photo.setActive(true);
            photo = hotelPhotosRepository.save(photo);

            HotelPhotoOutView res =modelMapper.map(photo,new TypeToken<HotelPhotoOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: uploadOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: uploadOne finished", logId);
        }
    }

    @Override
    public HotelPhotoOutView replacePhoto(Long id,MultipartFile file) throws TLEntityNotFound, IOException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : replacePhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: file: {};{}", logId,file.getContentType(),file.getSize());

        try{
            HotelPhotosEntity photo = hotelPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            Blob photoBlob = BlobProxy.generateProxy((file.getInputStream()), file.getSize());
            photo.setPhoto(photoBlob);
            photo = hotelPhotosRepository.save(photo);

            HotelPhotoOutView res =modelMapper.map(photo,new TypeToken<HotelPhotoOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: replaceOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: replaceOne finished", logId);
        }
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void deletePhoto(Long id) throws TLEntityNotFound, IOException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deletePhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);


        try{
            HotelPhotosEntity photo = hotelPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            hotelPhotosRepository.updatePhotoToUnActive(id);

        }catch (Exception e){
            log.error("{}: delete error", logId,e);
            throw e;
        }finally {
            log.info("{}: delete finished", logId);
        }
    }

    @Override
    public Blob downloadPhoto(Long id) throws TLEntityNotFound, IOException, SQLException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : downloadPhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);

        try{
            HotelPhotosEntity photo = hotelPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());


            return photo.getPhoto();

        }catch (Exception e){
            log.error("{}: delete error", logId,e);
            throw e;
        }finally {
            log.info("{}: delete finished", logId);
        }
    }

}
