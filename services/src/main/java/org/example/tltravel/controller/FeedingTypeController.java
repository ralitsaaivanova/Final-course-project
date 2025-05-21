package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IFeedingTypeService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.FeedingTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.FeedingTypeOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/FeedingType")
public class FeedingTypeController {

    @Autowired
    private IFeedingTypeService feedingTypeService;

    @GetMapping({""})
    public ResponseEntity<Page<FeedingTypeOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<FeedingTypeOutView> res = feedingTypeService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedingTypeOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<FeedingTypeOutView> res = feedingTypeService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<FeedingTypeOutView> addOne(@Validated @RequestBody FeedingTypeInView hotel) throws TLEntityNotFound {
        FeedingTypeOutView feedingTypeOutView = feedingTypeService.addOne(hotel);
        return ResponseEntity.ok(feedingTypeOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        feedingTypeService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<FeedingTypeOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody FeedingTypeInView feedingType) throws TLEntityNotFound {
        FeedingTypeOutView re = feedingTypeService.update(id,feedingType);
        return  ResponseEntity.ok(re);
    }


}
