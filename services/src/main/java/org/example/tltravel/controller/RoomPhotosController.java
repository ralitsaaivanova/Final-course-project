package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IHotelRoomService;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.service.IRoomPhotosService;
import org.example.tltravel.view.out.HotelPhotoOutView;
import org.example.tltravel.view.out.RoomPhotoOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/RoomPhotos")
public class RoomPhotosController {

    @Autowired
    private IRoomPhotosService service;

    @Autowired
    private IHotelRoomService hotelRoomService;

    @Autowired
    private IHotelService hotelService;

    @PostMapping({""})
    public ResponseEntity<RoomPhotoOutView> addPhoto(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("hotelRoomId") Long id) throws TLEntityNotFound, IOException {
        RoomPhotoOutView res= service.uploadPhoto(id,file);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/upload")
    public ModelAndView showUploadForm(@RequestParam("hotelRoomId") Long hotelRoomId, Model model) throws TLEntityNotFound {
        model.addAttribute("hotelRoomId", hotelRoomId);
        PageRequest pageable = PageRequest.of(0, 10);
        model.addAttribute("hotelRooms",hotelRoomService.getAll(pageable));
        return new ModelAndView("roomPhotos");
    }

    // Handle form submit
    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("hotelRoomId") Long hotelRoomId,
                                         @RequestParam("file") MultipartFile file,
                                         RedirectAttributes flash) {

        try {
            RoomPhotoOutView photo = service.uploadPhoto(hotelRoomId, file);
            flash.addFlashAttribute("message", "Uploaded photo #" + photo.getId());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Could not upload: " + e.getMessage());
        }
        return new ModelAndView("redirect:/RoomPhotos/list?hotelRoomId=" + hotelRoomId);
    }
//
    // List all active photos for a hotel
    @GetMapping("/list")
    public ModelAndView listPhotos(@RequestParam("hotelRoomId") Long hotelRoomId,
                                   @RequestParam("hotelId") Long hotelId, Model model) throws TLEntityNotFound {
        // You’ll need a method in your service/repo to fetch all photos by hotelId
        List<RoomPhotoOutView> photos = service.findActiveByRoomId(hotelRoomId);
        PageRequest pageable = PageRequest.of(0, 10);
        model.addAttribute("hotels",
                hotelService.getAll(pageable));
        model.addAttribute("photos", photos);
        model.addAttribute("hotelRooms",hotelRoomService.getAll(pageable));
        model.addAttribute("hotelId",hotelId);
        model.addAttribute("hotelRoomId", hotelRoomId);
        return new ModelAndView("roomPhotos");
    }

    @GetMapping("/delete/{id}")
    public String deletePhotoWeb(@PathVariable Long id,
                                 @RequestParam("hotelRoomId") Long hotelRoomId,
                                 RedirectAttributes flash) {
        try {
            service.deletePhoto(id);
            flash.addFlashAttribute("message", "Deleted photo #" + id);
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Could not delete: " + e.getMessage());
        }
        return "redirect:/RoomPhotos/list?hotelRoomId=" + hotelRoomId;
    }



//    @PutMapping({"/{id}"})
//    public ResponseEntity<RoomPhotoOutView> replacePhoto(@RequestParam("file") MultipartFile file,
//                                                          @PathVariable("id") Long id) throws TLEntityNotFound, IOException {
//        if(file.getSize()==0){
//            throw new IllegalArgumentException("File size is 0");
//        }
//
//        RoomPhotoOutView res= service.replacePhoto(id,file);
//        return ResponseEntity.ok(res);
//    }
//
//    @DeleteMapping({"/{id}"})
//    public ResponseEntity deletePhoto(@PathVariable("id") Long id) throws TLEntityNotFound, IOException {
//        service.deletePhoto(id);
//        return ResponseEntity.ok().build();
//    }
//
//
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
