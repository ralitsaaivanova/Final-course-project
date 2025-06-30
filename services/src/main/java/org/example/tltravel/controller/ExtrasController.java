package org.example.tltravel.controller;

import jakarta.validation.Valid;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IExtrasService;
import org.example.tltravel.view.in.ExtrasInView;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.out.ExtrasOutView;
import org.example.tltravel.view.out.HotelOutView;
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

import java.util.Optional;

@Controller
@RequestMapping("/Extras")
public class ExtrasController {
    @Autowired
    private IExtrasService service;

    @GetMapping("/extrasInfo")
    public ModelAndView showForm(@RequestParam(value="id", required=false) Long id,Model model) throws TLEntityNotFound {
        ExtrasInView inView = ExtrasInView.empty();
        if (id != null) {
            // fetch existing
            ExtrasOutView out = service
                    .getById(id)
                    .orElseThrow(() -> new TLEntityNotFound("Hotel not found: " + id));
            inView = ExtrasInView.from(out);
        }
        model.addAttribute("extrasInView", inView);
        model.addAttribute("id",id);
        model.addAttribute("extras",service.getAll(PageRequest.of(0,10)));
        return new ModelAndView ("extras");
    }

    @PostMapping("/extrasInfo")
    public ModelAndView addExtra(@RequestParam(value = "id", required = false) Long id,
                                @ModelAttribute("extrasInView") @Valid ExtrasInView extrasInView,
                                 RedirectAttributes flash,
                                    BindingResult bindingResult,
                                    Model model) throws TLEntityNotFound {
        if(bindingResult.hasErrors()){
            model.addAttribute("extras",service.getAll(PageRequest.of(0,10)));
            model.addAttribute("id",id);
            return new ModelAndView("extras");

        }

        if (id != null) {
            service.update(id, extrasInView);
            flash.addFlashAttribute("message", "Hotel updated");
        } else {
            service.addOne(extrasInView);
            flash.addFlashAttribute("message", "Hotel created");
        }

        return new ModelAndView("redirect:/Extras/extrasInfo?success");
    }

    @PostMapping("/extrasInfo/delete")
    public ModelAndView deleteExtra(
            @RequestParam("id") Long id,
            RedirectAttributes flash
    ) throws TLEntityNotFound, TLEntityNotActive {
        service.deleteOne(id);
        flash.addFlashAttribute("message", "Extra has been deleted");
        // redirect back to the empty form (and your table)
        return new ModelAndView("redirect:/Extras/extrasInfo");
    }


//    @GetMapping({""})
//    public ResponseEntity<Page<ExtrasOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
//        Page<ExtrasOutView> res = service.getAll(pageable);
//        return ResponseEntity.ok(res);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ExtrasOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
//        Optional<ExtrasOutView> res = service.getById(id);
//        if(res.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(res.get());
//    }
//
//    @PostMapping({""})
//    public ResponseEntity<ExtrasOutView> addOne(@Validated @RequestBody ExtrasInView hotel) throws TLEntityNotFound {
//        ExtrasOutView extrasOutView = service.addOne(hotel);
//        return ResponseEntity.ok(extrasOutView);
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
//    public ResponseEntity<ExtrasOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody ExtrasInView hotel) throws TLEntityNotFound {
//        ExtrasOutView re = service.update(id,hotel);
//        return  ResponseEntity.ok(re);
//    }

}
