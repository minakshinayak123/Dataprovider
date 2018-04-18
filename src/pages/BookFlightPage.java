package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;
import static utilities.MyThreadLocal.getDriver;


public class BookFlightPage extends SeleniumHelper {

	@FindBy(name = "buyFlights") WebElement securePurchaseButton;
	@FindBy(name = "passFirst0") WebElement firstName;
	@FindBy(name = "passLast0") WebElement lastName;
	@FindBy(name = "creditnumber") WebElement creditCardNo;


	public BookFlightPage() {
		PageFactory.initElements(getDriver(), this);
	}


	/**
	 * @param first_name
	 * @param last_name
	 * @param creditCardNumber
	 */
	public void fillUpPassengersInfo(String first_name, String last_name ,String creditCardNumber) {
		setText(firstName,first_name);
		setText(lastName,last_name);
		setText(creditCardNo,creditCardNumber);
	}

	public void bookFlight() {
		click(securePurchaseButton);
	}


}
