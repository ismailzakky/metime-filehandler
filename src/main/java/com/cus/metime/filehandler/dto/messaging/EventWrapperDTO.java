package com.cus.metime.filehandler.dto.messaging;

import java.io.Serializable;

/**
 * Created by C-US on 9/28/2017.
 */
public class EventWrapperDTO implements Serializable {

    private MessageEvent event;
    private Object message;

    public EventWrapperDTO() {
    }

    public EventWrapperDTO(MessageEvent event, Object message) {
        this.event = event;
        this.message = message;
    }

    public MessageEvent getEvent() {
        return event;
    }

    public void setEvent(MessageEvent event) {
        this.event = event;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }


    @Override
    public int hashCode() {
        int result = getEvent() != null ? getEvent().hashCode() : 0;
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventWrapperDTO{" +
            "event=" + event +
            ", message=" + message +
            '}';
    }
}
