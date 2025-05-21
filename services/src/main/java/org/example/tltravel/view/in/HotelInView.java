package org.example.tltravel.view.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class HotelInView {


    @Length(min = 2,max = 200)
    @NotBlank
    private String name;
    @Min(1)
    @Max(value = 5,message = "stars must be less than or equal to 5")
    private Integer stars;
    @Length(min=0,max=200)
    @NotBlank
    private String contacts;
    @JsonProperty("isTemporaryClosed")
    @NotNull
    private Boolean isTemporaryClosed;
    @NotNull
    private Long locationId;
    @NotNull
    private Long partnerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
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
}
