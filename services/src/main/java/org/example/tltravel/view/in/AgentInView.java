package org.example.tltravel.view.in;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class AgentInView {
    @Length(min = 2,max = 200)
    @NotBlank
    private String name;
    @DecimalMin("0.00")
    @DecimalMax("0.99")
    private BigDecimal commissionPercent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(BigDecimal commissionPercent) {
        this.commissionPercent = commissionPercent;
    }
}
