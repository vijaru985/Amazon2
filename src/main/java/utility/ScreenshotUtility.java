package utility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class ScreenshotUtility {

	public void captureScreenshot(WebDriver driver) {
		String scenarioName = ScenarioContext.getDataTable().get("scenarioName");
		scenarioName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
		String executionTime = ScenarioContext.getDataTable().get("executionTime");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String currentDate = GenericFunctions.currentDate();
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
}
