package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name="PAYMENTCHANNEL")
public class PaymentChannelEntity extends BaseEntity{
    @Column(name = "NAME",length = 200,nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name="PAYMENTCHANNEL_ID",updatable = false,insertable = false)
    List<ReservationPaymentEntity> reservationPayments;

    public PaymentChannelEntity() {
    }

    public List<ReservationPaymentEntity> getReservationPayments() {
        return reservationPayments;
    }

    public void setReservationPayments(List<ReservationPaymentEntity> reservationPayments) {
        this.reservationPayments = reservationPayments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
