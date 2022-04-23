package org.example.steps;


import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.example.helpers.Serializer;
import org.example.helpers.TestDataGen;
import org.example.models.PingPong;
import org.example.sockets.WebSocketKrClient;
import org.example.sockets.WebSocketLogic;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;


public class WebSocketSteps {

    public WebSocketSteps(TestContext testContext) {
        this.testContext = testContext;
        scenario = testContext.getScenario();
        webSocketLogic = new WebSocketLogic(testContext);
    }

    private TestContext testContext;
    private Scenario scenario;
    private WebSocketKrClient client;
    private Serializer serializer = new Serializer();
    private WebSocketLogic webSocketLogic;

    @Given("I can ping service")
    public void iCanPingService() {

    }

    @Given("web socket client connection is created")
    public void webSocketClientConnectionIsCreated() {
        try {
            client = new WebSocketKrClient(testContext);
            client.connectBlocking();
            testContext.setClient(client);

        } catch (URISyntaxException e) {
            Assertions.fail(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("web socket client state should be \"([^\"]*)\"$")
    public void webSocketClientStateShouldBe(ReadyState expectedState) {
        Assertions.assertTrue(webSocketLogic.waitForClientStateChange(expectedState, client), "Client state didn't change in expected time");
        Assertions.assertEquals(expectedState, client.getReadyState(), "WS client is in incorrect state");
    }

    @When("web socket client connection is disconnected")
    public void webSocketClientConnectionIsDisconnected() {
        client.close();
    }

    @When("I send ping request with {string} number")
    public void iSendPingRequestWithNumber(String arg0) {
        PingPong p;
        if (arg0.contains("empty")) {
            p = new PingPong("ping");

        } else {
            p = new PingPong("ping", TestDataGen.generateInteger(arg0, 5));
        }

        testContext.setSentPingPongMessage(p);
        client.send(serializer.serialize(p));
    }

    @Then("I receive pong response")
    public void iReceivePongResponse() throws IOException, InterruptedException {

        int newMessageIndex = webSocketLogic.waitForForMessage(1);
        Assertions.assertNotEquals(0, newMessageIndex, "There is no new messages");
        PingPong response = serializer.deserializeJson(testContext.getResponseArray().get(newMessageIndex), PingPong.class);

        Assertions.assertEquals("pong", response.getEvent());
        Assertions.assertEquals(testContext.getSentPingPongMessage().getReqid(), response.getReqid(), "Request ID is not as expected");
    }


}
