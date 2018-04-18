package listeners;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import utilities.SeleniumHelper;

/**
 *  This class helps to get more logs from the Webdriver and to track events those take place in WebDriver
 *  during script execution
 * 
 * @author M1035208
 *
 */
public class CustomWebDriverEventListener extends SeleniumHelper implements WebDriverEventListener {

	private String elementTextToReport = "";
	private String elementType = "";
	private boolean selected;

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] arg2) {
		if (!element.getAttribute("value").equals("")) {
			switch (element.getTagName()) {
			case "input":
				report("Set " + elementTextToReport + " to " + element.getAttribute("value"));
				break;
			}
		}
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		if(!elementTextToReport.equals("")) {
			switch (elementType) {
			case "checkbox":
			case "radio":
				if (selected) {
					report("Selected: " + elementTextToReport);
				} else {
					report("Deselected: " + elementTextToReport);
				}
				break;
			case "button":
				report("Clicked: " + elementTextToReport);
				break;
			case "image":
				report("Clicked: " + elementTextToReport+" button");
				break;
			case "a":
				report("Clicked: " + elementTextToReport);
				break;
			case "option":
				report("Selected " + element.getText() + " from " + elementTextToReport);
				break;
			}
		}
		waitForPageLoaded(driver);
		if(driver.getCurrentUrl().contains("SystemMaintenance")) {
			fail("System Maintenance error page", false);
		}

	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {

	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		waitUntilElementIsVisible(element);
		elementTextToReport = "";
		elementType = "";
		if (element.getTagName().equals("input")) {
			elementType = element.getAttribute("type");
		} else {
			elementType = element.getTagName();
		}
		if (elementType.equals("checkbox") || elementType.equals("radio")) {
			selected = !element.isSelected();
		}
		String elementText = element.getText();
		String elementName = element.getAttribute("name") != null ? element.getAttribute("name").split("\\.")[0].replaceAll("[-_]", " ") : "";
		String elementId = element.getAttribute("id") != null ? element.getAttribute("id") : "";
		String elementValue = element.getAttribute("value") != null ? element.getAttribute("value") : "";
		String elementTitle = element.getAttribute("title") != null ? element.getAttribute("title") : "";

		switch (elementType) {
		case "option":
			WebElement select = element.findElement(By.xpath(".."));
			elementText = select.getText();
			elementName = select.getAttribute("name") != null ? select.getAttribute("name").split("\\.")[0].replaceAll("[-_]", " ") : "";
			elementId = select.getAttribute("id") != null ? select.getAttribute("id") : "";
			elementValue = select.getAttribute("value") != null ? select.getAttribute("value") : "";
			elementTitle = select.getAttribute("title") != null ? select.getAttribute("title") : "";
			if (!elementId.equals("")) {
				elementTextToReport = 
						elementId;
			} else if (!elementName.equals("")) {
				elementTextToReport = elementName;
			} else if (!elementText.equals("")) {
				elementTextToReport = elementText;
			} else if (!elementValue.equals("")) {
				elementTextToReport = elementValue;
			} else if (!elementTitle.equals("")) {
				elementTextToReport = elementTitle;
			}
			break;
		default:
			if (!elementText.equals("")) {
				elementTextToReport = elementText;
			} else if (!elementName.equals("")) {
				elementTextToReport = elementName;
			} else if (!elementId.equals("")) {
				elementTextToReport = elementId;
			} else if (!elementValue.equals("")) {
				elementTextToReport = elementValue;
			} else if (!elementTitle.equals("")) {
				elementTextToReport = elementTitle;
			}
		}
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
	}

	@Override
	public void beforeAlertAccept(WebDriver webDriver) {
	}

	@Override
	public void afterAlertAccept(WebDriver webDriver) {
	}

	@Override
	public void afterAlertDismiss(WebDriver webDriver) {
	}

	@Override
	public void beforeAlertDismiss(WebDriver webDriver) {
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		report("Loaded URL (<a href=\"" + url + "\">" + url + "</a>)");
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] arg2) {

		String tmp = "";

		String elementName = element.getAttribute("name") != null ? element.getAttribute("name").split("\\.")[0].replaceAll("_", " ")
				: "";
		String elementId = element.getAttribute("id") != null
				? element.getAttribute("id").replaceAll("[A-Z]{6,}", "").replaceAll("[-_]", " ").replace("field", "").trim() : "";
				String elementValue = element.getAttribute("value") != null ? element.getAttribute("value") : "";

				if (!elementName.equals("")) {
					tmp = StringUtils
							.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementName)), ' ');
				} else if (!elementId.equals("")) {
					tmp = StringUtils
							.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementId)), ' ');
				} else if (!elementValue.equals("")) {
					tmp = elementValue;
				}

				elementTextToReport = tmp;
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}
}