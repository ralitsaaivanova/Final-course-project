package org.example.tltravel.view.out;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.List;

public class AgentOutView {
    private Long id;
    private String name;
    private BigDecimal commissionPercent;

    //не е необходимо
//    private List<HotelOutView> hotels;
//
//    public List<HotelOutView> getHotels() {
//        return hotels;
//    }
//
//    public void setHotels(List<HotelOutView> hotels) {
//        this.hotels = hotels;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(BigDecimal commissionPercent) {
        this.commissionPercent = commissionPercent;
    }
}
