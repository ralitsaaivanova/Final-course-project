package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.ReservationPaymentRepository;
import org.example.tltravel.service.IReservationPaymentService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.ReservationPaymentInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.ReservationPaymentOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ReservationPayment")
public class ReservationPaymentController {

    @Autowired
    private IReservationPaymentService reservationPaymentService;

    @GetMapping({""})
    public ResponseEntity<Page<ReservationPaymentOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationPaymentOutView> res = reservationPaymentService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationPaymentOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<ReservationPaymentOutView> res = reservationPaymentService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<ReservationPaymentOutView> addOne(@Validated @RequestBody ReservationPaymentInView reservationPayment) throws TLEntityNotFound {
        ReservationPaymentOutView agentOutView = reservationPaymentService.addOne(reservationPayment);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        reservationPaymentService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<ReservationPaymentOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ReservationPaymentInView agent) throws TLEntityNotFound {
        ReservationPaymentOutView re = reservationPaymentService.update(id,agent);
        return  ResponseEntity.ok(re);
    }
}
