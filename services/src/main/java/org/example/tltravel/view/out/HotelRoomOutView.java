package org.example.tltravel.view.out;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.tltravel.entities.HotelEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class HotelRoomOutView {
    private Long id;
    private Long hotel_id;
    private String name;
    private BigDecimal price;
    private String description;
    private BigInteger maxAdults;
    private BigInteger maxChildren;
    private BigInteger maxBabies;

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
