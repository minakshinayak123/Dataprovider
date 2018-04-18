package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.lang.reflect.Method;

/**
 * Class used to achieve thread safety
 * 
 *
 */
public class MyThreadLocal extends SeleniumHelper {

	private static final ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<>();
	private static final ThreadLocal<String> url = new ThreadLocal<>();
	private static final ThreadLocal<String> driver_type = new ThreadLocal<>();
	private static final ThreadLocal<Method> currentMethod = new ThreadLocal<>();


	static void setCurrentMethod(Method value) {
		currentMethod.set(value);
	}

	public static EventFiringWebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(EventFiringWebDriver _driver) {
		driver.set(new EventFiringWebDriver(_driver));
	}

	public static void setDriver(WebDriver _driver) {
		driver.set(new EventFiringWebDriver(_driver));
	}

	public static String getDriverType() {
		return driver_type.get();
	}

	static void setDriverType(String driverType) {
		driver_type.set(driverType);
	}

	public static String getURL() {
		return url.get();
	}

	public static void setURL(String _url) {
		url.set(_url);
	}

	static void clearMethodThread() {
		driver.remove();
		url.remove();
		driver_type.remove();
		currentMethod.remove();
	}

}