package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IExtrasService;
import org.example.tltravel.view.in.ExtrasInView;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.ExtrasOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Extras")
public class ExtrasController {
    @Autowired
    private IExtrasService service;
    @GetMapping({""})
    public ResponseEntity<Page<ExtrasOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ExtrasOutView> res = service.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtrasOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<ExtrasOutView> res = service.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<ExtrasOutView> addOne(@Validated @RequestBody ExtrasInView hotel) throws TLEntityNotFound {
        ExtrasOutView extrasOutView = service.addOne(hotel);
        return ResponseEntity.ok(extrasOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        service.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<ExtrasOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ExtrasInView hotel) throws TLEntityNotFound {
        ExtrasOutView re = service.update(id,hotel);
        return  ResponseEntity.ok(re);
    }

}
