package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.service.IReservationService;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.in.ReservationInView;
import org.example.tltravel.view.out.HotelOutView;
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
@RequestMapping("/Reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @GetMapping({""})
    public ResponseEntity<Page<ReservationOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationOutView> res = reservationService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<ReservationOutView> res = reservationService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<ReservationOutView> addOne(@Validated @RequestBody ReservationInView hotel) throws TLEntityNotFound {
        ReservationOutView hotelOutView = reservationService.addOne(hotel);
        return ResponseEntity.ok(hotelOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        reservationService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<ReservationOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ReservationInView hotel) throws TLEntityNotFound {
        ReservationOutView re = reservationService.update(id,hotel);
        return  ResponseEntity.ok(re);
    }

    @GetMapping({"{id}/ReservationRoom"})
    public ResponseEntity<Page<ReservationRoomOutView>> getAllReservationRooms(@PathVariable ("id") Long id,
                                                                               @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationRoomOutView> res = reservationService.getAllReservationRooms(id,pageable);
        return ResponseEntity.ok(res);
    }

}
