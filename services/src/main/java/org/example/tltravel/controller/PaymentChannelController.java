package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IPaymentChannelService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.PaymentChannelInView;
import org.example.tltravel.view.out.PaymentChannelOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/PaymentChannel")
public class PaymentChannelController {
    @Autowired
    private IPaymentChannelService paymentChannelService;


    @GetMapping({""})
    public ResponseEntity<Page<PaymentChannelOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<PaymentChannelOutView> res = paymentChannelService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentChannelOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<PaymentChannelOutView> res = paymentChannelService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<PaymentChannelOutView> addOne(@Validated @RequestBody PaymentChannelInView paymentChannel) throws TLEntityNotFound {
        PaymentChannelOutView agentOutView = paymentChannelService.addOne(paymentChannel);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        paymentChannelService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<PaymentChannelOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody PaymentChannelInView paymentChannel) throws TLEntityNotFound {
        PaymentChannelOutView re = paymentChannelService.update(id,paymentChannel);
        return  ResponseEntity.ok(re);
    }


}
