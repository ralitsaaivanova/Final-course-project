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

    @NotEmpty
    private Set<String> roles;   // e.g. ["ROLE_AGENT","ROLE_CLIENT"]

    private ClientFields client;

    private OperatorFields operator;

    private AgentFields agent;

    public ClientFields getClient() {
        return client;
    }

    public void setClient(ClientFields client) {
        this.client = client;
    }

    public OperatorFields getOperator() {
        return operator;
    }

    public void setOperator(OperatorFields operator) {
        this.operator = operator;
    }

    public AgentFields getAgent() {
        return agent;
    }

    public void setAgent(AgentFields agent) {
        this.agent = agent;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }


}
