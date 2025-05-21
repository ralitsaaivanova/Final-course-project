package org.example.tltravel.view.out;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ClientOutView {
    private Long id;

    private String name;

    private String email;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public  String getPhone() {
        return phone;
    }

    public void setPhone( String phone) {
        this.phone = phone;
    }
}
