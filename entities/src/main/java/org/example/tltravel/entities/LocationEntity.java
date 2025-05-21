package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="LOCATION")
public class LocationEntity extends BaseEntity{
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;
    @Column(name = "ISABROAD",nullable = false)
    private Boolean isAbroad;

    public List<HotelEntity> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelEntity> hotels) {
        this.hotels = hotels;
    }

    @OneToMany
    @JoinColumn(name = "LOCATION_ID",insertable = false,updatable = false)
    private List<HotelEntity> hotels;

    public LocationEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }
}
