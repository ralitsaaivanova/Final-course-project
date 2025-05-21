package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.sql.Blob;


@Entity(name ="HOTELPHOTOS")
public class HotelPhotosEntity extends BaseEntity{
    @Column(name="HOTEL_ID", nullable=false)
    private Long hotel_id;

    @Lob
    @Column(name = "PHOTO",nullable = false)
    private Blob photo;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID",insertable = false,updatable = false)
    private HotelEntity hotel;

    public HotelPhotosEntity() {
    }


    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
