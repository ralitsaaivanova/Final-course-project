package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IHotelPhotosService;
import org.example.tltravel.view.in.HotelPhotoInView;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

@RestController
@RequestMapping("/HotelPhotos")
public class HotelPhotosController {
    @Autowired
    private IHotelPhotosService service;

    @PostMapping({""})
    public ResponseEntity<HotelPhotoOutView> addPhoto(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("hotelId") Long id) throws TLEntityNotFound, IOException {
        HotelPhotoOutView res= service.uploadPhoto(id,file);
        return ResponseEntity.ok(res);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<HotelPhotoOutView> replacePhoto(@RequestParam("file") MultipartFile file,
                                                      @PathVariable("id") Long id) throws TLEntityNotFound, IOException {
        if(file.getSize()==0){
            throw new IllegalArgumentException("File size is 0");
        }

        HotelPhotoOutView res= service.replacePhoto(id,file);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity deletePhoto(@PathVariable("id") Long id) throws TLEntityNotFound, IOException {
        service.deletePhoto(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping({"/{id}"})
    public ResponseEntity<Resource> downloadPhoto(
                                                  @PathVariable("id") Long id) throws TLEntityNotFound, IOException, SQLException {
        Blob b = service.downloadPhoto(id);
        Resource res = new InputStreamResource(b.getBinaryStream());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");

        ContentDisposition contentDisposition = ContentDisposition.builder("Attachment").filename("photo_" + id + ".png",
                StandardCharsets.UTF_8).build();

        httpHeaders.setContentDisposition(contentDisposition);

        return ResponseEntity.ok().
                headers(httpHeaders)
                .contentLength(b.length()).contentType(MediaType.IMAGE_PNG)
                .body(res);
    }

}
