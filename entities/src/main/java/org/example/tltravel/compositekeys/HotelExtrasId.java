package org.example.tltravel.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class HotelExtrasId implements Serializable {

    private Long hotel_id;

    private Long extras_id;

    public HotelExtrasId(Long hotel_id, Long extras_id) {
        this.hotel_id = hotel_id;
        this.extras_id = extras_id;
    }

    public HotelExtrasId() {
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
        HotelExtrasId that = (HotelExtrasId) o;
        return Objects.equals(hotel_id, that.hotel_id) && Objects.equals(extras_id, that.extras_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel_id, extras_id);
    }
}
