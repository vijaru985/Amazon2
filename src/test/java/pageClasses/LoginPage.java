package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class LoginPage {
	WebDriver driver;
	@FindBy(xpath = "//span[contains(.,'Hello, sign in')]/ancestor::a")
	private WebElement signInBtn;

	public LoginPage(WebDriver driver) {
		 this.driver = driver;
			AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
			PageFactory.initElements(factory, this);
	}
	
	public void clickSignInButton() {
		signInBtn.click();
	}
}
