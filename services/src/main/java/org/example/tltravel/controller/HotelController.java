package org.example.tltravel.controller;

import jakarta.validation.Valid;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IAgentService;
import org.example.tltravel.service.IHotelService;
import org.example.tltravel.service.ILocationService;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Hotel")
public class HotelController {
    @Autowired
    private IHotelService service;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IAgentService agentService;

    @GetMapping("/hotelInfo")
    public ModelAndView showForm(@RequestParam(value="id", required=false) Long id, Model model)
            throws TLEntityNotFound {

        HotelInView inView = HotelInView.empty();
        if (id != null) {
            // fetch existing
            HotelOutView out = service
                    .getById(id)
                    .orElseThrow(() -> new TLEntityNotFound("Hotel not found: " + id));
            inView = HotelInView.from(out);
        }
        model.addAttribute("hotelInView", inView);

        model.addAttribute("hotelId",id);

        // load lookup data
        PageRequest pageable = PageRequest.of(0, 100);
        model.addAttribute("locations", locationService.getAll(pageable));
        model.addAttribute("partners", agentService.getAll(pageable) );

        model.addAttribute("hotels",service.getAll(PageRequest.of(0,10)));

        return new ModelAndView ("hotel");
    }

    @PostMapping("/hotelInfo")
    public ModelAndView addHotel(@RequestParam(value = "id", required = false) Long id,
                                 @ModelAttribute("hotelInView") @Valid HotelInView hotelInView,
                                    BindingResult bindingResult,
                                    RedirectAttributes flash,
                                    Model model) throws TLEntityNotFound {
        if(bindingResult.hasErrors()){
            model.addAttribute("locations", locationService.getAll(PageRequest.of(0,100)));
            model.addAttribute("partners",  agentService.getAll(PageRequest.of(0,100)));
            model.addAttribute("hotels",service.getAll(PageRequest.of(0,10)));
            model.addAttribute("hotelId",id);
            return new ModelAndView("hotel");

        }
        if (id != null) {
            service.update(id, hotelInView);
            flash.addFlashAttribute("message", "Hotel updated");
        } else {
            service.addOne(hotelInView);
            flash.addFlashAttribute("message", "Hotel created");
        }
        return new ModelAndView("redirect:/Hotel/hotelInfo?success");
    }

    @PostMapping("/hotelInfo/delete")
    public ModelAndView deleteHotel(
            @RequestParam("id") Long id,
            RedirectAttributes flash
    ) throws TLEntityNotFound, TLEntityNotActive {
        service.deleteOne(id);
        flash.addFlashAttribute("message", "Hotel has been deleted");
        // redirect back to the empty form (and your table)
        return new ModelAndView("redirect:/Hotel/hotelInfo");
    }


//    @GetMapping({""})
//    public ResponseEntity<Page<HotelOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
//        Page<HotelOutView> res = service.getAll(pageable);
//        return ResponseEntity.ok(res);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<HotelOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
//        Optional<HotelOutView> res = service.getById(id);
//        if(res.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(res.get());
//    }
//
//    @PostMapping({""})
//    public ResponseEntity<HotelOutView> addOne(@Validated @RequestBody HotelInView hotel) throws TLEntityNotFound {
//        HotelOutView hotelOutView = service.addOne(hotel);
//        return ResponseEntity.ok(hotelOutView);
//    }
//
//    @DeleteMapping ("/{id}")
//    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
//        service.deleteOne(id);
//        return ResponseEntity.ok().build();
//
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HotelOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody HotelInView hotel) throws TLEntityNotFound {
//        HotelOutView re = service.update(id,hotel);
//        return  ResponseEntity.ok(re);
//    }

    @GetMapping("/{id}/Extras")
    public ResponseEntity<List<ExtrasOutView>> getAllExtras(@PathVariable Long id) throws TLEntityNotFound {
        List<ExtrasOutView> res = service.getAllExtras(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<HotelOutView>> getAllByExtras(@PageableDefault Pageable pageable,
                                                             @RequestParam(name ="extraId",required = false, defaultValue = "") List<Long>ids) throws TLEntityNotFound {
        Page<HotelOutView> res = service.getAllByExtras(ids,pageable);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/Extras/{extraId}")
    public ResponseEntity addExtraToHotel(@PathVariable("id") Long id,
                                          @PathVariable(name = "extraId")Long extraId) throws TLEntityNotFound, TLEntityNotActive {
        service.addExtraToHotel(id,extraId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/Extras/{extraId}")
    public ResponseEntity removeExtraToHotel(@PathVariable("id") Long id,
                                             @PathVariable("extraId")Long extraId) throws TLEntityNotFound, TLEntityNotActive {
        service.removeExtraToHotel(id,extraId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/Photos")
    public ResponseEntity<List<HotelPhotoOutView>> getAllPhotos(@PathVariable Long id) throws TLEntityNotFound {
        List<HotelPhotoOutView> res = service.getAllPhotos(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping({"{id}/Rooms"})
    public ResponseEntity<Page<HotelRoomOutView>> getAllHotelRooms(@PathVariable ("id") Long id,
                                                           @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<HotelRoomOutView> res = service.getAllHotelRooms(id,pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping({"{id}/Reservations"})
    public ResponseEntity<Page<ReservationOutView>> getAllReservations(@PathVariable ("id") Long id,
                                                                     @PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<ReservationOutView> res = service.getAllReservations(id,pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/FeedingType")
    public ResponseEntity<List<FeedingTypeOutView>> getAllFeedingTypes(@PathVariable Long id) throws TLEntityNotFound {
        List<FeedingTypeOutView> res = service.getAllFeedingTypes(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/filterFeedingType")
    public ResponseEntity<Page<HotelOutView>> getAllByFeedingType(@PageableDefault Pageable pageable,
                                                             @RequestParam(name ="feedingTypeId",required = false, defaultValue = "") List<Long>ids) throws TLEntityNotFound {
        Page<HotelOutView> res = service.getAllByFeedingType(ids,pageable);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/FeedingTypes/{feedingTypeId}")
    public ResponseEntity addFeedingTypeToHotel(@PathVariable("id") Long id,
                                          @PathVariable(name = "feedingTypeId")Long feedingTypeId) throws TLEntityNotFound, TLEntityNotActive {
        service.addFeedingTypeToHotel(id,feedingTypeId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/FeedingTypes/{feedingType}")
    public ResponseEntity removeFeedingTypeFromHotel(@PathVariable("id") Long id,
                                             @PathVariable("feedingType")Long feedingType) throws TLEntityNotFound, TLEntityNotActive {
        service.removeFeedingTypeFromHotel(id,feedingType);

        return ResponseEntity.ok().build();
    }








}
