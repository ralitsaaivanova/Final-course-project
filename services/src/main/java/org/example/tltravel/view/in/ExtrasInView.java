package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ExtrasInView {
    @Length(min = 2,max = 200)
    @NotBlank
    private String name;

    public  String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }
}
