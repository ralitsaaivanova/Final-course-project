package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity(name = "ROOMPHOTOS")
public class RoomPhotosEntity extends BaseEntity {
    @Column(name="HOTELROOM_ID",nullable = false)
    private Long hotelRoomId;
    @Lob
    @Column(name = "PHOTO",nullable = false)
    private Blob photo;


    @ManyToOne
    @JoinColumn(name = "HOTELROOM_ID",insertable = false,updatable = false)
    private HotelRoomEntity hotelRoom;

    public RoomPhotosEntity() {
    }

    public Long getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelRoomId(Long hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
