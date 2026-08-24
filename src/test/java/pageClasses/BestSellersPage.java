package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utility.BaseClass;
import utility.GenericFunctions;

public class BestSellersPage extends BaseClass {

	private WebDriver driver;

	@FindBy(xpath = "(//a[@class='a-link-normal aok-block'])[1]")
	private WebElement bestSellerFirstProduct;

	public BestSellersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
	}

	public void clickOnBestSellersFirstProduct() {
		clickElement(bestSellerFirstProduct, "Best Sellers First Product", 5);
	}

}
