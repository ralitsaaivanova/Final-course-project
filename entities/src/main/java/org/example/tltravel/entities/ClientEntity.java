package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="CLIENT")
public class ClientEntity extends BaseEntity{
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;

    @Column(name = "PHONE",length = 50,nullable = false)
    private String phone;


    @OneToMany
    @JoinColumn(name="CLIENT_ID",insertable = false,updatable = false)
    private List<ReservationEntity> reservations;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID", unique = true,nullable = false)
//    private UserEntity user;


    public ClientEntity() {
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

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }

}
