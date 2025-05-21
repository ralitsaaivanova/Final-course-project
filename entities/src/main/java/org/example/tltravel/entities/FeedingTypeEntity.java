package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name ="FEEDINGTYPE")
public class FeedingTypeEntity extends BaseEntity{
    @Column(name="CODE",length = 50,nullable = true)
    private String code;
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;

    public FeedingTypeEntity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
