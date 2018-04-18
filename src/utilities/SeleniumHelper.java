package utilities;

import static org.testng.Assert.assertTrue;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import listeners.CustomWebDriverEventListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.github.javafaker.Faker;

import static utilities.MyThreadLocal.*;

/**
 * SeleniumHelper class contains useful Functions for Automation Framework 
 * 
 */
public class SeleniumHelper {

	protected static WebDriver driver;
	protected static String environment;
	protected Faker faker = new Faker();

	private static final String CHROME = ResourceBundleUtil.getProperty("chrome");
	private static final String FIREFOX = ResourceBundleUtil.getProperty("firefox");
	private static final String INTERNET_EXPLORER = ResourceBundleUtil.getProperty("internet_explorer");
	public static final String TEST_ENVIRONMENT = ResourceBundleUtil.getProperty("test_environment");
	public static final boolean CLOSE_BROWSER = Boolean.parseBoolean(ResourceBundleUtil.getProperty("close_browser"));
	private static final boolean SHOW_REPORT = Boolean.parseBoolean(ResourceBundleUtil.getProperty("show_report"));


	/**
	 * @param _environment
	 */
	@Parameters({"environment"})
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(String _environment) {
		if (_environment == null) {
			environment = System.getProperty("environment");
		} else {
			environment = TEST_ENVIRONMENT;
		}
	}

	/**
	 * 
	 * @param method
	 * @param browser
	 * @throws IOException
	 */
	@Parameters({"browser"})
	@BeforeMethod(alwaysRun = true)
	protected void openBrowser(Method method,@Optional String browser) throws IOException {
		setCurrentMethod(method);
		setDriverType(browser);
		openBrowserWindow();   
	}

	
	/**
	 * Method for initiating the driver
	 */
	protected static void openBrowserWindow() {
		if (getDriverType().equals(CHROME)) {
			ChromeDriverManager.getInstance().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type", "new-window", "disable-extensions", "disable-infobars", "start-maximized", "enable-automation");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions", "--disable-logging",
					"--ignore-certificate-errors", "--log-level=0", "--silent"));
			capabilities.setCapability("silent", true);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			setDriver(new ChromeDriver(capabilities));
		}
		if (getDriverType().equals(FIREFOX)) {
			FirefoxDriverManager.getInstance().setup();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			setDriver(new FirefoxDriver(capabilities));
		}
		if (getDriverType().equals(INTERNET_EXPLORER)) {
			InternetExplorerDriverManager.getInstance().setup();
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability("silent", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability("nativeEvents", false);
			setDriver(new InternetExplorerDriver(capabilities));
		}
		CustomWebDriverEventListener webDriverEventListener = new CustomWebDriverEventListener();
		getDriver().register(webDriverEventListener);
	}


	/**
	 * @param element
	 */
	public void click(WebElement element) {
		try {
			element.click();
		} catch (TimeoutException te) {
			new WebDriverWait(getDriver(), 15).until((ExpectedConditions.elementToBeClickable(element)));
			element.click();
		} catch (Exception e) {
			throw(e) ;
		}
	}

	/**
	 * @param element
	 * @param text
	 */
	public void setText(WebElement element,String text) {
		element.sendKeys(text);
	}

	/**
	 * @param target_URL
	 */
	public void loadURL(String target_URL) {
		try {
			getDriver().get(getURL() + target_URL);
		} catch (TimeoutException te) {
			try {
				getDriver().navigate().refresh();
			} catch (TimeoutException te1) {
				fail("Page failed to load", false);
			}
		}
	}

	/**
	 * @param target_URL
	 */
	public void loadFullURL(String target_URL) {
		getDriver().get(target_URL);
	}

	/**
	 * @param title
	 */
	public void verifyTitle(String title) {
		verifyText(getDriver().getTitle(), title);
	}

	/**
	 * @param actual
	 * @param expected
	 */
	public void verifyText(String actual, String expected) {
		actual = actual.trim();
		expected = expected.trim();

		actual = actual.replaceAll("&nbsp;", "");
		actual = actual.replaceAll(" {2}", " ");

		boolean same = actual.equalsIgnoreCase(expected);
		if (same) {
			pass("ACTUAL: " + actual + ", EXPECTED: " + expected);
		} else {
			fail("ACTUAL: " + actual + ", EXPECTED: " + expected, false);
		}
	}

	/**
	 * @param token
	 * @param actual
	 * @param expected
	 */
	protected void containExpected(String token, String actual, String expected) {
		if (actual.contains(expected)) {
			pass("Actual " + token + " \" " + actual + "\" contains expected " + token + " \"" + expected + "\"");
		} else {
			fail("Actual " + token + " \" " + actual + "\" does not contains expected " + token + " \"" + expected
					+ "\"", true);
		}
	}

	/**
	 * @param element
	 * @param elementName
	 * @return
	 */
	protected boolean verifyElementDisplayed(WebElement element, String elementName) {
		try {
			waitUntilElementIsVisible(element);
			element.isDisplayed();
			pass("\"" + elementName + "\" is displayed on page: " + getDriver().getCurrentUrl());
			return true;
		} catch (Exception e) {
			fail("Unable to find \"" + elementName + "\" on page: " + getDriver().getCurrentUrl(), true);
			String arr[] = ExceptionUtils.getStackTrace(e).split("method");
			if (arr.length > 0) {
				String sFailedLocator = arr[1].substring(2, arr[1].indexOf('}'));
				report(sFailedLocator);
			}
			return false;
		}
	}

	/**
	 * @param message
	 */
	public static void pass(String message) {
		Assert.assertTrue(true);
	}	

	/**
	 * @param message
	 * @param continueExecution
	 */
	public static void fail(String message, boolean continueExecution) {
		if (continueExecution) {
			try {
				assertTrue(false);
			} catch (Throwable e) {

			}
		} else {
			throw new AssertionError(message);
		}
	}

	/**
	 * @param testResult
	 * @throws IOException
	 */
	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			FileUtils.copyFile(scrFile, new File(currentDir + "\\screenshots\\" +testResult.getName()+ timeStamp + ".png"));
		}
		if (CLOSE_BROWSER) {
			try {
				getDriver().manage().deleteAllCookies();
				getDriver().quit();
			} catch (Exception exp) {
			}
		}
		clearMethodThread();
	}

	/**
	 * @param element
	 */
	protected void waitUntilElementToBeClickable(WebElement element) {
		try {
			new WebDriverWait(getDriver(), 15).until((ExpectedConditions.elementToBeClickable(element)));
		} catch (Exception e) {
			fail("Unable to find element with selector : " + element.toString() + " On page : " + getDriver().getCurrentUrl(), false);
			e.getMessage();
		}
	}


	/**
	 * @param element
	 */
	public void waitUntilElementIsVisible(final WebElement element) {
		ExpectedCondition<Boolean> expectation = _driver -> element.isDisplayed();

		Wait<WebDriver> wait = new WebDriverWait(getDriver(), 60);
		try {
			wait.until(webDriver -> expectation);
		} catch (Throwable ignored) {
		}
	}

	/**
	 * @param message
	 */
	public static void report(String message) {
		if (SHOW_REPORT) {
			Reporter.log(message);
		}
	}

	/**
	 * @param element
	 * @param value
	 */
	public void selectFromDropdownByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);

	}

	/**
	 * @param element
	 * @param text
	 */
	public void selectByVisibleText(WebElement element, String text) {
		Select selection = new Select(element);
		selection.selectByVisibleText(text);
	}

	/**
	 * @param driver
	 */
	public void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> condition = driver1 -> getDriver().executeScript("return document.readyState").equals("complete");

		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(condition);
		} catch (Exception error) {
			try {
				getDriver().navigate().refresh();
			} catch (TimeoutException ignored) {
			}
			try {
				Robot keys = new Robot();
				keys.keyPress(KeyEvent.VK_ESCAPE);
				keys.keyRelease(KeyEvent.VK_ESCAPE);
			} catch (AWTException e) {
			}
		}
	}

}
