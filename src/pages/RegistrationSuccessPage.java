package pages;

import static utilities.MyThreadLocal.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;

public class RegistrationSuccessPage extends SeleniumHelper{

	@FindBy(linkText = "SIGN-OFF") public WebElement signOffLink;
	@FindBy(xpath = "//table/tbody/tr[3]/td/p[2]/font") WebElement successMessage;

	public RegistrationSuccessPage() {
		PageFactory.initElements(getDriver(), this);
	}

	
	public void verifySuccessMessage() {
		containExpected("Registration success message",successMessage.getText(), "Thank you for registering.");
	}
}
