package org.example.context;


import io.cucumber.java.Scenario;
import io.cucumber.java.it.Ma;
import org.example.config.ConfigReader;
import org.example.models.Config;
import org.example.models.PingPong;
import org.example.models.subscriber.Subscribe;
import org.example.models.subscriber.SubscriptionStatus;
import org.example.models.subscriber.Trade.TradePayload;
import org.example.models.subscriber.spread.SpreadPayload;
import org.example.models.subscriber.ticker.TickerPayload;
import org.example.sockets.WebSocketKrClient;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TestContext {

    private Scenario scenario;
    private Config config;
    private WebSocketKrClient client;
    private PingPong sentPingPongMessage;
    private  Map<Integer, Subscribe> sentSubscriptions = new HashMap<>();
    private List<SubscriptionStatus>receivedSubscriptionConfirmation = new ArrayList<>();
    private List<String> responseArray = new ArrayList<>();

    private List<TickerPayload> tickerUpdates = new ArrayList<>();
    private List<SpreadPayload> spreadUpdates = new ArrayList<>();

    private List<TradePayload> tradeUpdates = new ArrayList<>();

    public List<TradePayload> getTradeUpdates() {
        return tradeUpdates;
    }

    public List<SpreadPayload> getSpreadUpdates() {
        return spreadUpdates;
    }

    public List<TickerPayload> getTickerUpdates() {
        return tickerUpdates;
    }

    public Map<Integer, Subscribe> getSentSubscriptions() {
        return sentSubscriptions;
    }

    public List<SubscriptionStatus> getReceivedSubscriptionConfirmation() {
        return receivedSubscriptionConfirmation;
    }

    public List<String> getResponseArray() {
        return responseArray;
    }

    public PingPong getSentPingPongMessage() {
        return sentPingPongMessage;
    }

    public void setSentPingPongMessage(PingPong sentPingPongMessage) {
        this.sentPingPongMessage = sentPingPongMessage;
    }

    public WebSocketKrClient getClient() {
        if (client == null) {
            Assertions.fail("WebSocket client haven't started. Use method - Given(web socket client connection is created), before calling client");
        }
        if(client.isClosed() || client.isClosing()) {
            try {
                client.connectBlocking(config.getReconnectTimeAmount(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return client;
    }

    public void setClient(WebSocketKrClient client) {
        this.client = client;
    }

    public Config getConfig() {
        if (config == null) {
            config = ConfigReader.getConfig();
        }
        return config;
    }


    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
