package org.example.models.subscriber.spread;

import io.cucumber.java.bs.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpreadPayload {
    private Integer channelID;
    private List<String>  spreadInfoList = new ArrayList<>();
    private String channelName;
    private String pair;

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public List<String> getSpreadInfoList() {
        return spreadInfoList;
    }

    public void setSpreadInfoList(List<String> spreadInfoList) {
        this.spreadInfoList = spreadInfoList;
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
    public SpreadPayload buildSpreadModel(ArrayList<?> arrayList) {
        SpreadPayload tickerPayload = new SpreadPayload();
        tickerPayload.channelID = Integer.valueOf(arrayList.get(0).toString());
        tickerPayload.spreadInfoList = (List<String> )arrayList.get(1);
        tickerPayload.channelName = arrayList.get(2).toString();
        tickerPayload.pair = arrayList.get(3).toString();

        return tickerPayload;
    }
}
