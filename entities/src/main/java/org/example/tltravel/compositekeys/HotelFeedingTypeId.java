package org.example.tltravel.compositekeys;

import java.io.Serializable;

public class HotelFeedingTypeId implements Serializable {
    private Long hotel_id;
    private Long feedingType_id;

    public HotelFeedingTypeId(Long hotel_id, Long feedingType_id) {
        this.hotel_id = hotel_id;
        this.feedingType_id = feedingType_id;
    }

    public HotelFeedingTypeId() {
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
