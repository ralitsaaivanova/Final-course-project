package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IPaymentTypeService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.PaymentTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.PaymentTypeOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/PaymentType")
public class PaymentTypeController {

    @Autowired
    private IPaymentTypeService paymentTypeService;
    @GetMapping({""})
    public ResponseEntity<Page<PaymentTypeOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<PaymentTypeOutView> res = paymentTypeService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<PaymentTypeOutView> res = paymentTypeService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<PaymentTypeOutView> addOne(@Validated @RequestBody PaymentTypeInView hotel) throws TLEntityNotFound {
        PaymentTypeOutView agentOutView = paymentTypeService.addOne(hotel);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        paymentTypeService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody PaymentTypeInView agent) throws TLEntityNotFound {
        PaymentTypeOutView re = paymentTypeService.update(id,agent);
        return  ResponseEntity.ok(re);
    }

}
