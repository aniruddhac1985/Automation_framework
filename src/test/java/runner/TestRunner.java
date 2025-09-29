package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/resources/Features",
        glue= {"stepDefination"},
        plugin = {
                "pretty",
                "ExtentListeners/ExtentTestManager" // Initializes ExtentReports
        },
        monochrome = true,
        dryRun=false
)
public class TestRunner {

}
