package org.example.tltravel.controller;



import org.example.tltravel.exceptions.TLEntityNotFound;

import org.example.tltravel.service.IRoleService;
import org.example.tltravel.view.in.RoleInView;
import org.example.tltravel.view.out.RoleOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Admin/Roles")

public class RoleController {

    @Autowired
    private IRoleService roleService;
    @GetMapping({""})
    public ResponseEntity<Page<RoleOutView>> getAll(@PageableDefault Pageable pageable) throws TLEntityNotFound {
        Page<RoleOutView> res = roleService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleOutView> getById(@PathVariable Long id) throws TLEntityNotFound {
        Optional<RoleOutView> res = roleService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @PostMapping({""})
    public ResponseEntity<RoleOutView> addOne(@Validated @RequestBody RoleInView role) throws TLEntityNotFound {
        RoleOutView roleOutView = roleService.addOne(role);
        return ResponseEntity.ok(roleOutView);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws TLEntityNotFound{
        roleService.deleteOne(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<RoleOutView> updateOne(@PathVariable ("id") Long id, @Validated @RequestBody RoleInView role) throws TLEntityNotFound {
        RoleOutView re = roleService.updateOne(id,role);
        return  ResponseEntity.ok(re);
    }
}
