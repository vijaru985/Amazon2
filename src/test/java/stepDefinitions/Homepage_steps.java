package stepDefinitions;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import driver.DriverInstance;
import io.cucumber.java.en.*;
import pageClasses.Homepage;
import pageClasses.LoginPage;

public class Homepage_steps {
	private WebDriver driver;
	private Homepage hp;

	public Homepage_steps() {
		driver = DriverInstance.getDriver();
		hp = new Homepage(driver);
	}
	
	@Given("^user is on amazon homepage$")
	public void user_is_on_amazon_homepage() throws IOException {
		LoginPage lp = new LoginPage(driver);
		lp.hitLoginURL();
	}

	@When("user clicks on location section in homepage")
	public void user_clicks_on_location_sectionin_homepage() {
		hp.clickOnLocationButton();
	}

	@And("user enters pincode and clicks on apply button in choose location popup")
	public void user_enters_pincode_and_clicks_on_apply_button_in_choose_location_popup() {
		hp.verifyChooseLocationPopup();
		hp.enterPincodeAndClickOnApply();
	}

	@Then("user verifies location is updated successfully")
	public void user_verifies_location_is_updated_successfully() {
		hp.verifyLocationUpdate();
	}

}
