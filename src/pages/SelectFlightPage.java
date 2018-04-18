package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;
import static utilities.MyThreadLocal.getDriver;


public class SelectFlightPage extends SeleniumHelper {

	@FindBy(name = "reserveFlights") WebElement continueButton;


	public SelectFlightPage() {
		PageFactory.initElements(getDriver(), this);
	}

	public void clickContinueButton() {
		click(continueButton);
		report("Selected the flight");
	}


}
