package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import driver.DataTableInstance;
import driver.DriverInstance;
import io.cucumber.java.en.*;
import pageClasses.BestSellersPage;
import pageClasses.EnterMobileNumberOrEmailPage;
import pageClasses.Homepage;
import pageClasses.LoginPage;
import pageClasses.PasswordPage;

public class Login_steps {
	private WebDriver driver;
	private LoginPage lp;
	private EnterMobileNumberOrEmailPage ep;
	private PasswordPage pp;
	private Homepage hp;
	private BestSellersPage bsp;
	private HashMap<String, String> dataTable;

	public Login_steps() {
		driver = DriverInstance.getDriver();
		dataTable = DataTableInstance.getDataTable();
	}

	@Given("^user is on amazon sign in page$")
	public void user_is_on_amazon_sign_in_page() throws IOException {
		lp = new LoginPage(driver);
		lp.hitLoginURL();
		lp.clickSignInButton();
	}

	@When("^user enters username and password$")
	public void user_enters_username_and_password() {
		ep = new EnterMobileNumberOrEmailPage(driver);
		pp = new PasswordPage(driver);
		ep.enterMobileNoOrEmail();
		ep.clickOnContinue();
		pp.enterPassword();
	}

	@And("^user clicks on sign in button$")
	public void user_clicks_on_sign_in_button() {
		pp = new PasswordPage(driver);
		pp.clickOnSignInButton();
	}

	@Then("^user lands on amazon homepage successfully$")
	public void user_lands_on_amazon_homepage_successfully() {
		hp = new Homepage(driver);
		hp.verifyHomepageLanding();
	}

	@When("^user enters invalid (.*) or (.*)$")
	public void user_enters_invalid_username_or_password(String username, String password) {
		pp = new PasswordPage(driver);
		ep = new EnterMobileNumberOrEmailPage(driver);
		ep.enterMobileNoOrEmail(username);
		ep.clickOnContinue();
		pp.enterPassword(password);
	}

	@Then("^user fails to land on amazon homepage$")
	public void user_fails_to_land_on_amazon_homepage() {
		hp = new Homepage(driver);
		hp.verifyLoginWithInvalidCredentials();
	}

	@When("^user enters username and password from database$")
	public void user_enters_username_and_password_from_database() {
		ep = new EnterMobileNumberOrEmailPage(driver);
		pp = new PasswordPage(driver);
		ep.enterMobileNoOrEmail();
		ep.clickOnContinue();
		pp.enterPassword();
	}

	@And("user clicks on all menu button")
	public void user_clicks_on_all_menu_button() {
		hp = new Homepage(driver);
		hp.clickOnAllMenuButton();
	}

	@When("^user clicks on (.*) link in menu section$")
	public void user_clicks_on_bestsellers_link_in_menu_section(String linkName) {
		hp = new Homepage(driver);
		hp.clickOnMenuLink(linkName);
	}

	@And("user clicks on first product in best sellers page")
	public void user_clicks_on_first_product_in_best_sellers_page() {
		bsp = new BestSellersPage(driver);
		bsp.clickOnBestSellersFirstProduct();
	}

	@Then("user verifies eligibility API call is successful")
	public void user_verifies_eligibility_api_call_is_successful() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.verifyAPICall();
	}
	
	@Given("user logged into amazon website")
	public void user_logged_into_amazon_website() throws IOException {
		lp = new LoginPage(driver);
		lp.hitLoginURL();
		lp.clickSignInButton();
		ep = new EnterMobileNumberOrEmailPage(driver);
		pp = new PasswordPage(driver);
		ep.enterMobileNoOrEmail();
		ep.clickOnContinue();
		pp.enterPassword();
		pp.clickOnSignInButton();
		hp = new Homepage(driver);
		hp.verifyHomepageLanding();
	}

}
