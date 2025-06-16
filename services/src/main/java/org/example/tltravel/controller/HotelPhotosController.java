package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IHotelPhotosService;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.view.out.HotelPhotoOutView;
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
@RequestMapping("/HotelPhotos")
public class HotelPhotosController {
    @Autowired
    private IHotelPhotosService service;

    @Autowired
    private IHotelService hotelService;

    @GetMapping("/upload")
    public ModelAndView showUploadForm(@RequestParam("hotelId") Long hotelId, Model model) throws TLEntityNotFound {
        model.addAttribute("hotelId", hotelId);
        PageRequest pageable = PageRequest.of(0, 10);
        model.addAttribute("hotels",hotelService.getAll(pageable));
        return new ModelAndView("hotelPhotos");
    }

    // Handle form submit
    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("hotelId") Long hotelId,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes flash) {
        try {
            HotelPhotoOutView photo = service.uploadPhoto(hotelId, file);
            flash.addFlashAttribute("message", "Uploaded photo #" + photo.getId());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Could not upload: " + e.getMessage());
        }
        return new ModelAndView("redirect:/HotelPhotos/list?hotelId=" + hotelId);
    }

    // List all active photos for a hotel
    @GetMapping("/list")
    public ModelAndView listPhotos(@RequestParam("hotelId") Long hotelId, Model model) throws TLEntityNotFound {
        // You’ll need a method in your service/repo to fetch all photos by hotelId
        List<HotelPhotoOutView> photos = service.findActiveByHotelId(hotelId);
        PageRequest pageable = PageRequest.of(0, 10);
        model.addAttribute("hotels",hotelService.getAll(pageable));
        model.addAttribute("photos", photos);
        model.addAttribute("hotelId", hotelId);
        return new ModelAndView("hotelPhotos");
    }

    @GetMapping("/delete/{id}")
    public String deletePhotoWeb(@PathVariable Long id,
                                 @RequestParam("hotelId") Long hotelId,
                                 RedirectAttributes flash) {
        try {
            service.deletePhoto(id);
            flash.addFlashAttribute("message", "Deleted photo #" + id);
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Could not delete: " + e.getMessage());
        }
        return "redirect:/HotelPhotos/list?hotelId=" + hotelId;
    }

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
