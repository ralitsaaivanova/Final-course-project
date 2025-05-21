package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "RESERVATIONPAYMENT")
public class ReservationPaymentEntity extends BaseEntity{
    @Column(name = "RESERVATION_ID",nullable = false)
    private Long reservation_id;
    @Column(name = "DUEDATE",nullable = false)
    private LocalDateTime duedate;
    @Column(name = "PAYMENTTYPE_ID",nullable = false)
    private Long paymentType_id;
    @Column(name = "PAYMENTCHANNEL_ID",nullable = false)
    private Long paymentChannel_id;
    @Column(name = "AMOUNT",nullable = false)
    private BigDecimal amount;
    @Column(name = "ISPAID",nullable = false)
    private Boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "PAYMENTTYPE_ID",insertable = false,updatable = false)
    private PaymentTypeEntity paymentType;

    @ManyToOne
    @JoinColumn(name="RESERVATION_ID",insertable = false,updatable = false)
    private ReservationEntity reservation;

    @ManyToOne
    @JoinColumn(name ="PAYMENTCHANNEL_ID",insertable = false,updatable = false)
    private PaymentChannelEntity paymentChannel;



    public ReservationPaymentEntity() {
    }

    public PaymentChannelEntity getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannelEntity paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public PaymentTypeEntity getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEntity paymentType) {
        this.paymentType = paymentType;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public LocalDateTime getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDateTime duedate) {
        this.duedate = duedate;
    }

    public Long getPaymentType_id() {
        return paymentType_id;
    }

    public void setPaymentType_id(Long paymentType_id) {
        this.paymentType_id = paymentType_id;
    }

    public Long getPaymentChannel_id() {
        return paymentChannel_id;
    }

    public void setPaymentChannel_id(Long paymentChannel_id) {
        this.paymentChannel_id = paymentChannel_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }


}
