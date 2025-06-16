package org.example.tltravel.controller;

import jakarta.validation.Valid;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IUserService;
import org.example.tltravel.view.in.HotelInView;
import org.example.tltravel.view.in.UserInView;
import org.example.tltravel.view.out.UserOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private IUserService service;


    @GetMapping("/addUser")
    public ModelAndView userPage(@RequestParam(value="id", required=false) Long id, Model model) {
        UserInView inView = UserInView.empty();
        model.addAttribute("userInView", inView);
        return new ModelAndView("user");
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(@RequestParam(value = "id", required = false) Long id,
                                 @ModelAttribute("userInView") @Valid UserInView userInView,
                                 BindingResult bindingResult,
                                 RedirectAttributes flash,
                                 Model model) throws TLEntityNotFound {
        if(bindingResult.hasErrors()){
//            model.addAttribute("locations", locationService.getAll(PageRequest.of(0,100)));
//            model.addAttribute("partners",  agentService.getAll(PageRequest.of(0,100)));
//            model.addAttribute("hotels",service.getAll(PageRequest.of(0,10)));
            model.addAttribute("userId",id);
            return new ModelAndView("user");

        }
        if (id != null) {
            service.update(id, userInView);
            flash.addFlashAttribute("message", "User updated");
        } else {
            service.createUser(userInView);
            flash.addFlashAttribute("message", "User created");
        }
        return new ModelAndView("redirect:/Users/addUser?success");
    }

    @GetMapping("")
    public ResponseEntity<Page<UserOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<UserOutView> res = service.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<UserOutView> res = service.getById(id);
        if (res.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<UserOutView> createUser(@Validated @RequestBody UserInView inView) throws TLEntityNotFound {
        UserOutView userOut = service.createUser(inView);
        return ResponseEntity.ok(userOut);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutView> updateUser(
            @PathVariable Long id,
            @Validated @RequestBody UserInView inView
    ) throws TLEntityNotFound {
        UserOutView updated = service.update(id, inView);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws TLEntityNotFound, TLEntityNotActive {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
