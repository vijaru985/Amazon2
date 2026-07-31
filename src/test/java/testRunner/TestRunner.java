package testRunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions( features = "src/test/resources/features",
                  glue = {"stepDefinitions", "hooks"},
                  monochrome = true,
                  tags = ("@Smoke"),
                  plugin = {"pretty", "html:target/report/report.html",
                		     				  "json:target/report/report.json",
                		          "junit:target/report/report.xml"
                		  }
		
		)
public class TestRunner extends AbstractTestNGCucumberTests{

	    @Override
	    @DataProvider(parallel = true)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}
