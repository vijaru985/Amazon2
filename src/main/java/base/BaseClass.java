package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.google.common.io.Files;
import driver.DriverInstance;
import utility.ConfigReader;
import utility.GenericFunctions;
import utility.Log;
import utility.TestCaseData;

public class BaseClass {
    WebDriver driver;

    private static final ThreadLocal<TestCaseData> testData =
            new ThreadLocal<>();

    public static void setTestData(TestCaseData data) {
        testData.set(data);
    }

    public static String getTestData(String columnName) {

        TestCaseData data = testData.get();

        if (data == null) {
            throw new IllegalStateException(
                "TestCaseData is not initialized for the current thread."
            );
        }

        return data.get(columnName);
    }

    public static void unloadTestData() {
        testData.remove();
    }
    
	public BaseClass() {
		this.driver = DriverInstance.getDriver();
	}
	
	public static String getConfigProperty(String key) {
		return ConfigReader.getProperty(key);
	}

	public void moveToElementAndClick(WebElement element) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    wait.until(ExpectedConditions.visibilityOf(element));

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center', inline:'center'});",
	            element);

	    wait.until(ExpectedConditions.elementToBeClickable(element));

	    new Actions(driver)
	            .moveToElement(element)
	            .click()
	            .perform();
	}

	public void isElementDisplayed(WebElement element, String elementName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf(element));
		Log.logger.info(elementName + " is displayed");
	}

	public boolean isElementDisplayedOptional(WebElement element, String elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOf(element));
			Log.logger.info(elementName + " is displayed");
			return true;
		} catch (TimeoutException e) {
			Log.logger.error("Element is not displayed : " + e.getMessage());
		}
		return false;

	}

	public void enterText(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
		Log.logger.info("Entered Text is : "+value);
	}
	
	public void clickElement(WebElement element, String elementName) {
		element.click();
		Log.logger.info("Clicked on "+elementName+" successfully");
	}
	
	public String getElementText(WebElement element, String elementName) {
		String text = element.getText();
		Log.logger.info("Text retrieved from " + elementName + ": " + text);
		return text;
	}
	
	public void doValidation(boolean condition, String successMessage, String failureMessage) {
		Assert.assertTrue(condition, failureMessage);
		Log.logger.info("Validation Passed : "+successMessage);
	}
}
