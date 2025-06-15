package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class FeedingTypeInView {
    @NotNull
    @Length(max = 50)
    private String code;
    @NotNull
    @Length(max = 200)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public static FeedingTypeInView empty(){
        return new FeedingTypeInView();
    }
}
