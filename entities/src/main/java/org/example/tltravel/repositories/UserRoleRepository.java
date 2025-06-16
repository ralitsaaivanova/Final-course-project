package org.example.tltravel.repositories;

import org.example.tltravel.compositekeys.UserRoleId;
import org.example.tltravel.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {
}
