package Parallel_Testing;

import java.io.File; 
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class ParallelTest {

	WebDriver driver = null;
	String filePath = System.getProperty("user.dir");

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.out.println("Inside ChromeTest Method");
			System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
			
		else if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\Drivers\\geckodriver.exe");
			System.out.println("Inside FireFoxTest Method");
			driver = new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "D:\\Drivers\\msedgedriver.exe");
			System.out.println("Inside Edge Method");
			driver = new EdgeDriver();
		}		
	}

	public void amazon(WebDriver driver) throws IOException, InterruptedException {
		// Navigating to Amazon Website
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();

		// Entering the input search text
		WebElement searchInputBar = driver.findElement(By.id("twotabsearchtextbox"));
		searchInputBar.sendKeys("iPhone 11");

		// Searching the input text using search icon
		WebElement searchIcon = driver.findElement(By.id("nav-search-submit-button"));
		searchIcon.click();

		// Selecting the first result of iPhone 11
		WebElement result = driver.findElement(By.linkText("New Apple iPhone 11 (64GB) - Black"));
		result.click();

		// Switching to result window
		String parentHandle = driver.getWindowHandle();
		Set<String> allHandles = driver.getWindowHandles();

		Iterator<String> itr = allHandles.iterator();
		while (itr.hasNext()) {
			String nextWindow = itr.next();

			if (!parentHandle.equals(nextWindow)) {
				driver.switchTo().window(nextWindow);

				WebElement cartButton = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
				cartButton.click();
				
				/*
				 * WebElement closePopUp =
				 * driver.findElement(By.xpath("//a[@id='attach-close_sideSheet-link']"));
				 * closePopUp.click();
				 */
				
				Thread.sleep(10000);
				
				// Taking screenshot
				TakesScreenshot pic = ((TakesScreenshot) driver);
				File scrshot = pic.getScreenshotAs(OutputType.FILE);
				File destFile = new File("D:\\BrowserScreenshots.png");
				FileUtils.copyFile(scrshot, destFile);
			}
		}
	}
}