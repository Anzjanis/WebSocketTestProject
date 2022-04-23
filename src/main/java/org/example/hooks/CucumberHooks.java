package org.example.hooks;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.context.TestContext;
import org.example.helpers.Serializer;
import org.example.models.Config;
import org.example.sockets.WebSocketLogic;
import org.java_websocket.enums.ReadyState;


public class CucumberHooks {
    TestContext testContext;
    Scenario scenario;


    public CucumberHooks(TestContext testContext) {
        this.testContext = testContext;
        webSocketLogic = new WebSocketLogic(testContext);
    }
    private WebSocketLogic webSocketLogic;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        testContext.setScenario(scenario);
    }

    @After
    public void after() {
        var client = testContext.getClient();

        try {
            if(client.isOpen()) {
                client.close();
                webSocketLogic.waitForClientStateChange(ReadyState.CLOSED, client);
            }
        } catch (Exception e) {
            scenario.log("Unable to close WS connection");
            scenario.log(e.getMessage());
        }
    }
}
