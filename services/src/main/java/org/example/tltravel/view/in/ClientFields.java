package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotNull;

public class ClientFields {
    @NotNull
    private String name;
    @NotNull
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
