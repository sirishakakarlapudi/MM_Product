package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;

import utilities.ScreenshotUtil;
import utilities.WaitUtil;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;

public class BaseClass {
	public WebDriver driver;
	public Logger log = LogManager.getLogger(this.getClass());
	public String downloadPath;

	public void browserOpen() throws Exception {
		String projectPath = System.getProperty("user.dir");
		downloadPath = projectPath + "\\src\\test\\resources\\Downloads";

		// Create the directory if it doesn't exist
		java.io.File dir = new java.io.File(downloadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("start-maximized", "--force-device-scale-factor=0.8");
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		prefs.put("download.default_directory", downloadPath);
		prefs.put("download.prompt_for_download", false);
		prefs.put("plugins.always_open_pdf_externally", false); // Set to false to allow viewing in browser

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	public void navigateTo(String url) {
		driver.get(url);
		WaitUtil.waitForPageLoad(driver, 20); // ✅ Always wait until page load
	}

	@AfterClass
	public void tearDown() throws Exception {

		// Finalize the document (this adds template at the end)
		ScreenshotUtil.insertScreenshotsAndAppendTemplate();

		driver.quit();
	}

}
