package org.example.tltravel.service.impl;

import org.example.tltravel.entities.RoleEntity;
import org.example.tltravel.entities.UserEntity;
import org.example.tltravel.entities.UserRoleEntity;
import org.example.tltravel.repositories.RoleRepository;
import org.example.tltravel.repositories.UserRepository;
import org.example.tltravel.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        List<UserRoleEntity> userRoles =  userRoleRepository.findAllByUserIdAndActiveTrue(user.getId());

        Set<String> roleNames = userRoles.stream()
                .map(userRole -> roleRepository.findById(userRole.getRole_id()))
                .filter(Optional::isPresent)
                .map(optionalRole -> optionalRole.get().getName())
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = roleNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        System.out.println("User " + email + " has authorities: " + authorities);  // Log here

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();

//        return userRepository.findByEmail(email)
//                .map(UserDetailsServiceImpl::map)
//                .orElseThrow(()->new UsernameNotFoundException("User " + email + " not found!")) ;
    }

//    private static UserDetails map(UserEntity userEntity) {
//        return org.springframework.security.core.userdetails.User
//                .withUsername(userEntity.getEmail())
//                .password(userEntity.getPassword())
//                .authorities(userEntity.getRoles().stream().map(UserDetailsServiceImpl::map).toList())
//                .build();
//    }
}
