package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity(name ="HOTELROOM")
public class HotelRoomEntity extends BaseEntity{
    @Column(name= "HOTEL_ID",nullable = false)
    private Long hotel_id;
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;
    @Column(name="PRICE",nullable = false)
    private BigDecimal price;
    @Column(name = "DESCRIPTION",length = 4000,nullable = false)
    private String description;
    @Column(name = "MAXADULTS",nullable = false)
    private BigInteger maxAdults;
    @Column(name = "MAXCHILDREN",nullable = false)
    private BigInteger maxChildren;
    @Column(name = "MAXBABIES",nullable = false)
    private BigInteger maxBabies;
    @ManyToOne
    @JoinColumn(name="HOTEL_ID",insertable = false,updatable = false)
    private HotelEntity hotel;


    @OneToMany
    @JoinColumn(name = "HOTELROOM_ID",nullable = false,insertable = false)
    private List<RoomPhotosEntity> photos;


    @OneToMany
    @JoinColumn(name="HOTELROOM_ID",insertable = false,updatable = false)
    private List<ReservationRoomsEntity> reservationRooms;

    public HotelRoomEntity() {
    }

    public List<ReservationRoomsEntity> getReservationRooms() {
        return reservationRooms;
    }

    public void setReservationRooms(List<ReservationRoomsEntity> reservationRooms) {
        this.reservationRooms = reservationRooms;
    }

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public List<RoomPhotosEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RoomPhotosEntity> photos) {
        this.photos = photos;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(BigInteger maxAdults) {
        this.maxAdults = maxAdults;
    }

    public BigInteger getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(BigInteger maxChildren) {
        this.maxChildren = maxChildren;
    }

    public BigInteger getMaxBabies() {
        return maxBabies;
    }

    public void setMaxBabies(BigInteger maxBabies) {
        this.maxBabies = maxBabies;
    }
}
