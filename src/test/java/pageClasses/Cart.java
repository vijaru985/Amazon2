package pageClasses;

import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utility.BaseClass;
import utility.Log;
import utility.ScenarioContext;

public class Cart extends BaseClass {

	private WebDriver driver;
	private HashMap<String, String> dataTable;

	@FindBy(xpath = "//span[contains(@id, 'nav-cart-count')]")
	private WebElement cart;
	
	@FindBy(xpath = "//span[@class='sc-action-quantity']/preceding::span[@class='a-truncate-cut'  and not(contains(@style,'auto'))][1]")
	private List<WebElement> itemsInCart;
	
	@FindBy(xpath = "//span[@id='nav-cart-count']")
	private WebElement cartCount;
	
	@FindBy(xpath = "//span[@id='productTitle']")
	private WebElement productTitle;
	
	@FindBy(xpath = "//span[contains(text(),'Add to cart') and contains(@id,'submit')]/preceding-sibling::input[@type='submit']")
	private WebElement addToCartButton;
	
	@FindBy(xpath = "//a[@aria-label='Exit this panel and return to the product page.']")
	private WebElement addedToCartPopupCloseIcon;

	public Cart(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		dataTable = ScenarioContext.getDataTable();
	}


	public void addProductToCart() {
		String productTitleValue = getElementText(productTitle, "Product Title");
		clickElement(addToCartButton, "Add To Cart");
		if(isElementDisplayedOptional(addedToCartPopupCloseIcon, "Added To Cart Popup close Icon", 2)) {
			clickElement(addToCartButton, "Added To Cart Popup close Icon");
		}
		dataTable.put("AddedProductToCart", productTitleValue);
	}
	
	public String cartCount() {
		return getElementText(cartCount, "Cart Count");
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
