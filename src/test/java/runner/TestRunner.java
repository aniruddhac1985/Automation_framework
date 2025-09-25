package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/resources/Features",
        glue= {"src/test/java/stepDefination"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Initializes ExtentReports
        },
        monochrome = true,
        dryRun=false
)
public class TestRunner {

}
