package org.example.models;

public class Config {
    private String url;
    private int reconnectFrequency;
    private int retryAmount;

    public int getRetryAmount() {
        return retryAmount;
    }

    public void setRetryAmount(int retryAmount) {
        this.retryAmount = retryAmount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public int getReconnectFrequency() {
        return reconnectFrequency;
    }

    public void setReconnectFrequency(int reconnectFrequency) {
        this.reconnectFrequency = reconnectFrequency;
    }
}
