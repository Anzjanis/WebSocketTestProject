package org.example.sockets;

import org.example.context.TestContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;

import java.io.IOException;

public class WebSocketLogic {

    TestContext testContext;

    public WebSocketLogic(TestContext testContext) {
        this.testContext = testContext;
    }

    // Should be improved as it is quite slow and not effective
    // Checks list if there is new message available
    public int waitForForMessage(int expectedAmountOfNewMessages) throws IOException {
        int size = testContext.getResponseArray().size();
        for (int i = 0; i < testContext.getConfig().getRetryAmount(); i++) {
            if (size + expectedAmountOfNewMessages <= testContext.getResponseArray().size()) {
                return testContext.getResponseArray().size()-1;
            }

            try {
                Thread.sleep(300);
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
}
