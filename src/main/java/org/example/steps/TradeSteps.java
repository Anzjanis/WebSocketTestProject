package org.example.steps;

import io.cucumber.java.en.Then;
import org.example.context.TestContext;
import org.example.sockets.WebSocketKrClient;
import org.example.sockets.WebSocketLogic;
import org.junit.jupiter.api.Assertions;

public class TradeSteps {
    TestContext testContext;
    WebSocketKrClient client;
    WebSocketLogic webSocketLogic;

    public TradeSteps(TestContext testContext) {
        this.testContext = testContext;
        client = testContext.getClient();
        webSocketLogic = new WebSocketLogic(testContext);
    }

    @Then("I expect to receive at least {int} updates about my trade subscription")
    public void iExpectToReceiveAtLeastUpdatesAboutMyTradeSubscription(Integer arg0) {
        webSocketLogic.waitForForMessage(arg0, testContext.getTradeUpdates());
        Assertions.assertTrue(arg0 <= testContext.getTradeUpdates().size(), "Didn't receive " + arg0 + " updates in given time");
    }

    @Then("I expect no more new updates are coming from trade subscription - waiting time {int} seconds")
    public void iExpectNoMoreNewUpdatesAreComingFromTradeSubscription(Integer waitTime) throws InterruptedException {
        var subscriptionAmount = testContext.getTradeUpdates();
        Thread.sleep(waitTime * 1000);
        var subscriptionAmountAfterTime = testContext.getTradeUpdates();

        Assertions.assertEquals(subscriptionAmount, subscriptionAmountAfterTime, "Unsubscribed client received updates");
    }
}
