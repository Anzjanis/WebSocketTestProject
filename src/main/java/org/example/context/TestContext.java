package org.example.context;


import io.cucumber.java.Scenario;
import org.example.config.ConfigReader;
import org.example.models.Config;
import org.example.models.PingPong;
import org.example.sockets.WebSocketKrClient;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class TestContext {

    private Scenario scenario;
    private Config config;
    private WebSocketKrClient client;
    private PingPong sentPingPongMessage;

    List<String> responseArray = new ArrayList<>();

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
