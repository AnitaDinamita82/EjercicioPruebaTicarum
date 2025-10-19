package application.GeDeCo;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "application.GeDeCo", plugin = { "pretty",
        "html:target/cucumber-reports" })
public class CucumberRunnerTest {
    // Esta clase se deja vacía. Sus anotaciones se encargan de la configuración.
}