package org.example.models.subscriber.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradePayload {
    private Integer channelID;
    private ArrayList<ArrayList<String>> tickerInfoMap = new ArrayList<>();
    private String channelName;
    private String pair;

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public ArrayList<ArrayList<String>> getTickerInfoMap() {
        return tickerInfoMap;
    }

    public void setTickerInfoMap(ArrayList<ArrayList<String>> tickerInfoMap) {
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
    public TradePayload buildTradeModel(ArrayList<?> arrayList) {
        TradePayload tradePayload = new TradePayload();
        tradePayload.channelID = Integer.valueOf(arrayList.get(0).toString());
        tradePayload.tickerInfoMap = (ArrayList<ArrayList<String>>)arrayList.get(1);
        tradePayload.channelName = arrayList.get(2).toString();
        tradePayload.pair = arrayList.get(3).toString();

        return tradePayload;
    }
}
