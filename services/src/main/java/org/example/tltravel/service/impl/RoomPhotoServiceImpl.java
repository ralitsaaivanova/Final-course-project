package org.example.tltravel.service.impl;

import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.HotelPhotosEntity;
import org.example.tltravel.entities.HotelRoomEntity;
import org.example.tltravel.entities.RoomPhotosEntity;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.HotelRoomRepository;
import org.example.tltravel.repositories.RoomPhotosRepository;
import org.example.tltravel.service.IRoomPhotosService;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.example.tltravel.view.out.RoomPhotoOutView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomPhotoServiceImpl implements IRoomPhotosService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomPhotosRepository roomPhotosRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;


    @Override
    public List<RoomPhotoOutView> findActiveByRoomId(Long hotelRoomId) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : findActiveByHotelId start", logId);
        log.debug("{} : params: hotelId: {}", logId, hotelRoomId);

        // ensure hotel exists (reuse your hotelRepository check)
        hotelRoomRepository.findByIdAndIsActive(hotelRoomId)
                .orElseThrow(() -> new TLEntityNotFound("Hotel not found: " + hotelRoomId));

        List<RoomPhotosEntity> entities = roomPhotosRepository
                .findAllByHotelAndIsActive(hotelRoomId);

        // map to DTOs
        List<RoomPhotoOutView> photos = modelMapper.map(
                entities,
                new TypeToken<List<RoomPhotoOutView>>() {}.getType()
        );

        log.info("{} : findActiveByHotelId finished – found {} photos", logId, photos.size());
        return photos;
    }
    @Override
    public RoomPhotoOutView uploadPhoto(Long id, MultipartFile file) throws TLEntityNotFound, IOException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : uploadPhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: file: {};{}", logId,file.getContentType(),file.getSize());

        try{
            HotelRoomEntity entity = hotelRoomRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());
            RoomPhotosEntity photo = new RoomPhotosEntity();
            photo.setHotelRoomId(id);

            Blob photoBlob = BlobProxy.generateProxy((file.getInputStream()), file.getSize());
            photo.setPhoto(photoBlob);
            photo.setActive(true);
            photo = roomPhotosRepository.save(photo);

            RoomPhotoOutView res =modelMapper.map(photo,new TypeToken<RoomPhotoOutView>(){}.getType());
            return res;

        }catch (Exception e){
            log.error("{}: uploadOne error", logId,e);
            throw e;
        }finally {
            log.info("{}: uploadOne finished", logId);
        }
    }

    @Override
    public RoomPhotoOutView replacePhoto(Long id, MultipartFile file) throws TLEntityNotFound, IOException {
        String logId = UUID.randomUUID().toString();
        log.info("{} : replacePhoto start", logId);
        log.debug("{} : params: id: {}", logId,id);
        log.debug("{} : params: file: {};{}", logId,file.getContentType(),file.getSize());

        try{
            RoomPhotosEntity photo = roomPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            Blob photoBlob = BlobProxy.generateProxy((file.getInputStream()), file.getSize());
            photo.setPhoto(photoBlob);
            photo = roomPhotosRepository.save(photo);

            RoomPhotoOutView res =modelMapper.map(photo,new TypeToken<RoomPhotoOutView>(){}.getType());
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
            RoomPhotosEntity photo = roomPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());

            roomPhotosRepository.updatePhotoToUnActive(id);

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
            RoomPhotosEntity photo = roomPhotosRepository.findByIdAndIsActive(id).orElseThrow(()->new TLEntityNotFound());


            return photo.getPhoto();

        }catch (Exception e){
            log.error("{}: delete error", logId,e);
            throw e;
        }finally {
            log.info("{}: delete finished", logId);
        }
    }
}
