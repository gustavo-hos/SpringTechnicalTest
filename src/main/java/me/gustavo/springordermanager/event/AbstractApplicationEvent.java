package me.gustavo.springordermanager.event;

import org.springframework.context.ApplicationEvent;

public class AbstractApplicationEvent extends ApplicationEvent {

    private final String eventName;

    public AbstractApplicationEvent(String eventName, Object source) {
        super(source);
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
