package org.example.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.example.context.TestContext;
import org.example.helpers.Serializer;
import org.example.models.subscriber.Subscribe;
import org.example.models.subscriber.Subscription;
import org.example.sockets.WebSocketKrClient;

public class SubscriptionSteps {
    TestContext testContext;
    Serializer serializer = new Serializer();
    public SubscriptionSteps(TestContext testContext) {
        this.testContext = testContext;
        client = testContext.getClient();
    }
    WebSocketKrClient client;

    @When("I create event {string} with the following information:")
    public void iCreateEventWithTheFollowingInformation(String event, DataTable table) {
        Subscribe subscription = new Subscribe().buildSubscribeModel(table);
        subscription.setEvent(event);
        client.send(serializer.serialize(subscription));
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var x = "";
    }
}
