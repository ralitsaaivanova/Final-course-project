package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotNull;

public class OperatorFields {
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
