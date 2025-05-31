package org.example.tltravel.view.in;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;

public class RoleInView {
    @Length(min = 2, max = 50)
    @NotNull
    private String name;

    public RoleInView() {
    }

    public @Length(min = 2, max = 20) @NotNull String getName() {
        return name;
    }

    public void setName(@Length(min = 2, max = 20) @NotNull String name) {
        this.name = name;
    }


}
