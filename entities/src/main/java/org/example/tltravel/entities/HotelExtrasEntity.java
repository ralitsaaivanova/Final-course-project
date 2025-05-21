package org.example.tltravel.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.example.tltravel.compositekeys.HotelExtrasId;

import java.util.Objects;

@IdClass(HotelExtrasId.class)
@Entity(name = "HOTELEXTRAS")
public class HotelExtrasEntity extends AuditableEntity{
    @Id
    @Column(name = "HOTEL_ID",nullable = false)
    private Long hotel_id;

    @Id
    @Column(name = "EXTRAS_ID",nullable = false)
    private Long extras_id;

    public HotelExtrasEntity() {
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Long getExtras_id() {
        return extras_id;
    }

    public void setExtras_id(Long extras_id) {
        this.extras_id = extras_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelExtrasEntity that = (HotelExtrasEntity) o;
        return Objects.equals(hotel_id, that.hotel_id) && Objects.equals(extras_id, that.extras_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel_id, extras_id);
    }
}
