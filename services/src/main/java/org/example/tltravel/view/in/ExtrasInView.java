package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotBlank;
import org.example.tltravel.view.out.ExtrasOutView;
import org.hibernate.validator.constraints.Length;

public class ExtrasInView {
    @Length(min = 2,max = 200)
    @NotBlank
    private String name;

    public static ExtrasInView from(ExtrasOutView out) {
        ExtrasInView in = new ExtrasInView();
        in.setName(out.getName());
        return in;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ExtrasInView empty(){
        return new ExtrasInView();
    }
}
