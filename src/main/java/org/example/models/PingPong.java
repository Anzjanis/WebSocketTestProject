package org.example.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PingPong {
    private String event;
    private Integer reqid;

    public PingPong() {
    }

    public PingPong(String event) {
        this.event = event;
    }

    public PingPong(String event, Integer reqid) {
        this.event = event;
        this.reqid = reqid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getReqid() {
        return reqid;
    }

    public void setReqid(Integer reqid) {
        this.reqid = reqid;
    }
}
