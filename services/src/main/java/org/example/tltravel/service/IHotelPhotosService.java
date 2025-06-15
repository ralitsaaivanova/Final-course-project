package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public interface IHotelPhotosService {

    List<HotelPhotoOutView> findActiveByHotelId(Long hotelId) throws TLEntityNotFound;

    HotelPhotoOutView uploadPhoto(Long id, MultipartFile photo) throws TLEntityNotFound, IOException;
    HotelPhotoOutView replacePhoto(Long id,MultipartFile photo) throws TLEntityNotFound, IOException;
    void deletePhoto(Long id) throws TLEntityNotFound, IOException;
    Blob downloadPhoto(Long id) throws TLEntityNotFound, IOException, SQLException;
}
