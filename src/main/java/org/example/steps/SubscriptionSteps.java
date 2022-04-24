package org.example.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.context.TestContext;
import org.example.helpers.Serializer;
import org.example.models.subscriber.Subscribe;
import org.example.models.subscriber.SubscriptionStatus;
import org.example.sockets.WebSocketKrClient;
import org.example.sockets.WebSocketLogic;
import org.junit.jupiter.api.Assertions;

public class SubscriptionSteps {
    TestContext testContext;
    Serializer serializer = new Serializer();
    WebSocketKrClient client;
    WebSocketLogic webSocketLogic;
    public SubscriptionSteps(TestContext testContext) {
        this.testContext = testContext;
        client = testContext.getClient();
        webSocketLogic = new WebSocketLogic(testContext);
    }

    @When("I create event {string} with the following information:")
    public void iCreateEventWithTheFollowingInformation(String event, DataTable table) {
        var map = table.entries().get(0);
        Subscribe subscription = new Subscribe().buildSubscribeModel(map);
        subscription.setEvent(event);
        client.send(serializer.serialize(subscription));
        testContext.getSentSubscriptions().put(subscription.getReqid(), subscription);
    }

    @Then("I expect to receive successful confirmation message about my subscription")
    public void iExpectToReceiveConfirmationAboutMySubscription() {
        int index = webSocketLogic.waitForForMessage(1, testContext.getReceivedSubscriptionConfirmation());
        var ss = testContext.getReceivedSubscriptionConfirmation().get(index);
        var sentSub = testContext.getSentSubscriptions().get(ss.getReqid());

        validateSuccessfulSentSubscriptionAndConfirmationMessage(sentSub, ss);

    }

    @Then("I expect to receive unsuccessful confirmation message about my subscription with error message {string}")
    public void iExpectToReceiveUnsuccessfulConfirmationMessageAboutMySubscriptionWithErrorMessage(String arg0) {
        int index = webSocketLogic.waitForForMessage(1, testContext.getReceivedSubscriptionConfirmation());
        var ss = testContext.getReceivedSubscriptionConfirmation().get(index);
        var sentSub = testContext.getSentSubscriptions().get(ss.getReqid());
        Assertions.assertTrue(ss.getErrorMessage().contains(arg0), "Error message doesn't contain expected results");

        validateUnSuccessfulSentSubscriptionAndConfirmationMessage(sentSub, ss);
    }

    private void validateUnSuccessfulSentSubscriptionAndConfirmationMessage(Subscribe sentSub, SubscriptionStatus ss) {
        Assertions.assertNull(ss.getChannelID(), "Channel id is not empty");
        Assertions.assertEquals("error", ss.getStatus());
        Assertions.assertNull(ss.getChannelName());
        Assertions.assertEquals(sentSub.getReqid(), ss.getReqid());
        Assertions.assertEquals("subscriptionStatus", ss.getEvent());
        Assertions.assertTrue(sentSub.getPair().contains(ss.getPair()), "Received incorrect currency pair");
        Assertions.assertEquals(sentSub.getSubscription().getDepth(), ss.getSubscription().getDepth());
        Assertions.assertEquals(sentSub.getSubscription().getInterval(), ss.getSubscription().getInterval());
    }

    private void validateSuccessfulSentSubscriptionAndConfirmationMessage(Subscribe sentSub, SubscriptionStatus ss) {
        Assertions.assertEquals("subscribed", ss.getStatus());
        Assertions.assertNull(ss.getErrorMessage(), "Error message expected to be empty");
        Assertions.assertNotNull(ss.getChannelID(), "Channel id is empty");
        Assertions.assertEquals(sentSub.getReqid(), ss.getReqid());
        Assertions.assertEquals("subscriptionStatus", ss.getEvent());
        Assertions.assertTrue(sentSub.getPair().contains(ss.getPair()), "Received incorrect currency pair");
        Assertions.assertEquals(sentSub.getSubscription().getDepth(), ss.getSubscription().getDepth());
        Assertions.assertEquals(sentSub.getSubscription().getInterval(), ss.getSubscription().getInterval());
    }


}
