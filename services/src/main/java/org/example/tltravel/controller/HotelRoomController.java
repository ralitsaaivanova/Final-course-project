package org.example.tltravel.controller;

import jakarta.validation.Valid;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.HotelRoomRepository;
import org.example.tltravel.service.IHotelRoomService;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.view.in.AgentInView;
import org.example.tltravel.view.in.FeedingTypeInView;
import org.example.tltravel.view.in.HotelRoomInView;
import org.example.tltravel.view.out.AgentOutView;
import org.example.tltravel.view.out.HotelRoomOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/HotelRoom")
public class HotelRoomController {
    @Autowired
    private IHotelRoomService hotelRoomService;

    @Autowired
    private IHotelService hotelService;


    @GetMapping("/hotelRoomInfo")
    public ModelAndView showForm(Model model) throws TLEntityNotFound {

        model.addAttribute("hotelRoomInView", HotelRoomInView.empty());
        PageRequest pageable = PageRequest.of(0, 100);
        model.addAttribute("rooms",hotelRoomService.getAll(pageable));
        model.addAttribute("hotels", hotelService.getAll(pageable));
        return new ModelAndView ("hotelRoom");        // resolves to /templates/index.html
    }


    @PostMapping("/hotelRoomInfo")
    public ModelAndView addExtra(@ModelAttribute("hotelRoomInView") @Valid HotelRoomInView hotelRoomInView,
                                 BindingResult bindingResult,
                                 Model model) throws TLEntityNotFound {
        if(bindingResult.hasErrors()){
            return new ModelAndView("hotelRoom");

        }
        hotelRoomService.addOne(hotelRoomInView);
        return new ModelAndView("redirect:/HotelRoom/hotelRoomInfo?success");
    }


    @GetMapping({""})
    public ResponseEntity<Page<HotelRoomOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<HotelRoomOutView> res = hotelRoomService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<HotelRoomOutView> res = hotelRoomService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<HotelRoomOutView> addOne(@Validated @RequestBody HotelRoomInView hotel) throws TLEntityNotFound {
        HotelRoomOutView agentOutView = hotelRoomService.addOne(hotel);
        return ResponseEntity.ok(agentOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        hotelRoomService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<HotelRoomOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody HotelRoomInView agent) throws TLEntityNotFound {
        HotelRoomOutView re = hotelRoomService.update(id,agent);
        return  ResponseEntity.ok(re);
    }
}
