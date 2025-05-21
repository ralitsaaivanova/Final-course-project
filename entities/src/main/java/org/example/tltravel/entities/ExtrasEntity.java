package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "EXTRAS")
public class ExtrasEntity extends BaseEntity {
    @Column(name = "NAME",length = 200, nullable = false)
    private String name;


    public ExtrasEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
