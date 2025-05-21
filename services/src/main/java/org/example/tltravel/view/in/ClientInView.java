package org.example.tltravel.view.in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ClientInView {
    @NotNull
    @Length(max = 200)
    private String name;
    @NotNull
    @Length(max = 200)
    private String email;
    @NotNull
    @Length(max = 50)
    private String phone;

    public @NotNull @Length(max = 200) String getName() {
        return name;
    }

    public void setName(@NotNull @Length(max = 200) String name) {
        this.name = name;
    }

    public @NotNull @Length(max = 200) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Length(max = 200) String email) {
        this.email = email;
    }

    public @NotNull @Length(max = 50) String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull @Length(max = 50) String phone) {
        this.phone = phone;
    }
}
