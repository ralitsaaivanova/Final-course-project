package org.example.tltravel.view.out;

import org.example.tltravel.view.in.AgentFields;
import org.example.tltravel.view.in.ClientFields;
import org.example.tltravel.view.in.OperatorFields;

import java.util.Set;

public class UserOutView {
    private Long id;
    private String email;
    private boolean enabled;
    private String role;   // e.g. ["ROLE_AGENT", "ROLE_CLIENT"]

    // If you want to include links to the related sub-entities, you could add:
    private AgentFields agent;
    private ClientFields client;
    private OperatorFields operator;

    public UserOutView() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return role;
    }
    public void setRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AgentFields getAgent() {
        return agent;
    }

    public void setAgent(AgentFields agent) {
        this.agent = agent;
    }

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
}
