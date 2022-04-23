package org.example.sockets;

import io.cucumber.java.eo.Se;
import org.example.context.TestContext;
import org.example.helpers.Serializer;
import org.example.models.subscriber.SubscriptionStatus;
import org.example.models.subscriber.spread.SpreadPayload;
import org.example.models.subscriber.ticker.TickerPayload;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebSocketLogic {

    TestContext testContext;
    Serializer serializer = new Serializer();

    public WebSocketLogic(TestContext testContext) {
        this.testContext = testContext;
    }

    // Should be improved as it is quite slow and not effective
    // Checks list if there is new message available
    public int waitForForMessage(int expectedAmountOfNewMessages, List<?> array) {
        int size = array.size();
        for (int i = 0; i < testContext.getConfig().getRetryAmount(); i++) {
            if (size + expectedAmountOfNewMessages <= array.size()) {
                return array.size()-1;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    public boolean waitForClientStateChange(ReadyState expectedState, WebSocketClient client) {
        for (int i = 0; i < testContext.getConfig().getRetryAmount(); i++) {
            if (expectedState == client.getReadyState()) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public void divideTraffic(String s) {
        testContext.getResponseArray().add(s);
        if(s.contains("\"event\":\"subscriptionStatus\"")) {
            var ss = serializer.deserializeJson(s, SubscriptionStatus.class);
            testContext.getReceivedSubscriptionConfirmation().add(ss);
        }
        // not the best, but will help to divide messages
        if(s.contains("\"o\":[") && s.contains("\"a\":[") && s.contains("\"b\":[") && s.contains("\"c\":[")) {
            var ticker = serializer.deserializeJson(s, ArrayList.class);
            TickerPayload tickerPayload = new TickerPayload().buildTickerModel(ticker);

            testContext.getTickerUpdates().add(tickerPayload);
        }

        if(s.contains("\"spread\"") && s.contains("\"]") && s.contains("[") ) {
            var spread = serializer.deserializeJson(s, ArrayList.class);
            SpreadPayload spreadPayload = new SpreadPayload().buildSpreadModel(spread);

            testContext.getSpreadUpdates().add(spreadPayload);
        }
    }
}
