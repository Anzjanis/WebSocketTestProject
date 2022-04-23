package org.example.models.subscriber;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include. NON_NULL)
public class Subscription {
    private Integer depth;
    private Integer interval;
    private String name;
    private Boolean ratecounter;
    private Boolean snapshot;

    private Integer maxratecount;

    public Integer getMaxratecount() {
        return maxratecount;
    }

    public void setMaxratecount(Integer maxratecount) {
        this.maxratecount = maxratecount;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRatecounter() {
        return ratecounter;
    }

    public void setRatecounter(Boolean ratecounter) {
        this.ratecounter = ratecounter;
    }

    public Boolean getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Boolean snapshot) {
        this.snapshot = snapshot;
    }
}
