package org.example.tltravel.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "ROLE")
public class RoleEntity extends BaseEntity {
    @Column(name = "NAME", unique = true, nullable = false)
    private String name; // e.g. "CLIENT", "AGENT", "OPERATOR"

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleEntity> userRoles;

    public RoleEntity() {}

    public RoleEntity(String name) {
        this.name = name;
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
