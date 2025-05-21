package org.example.tltravel.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="HOTEL")
public class HotelEntity extends BaseEntity{

    @Column(name = "NAME",length = 200,nullable = false)
    private String name;
    @Column(name = "STARS",nullable = false)
    private BigInteger stars;
    @Column(name = "CONTACTS",length = 500,nullable = false)
    private String contacts;
    @Column(name = "ISTEMPORARYCLOSED",nullable = false)
    private Boolean isTemporaryClosed;
    @Column(name = "LOCATION_ID",nullable = false)
    private Long locationId;
    @Column(name = "PARTNER_ID",nullable = false)
    private Long partnerId;

    @ManyToOne
    @JoinColumn(name = "PARTNER_ID",insertable = false,updatable = false)
    private AgentEntity agent;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID",insertable = false,updatable = false)
    private LocationEntity location;

    @OneToMany
    @JoinColumn(name = "HOTEL_ID",insertable = false,updatable = false)
    private List<HotelPhotosEntity> hotelPhotos;

    @OneToMany
    @JoinColumn(name="HOTEL_ID",insertable = false,updatable = false)
    private List<HotelRoomEntity>hotelRooms;


    @ManyToMany
    @JoinTable(
            name="HOTELEXTRAS",
            joinColumns = @JoinColumn(name = "HOTEL_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXTRAS_ID")
    )
    private List<ExtrasEntity> extras;



    @ManyToMany
    @JoinTable(name="HOTELFEEDINGTYPES",
            joinColumns = @JoinColumn(name="HOTEL_ID"),
            inverseJoinColumns = @JoinColumn(name = "FEEDINGTYPE_ID")
    )
    private List<FeedingTypeEntity> feedingTypes;


    @OneToMany
    @JoinColumn(name="HOTEL_ID",insertable = false,updatable = false)
    private List<ReservationEntity> reservations;



    public HotelEntity() {
    }

    public List<FeedingTypeEntity> getFeedingTypes() {
        return feedingTypes;
    }

    public void setFeedingTypes(List<FeedingTypeEntity> feedingTypes) {
        this.feedingTypes = feedingTypes;
    }

    public List<ExtrasEntity> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasEntity> extras) {
        this.extras = extras;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public List<HotelPhotosEntity> getHotelPhotos() {
        return hotelPhotos;
    }

    public void setHotelPhotos(List<HotelPhotosEntity> hotelPhotos) {
        this.hotelPhotos = hotelPhotos;
    }

    public List<HotelRoomEntity> getHotelRooms() {
        return hotelRooms;
    }

    public void setHotelRooms(List<HotelRoomEntity> hotelRooms) {
        this.hotelRooms = hotelRooms;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public AgentEntity getAgent() {
        return agent;
    }

    public void setAgent(AgentEntity agent) {
        this.agent = agent;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getStars() {
        return stars;
    }

    public void setStars(BigInteger stars) {
        this.stars = stars;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Boolean getIsTemporaryClosed() {
        return isTemporaryClosed;
    }

    public void setIsTemporaryClosed(Boolean temporaryClosed) {
        isTemporaryClosed = temporaryClosed;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "HotelEntity{" +
                "name='" + name + '\'' +
                ", stars=" + stars +
                ", contacts='" + contacts + '\'' +
                ", isTemporaryClosed=" + isTemporaryClosed +
                ", locationId=" + locationId +
                ", partnerId=" + partnerId +
                ", id=" + id +
                ", isActive=" + isActive +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
