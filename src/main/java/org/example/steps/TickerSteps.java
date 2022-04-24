package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.example.context.TestContext;
import org.example.sockets.WebSocketKrClient;
import org.example.sockets.WebSocketLogic;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class TickerSteps {
    TestContext testContext;
    WebSocketKrClient client;
    WebSocketLogic webSocketLogic;
    public TickerSteps(TestContext testContext) {
        this.testContext = testContext;
        client = testContext.getClient();
        webSocketLogic = new WebSocketLogic(testContext);
    }

    @Then("I expect to receive information about ticker")
    public void iExpectToReceiveInformationAboutTicker() {
        int index = webSocketLogic.waitForForMessage(1, testContext.getTickerUpdates());
        var ticker = testContext.getTickerUpdates().get(index);
        Assertions.assertNotNull(ticker.getTickerInfoMap(), "Ticker information is empty");
        Assertions.assertNotNull(ticker.getChannelID(), "Ticker chanel ID is empty");
        Assertions.assertNotNull(ticker.getChannelName(), "Ticker name is empty");
        Assertions.assertNotNull(ticker.getPair(), "Ticker Pair is empty");
        var ss = testContext.getReceivedSubscriptionConfirmation().get(testContext.getReceivedSubscriptionConfirmation().size() - 1);

        Assertions.assertEquals(ss.getChannelID(), ticker.getChannelID());
        Assertions.assertEquals(ss.getPair(), ticker.getPair());
        Assertions.assertEquals(ss.getChannelName(), ticker.getChannelName());

    }

    @And("I check following information: ask price is bigger then bid price")
    public void iCheckFollowingInformationAskPriceIsBiggerThenBidPrice() {
        int index = webSocketLogic.waitForForMessage(1, testContext.getTickerUpdates());
        var ticker = testContext.getTickerUpdates().get(index);
        var a = ticker.getTickerInfoMap().get("a");
        var b = ticker.getTickerInfoMap().get("b");

        Assertions.assertTrue(new BigDecimal(a.get(0)).compareTo(new BigDecimal(b.get(0))) > 0, "Ask price always need to be bigger then bid");
    }

}
