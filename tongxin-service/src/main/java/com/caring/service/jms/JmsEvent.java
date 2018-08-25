package com.caring.service.jms;

/**
 *
 * @author james
 */
public class JmsEvent {

    private String type;

    private Long eventId;

    public JmsEvent() {

    }

    public JmsEvent(String type, Long eventId) {
        this.type = type;
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
