package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class Homepage {
	WebDriver driver;

	@FindBy(xpath = "//span[contains(text(),'Hello,')]")
	private WebElement helloUserText;
	
	@FindBy(xpath = "//div[contains(text(),'Your password is incorrect')]")
	private WebElement loginErrorText;

	public Homepage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
	}

	public void verifyHomepageLanding() {
		if (helloUserText.isDisplayed()) {
			System.out.println("User landed on amazon homepage successfully");
		}
		
		  else { Assert.fail("user failed to land on amazon homepage"); }
	}
	
	public void verifyLoginWithInvalidCredentials() {
		if(!loginErrorText.isDisplayed()) {
			System.out.println("User failed to land on amazon homepage with invalid credentials");
		}
		
		  else { Assert.fail("user landed on amazon homepage with invalid credentials"); }
	}
}
