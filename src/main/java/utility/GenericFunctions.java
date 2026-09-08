package utility;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import base.BaseClass;


public class GenericFunctions extends BaseClass{
	private WebDriver driver;
	
	
	
	private HashMap<String, String> dataTable;

	public GenericFunctions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
		dataTable = ScenarioContext.getDataTable();
	}
	
	public static String currentDate() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		return timeStamp;
	}
	
}
