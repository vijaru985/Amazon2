package hooks;


import java.text.SimpleDateFormat;
import java.time.Duration;
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
import utility.DatabaseUtil;
import utility.DevToolsManager;
import utility.Environment;
import utility.GenericFunctions;
import utility.Log;
import utility.ScenarioContext;
import utility.ScreenshotUtility;

public class Hooks {

    private static final ThreadLocal<Scenario> scenario =
            new ThreadLocal<>();

    private static final ThreadLocal<String>
            scenarioExecutionTime =
            new ThreadLocal<>();

    GenericFunctions oGF;
    WebDriver driver;

    @Before(order = 0)
    public void setup() {

        String browser = Environment.getBrowser();

        if (browser == null || browser.isBlank()) {
            browser = "chrome";
        }

        switch (browser.toLowerCase()) {

            case "chrome" ->
                    driver = new ChromeDriver();

            case "edge" ->
                    driver = new EdgeDriver();

            case "firefox" ->
                    driver = new FirefoxDriver();

            default ->
                    driver = new ChromeDriver();
        }

        Log.logger.info(
                "Launching " + browser + " browser");

        driver.manage()
                .window()
                .maximize();

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(15));

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(10));

        DriverInstance.setDriver(driver);

        if (driver instanceof ChromeDriver chromiumDriver) {

            DevToolsManager devToolsManager =
                    new DevToolsManager(chromiumDriver);

            DriverInstance.setDevToolsManager(
                    devToolsManager);

            devToolsManager
                    .captureEligibilityRequest();

            devToolsManager
                    .captureEligibilityResponse();
        }
    }

    @Before(order = 1)
    public void setUpScenario(Scenario sc) {

        scenario.set(sc);

        ScenarioContext.getDataTable().put(
                "scenarioName",
                sc.getName());
    }

    @Before(order = 2)
    public void beforeScenario(Scenario scenario) {

        String executionTime = new SimpleDateFormat(
                "yyyyMMdd_HHmmss")
                .format(new Date());

        scenarioExecutionTime.set(executionTime);

        ScenarioContext.getDataTable().put(
                "executionTime",
                executionTime);
    }

    public static String getScenarioExecutionTime() {

        return scenarioExecutionTime.get();
    }

    public static Scenario getScenario() {

        return scenario.get();
    }

    @After(order = 0)
    public void tearDown() {

        try {

            if (DriverInstance.getDriver() != null) {
                DriverInstance.getDriver().quit();
            }

        } finally {

        	ScenarioContext.unload();
            DriverInstance.unload();
            scenario.remove();
            scenarioExecutionTime.remove();
        }
    }

    @After(order = 1)
    public void tearDownDB() {

        DatabaseUtil.closeConnection();
    }
    
    @AfterStep
    public  void captureScreenshotAfterStep() {
    	ScreenshotUtility su = new ScreenshotUtility();
    	su.captureScreenshot(driver);
    }
}