package org.example.models.subscriber.ticker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TickerPayload {
    private Integer channelID;
    private Map<String, List<String>> tickerInfoMap = new HashMap<>();
    private String channelName;
    private String pair;

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public Map<String, List<String>> getTickerInfoMap() {
        return tickerInfoMap;
    }

    public void setTickerInfoMap(Map<String, List<String>> tickerInfoMap) {
        this.tickerInfoMap = tickerInfoMap;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    @SuppressWarnings("unchecked")
    public TickerPayload buildTickerModel(ArrayList<?> arrayList) {
        TickerPayload tickerPayload = new TickerPayload();
        tickerPayload.channelID = Integer.valueOf(arrayList.get(0).toString());
        tickerPayload.tickerInfoMap = (Map<String, List<String>>)arrayList.get(1);
        tickerPayload.channelName = arrayList.get(2).toString();
        tickerPayload.pair = arrayList.get(3).toString();

        return tickerPayload;
    }
}
