package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import driver.DriverInstance;
import io.cucumber.java.en.*;
import pageClasses.EnterMobileNumberOrEmailPage;
import pageClasses.Homepage;
import pageClasses.LoginPage;
import pageClasses.PasswordPage;
import utility.GenericFunctions;

public class Login_steps {
	WebDriver driver;
	LoginPage lp;
	EnterMobileNumberOrEmailPage ep;
	PasswordPage pp;
	Homepage hp;
	GenericFunctions oGF;

	public Login_steps() {
		driver = DriverInstance.getDriver();
		oGF = new GenericFunctions(driver);
	}

	@Given("user is on amazon sign in page")
	public void user_is_on_amazon_sign_in_page() throws IOException {
		String url = oGF.getConfigProperty("URL");
		System.out.println("URL from properties file: " + url);
		driver.get(url);
		//oGF.captureScreenshot(driver);
		lp = new LoginPage(driver);
		lp.clickSignInButton();
	}

	@When("^user enters (.*) and (.*)$")
	public void user_enters_username_and_password(String username, String password) {
		ep = new EnterMobileNumberOrEmailPage(driver);
		pp = new PasswordPage(driver);
		ep.enterMobileNoOrEmail(username);
		//oGF.captureScreenshot(driver);
		ep.clickOnContinue();
		pp.enterPassword(password);
		//oGF.captureScreenshot(driver);
	}

	@And("user clicks on sign in button")
	public void user_clicks_on_sign_in_button() {
		pp.clickOnSignInButton();
	}

	@Then("user lands on amazon homepage successfully")
	public void user_lands_on_amazon_homepage_successfully() {
		hp = new Homepage(driver);
		//oGF.captureScreenshot(driver);
		hp.verifyHomepageLanding();
	}

	@Then("user fails to land on amazon homepage")
	public void user_fails_to_land_on_amazon_homepage() {
		hp = new Homepage(driver);
		hp.verifyLoginWithInvalidCredentials();
	}

}
