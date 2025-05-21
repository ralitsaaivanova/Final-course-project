package org.example.tltravel.view.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class LocationInView {
    @Length(min = 2,max = 200)
    @NotBlank
    private String name;
    @NotNull
    @JsonProperty("isAbroad")
    private Boolean isAbroad;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }
}
