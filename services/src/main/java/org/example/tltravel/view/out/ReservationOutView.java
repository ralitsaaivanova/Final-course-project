package org.example.tltravel.view.out;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationOutView {

    private Long id;
    private Long hotel_id;
    private Long client_id;
    private Long operator_id;
    private Long reservationStatus_Id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String customerNotes;
    private BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(Long operator_id) {
        this.operator_id = operator_id;
    }

    public Long getReservationStatus_Id() {
        return reservationStatus_Id;
    }

    public void setReservationStatus_Id(Long reservationStatus_Id) {
        this.reservationStatus_Id = reservationStatus_Id;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
