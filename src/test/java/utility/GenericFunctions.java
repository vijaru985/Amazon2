package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

import hooks.Hooks;

public class GenericFunctions {
    WebDriver driver;
    
    public GenericFunctions(WebDriver driver) {
    	this.driver = driver;
    }
    
	public String getConfigProperty(String key) {
		File configFile = new File("./src/test/resources/configure.properties");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop.getProperty(key);
	}
	
	public void captureScreenshot(WebDriver driver) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String currentDate = currentDate();
		String scenarioName = Hooks.getScenario().getName();
		scenarioName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
		
		File folder = new File("./target/screenshots/"+scenarioName);
		
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
}
