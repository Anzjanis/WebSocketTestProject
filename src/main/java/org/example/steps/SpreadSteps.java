package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.example.context.TestContext;
import org.example.sockets.WebSocketKrClient;
import org.example.sockets.WebSocketLogic;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class SpreadSteps {
    TestContext testContext;
    WebSocketKrClient client;
    WebSocketLogic webSocketLogic;

    public SpreadSteps(TestContext testContext) {
        this.testContext = testContext;
        client = testContext.getClient();
        webSocketLogic = new WebSocketLogic(testContext);
    }

    @Then("I expect to receive at least {int} updates about my spread subscription")
    public void iExpectToReceiveAtLeastUpdatesAboutMySpreadSubscription(int arg0) {
        webSocketLogic.waitForForMessage(arg0, testContext.getSpreadUpdates());
        Assertions.assertTrue(arg0 <= testContext.getSpreadUpdates().size(), "Didn't receive " + arg0 + " updates in given time");
    }

    @Then("I expect to receive information about spread")
    public void iExpectToReceiveInformationAboutSpread() {
        var index = webSocketLogic.waitForForMessage(1, testContext.getSpreadUpdates());
        var spread = testContext.getSpreadUpdates().get(index);

        Assertions.assertNotNull(spread.getSpreadInfoList(), "Spread information is empty");
        Assertions.assertNotNull(spread.getChannelID(), "Spread chanel ID is empty");
        Assertions.assertNotNull(spread.getChannelName(), "Spread name is empty");
        Assertions.assertNotNull(spread.getPair(), "Spread Pair is empty");
        var ss = testContext.getReceivedSubscriptionConfirmation().get(testContext.getReceivedSubscriptionConfirmation().size() - 1);

        Assertions.assertEquals(ss.getChannelID(), spread.getChannelID());
        Assertions.assertEquals(ss.getPair(), spread.getPair());
        Assertions.assertEquals(ss.getChannelName(), spread.getChannelName());
        var spreadInfo = spread.getSpreadInfoList();
        var big = new BigDecimal("0.0");

        Assertions.assertTrue(new BigDecimal(spreadInfo.get(0)).compareTo(big) > 0, "Spread values are expected to be bigger then 0, and in big decimal format");
        Assertions.assertTrue(new BigDecimal(spreadInfo.get(1)).compareTo(big) > 0, "Spread values are expected to be bigger then 0, and in big decimal format");
        Assertions.assertTrue(new BigDecimal(spreadInfo.get(2)).compareTo(big) > 0, "Spread values are expected to be bigger then 0, and in big decimal format");
        Assertions.assertTrue(new BigDecimal(spreadInfo.get(3)).compareTo(big) > 0, "Spread values are expected to be bigger then 0, and in big decimal format");
        Assertions.assertTrue(new BigDecimal(spreadInfo.get(4)).compareTo(big) > 0, "Spread values are expected to be bigger then 0, and in big decimal format");
    }

    @And("I check following information in spread: timestamp is increasing for each entry")
    public void iCheckFollowingInformationInSpreadTimestampIsIncreasingForEachEntry() {
        for (int i = 1; i < testContext.getSpreadUpdates().size(); i++) {
            var spread0 = testContext.getSpreadUpdates().get(i - 1);
            var timestamp0 = spread0.getSpreadInfoList().get(2);

            var spread1 = testContext.getSpreadUpdates().get(i);
            var timestamp1 = spread1.getSpreadInfoList().get(2);

            Assertions.assertTrue(new BigDecimal(timestamp0).compareTo(new BigDecimal(timestamp1)) < 0, "TStamp1: " + timestamp0 + " TStamp2: " + timestamp1);
        }
    }
}
