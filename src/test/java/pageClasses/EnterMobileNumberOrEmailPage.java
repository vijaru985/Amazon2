package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class EnterMobileNumberOrEmailPage {

	WebDriver driver;
	
	@FindBy(id = "ap_email_login")
	private WebElement mobileNoOrEmailTextBox;
	
	@FindBy(xpath = "//span[@id='continue']//input[@type='submit']")
	private WebElement continueBtn;
	
	public EnterMobileNumberOrEmailPage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
	}
	
	public void enterMobileNoOrEmail(String username) {
		mobileNoOrEmailTextBox.sendKeys(username);
	}
	
	public void clickOnContinue() {
		continueBtn.click();
	}
}
