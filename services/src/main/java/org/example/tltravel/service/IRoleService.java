package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.RoleInView;
import org.example.tltravel.view.out.RoleOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRoleService {
    public Page<RoleOutView> getAll(Pageable pageable) throws TLEntityNotFound;

    public Optional<RoleOutView> getById(Long id) throws TLEntityNotFound;

    public RoleOutView addOne(RoleInView body) throws TLEntityNotFound;

    public void deleteOne(Long id) throws TLEntityNotFound;

    public RoleOutView updateOne(Long id, RoleInView body) throws TLEntityNotFound;
}
