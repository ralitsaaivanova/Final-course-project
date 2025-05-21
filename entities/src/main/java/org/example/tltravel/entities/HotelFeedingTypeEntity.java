package org.example.tltravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.example.tltravel.compositekeys.HotelFeedingTypeId;

@IdClass(HotelFeedingTypeId.class)
@Entity(name = "HOTELFEEDINGTYPES")
public class HotelFeedingTypeEntity extends AuditableEntity{
    @Id
    @Column(name = "HOTEL_ID",nullable = false)
    private Long hotel_id;

    @Id
    @Column(name = "FEEDINGTYPE_ID",nullable = false)
    private Long feedingType_id;

    public HotelFeedingTypeEntity() {
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Long getFeedingType_id() {
        return feedingType_id;
    }

    public void setFeedingType_id(Long feedingType_id) {
        this.feedingType_id = feedingType_id;
    }
}
