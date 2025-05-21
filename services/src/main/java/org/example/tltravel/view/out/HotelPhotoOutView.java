package org.example.tltravel.view.out;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.sql.Blob;

public class HotelPhotoOutView {
    private Long id;
    private Long hotel_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }
}
