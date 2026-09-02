package utility;


import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import driver.DataTableInstance;

public class GenericFunctions extends BaseClass{
	private WebDriver driver;
	
	@FindBy(xpath = "//span[@id='nav-cart-count']")
	private WebElement cartCount;
	
	@FindBy(xpath = "//span[@id='productTitle']")
	private WebElement productTitle;
	
	@FindBy(xpath = "//span[contains(text(),'Add to cart') and contains(@id,'submit')]/preceding-sibling::input[@type='submit']")
	private WebElement addToCartButton;
	
	@FindBy(xpath = "//a[@aria-label='Exit this panel and return to the product page.']")
	private WebElement addedToCartPopupCloseIcon;
	
	private HashMap<String, String> dataTable;

	public GenericFunctions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
		dataTable = DataTableInstance.getDataTable();
	}

	public String cartCount() {
		return getElementText(cartCount, "Cart Count");
	}
	
	public void addProductToCart() {
		String productTitleValue = getElementText(productTitle, "Product Title");
		clickElement(addToCartButton, "Add To Cart");
		if(isElementDisplayedOptional(addedToCartPopupCloseIcon, "Added To Cart Popup close Icon", 2)) {
			clickElement(addToCartButton, "Added To Cart Popup close Icon");
		}
		dataTable.put("AddedProductToCart", productTitleValue);
	}
}
