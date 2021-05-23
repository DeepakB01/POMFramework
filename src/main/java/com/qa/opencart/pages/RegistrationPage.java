package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {

	private ElementUtil elementUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("(//label[@class = 'radio-inline'])[1]/input");
	private By subscribeNo = By.xpath("(//label[@class = 'radio-inline'])[2]/input");
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type = 'submit']");
	private By successMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegistrationPage(WebDriver driver) {
		elementUtil = new ElementUtil(driver);
	}
	
	public boolean accountRegistration(String firstName, String lastName, 
			String email,String telephone, 
			String password, String subscribe) {
		
		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirmPassword, password);
		
		
		if(subscribe.equalsIgnoreCase("yes")) {
			elementUtil.doClick(subscribeYes);
		}
		else {
			elementUtil.doClick(subscribeNo);
		}
		
		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(continueButton);
		
	   String msg = elementUtil.waitForElementVisibleWithElement(successMsg, 5).getText();
	   System.out.println("Account Creation: " + msg);
	   
	   if(msg.contains(Constants.REGISTER_SUCCESS_MSG)) {
		   elementUtil.doClick(logoutLink);
		   elementUtil.doClick(registerLink);
		   return true;
	   }
	   return false;
	}
}