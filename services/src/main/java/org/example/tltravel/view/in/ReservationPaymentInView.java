package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationPaymentInView {
    @NotNull
    private Long reservation_id;
    @NotNull
    private LocalDateTime duedate;
    @NotNull
    private Long paymentType_id;
    @NotNull
    private Long paymentChannel_id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Boolean isPaid;

    public  Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id( Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public @NotNull LocalDateTime getDuedate() {
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

    public Long getPaymentChannel_id() {
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
