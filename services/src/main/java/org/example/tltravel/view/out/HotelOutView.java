package org.example.tltravel.view.out;

public class HotelOutView {
    private Long id;
    private String name;
    private Integer stars;
    private String contacts;
    private Boolean isTemporaryClosed;
    private Long locationId;
    private Long partnerId;

//    не е необходимо
//    private AgentOutView agent;
//
//    public AgentOutView getAgent() {
//        return agent;
//    }
//
//    public void setAgent(AgentOutView agent) {
//        this.agent = agent;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Boolean getIsTemporaryClosed() {
        return isTemporaryClosed;
    }

    public void setIsTemporaryClosed(Boolean temporaryClosed) {
        isTemporaryClosed = temporaryClosed;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }
}
