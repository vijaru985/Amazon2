package testRunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", 
                 glue = { "stepDefinitions","hooks" }, 
                 monochrome = true, 
                 //tags = ("@Regression"), 
                 plugin = { "pretty", "json:target/cucumber.json",
				                       "html:target/cucumber-report.html" }

)
public class TestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
