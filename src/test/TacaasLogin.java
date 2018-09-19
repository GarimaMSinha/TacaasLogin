package test;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TacaasLogin {
	public WebDriver driver;
	public Properties Credential = new Properties();
	public Logger log = Logger.getLogger("devpinoyLogger");
	
	@BeforeClass
	public void PreLogin() {
		try {
		FileInputStream fis = new FileInputStream("C:\\Users\\IBM_ADMIN\\Desktop\\selenium\\Trial\\TacaasLogin\\src\\test\\tacaasCredential.properties");
		Credential.load(fis);
		log.info("Properties file loaded in Credential");
		}
		catch(Exception e) {
			log.error(e);
		}
				
	}
	
	@DataProvider(name="getCredentials")
	public Object[][] getCredentials() {
		Object[][] cred = new Object[1][2];
		cred[0][0] = Credential.getProperty("Username");
		cred[0][1] = Credential.getProperty("Password");
		return cred;
		
	}
	
	@BeforeMethod
	public void OpenLink() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		driver = new FirefoxDriver(profile);
		driver.get("https://129.39.136.163:950/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			}
	
	
	@Test(dataProvider = "getCredentials")
	public void Login(String Username,String Password) {
		AssertJUnit.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Client Authentication Remote Service')]")).isDisplayed());
		driver.findElement(By.xpath("//input[@name='DATA']")).sendKeys(Username);
		log.debug("Providing Username");
		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		driver.findElement(By.xpath("//input[@type='password' and @name='DATA']")).sendKeys(Password);
		log.debug("Providing Password");
		driver.findElement(By.xpath("//input[@value='Submit']")).click();	
		if(driver.findElement(By.xpath("//input[@type='radio' and @value='1']")).getAttribute("checked").isEmpty())
		{
			driver.findElement(By.xpath("//input[@type='radio' and @value='1']")).click();
		}
		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		log.debug("Login to TACAAS successful");
	
}

	@AfterTest
	public void BrowserClose() {
		driver.quit();
	}
}