package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "RESERVATION")
public class ReservationEntity extends BaseEntity {
    @Column(name = "HOTEL_ID",nullable = false)
    private Long hotel_id;
    @Column(name = "CLIENT_ID",nullable = false)
    private Long client_id;
    @Column(name = "OPERATOR_ID",nullable = false)
    private Long operator_id;
    @Column(name = "RESERVATIONSTATUS_ID",nullable = false)
    private Long reservationStatus_Id;
    @Column(name = "DATEFROM",nullable = false)
    private LocalDateTime dateFrom;
    @Column(name = "DATETO",nullable = false)
    private LocalDateTime dateTo;
    @Column(name = "CUSTOMERNOTES",nullable = true,length = 4000)
    private String customerNotes;
    @Column(name = "TOTALPRICE",nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name="HOTEL_ID",insertable = false,updatable = false)
    private HotelEntity hotel;


    @ManyToOne
    @JoinColumn(name = "OPERATOR_ID",insertable = false,updatable = false)
    private OperatorEntity operator;


    @ManyToOne
    @JoinColumn(name="CLIENT_ID",insertable = false,updatable = false)
    private ClientEntity client;

    @OneToMany
    @JoinColumn(name="RESERVATION_ID",insertable = false,updatable = false)
    private List<ReservationRoomsEntity> reservationRooms;


    @OneToMany
    @JoinColumn(name = "RESERVATION_ID",updatable = false,insertable = false)
    private List<ReservationPaymentEntity> reservationPayments;

    @ManyToOne
    @JoinColumn(name = "RESERVATIONSTATUS_ID",updatable = false,insertable = false)
    private ReservationStatusEntity reservationStatus;




    public ReservationEntity() {
    }

    public ReservationStatusEntity getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatusEntity reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public List<ReservationPaymentEntity> getReservationPayments() {
        return reservationPayments;
    }

    public void setReservationPayments(List<ReservationPaymentEntity> reservationPayments) {
        this.reservationPayments = reservationPayments;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<ReservationRoomsEntity> getReservationRooms() {
        return reservationRooms;
    }

    public void setReservationRooms(List<ReservationRoomsEntity> reservationRooms) {
        this.reservationRooms = reservationRooms;
    }

    public OperatorEntity getOperator() {
        return operator;
    }

    public void setOperator(OperatorEntity operator) {
        this.operator = operator;
    }

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
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
