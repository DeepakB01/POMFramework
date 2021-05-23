package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest{

	SoftAssert softAssert = new SoftAssert();
	
	@BeforeClass
	public void productInfoSetup() {
	 accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] searchData() {
		return new Object[][] {{"MacBook"}, {"iMac"}};
	}
	
	@Test(dataProvider = "searchData")
	public void productCountTest(String productName) {
		searchResultPage = accPage.doSearch(productName);
	  Assert.assertTrue(searchResultPage.getProductResultsCount() > 3);
	}
	
	@Test(priority=2)
	public void productInfoHeaderTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProductFromResults("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeaderText(), "MacBook Pro");
	}
	
	@Test(priority=3)
	public void productImagesTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProductFromResults("MacBook Pro");
		Assert.assertTrue(productInfoPage.getProductImagesCount() == 4);
	}
	
	@Test(priority=4)
	public void productInfoTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProductFromResults("MacBook Pro");
	Map<String, String> actProductMetaData = productInfoPage.getProductInformation();
	actProductMetaData.forEach((k, v) -> System.out.println(k + " : " + v));
	
	softAssert.assertEquals(actProductMetaData.get("name"), "MacBook Pro");
	softAssert.assertEquals(actProductMetaData.get("Brand"), "Apple");
	softAssert.assertEquals(actProductMetaData.get("Availability"), "Out Of Stock");
	softAssert.assertEquals(actProductMetaData.get("price"), "$2,000.00");
	
	softAssert.assertAll();
	}
	
	@Test(priority=5)
	public void addToCartTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProductFromResults("MacBook Pro");
		productInfoPage.selectQuantity("1");
		productInfoPage.addToCart();
	//	Assert.assertEquals(productInfoPage.getSuccessMessage(), "Success: You have added MacBook Pro to your shopping cart!");
	}
	
}
