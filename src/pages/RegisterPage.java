package pages;

import static utilities.MyThreadLocal.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumHelper;


public class RegisterPage extends SeleniumHelper{

	@FindBy(name = "firstName") WebElement firstName;
	@FindBy(name = "lastName") WebElement lastName;
	@FindBy(name = "phone") WebElement phone;
	@FindBy(name = "userName") WebElement email;
	@FindBy(name = "address1") WebElement address;
	@FindBy(name = "city") WebElement city;
	@FindBy(name = "state") WebElement state;
	@FindBy(name = "postalCode") WebElement postalCode;
	@FindBy(name = "email") WebElement username;
	@FindBy(name = "password") WebElement password;
	@FindBy(name = "confirmPassword") WebElement confirmPassword;
	@FindBy(css = "input[name='register']") WebElement registerSubmitButton;

	public RegisterPage() {
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * @param login
	 * @param pwd
	 * @param phoneNumber
	 * @param address2
	 * @param city2
	 * @param state2
	 * @param postalCode2
	 */
	public void register(String login, String pwd, String phoneNumber, String address2, String city2, String state2, String postalCode2) {
		String first_name = faker.name().firstName().replaceAll("'", "");
		String last_name = faker.name().lastName().replaceAll("'", "");
		setText(firstName,first_name);
		setText(lastName,last_name);
		setText(email,first_name+"."+last_name+"test.com");
		setText(phone,phoneNumber);
		setText(address,address2);
		setText(city,city2);
		setText(state,state2);
		setText(postalCode,postalCode2);

		setText(username,login);
		setText(password,pwd);
		setText(confirmPassword,pwd);
		clickRegisterSubmitLink();
	}

	public void clickRegisterSubmitLink() {
		//waitUntilElementToBeClickable(registerSubmitButton);
		click(registerSubmitButton);
	}
}
