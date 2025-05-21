package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.example.tltravel.view.out.RoomPhotoOutView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public interface IRoomPhotosService {

    RoomPhotoOutView uploadPhoto(Long id, MultipartFile photo) throws TLEntityNotFound, IOException;
    RoomPhotoOutView replacePhoto(Long id,MultipartFile photo) throws TLEntityNotFound, IOException;
    void deletePhoto(Long id) throws TLEntityNotFound, IOException;
    Blob downloadPhoto(Long id) throws TLEntityNotFound, IOException, SQLException;
}
