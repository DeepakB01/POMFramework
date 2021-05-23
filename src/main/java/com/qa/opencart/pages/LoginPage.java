package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {

    private WebDriver driver;
	private ElementUtil elementUtil;
	// By locators

	private By email = By.xpath("//input[@id = 'input-email']");
	private By password = By.xpath("//input[@id = 'input-password']");
	private By loginButton = By.xpath("//input[@class='btn btn-primary']");
	private By forgotPwdLink = By.xpath("//div[@class = 'form-group']/a[text() = 'Forgotten Password']");
	private By registerLink = By.linkText("Register");

	// page class constructor

	public LoginPage(WebDriver driver) {
	    this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	// public page actions (methods)
	public String getLoginPageTitle() {
		return elementUtil.waitForTitle(5, Constants.LOGIN_PAGE_TITLE);
	}

	public String getLoginPageUrl() {
		return elementUtil.getPageUrl();
	}

	public boolean isForgotPwdLinkExist() {
          return elementUtil.doIsDisplayed(forgotPwdLink);
	}
	
	public AccountsPage doLogin(String un, String pwd) {
		elementUtil.doSendKeys(email, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton);
		
		return new AccountsPage(driver);
	}
	
	public RegistrationPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);		
	}
}
