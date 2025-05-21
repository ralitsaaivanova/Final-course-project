package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IClientService;
import org.example.tltravel.view.in.ClientInView;
import org.example.tltravel.view.in.ReservationInView;
import org.example.tltravel.view.out.ClientOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.example.tltravel.view.out.ReservationRoomOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping({""})
    public ResponseEntity<Page<ClientOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ClientOutView> res = clientService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<ClientOutView> res = clientService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<ClientOutView> addOne(@Validated @RequestBody ClientInView reservation) throws TLEntityNotFound {
        ClientOutView hotelOutView = clientService.addOne(reservation);
        return ResponseEntity.ok(hotelOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        clientService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<ClientOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ClientInView hotel) throws TLEntityNotFound {
        ClientOutView re = clientService.update(id,hotel);
        return  ResponseEntity.ok(re);
    }

    @GetMapping({"{id}/Reservations"})
    public ResponseEntity<Page<ReservationOutView>> getAllReservations(@PathVariable ("id") Long id,
                                                                               @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationOutView> res = clientService.getAllReservations(id,pageable);
        return ResponseEntity.ok(res);
    }
}
