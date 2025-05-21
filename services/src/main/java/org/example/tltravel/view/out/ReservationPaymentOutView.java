package org.example.tltravel.view.out;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationPaymentOutView {
    private Long id;
    private Long reservation_id;

    private LocalDateTime duedate;

    private Long paymentType_id;

    private Long paymentChannel_id;

    private BigDecimal amount;

    private Boolean isPaid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id( Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public LocalDateTime getDuedate() {
        return duedate;
    }

    public void setDuedate( LocalDateTime duedate) {
        this.duedate = duedate;
    }

    public  Long getPaymentType_id() {
        return paymentType_id;
    }

    public void setPaymentType_id( Long paymentType_id) {
        this.paymentType_id = paymentType_id;
    }

    public  Long getPaymentChannel_id() {
        return paymentChannel_id;
    }

    public void setPaymentChannel_id( Long paymentChannel_id) {
        this.paymentChannel_id = paymentChannel_id;
    }

    public  BigDecimal getAmount() {
        return amount;
    }

    public void setAmount( BigDecimal amount) {
        this.amount = amount;
    }

    public  Boolean getPaid() {
        return isPaid;
    }

    public void setPaid( Boolean paid) {
        isPaid = paid;
    }
}
