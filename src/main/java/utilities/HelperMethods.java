package utilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperMethods {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected final Logger log = LogManager.getLogger(this.getClass());
	protected String[] tableHeaders = null;

	public HelperMethods(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public enum TableActionType {
		CLICK, CHECK_VISIBILITY, GET_TEXT, CHECK_MENU
	}

	/*--------------------	Helper Methods---------------------*/

	public void waitForElementandClick(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	public String waitForToast() {
		try {
			WebElement toast = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-toast-detail")));
			return toast.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public void waitForLoading() {
		try {
			new WebDriverWait(driver, Duration.ofMillis(300))
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.loader-container")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader-container")));
		} catch (Exception e) {
		}
	}

	public void waitForElementToVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void scrollAndClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void triggerInputAndChange(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", element);
		js.executeScript("arguments[0].dispatchEvent(new Event('blur', {bubbles: true}));", element);
	}

	public void clearField(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '';", element);
		triggerInputAndChange(element);
	}

	protected void waitAndSendKeys(WebElement element, String value) {
		wait.until(ExpectedConditions.visibilityOf(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		element.sendKeys(value.trim());
		triggerInputAndChange(element);
	}

	protected void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	protected void jsScrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	public void scrollAndClickWithPageDown(WebElement element) throws InterruptedException {
		Actions actions = new Actions(driver);
		int maxScrolls = 5;
		for (int i = 0; i < maxScrolls; i++) {
			actions.sendKeys(Keys.PAGE_DOWN).perform();
			Thread.sleep(300);
			if (element.isDisplayed())
				break;
		}
		element.click();
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	protected void clickVisibleElementByXpath(String xpath) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		boolean isClicked = false;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
				try {
					element.click();
				} catch (Exception e) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
				isClicked = true;
				break;
			}
		}
		if (!isClicked) {
			try {
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				element.click();
			} catch (Exception e) {
				throw new RuntimeException(
						"Element with XPath '" + xpath + "' found in DOM but none were visible/clickable.");
			}
		}
	}

	public String getElementText(String xpath) {
		try {
			return driver.findElement(By.xpath(xpath)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	/*----------------Methods for Table Actions--------------------*/

	public void setTableHeaders(String... headers) {
		this.tableHeaders = headers;
	}

	public void setTableHeaders(String commaSeparatedHeaders) {
		if (commaSeparatedHeaders != null && !commaSeparatedHeaders.isEmpty()) {
			this.tableHeaders = commaSeparatedHeaders.split(",");
			for (int i = 0; i < this.tableHeaders.length; i++) {
				this.tableHeaders[i] = this.tableHeaders[i].trim();
			}
		}
	}

	public int getColumnIndexByHeader(String headerName) {
		List<WebElement> headers = driver.findElements(By.xpath("//thead//th"));
		for (int i = 0; i < headers.size(); i++) {
			if (headers.get(i).getText().trim().equalsIgnoreCase(headerName)) {
				return i + 1;
			}
		}
		throw new RuntimeException("Column not found: " + headerName);
	}

	protected List<Integer> getDynamicColumnIndices() {
		List<Integer> indices = new ArrayList<>();
		if (tableHeaders == null || tableHeaders.length == 0) {
			indices.add(1);
			return indices;
		}
		List<WebElement> headerElements = driver.findElements(By.xpath("//thead//th"));
		for (String expectedHeader : tableHeaders) {
			boolean found = false;
			for (int i = 0; i < headerElements.size(); i++) {
				if (headerElements.get(i).getText().trim().equalsIgnoreCase(expectedHeader)) {
					indices.add(i + 1);
					found = true;
					break;
				}
			}
			if (!found)
				throw new RuntimeException("Header not found in table: " + expectedHeader);
		}
		return indices;
	}

	protected Object scanTable(String rowXpath, String paginatorXpath, TableActionType type, String subPath,
			boolean expectedVisibility, String... expectedValues) throws Exception {
		int totalPages = driver.findElements(By.xpath(paginatorXpath)).size();
		if (totalPages == 0)
			totalPages = 1;
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			for (int r = 1; r <= rows.size(); r++) {
				boolean match = true;
				for (int i = 0; i < targetIndices.size(); i++) {
					WebElement cell = driver
							.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
					if (i == 0)
						jsScrollToElement(cell);
					if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
						match = false;
						break;
					}
				}
				if (match) {
					switch (type) {
						case CLICK:
							jsClick(driver.findElement(By.xpath(rowXpath + "[" + r + "]" + subPath)));
							Thread.sleep(1000);
							return true;
						case CHECK_VISIBILITY:
							boolean visible = !driver.findElements(By.xpath(rowXpath + "[" + r + "]" + subPath))
									.isEmpty();
							if (visible != expectedVisibility)
								throw new RuntimeException("Visibility mismatch for " + subPath);
							return true;
						case GET_TEXT:
							int col = getColumnIndexByHeader(subPath);
							return driver.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + col + "]")).getText()
									.trim();
						case CHECK_MENU:
							jsClick(driver.findElement(
									By.xpath(rowXpath + "[" + r + "]//span[contains(@class,'pi-ellipsis-v')]")));
							Thread.sleep(1000);
							List<String> actual = driver.findElements(By.xpath(
									"//ul[@role='menu']//li//span | //div[contains(@class,'p-menu-overlay')]//li//span"))
									.stream().map(e -> e.getText().trim()).filter(t -> !t.isEmpty())
									.collect(java.util.stream.Collectors.toList());
							jsClick(driver.findElement(By.tagName("body")));
							return actual;
					}
				}
			}
			if (p < totalPages) {
				jsClick(wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath(paginatorXpath + "[" + (p + 1) + "]"))));
				Thread.sleep(2000);
			}
		}
		throw new RuntimeException("Item not found: " + Arrays.toString(expectedValues));
	}

	protected void performTableActionGeneric(List<WebElement> pageCountElements, String rowXpath,
			String paginatorButtonPrefix, String actionButtonSubPath, String... expectedValues) throws Exception {
		int totalPages = (pageCountElements.size() == 0) ? 1 : pageCountElements.size();
		List<Integer> targetIndices = getDynamicColumnIndices();

		for (int p = 1; p <= totalPages; p++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
			List<WebElement> rows = driver.findElements(By.xpath(rowXpath));
			for (int r = 1; r <= rows.size(); r++) {
				try {
					boolean rowMatches = true;
					for (int i = 0; i < targetIndices.size(); i++) {
						WebElement cell = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]/td[" + targetIndices.get(i) + "]"));
						if (i == 0)
							jsScrollToElement(cell);
						if (!cell.getText().trim().equalsIgnoreCase(expectedValues[i].trim())) {
							rowMatches = false;
							break;
						}
					}
					if (rowMatches) {
						WebElement actionBtn = driver
								.findElement(By.xpath(rowXpath + "[" + r + "]" + actionButtonSubPath));
						jsClick(actionBtn);
						Thread.sleep(2000);
						return;
					}
				} catch (Exception e) {
				}
			}
			if (p < totalPages) {
				String nextBtnXpath = paginatorButtonPrefix + "[" + (p + 1) + "]";
				WebElement nextPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextBtnXpath)));
				jsScrollToElement(nextPage);
				nextPage.click();
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
			}
		}
		throw new RuntimeException("Item not found in table: " + Arrays.toString(expectedValues));
	}

	
	public String getStatus(String... expItemNames) throws Exception {
		return (String) scanTable("//tbody[@role='rowgroup']/tr",
				"//span[@class='p-paginator-pages ng-star-inserted']/button",
				TableActionType.GET_TEXT, "Status", false, expItemNames);
	}

	public WebElement getButtonByText(String text) {
		try {
			return driver.findElement(By.xpath(
					"//button[translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
							+ text.toUpperCase() + "']"));
		} catch (Exception e) {
			return null;
		}
	}

	public void clickVisibleElementByText(String text) {
		clickVisibleElementByXpath("//*[normalize-space()='" + text + "']");
	}
}
