package org.example.tltravel.view.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Set;

public class UserInView {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role; // Only used to determine what extra data to store

    private AgentFields agentFields;
    private ClientFields clientFields;
    private OperatorFields operatorFields;


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public BigDecimal getCommissionPercent() {
//        return commissionPercent;
//    }
//
//    public void setCommissionPercent(BigDecimal commissionPercent) {
//        this.commissionPercent = commissionPercent;
//    }


    public AgentFields getAgentFields() {
        return agentFields;
    }

    public void setAgentFields(AgentFields agentFields) {
        this.agentFields = agentFields;
    }

    public ClientFields getClientFields() {
        return clientFields;
    }

    public void setClientFields(ClientFields clientFields) {
        this.clientFields = clientFields;
    }

    public OperatorFields getOperatorFields() {
        return operatorFields;
    }

    public void setOperatorFields(OperatorFields operatorFields) {
        this.operatorFields = operatorFields;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public static UserInView empty(){
        return new UserInView();
    }



}
