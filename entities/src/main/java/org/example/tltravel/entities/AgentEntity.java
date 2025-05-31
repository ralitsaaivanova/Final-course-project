package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="AGENT")
public class AgentEntity extends BaseEntity{
    @Column(name = "NAME",length = 200, nullable = false)
    private String name;
    @Column(name = "COMMISSIONPERCENT", precision = 5,scale = 2, nullable = true)
    private BigDecimal commissionPercent;

    @OneToMany
    @JoinColumn(name = "PARTNER_ID",insertable = false,updatable = false)
    private List<HotelEntity> hotels;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", unique = true, nullable = false)
    private UserEntity user;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
