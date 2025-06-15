package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.example.tltravel.entities.HotelEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.math.BigInteger;

public class HotelRoomInView {
    @NotNull
    private Long hotel_id;
    @NotNull
    @Length(max = 200)
    private String name;
    @NotNull
    private BigDecimal price;
    @Length(max=4000)
    private String description;
    @NotNull
    private BigInteger maxAdults;
    @NotNull
    private BigInteger maxChildren;
    @NotNull
    private BigInteger maxBabies;

    public @NotNull Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(@NotNull Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public @NotNull @Length(max = 200) String getName() {
        return name;
    }

    public void setName(@NotNull @Length(max = 200) String name) {
        this.name = name;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull BigInteger getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(@NotNull BigInteger maxChildren) {
        this.maxChildren = maxChildren;
    }

    public @NotNull BigInteger getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(@NotNull BigInteger maxAdults) {
        this.maxAdults = maxAdults;
    }

    public @NotNull BigInteger getMaxBabies() {
        return maxBabies;
    }

    public void setMaxBabies(@NotNull BigInteger maxBabies) {
        this.maxBabies = maxBabies;
    }

    public static HotelRoomInView empty(){
        return new HotelRoomInView();
    }
}
