package pageClasses;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utility.BaseClass;
import utility.ScenarioContext;

public class BestSellersPage extends BaseClass {

	private WebDriver driver;
	private HashMap<String, String> dataTable;

	@FindBy(xpath = "(//a[@class='a-link-normal aok-block'])[1]")
	private WebElement bestSellerFirstProduct;
	
	@FindBy(xpath = "//span[@id='productTitle']")
	private WebElement firstProductTitle;

	public BestSellersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		dataTable = ScenarioContext.getDataTable();
	}

	public void clickOnBestSellersFirstProduct() {
		clickElement(bestSellerFirstProduct, "Best Sellers First Product");
		dataTable.put("BestSellersFirstProductTitle", getElementText(firstProductTitle, "First Product Title"));
	}

}
