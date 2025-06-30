package org.example.tltravel.controller;


import jakarta.servlet.http.HttpSession;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/Agent")
public class AgentController {

    @Autowired
    private IAgentService service;

    @GetMapping("/{id}/HotelView")
    public ModelAndView showHotelsByAgent(@PathVariable("id") Long agentId,
                                          @PageableDefault(size = 10) Pageable pageable,
                                          Model model,
                                          HttpSession session) throws TLEntityNotFound {
        Page<HotelOutView> hotels = service.getAllHotels(agentId, pageable);
        model.addAttribute("hotels", hotels);
        model.addAttribute("agents",service.getAll(pageable));

        model.addAttribute("agentId", agentId);
//        session.setAttribute("agentId", agentId);
        return new ModelAndView("agentHotels");
    }


//    @GetMapping({"{id}/Hotel"})
//    public ResponseEntity<Page<HotelOutView>> getAllHotels(@PathVariable ("id") Long id,
//                                                     @PageableDefault Pageable pageable) throws TLEntityNotFound {
//        Page<HotelOutView> res = service.getAllHotels(id,pageable);
//        return ResponseEntity.ok(res);
//    }
}
