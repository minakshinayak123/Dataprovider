package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;
import static utilities.MyThreadLocal.getDriver;


public class HomePage extends SeleniumHelper {

	@FindBy(linkText = "REGISTER") WebElement registerLink;
	@FindBy(linkText = "SIGN-ON") WebElement signOnLink;
	@FindBy(linkText = "SUPPORT") WebElement supportLink;
	@FindBy(linkText = "CONTACT") WebElement contactLink;
	@FindBy(css = "img[src='/images/featured_destination.gif']") public WebElement featuredDestinationImage;
	@FindBy(name = "userName") WebElement username;
	@FindBy(name = "password") WebElement password;
	@FindBy(name = "login") WebElement signInButton;


	public HomePage() {
		PageFactory.initElements(getDriver(), this);
		if (!getDriver().getCurrentUrl().equals("http://newtours.demoaut.com/")) {
			loadFullURL("http://newtours.demoaut.com/");
		}
	}

	public void clickRegisterLink() {
		click(registerLink);
	}

	/**
	 * @param user
	 * @param pwd
	 */
	public void signIn(String user,String pwd) {
		setText(username,user);
		setText(password,pwd);
		click(signInButton);
	}

	public void verifyAllLinksOnHomePage() {
		verifyElementDisplayed(registerLink, "Register Link");
		verifyElementDisplayed(signOnLink, "Sign-on Link");
		verifyElementDisplayed(supportLink, "Support Link");
		verifyElementDisplayed(contactLink, "Contact Link");

	}
}
