package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utility.BaseClass;
import utility.Environment;

public class EnterMobileNumberOrEmailPage extends BaseClass {

	private WebDriver driver;

	@FindBy(id = "ap_email_login")
	private WebElement mobileNoOrEmailTextBox;

	@FindBy(xpath = "//span[@id='continue']//input[@type='submit']")
	private WebElement continueBtn;

	public EnterMobileNumberOrEmailPage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
	}

	public void enterMobileNoOrEmail() {
		enterText(mobileNoOrEmailTextBox, Environment.getUsername());
	}

	public void clickOnContinue() {
		clickElement(continueBtn, "Continue Button", 5);
	}

	public void enterMobileNoOrEmail(String username) {
		enterText(mobileNoOrEmailTextBox, username);
		
	}
}
