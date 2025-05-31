package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "ROLE")
public class RoleEntity extends BaseEntity {
    @Column(name = "NAME", unique = true, nullable = false)
    private String name; // e.g. "CLIENT", "AGENT", "OPERATOR"

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
