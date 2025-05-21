package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IOperatorService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.OperatorInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelOutView;
import org.example.tltravel.view.out.OperatorOutView;
import org.example.tltravel.view.out.ReservationOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Operator")
public class OperatorController {

    @Autowired
    private IOperatorService operatorService;

    @GetMapping({""})
    public ResponseEntity<Page<OperatorOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<OperatorOutView> res = operatorService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<OperatorOutView> res = operatorService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<OperatorOutView> addOne(@Validated @RequestBody OperatorInView hotel) throws TLEntityNotFound {
        OperatorOutView operatorOutViewOutView = operatorService.addOne(hotel);
        return ResponseEntity.ok(operatorOutViewOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        operatorService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<OperatorOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody OperatorInView agent) throws TLEntityNotFound {
        OperatorOutView re = operatorService.update(id,agent);
        return  ResponseEntity.ok(re);
    }


    @GetMapping({"{id}/Reservations"})
    public ResponseEntity<Page<ReservationOutView>> getAllReservations(@PathVariable ("id") Long id,
                                                           @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationOutView> res = operatorService.getAllReservations(id,pageable);
        return ResponseEntity.ok(res);
    }
}
