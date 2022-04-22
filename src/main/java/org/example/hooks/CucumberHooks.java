package org.example.hooks;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.context.TestContext;
import org.example.helpers.Serializer;
import org.example.models.Config;


public class CucumberHooks {
    TestContext testContext;
    Scenario scenario;

    public CucumberHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        Config s = new Config();

        testContext.setScenario(scenario);
        testContext.setC(s);
    }
}
