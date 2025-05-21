package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "OPERATOR")
public class OperatorEntity extends BaseEntity {
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;
    @Column(name = "EMAIL",length = 200,nullable = false)
    private String email;
    @Column(name = "PASSWORD",length = 100,nullable = false)
    private String password;
    @Column(name = "PHONE",length = 50,nullable = false)
    private String phone;
    @Column(name = "ADDRESS",length = 500,nullable = false)
    private String address;

    @OneToMany
    @JoinColumn(name = "OPERATOR_ID",insertable = false,updatable = false)
    private List<ReservationEntity> reservations;

    public OperatorEntity() {
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
