package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IReservationRoomsService;
import org.example.tltravel.view.in.ReservationPaymentInView;
import org.example.tltravel.view.in.ReservationRoomInView;
import org.example.tltravel.view.out.ReservationPaymentOutView;
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
@RequestMapping("/ReservationRooms")
public class ReservationRoomsController {
    @Autowired
    private IReservationRoomsService reservationRoomsService;

    @GetMapping({""})
    public ResponseEntity<Page<ReservationRoomOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationRoomOutView> res = reservationRoomsService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationRoomOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<ReservationRoomOutView> res = reservationRoomsService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<ReservationRoomOutView> addOne(@Validated @RequestBody ReservationRoomInView reservationPayment) throws TLEntityNotFound {
        ReservationRoomOutView agentOutView = reservationRoomsService.addOne(reservationPayment);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        reservationRoomsService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<ReservationRoomOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ReservationRoomInView reservationRoomInView) throws TLEntityNotFound {
        ReservationRoomOutView re = reservationRoomsService.update(id,reservationRoomInView);
        return  ResponseEntity.ok(re);
    }
}
