package hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import driver.DriverInstance;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utility.BaseClass;
import utility.DatabaseUtil;
import utility.DevToolsManager;
import utility.Environment;
import utility.GenericFunctions;
import utility.Log;

public class Hooks {
	private static ThreadLocal<Scenario> scenario = new ThreadLocal<>();
	GenericFunctions oGF;
	WebDriver driver;
	private static String scenarioExecutionTime;

	@Before(order = 0)
	public void setup() {
		String browser = Environment.getBrowser();
		switch (browser == null ? "chrome" : browser.toLowerCase()) {
		case "chrome" -> driver = new ChromeDriver();
		case "edge" -> driver = new EdgeDriver();
		case "firefox" -> driver = new FirefoxDriver();
		default -> driver = new ChromeDriver();
		}
		Log.logger.info("Launching " + browser + " browser");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		DriverInstance.setDriver(driver);

		if (driver instanceof ChromeDriver chromiumDriver) {

			DevToolsManager devToolsManager = new DevToolsManager(chromiumDriver);

			DriverInstance.setDevToolsManager(devToolsManager);

			devToolsManager.captureEligibilityRequest();
			devToolsManager.captureEligibilityResponse();
		} 
		else if (driver instanceof FirefoxDriver) {

			Log.logger.info(
					"Firefox detected. Selenium DevTools/CDP network monitoring is not initialized for Firefox.");
		}

	}

	@Before(order = 1)
	public void setUpScenario(Scenario sc) {
		scenario.set(sc);
	}

	@Before(order = 2)
	public void beforeScenario(Scenario scenario) {
		scenarioExecutionTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}

	public static String getScenarioExecutionTime() {
	    return scenarioExecutionTime;
	}
	
//	@BeforeAll
	public static void deleteScreenshotFolder() throws IOException {
		Path path = Paths.get("./target/screenshots");
		if (Files.exists(path)) {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		}
	}

	public static Scenario getScenario() {
		return scenario.get();
	}

	//@After(order = 0)
	public void tearDown() {
		DriverInstance.getDriver().quit();
		DriverInstance.unload();
	}

	//@AfterStep(order = 0)
	public void takeScreenshotAfterEachStep() {
		BaseClass bc = new BaseClass();
		bc.captureScreenshot(DriverInstance.getDriver());
	}

	@After(order = 1)
	public void tearDownDB() {
		DatabaseUtil.closeConnection();
	}
}
