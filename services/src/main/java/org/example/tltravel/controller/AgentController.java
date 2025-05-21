package org.example.tltravel.controller;


import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IAgentService;

import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.out.AgentOutView;
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
@RequestMapping("/Agent")
public class AgentController {

    @Autowired
    private IAgentService service;


    @GetMapping({""})
    public ResponseEntity<Page<AgentOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<AgentOutView> res = service.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<AgentOutView> res = service.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<AgentOutView> addOne(@Validated @RequestBody AgentInView hotel) throws TLEntityNotFound {
        AgentOutView agentOutView = service.addOne(hotel);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        service.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<AgentOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody AgentInView agent) throws TLEntityNotFound {
        AgentOutView re = service.update(id,agent);
        return  ResponseEntity.ok(re);
    }


    @GetMapping({"{id}/Hotel"})
    public ResponseEntity<Page<HotelOutView>> getAllHotels(@PathVariable ("id") Long id,
                                                     @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<HotelOutView> res = service.getAllHotels(id,pageable);
        return ResponseEntity.ok(res);
    }
}
