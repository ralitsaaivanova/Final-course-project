package org.example.tltravel.controller;

import jakarta.validation.Valid;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IFeedingTypeService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.ExtrasInView;
import org.example.tltravel.view.in.FeedingTypeInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.FeedingTypeOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/FeedingType")
public class FeedingTypeController {

    @Autowired
    private IFeedingTypeService feedingTypeService;

    @GetMapping("/feedingTypeInfo")
    public ModelAndView showForm(Model model){

        model.addAttribute("feedingTypeInView", FeedingTypeInView.empty());
        return new ModelAndView ("feedingTypes");        // resolves to /templates/index.html
    }


    @PostMapping("/feedingTypeInfo")
    public ModelAndView addExtra(@ModelAttribute("feedingTypeInView") @Valid FeedingTypeInView feedingTypeInView,
                                 BindingResult bindingResult,
                                 Model model) throws TLEntityNotFound {
        if(bindingResult.hasErrors()){
            return new ModelAndView("feedingTypes");

        }
        feedingTypeService.addOne(feedingTypeInView);
        return new ModelAndView("redirect:/FeedingType/feedingTypeInfo?success");
    }

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
