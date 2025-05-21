package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name="RESERVATIONSTATUS")
public class ReservationStatusEntity extends BaseEntity{
    @Column(name = "CODE",nullable = false,length = 50)
    private String code;
    @Column(name = "NAME",nullable = false,length = 200)
    private String name;

    @OneToMany
    @JoinColumn(name = "RESERVATIONSTATUS_ID",updatable = false,insertable = false)
    private List<ReservationEntity> reservations;



    public ReservationStatusEntity() {
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
