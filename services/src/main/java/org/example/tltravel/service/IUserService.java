package org.example.tltravel.service;

import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.view.in.UserInView;
import org.example.tltravel.view.out.UserOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {
    UserOutView createUser(UserInView inView);
    Page<UserOutView> getAll(Pageable pageable) throws TLEntityNotFound;

    Optional<UserOutView> getById(Long id) throws TLEntityNotFound;

    UserOutView update(Long id, UserInView inView) throws TLEntityNotFound;

    void delete(Long id) throws TLEntityNotFound, TLEntityNotActive;
}
