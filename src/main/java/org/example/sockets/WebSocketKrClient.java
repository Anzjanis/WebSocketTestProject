package org.example.sockets;

import org.example.context.TestContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

public class WebSocketKrClient extends WebSocketClient {

    TestContext testContext;

    public WebSocketKrClient(TestContext testContext) throws URISyntaxException {
        super(new URI(testContext.getConfig().getUrl()));
        this.testContext = testContext;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        testContext.getScenario().log("WS Connection started");
    }

    @Override
    public void onMessage(String s) {
        testContext.getResponseArray().add(s);
        testContext.getScenario().log("Received message: " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
       testContext.getScenario().log("WS Connection closed");
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void send(String e) {
        super.send(e);
        testContext.getScenario().log("Received message: " + e);
    }
}
