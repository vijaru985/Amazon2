package stepDefinitions;


import org.openqa.selenium.WebDriver;
import driver.DriverInstance;
import io.cucumber.java.en.*;
import pageClasses.Cart;
import utility.GenericFunctions;

public class Cart_steps {
	private WebDriver driver;

	public Cart_steps() {
		driver = DriverInstance.getDriver();
	}
	
	@Given("user clicks on add to cart button")
	public void user_clicks_on_add_to_cart_button() throws Exception {
		GenericFunctions oGF = new GenericFunctions(driver);
		Cart oc = new Cart(driver);
		oGF.addProductToCart();
		oc.verifyAddedProductToCart();
	}


}
