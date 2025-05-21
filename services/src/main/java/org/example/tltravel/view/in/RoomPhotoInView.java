package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotNull;

public class RoomPhotoInView {
    @NotNull
    private Long hotelroom_id;

    public @NotNull Long getHotelRoom_id() {
        return hotelroom_id;
    }

    public void setHotelRoom_id(@NotNull Long hotelroom_id) {
        this.hotelroom_id = hotelroom_id;
    }
}
