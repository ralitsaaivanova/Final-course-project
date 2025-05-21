package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IAgentService;
import org.example.tltravel.service.ILocationService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.LocationInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.LocationOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Location")
public class LocationController {
    @Autowired
    private ILocationService service;


    @GetMapping({""})
    public ResponseEntity<Page<LocationOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<LocationOutView> res = service.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<LocationOutView> res = service.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<LocationOutView> addOne(@Validated @RequestBody LocationInView location) throws TLEntityNotFound {
        LocationOutView hotelOutView = service.addOne(location);
        return ResponseEntity.ok(hotelOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        service.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<LocationOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody LocationInView location) throws TLEntityNotFound {
        LocationOutView re = service.update(id,location);
        return  ResponseEntity.ok(re);
    }


    @GetMapping({"{id}/Hotel"})
    public ResponseEntity<Page<HotelOutView>> getAllHotels(@PathVariable ("id") Long id,
                                                           @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<HotelOutView> res = service.getAllHotels(id,pageable);
        return ResponseEntity.ok(res);
    }
}
