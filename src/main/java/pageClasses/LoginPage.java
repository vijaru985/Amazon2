package pageClasses;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

import base.BaseClass;
import driver.DriverInstance;
import utility.DevToolsManager;
import utility.Log;

public class LoginPage extends BaseClass {
	private WebDriver driver;
	@FindBy(xpath = "//span[contains(.,'Hello, sign in')]/ancestor::a")
	private WebElement signInBtn;
	
	@FindBy(xpath = "//button[contains(text(),'Continue shopping')]")
	private WebElement continueShoppingBtn;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
	}

	public void hitLoginURL() {
		Log.logger.info("Product is : "+getTestData("Product"));
		driver.get(getConfigProperty("url"));
	}

	public void clickSignInButton() {
		clickElement(signInBtn, "Sign In Button");
	}

    public void loginToAmazon() {
    	EnterMobileNumberOrEmailPage ep = new EnterMobileNumberOrEmailPage(driver);
    	PasswordPage pp = new PasswordPage(driver);
		Homepage hp = new Homepage(driver);
    	hitLoginURL();
		clickSignInButton();
		
		ep = new EnterMobileNumberOrEmailPage(driver);
		pp = new PasswordPage(driver);
		ep.enterMobileNoOrEmail();
		ep.clickOnContinue();
		pp.enterPassword();
		pp.clickOnSignInButton();
		hp = new Homepage(driver);
		hp.verifyHomepageLanding();
    }
	
	public void verifyAPICall() {

		DevToolsManager devToolsManager = DriverInstance.getDevToolsManager();

		Assert.assertNotNull(devToolsManager, "DevToolsManager is not initialized. API validation requires Chrome.");

		// Wait maximum 15 seconds for API response
		boolean responseReceived = devToolsManager.waitForEligibilityApiResponse(driver, Duration.ofSeconds(30));

		Assert.assertTrue(responseReceived, "Eligibility API response was not received within 30 seconds");

		// Validate API was called
		Assert.assertTrue(devToolsManager.isEligibilityApiCalled(), "Eligibility API was not called");

		// Validate HTTP status
		Assert.assertEquals(devToolsManager.getStatusCode(), 200, "Eligibility API returned unexpected status code");

		// Print useful information
		System.out.println("Eligibility API URL: " + devToolsManager.getEligibilityApiUrl());

		System.out.println("Eligibility API Status Code: " + devToolsManager.getStatusCode());

		System.out.println("Eligibility API Response: " + devToolsManager.getResponseBody());
	}
	
	public void handleContinueShoppingPopup() {
		if (isElementDisplayedOptional(continueShoppingBtn, "Continue Shopping Button", 5)) {
			clickElement(continueShoppingBtn, "Continue Shopping Button");
		}
	}

}
