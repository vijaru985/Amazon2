package pageClasses;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import driver.DataTableInstance;
import utility.BaseClass;
import utility.GenericFunctions;
import utility.Log;

public class Cart extends BaseClass {

	private WebDriver driver;
	private HashMap<String, String> dataTable;

	@FindBy(xpath = "//span[contains(@id, 'nav-cart-count')]")
	private WebElement cart;
	
	@FindBy(xpath = "//span[@class='sc-action-quantity']/preceding::span[@class='a-truncate-cut'  and not(contains(@style,'auto'))][1]")
	private List<WebElement> itemsInCart;

	public Cart(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		dataTable = DataTableInstance.getDataTable();
	}



	public void verifyAddedProductToCart() throws Exception {
		clickElement(cart, "Cart");
		String addedItem = dataTable.get("AddedProductToCart");
		boolean flag = false;
		for(WebElement ele : itemsInCart) {
			String itemInCart = getElementText(ele, "Element in Cart").replace("…", "").trim();
			Log.logger.info("Item in Cart : "+itemInCart);
			if(addedItem.startsWith(itemInCart)){
				flag = true;
				break;
			}
		}
		
		if(flag) {
			Log.logger.info("Item added successfully to cart : "+addedItem);
		}
		else
			throw new Exception("Item not added to cart : "+addedItem);
	}

	

}
