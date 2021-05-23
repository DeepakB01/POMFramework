package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	private OptionsManager optionsManager;
	
	public static String highlight = null;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); 
	
	public WebDriver init_driver(Properties prop) {
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		String browsername = prop.getProperty("browser").trim();
		
		System.out.println("Browser name is: "+ browsername);
		
		if(browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			//	driver = new ChromeDriver(optionsManager.getChromeOptions());
		}
		else if(browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		  //	driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
		}
		else {
			System.out.println("Please pass the correct browser");
		}
		getDiver().manage().window().maximize();
		getDiver().manage().deleteAllCookies();
		getDiver().get(prop.getProperty("url").trim());
		
		return getDiver();
	}
	
	public static synchronized WebDriver getDiver() {
		return tlDriver.get();
	}
	
	
	public Properties init_prop() {
		FileInputStream ip = null;
		prop = new Properties();
		String env = System.getProperty("env");
				
		if(env == null) {
			System.out.println("Running on Environment : --> prod");
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Running on Environment : --> " + env);
			try {
				switch (env) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;	
				default:
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			prop.load(ip);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
		return prop;
	}
	
	/******************** take screenshot ********************/
	
	public String getScreenshot() {
		
	File src = ((TakesScreenshot)getDiver()).getScreenshotAs(OutputType.FILE);
	
	String path = System.getProperty("user.dir")+"/screenshots/"+ System.currentTimeMillis()+".png";
	
	File destination = new File(path);
	
	try {
		FileUtils.copyFile(src, destination);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return path;
	
	}

}
