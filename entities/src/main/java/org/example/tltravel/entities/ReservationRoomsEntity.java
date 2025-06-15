package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "RESERVATIONROOMS")
public class ReservationRoomsEntity  extends BaseEntity{
    @Column(name="ADULTS", nullable=false,precision = 0)
    private BigDecimal adults;
    @Column(name="CHILDREN", nullable=false,precision = 0)
    private BigDecimal children;
    @Column(name="BABIES", nullable=false,precision = 0)
    private BigDecimal babies;
    @Column(name="PRICE", nullable=false)
    private BigDecimal price;
//    @Id
//    @Column(name="ID",nullable = false)
//    private Long id;
    @Column(name="HOTELROOM_ID", nullable=false)
    private Long hotelRoom_id;

    @Column(name = "RESERVATION_ID",nullable = false)
    private Long reservation_id;
    @Column(name="FEEDINGTYPE_ID", nullable=false)
    private Long feedingType_id;

    @ManyToOne
    @JoinColumn(name="RESERVATION_ID",insertable = false, updatable = false)
    private ReservationEntity reservation;


    @ManyToOne
    @JoinColumn(name = "HOTELROOM_ID",insertable = false,updatable = false)
    private HotelRoomEntity hotelRoom;

    public ReservationRoomsEntity() {
    }

    public HotelRoomEntity getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoomEntity hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public BigDecimal getAdults() {
        return adults;
    }

    public void setAdults(BigDecimal adults) {
        this.adults = adults;
    }

    public BigDecimal getChildren() {
        return children;
    }

    public void setChildren(BigDecimal children) {
        this.children = children;
    }

    public BigDecimal getBabies() {
        return babies;
    }

    public void setBabies(BigDecimal babies) {
        this.babies = babies;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelRoom_id() {
        return hotelRoom_id;
    }

    public void setHotelRoom_id(Long hotelRoom_id) {
        this.hotelRoom_id = hotelRoom_id;
    }

    public Long getFeedingType_id() {
        return feedingType_id;
    }

    public void setFeedingType_id(Long feedingType_id) {
        this.feedingType_id = feedingType_id;
    }
}
