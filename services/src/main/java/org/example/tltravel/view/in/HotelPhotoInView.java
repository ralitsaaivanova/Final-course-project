package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotNull;

public class HotelPhotoInView {
    @NotNull
    private Long hotel_id;

    public @NotNull Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(@NotNull Long hotel_id) {
        this.hotel_id = hotel_id;
    }
}
