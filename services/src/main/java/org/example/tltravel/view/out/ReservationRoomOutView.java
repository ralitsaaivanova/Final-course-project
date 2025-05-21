package org.example.tltravel.view.out;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ReservationRoomOutView {
    private Long id;
    private BigDecimal adults;
    private BigDecimal children;
    private BigDecimal babies;
    private BigDecimal price;
    private Long hotelRoom_id;
    private Long reservation_id;
    private Long feedingType_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getHotelRoom_id() {
        return hotelRoom_id;
    }

    public void setHotelRoom_id(Long hotelRoom_id) {
        this.hotelRoom_id = hotelRoom_id;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Long getFeedingType_id() {
        return feedingType_id;
    }

    public void setFeedingType_id(Long feedingType_id) {
        this.feedingType_id = feedingType_id;
    }
}
