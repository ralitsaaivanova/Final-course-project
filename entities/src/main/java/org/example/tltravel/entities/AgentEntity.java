package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="AGENT")
public class AgentEntity extends BaseEntity{
    @Column(name = "NAME",length = 200, nullable = false)
    private String name;
    @Column(name = "COMISSIONPERCENT", precision = 2,scale = 2, nullable = true)
    private BigDecimal commissionPercent;

    @OneToMany
    @JoinColumn(name = "PARTNER_ID",insertable = false,updatable = false)
    private List<HotelEntity> hotels;

    public List<HotelEntity> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelEntity> hotels) {
        this.hotels = hotels;
    }

    public AgentEntity() {
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
