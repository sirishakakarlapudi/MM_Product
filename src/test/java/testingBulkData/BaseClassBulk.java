package testingBulkData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClassBulk {
	
	WebDriver driver;
	@BeforeClass
	
	public void setup() {
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("http://localhost:8080/login");
		
		
		
		
		
	}
	
	@AfterClass
	
	public void tearDown() {
		driver.quit();
	}

}
