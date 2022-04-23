package org.example.models;

public class Config {
    private String url;
    private int reconnectTimeAmount;
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
    public int getReconnectTimeAmount() {
        return reconnectTimeAmount;
    }

    public void setReconnectTimeAmount(int reconnectTimeAmount) {
        this.reconnectTimeAmount = reconnectTimeAmount;
    }
}
