package tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ReadConfig;

public class BaseClass {

	ReadConfig readConfig = new ReadConfig();
	String url = readConfig.getBaseUrl();
	public static WebDriver driver;
	public static Logger logger;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		logger = LogManager.getLogger("CS_FrameworkSeleniumJava");
	}

	@AfterClass
	public void tearDown() {
		// driver.close();
		// driver.quit();
	}

	// user method to capture screen shot
	public void captureScreenShot(String testCase) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		// step2: call getScreenshotAs method to create image file
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\Failed__"+testCase+"_"+timeStamp+"_" + ".png");
		// step3: copy image file to destination
		FileUtils.copyFile(src, dest);
	}

	// To verify two strings are equal
	public void verifyTwoStringsAreEqual(String actual, String expectexd) {
		Assert.assertEquals(actual, actual, "" + actual + " and " + expectexd + " are not equal");
		logger.info("Expected text :" + expectexd + " and actual text : " + actual + " are equal");
	}

	// wait for specific duration
	public void hardWait(int wait) {
		try {
			Thread.sleep(wait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// To click on element using wait
	public void clickElementWithWait(WebElement ele, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
	}

	// To click on element using action class
	public void clickUsingAction(WebElement ele, WebDriver driver) {
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ele));
		action.moveToElement(ele).click().perform();
	}

	// To scroll to middle of page
	public void scrollBy(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0, 700);");
	}

}
