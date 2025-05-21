package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class OperatorInView {
    @NotNull
    @Length(max = 200)
    private String name;
    @NotNull
    @Length(max = 200)
    private String email;
    @NotNull
    @Length(max = 100)
    private String password;
    @NotNull
    @Length(max = 50)
    private String phone;
    @NotNull
    @Length(max = 500)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
