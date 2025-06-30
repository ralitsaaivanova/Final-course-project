package org.example.tltravel.repositories;

import org.example.tltravel.compositekeys.UserRoleId;
import org.example.tltravel.entities.RoleEntity;
import org.example.tltravel.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {
    @Query("SELECT  h from USERROLES h WHERE h.user_id = :id")
    UserRoleEntity findByUserId(@Param("id")Long id);

    @Query("SELECT  h from USERROLES h WHERE h.user_id = :id AND h.isActive = true")
    List<UserRoleEntity> findAllByUserIdAndActiveTrue (@Param("id")Long id);
}
