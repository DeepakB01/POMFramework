package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultPage;

public class BaseTest {

	DriverFactory df;
	protected WebDriver driver;
    protected LoginPage loginPage;
    protected AccountsPage accPage;
    protected SearchResultPage searchResultPage;
    public ProductInfoPage productInfoPage;
    public RegistrationPage registrationPage;
    public Properties prop;

	@BeforeTest
	@Parameters({"browser"}) 
	public void setUp(String browserName)
//	public void setUp()
	{
		df = new DriverFactory();
		prop = df.init_prop();
		prop.setProperty("browser", browserName);
		driver = df.init_driver(prop);
		loginPage = new LoginPage(driver);
		
	}

	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	
}
