package pageClasses;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utility.BaseClass;
import utility.Log;

public class Homepage extends BaseClass {
	private WebDriver driver;

	@FindBy(xpath = "//span[contains(text(),'Hello,')]")
	private WebElement helloUserText;

	@FindBy(xpath = "//div[contains(text(),'Your password is incorrect')]")
	private WebElement loginErrorText;

	@FindBy(xpath = "//span[contains(text(),'All')]/parent::a")
	private WebElement allMenuButton;

	private String menuLinkXpath = "//div[contains(@id,'hmenu-content')]/descendant::a[contains(text(),'TEMP')]";

	@FindBy(xpath = "//a[contains(@id, 'location-popover-link')]")
	private WebElement locationButton;

	@FindBy(xpath = "//h4[contains(text(),'Choose your location')]")
	private WebElement chooseYourLocationHeader;

	@FindBy(xpath = "//input[contains(@aria-label,'enter an Indian pincode')]")
	private WebElement enterPincodeTextbox;

	@FindBy(xpath = "//span[contains(text(),'Apply')]/preceding-sibling::input[@type='submit']")
	private WebElement applyButton;

	@FindBy(xpath = "//span[contains(text(),'Deliver to')]/following-sibling::span")
	private WebElement updatedLocation;

	public Homepage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 15);
		PageFactory.initElements(factory, this);
	}

	public void verifyHomepageLanding() {
		doValidation(helloUserText.isDisplayed(), 
				"user landed on amazon homepage successfully",
				"user failed to land on amazon homepage");
	}

	public void verifyLoginWithInvalidCredentials() {
		doValidation(loginErrorText.isDisplayed(),
				"User failed to land on amazon homepage with invalid credentials",
				"User landed on amazon homepage with invalid credentials");
	}

	public void clickOnAllMenuButton() {
		clickElement(allMenuButton, "All Menu Button");
	}

	public void clickOnMenuLink(String linkName) {
		String menuLinkUpdatedXpath = menuLinkXpath.replace("TEMP", linkName);
		Log.logger.info("Menu link xapth is : " + menuLinkUpdatedXpath);
		WebElement element = driver.findElement(By.xpath(menuLinkUpdatedXpath));
		moveToElementAndClick(element);
	}

	public void clickOnLocationButton() {
		clickElement(locationButton, "Location Button");
	}

	public void verifyChooseLocationPopup() {
		doValidation(chooseYourLocationHeader.isDisplayed(), 
				"Choose Your Location Popup is displayed",
				"Choose Your Location Popup is not displayed");
	}

	public void enterPincodeAndClickOnApply() {
		enterText(enterPincodeTextbox, getConfigProperty("pincode"));
		clickElement(applyButton, "Apply Button");
	}

	public void verifyLocationUpdate() {
		String updatedLocationText = getElementText(updatedLocation, "Updated Location");
		String expectedLocationText = getConfigProperty("location");
		String expectedPincodeText = getConfigProperty("pincode");
		doValidation(updatedLocationText.contains(expectedLocationText),
				"Location is updated successfully",
				"Location was not updated. Expected: " + expectedLocationText);
		doValidation(updatedLocationText.contains(expectedPincodeText),
				"Pincode is updated successfully",
				"Pincode was not updated. Expected: " + expectedLocationText);

	}

}
