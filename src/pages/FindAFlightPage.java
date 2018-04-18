package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;
import static utilities.MyThreadLocal.getDriver;


public class FindAFlightPage extends SeleniumHelper {

	@FindBy(name = "findFlights") WebElement continueButton;
	@FindBy(css = "input[type='radio'][value='Business']") WebElement businessClass;
	@FindBy(css = "input[type='radio'][value='Coach']") WebElement economyClass;
	@FindBy(css = "input[type='radio'][value='First']") WebElement firstClass;
	@FindBy(css = "select[name='passCount']") WebElement selectPassengers;
	@FindBy(css = "select[name='airline']") WebElement selectAirline;


	public FindAFlightPage() {
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * @param serviceClassType
	 */
	public void selectServiceClass(String serviceClassType) {
		serviceClassType= serviceClassType.toUpperCase();

		switch (serviceClassType) {
		case "BUSINESS":
			click(businessClass);
			break;

		case "ECONOMY":
			click(economyClass);
			break;

		case "FIRST":
			click(firstClass);
			break;

		default:
			click(economyClass);
			break;
		}
	}

	/**
	 * @param noOfPassengers
	 */
	public void selectPassengers(String noOfPassengers) {
		selectFromDropdownByValue(selectPassengers, noOfPassengers);
	}

	/**
	 * @param airline
	 */
	public void selectAirlines(String airline) {
		selectByVisibleText(selectAirline, airline);
	}

	public void clickContinueButton() {
		click(continueButton);
		report("Successfully found the flight");
	}


}
