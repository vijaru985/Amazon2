package driver;

import org.openqa.selenium.WebDriver;
import utility.DevToolsManager;

public class DriverInstance {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static final ThreadLocal<DevToolsManager> devToolsManager = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver driverInstance) {
		driver.set(driverInstance);
	}

	public static void unload() {
		driver.remove();
	}
	
	public static DevToolsManager getDevToolsManager() {
	    return  devToolsManager.get();
	}
	
	public static void setDevToolsManager(DevToolsManager manager) {
		devToolsManager.set(manager);
	}
}

