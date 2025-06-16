package org.example.tltravel.view.in;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AgentFields {
    @NotNull
    private  String name;
    @NotNull
//    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer.class)
    private Double commissionPercent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Double commissionPercent) {
        this.commissionPercent = commissionPercent;
    }
}
