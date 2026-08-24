package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utility.BaseClass;
import utility.DatabaseOperations;
import utility.Environment;


public class PasswordPage extends BaseClass{
   private WebDriver driver;
   
   @FindBy(xpath = "//label[contains(.,'Password')]")
   private WebElement passwordLabel;
   
   @FindBy(xpath = "//input[@id='ap_password']")
   private WebElement passwordInput;
   
   @FindBy(xpath = "//input[@id='signInSubmit']")
   private WebElement signInBtn;
   
   public PasswordPage(WebDriver driver) {
	   this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(this.driver, 30);
		PageFactory.initElements(factory, this);
   }
   
	public void enterPassword() {
		enterText(passwordInput, Environment.getPassword());
		//enterText(passwordInput, DatabaseOperations.getPassword(Environment.getUsername())); //To fetch password from Database
	}
	
	public void clickOnSignInButton() {
		clickElement(signInBtn, "Sign In Button", 5);
	}

	public void enterPassword(String password) {
		enterText(passwordInput, password);
		
	}
}
