package org.example.tltravel.view.out;

import java.util.Set;

public class UserOutView {
    private Long id;
    private String email;
    private boolean enabled;
    private Set<String> roles;   // e.g. ["ROLE_AGENT", "ROLE_CLIENT"]

    // If you want to include links to the related sub-entities, you could add:
    private Long agentId;
    private Long clientId;
    private Long operatorId;

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

    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getAgentId() {
        return agentId;
    }
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
