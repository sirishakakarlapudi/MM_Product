package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import java.io.File;

public class PDFUtil {

    /**
     * Reusable method to open a downloaded PDF in a new tab, scroll through it,
     * take screenshots of all pages, and return to the parent window.
     * 
     * @param driver     The WebDriver instance
     * @param folderPath The path where the PDF is downloaded
     * @param fileName   The name of the PDF file (e.g., "Report.pdf")
     * @param stepLabel  The prefix label for the screenshots
     */
    public static void openAndCapturePDF(WebDriver driver, String folderPath, String fileName, String stepLabel)
            throws Exception {
        File pdf = new File(folderPath + File.separator + fileName);

        // 1. Wait until file exists (up to 10 seconds)
        int attempts = 0;
        while (!pdf.exists() && attempts < 20) {
            Thread.sleep(500);
            attempts++;
        }

        if (!pdf.exists()) {
            System.out.println("❌ PDF file not found after waiting: " + pdf.getAbsolutePath());
            return;
        }

        String parentWindow = driver.getWindowHandle();

        // 2. Security Bypass: Open blank tab first, then navigate to local file
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");

        // Switch focus to the new tab
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Navigate to the PDF file
        driver.get(pdf.toURI().toString());
        Thread.sleep(5000); // Time for PDF viewer to initialize

        // 3. Ensure the PDF viewer has focus to enable scrolling
        try {
            // Gain focus by clicking in the center of the window
            int width = driver.manage().window().getSize().getWidth();
            int height = driver.manage().window().getSize().getHeight();
            new Actions(driver).moveByOffset(width / 2, height / 2).click().perform();
            Thread.sleep(1000); // 1s for focus processing
        } catch (Exception e) {
            System.out.println("⚠️ Focus click failed, attempting basic body click...");
            try {
                driver.findElement(By.tagName("body")).click();
            } catch (Exception ex) {
            }
        }
        Thread.sleep(2000);

        // 4. Dynamic Scroll and Capture
        System.out.println("📸 Starting PDF capture for: " + fileName);
        long lastScrollPosition = -1;

        for (int i = 1; i <= 15; i++) {
            // Detect if we reached the bottom by checking scroll position change
            long currentScroll = -1;
            try {
                currentScroll = ((Number) ((JavascriptExecutor) driver).executeScript(
                        "return window.pageYOffset || document.documentElement.scrollTop || window.scrollY || document.scrollingElement.scrollTop || 0;"))
                        .longValue();

                System.out.println(">>> Page " + i + " | Scroll Position: " + currentScroll);

                // If position hasn't changed since the last iteration, we are at the bottom.
                // We check i > 1 to ensure we take at least one screenshot even if the scroll
                // is 0.
                if (i > 1 && currentScroll == lastScrollPosition) {
                    System.out.println(">>> Reached end of PDF (No movement detected). Stopping.");
                    break;
                }
                lastScrollPosition = currentScroll;
            } catch (Exception e) {
                System.out.println("ℹ️ Scroll detection failed, relying on max page count.");
            }

            // Capture current view
            String suffix = " - Page " + i;
            if (stepLabel != null && !stepLabel.isEmpty()) {
                ScreenshotUtil.takeStepScreenshot(stepLabel + suffix);
            } else {
                ScreenshotUtil.capture(suffix);
            }

            // Perform Page Down to advance document
            new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
            Thread.sleep(3000);

            System.out.println("✔️ Processed page " + i);
        }
        System.out.println("✅ PDF capture sequence completed.");

        // 5. Cleanup: Close tab and return to parent
        driver.close();
        driver.switchTo().window(parentWindow);
    }
}
