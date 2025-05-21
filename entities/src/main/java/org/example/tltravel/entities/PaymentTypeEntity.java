package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "PAYMENTTYPE")
public class PaymentTypeEntity extends BaseEntity{
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;

//    @OneToMany
//    @JoinColumn(name = "PAYMENTTYPE_ID",insertable = false,updatable = false)
//    private List<ReservationPaymentEntity> reservationPayment;



    public PaymentTypeEntity() {
    }

//    public List<ReservationPaymentEntity> getReservationPayment() {
//        return reservationPayment;
//    }
//
//    public void setReservationPayment(List<ReservationPaymentEntity> reservationPayment) {
//        this.reservationPayment = reservationPayment;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
