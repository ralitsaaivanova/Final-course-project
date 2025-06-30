package org.example.tltravel.entities;

import jakarta.persistence.*;
import org.example.tltravel.compositekeys.UserRoleId;

import java.util.Objects;

@IdClass(UserRoleId.class)
@Entity(name = "USERROLES")
public class UserRoleEntity extends AuditableEntity{
    @Id
    @Column(name = "USER_ID",nullable = false)
    private Long user_id;

    @Id
    @Column(name = "ROLE_ID",nullable = false)
    private Long role_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    private RoleEntity role;
    public UserRoleEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(role_id, that.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, role_id);
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
}
