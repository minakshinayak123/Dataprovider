package scripts;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.*;

import pages.HomePage;
import pages.RegisterPage;
import pages.RegistrationSuccessPage;
import utilities.Constants;
import utilities.SeleniumHelper;
import utilities.XMLParser;

/**
 * The class contains test method for verifying new user registration
 */
public class RegisterTest extends SeleniumHelper {

	public XMLParser xml = new XMLParser(Constants.SCRIPTS + "/data/RegistrationData.xml");
	private static Logger logger = Logger.getLogger(RegisterTest.class.getName());


	/**
	 * The method verifies new user registration for flight booking using the parameters specified
	 * 
	 * @param username
	 * @param password
	 * @param phoneNo
	 * @param address
	 * @param city
	 * @param state
	 * @param postalCode
	 */
	@Test(testName = "Register",dataProvider = "RegisterData")
	public void testRegister(String username , String password , String phoneNo, String address,String city,String state,String postalCode) {
		try{
			HomePage homePage = new HomePage();
			homePage.clickRegisterLink();
			verifyTitle("Register: Mercury Tours");
			RegisterPage registerPage = new RegisterPage();
			registerPage.register(username,password,phoneNo,address ,city ,state, postalCode);
			RegistrationSuccessPage successPage =  new RegistrationSuccessPage();
			successPage.verifySuccessMessage();
			verifyElementDisplayed(successPage.signOffLink,"SignOff Link");
			report("Registration successful");
		}
		catch(Exception e){
			logger.error("Registration unsuccessful");
			fail("Error occured in execution", false);
		}
	}


	/**
	 * @return
	 */
	@DataProvider(name="RegisterData")
	public Object[][] getRegistrationData(){
		List<String> phoneNo =  xml.getSimilarXMLData("CONTACT_INFO","PHONE");
		List<String> address =  xml.getSimilarXMLData("MAILING_INFO","ADDRESS");
		List<String> city =  xml.getSimilarXMLData("MAILING_INFO","CITY");
		List<String> state =  xml.getSimilarXMLData("MAILING_INFO","STATE");
		List<String> postalCode =  xml.getSimilarXMLData("MAILING_INFO","POSTAL_CODE");
		List<String> username =  xml.getSimilarXMLData("USER_INFO","USER_NAME");
		List<String> pwd =  xml.getSimilarXMLData("USER_INFO","PASSWORD");

		String[][] dataArray = new String[username.size()][7];
		for(int i=0;i<username.size();i++)
		{
			dataArray[i][0]= username.get(i);
			dataArray[i][1]= pwd.get(i);
			dataArray[i][2]= phoneNo.get(i);
			dataArray[i][3]= address.get(i);
			dataArray[i][4]= city.get(i);
			dataArray[i][5]= state.get(i);
			dataArray[i][6]= postalCode.get(i);
		}
		return dataArray;
	}

}
