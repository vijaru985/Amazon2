package hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import driver.DriverInstance;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utility.GenericFunctions;
import utility.Log;

public class Hooks {
    private static ThreadLocal<Scenario> scenario = new ThreadLocal<>();
    GenericFunctions oGF;
    WebDriver driver;
    
	@Before
	public void setup() {
		driver = new ChromeDriver();
		Log.logger.info("Launching Chrome browser");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		DriverInstance.setDriver(driver);
	}
	
	@Before
	public void setUpScenario(Scenario sc) {
		scenario.set(sc);
	}
	
//	@BeforeAll
	public static void deleteScreenshotFolder() throws IOException   {
	    Path path = Paths.get("./target/screenshots");
	    if (Files.exists(path)) {
	        Files.walk(path)
	             .sorted(Comparator.reverseOrder())
	             .map(Path::toFile)
	             .forEach(File::delete);
	    }
	}
	
	public static Scenario getScenario() {
		return scenario.get();
	}
	//@Before
	public void setup2() {
        
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		DriverInstance.setDriver(driver);
	}

	@After
	public void tearDown(){
	    DriverInstance.getDriver().quit();
	    DriverInstance.unload();
	}
	
	@AfterStep
	public void takeScreenshotAfterEachStep() {
		oGF = new GenericFunctions(DriverInstance.getDriver());
		oGF.captureScreenshot(DriverInstance.getDriver());
	}
}
