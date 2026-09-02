package pageClasses;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import driver.DataTableInstance;
import utility.BaseClass;
import utility.GenericFunctions;

public class BestSellersProductPage extends BaseClass {

	private WebDriver driver;
	private HashMap<String, String> dataTable;

	@FindBy(xpath = "(//a[@class='a-link-normal aok-block'])[1]")
	private WebElement bestSellerFirstProduct;
	

	public BestSellersProductPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		dataTable = DataTableInstance.getDataTable();
	}

}

