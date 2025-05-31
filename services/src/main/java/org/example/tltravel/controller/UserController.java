package org.example.tltravel.controller;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.service.IUserService;
import org.example.tltravel.view.in.UserInView;
import org.example.tltravel.view.out.UserOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private IUserService service;

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
