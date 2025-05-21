package org.example.tltravel.view.out;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class FeedingTypeOutView {
    private Long id;

    private String code;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
