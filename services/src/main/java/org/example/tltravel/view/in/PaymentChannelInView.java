package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class PaymentChannelInView {
    @NotNull
    @Length(max=200)
    private String name;

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }
}
