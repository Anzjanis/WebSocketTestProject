package org.example.context;





import io.cucumber.java.Scenario;
import org.example.models.Config;



public class TestContext{

    private Scenario scenario;
    private Config c;

    public Config getC() {
        return c;
    }

    public void setC(Config c) {
        this.c = c;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
