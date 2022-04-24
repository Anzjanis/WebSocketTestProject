package org.example.sockets;

import org.example.context.TestContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketKrClient extends WebSocketClient {

    TestContext testContext;

    public WebSocketKrClient(TestContext testContext) throws URISyntaxException {
        super(new URI(testContext.getConfig().getUrl()));
        this.testContext = testContext;
        webSocketLogic = new WebSocketLogic(testContext);
    }
    WebSocketLogic webSocketLogic;
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        testContext.getScenario().log("WS Connection started");
    }

    @Override
    public void onMessage(String s) {
//        ATM no need to log it.
        if(!s.contains("heartbeat")) {
            webSocketLogic.sortTraffic(s);

            testContext.getScenario().log("Received message: " + s);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void send(String e) {
        super.send(e);
        testContext.getScenario().log("Sent message: " + e);
    }

//    move messages into correct places

}
