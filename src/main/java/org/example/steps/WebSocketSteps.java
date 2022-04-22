package org.example.steps;






import org.example.config.ConfigReader;
import org.example.context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.example.models.Config;


public class WebSocketSteps {

    public WebSocketSteps (TestContext testContext) {
        this.testContext = testContext;
        scenario = testContext.getScenario();
    }

    private TestContext testContext;
    private Scenario scenario;

    @Given("I can ping service")
    public void iCanPingService() {
        Config c = new ConfigReader().getConfig();
        String x = scenario.getName();
    }
}
