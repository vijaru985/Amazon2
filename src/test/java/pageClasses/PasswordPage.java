package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class PasswordPage {
   WebDriver driver;
   
   @FindBy(xpath = "//label[contains(.,'Password')]")
   private WebElement passwordLabel;
   
   @FindBy(xpath = "//input[@id='ap_password']")
   private WebElement passwordInput;
   
   @FindBy(xpath = "//input[@id='signInSubmit']")
   private WebElement signInBtn;
   
   public PasswordPage(WebDriver driver) {
	   this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
   }
   
	public void enterPassword(String password) {
		passwordInput.sendKeys(password);
	}
	
	public void clickOnSignInButton() {
		signInBtn.click();
	}
}
