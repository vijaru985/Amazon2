package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

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
import hooks.Hooks;

public class BaseClass {
    WebDriver driver;
    
	public BaseClass() {
		this.driver = DriverInstance.getDriver();
	}
	
	public String getConfigProperty(String key) {
		/*
		 * File configFile = new File("./src/test/resources/configure.properties");
		 * FileInputStream fis = null; try { fis = new FileInputStream(configFile); }
		 * catch (FileNotFoundException e) { e.printStackTrace(); } Properties prop =
		 * new Properties(); try { prop.load(fis); } catch (IOException e) {
		 * e.printStackTrace(); }
		 * 
		 * return prop.getProperty(key);
		 */
		
		return ConfigReader.getProperty(key);
	}

	public void captureScreenshot(WebDriver driver) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String currentDate = currentDate();
		String scenarioName = Hooks.getScenario().getName();
		scenarioName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
		String executionTime = Hooks.getScenarioExecutionTime();
		File folder = new File("./target/screenshots/" + scenarioName + "/" + executionTime);
	    
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File destFile = new File(folder, currentDate + ".png");
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String currentDate() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		return timeStamp;
	}

	public void moveToElementAndClick(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
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
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		//wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		Log.logger.info("Clicked on "+elementName+" successfully");
	}
	
	public String getElementText(WebElement element, String elementName) {
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		//wait.until(ExpectedConditions.visibilityOf(element));
		String text = element.getText();
		Log.logger.info("Text retrieved from " + elementName + ": " + text);
		return text;
	}
	
	public void doValidation(boolean condition, String successMessage, String failureMessage) {
		Assert.assertTrue(condition, failureMessage);
		Log.logger.info("Validation Passed : "+successMessage);
	}
}
