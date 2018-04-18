package scripts;

import java.util.List;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import pages.HomePage;
import utilities.Constants;
import utilities.SeleniumHelper;
import utilities.XMLParser;

/**
 * The class contains test method for verifying Sign In functionality
 *
 */
public class SignInTest extends SeleniumHelper {

	public XMLParser xml = new XMLParser(Constants.SCRIPTS + "/data/LoginData.xml");
	private static Logger logger = Logger.getLogger(SignInTest.class.getName());


	/**
	 * This method verifies user sign in from home page
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(testName = "Sign In",dataProvider = "SignInData")
	public void testSignInFromHomePage(String username , String password) throws Exception {
		try{

			HomePage homePage = new HomePage();
			homePage.signIn(username, password);
			verifyTitle(xml.getXMLData("PAGE_TITLE_VALIDATION","SIGN_IN"));
			report("Login successful");
		}
		catch(Exception e){
			report(e.getMessage());
			logger.error("Login unsuccessful");
			fail("Error in execution", false);
		}
	}


	/**
	 * @return
	 */
	@DataProvider(name="SignInData")
	public Object[][] getSignInData(){

		List<String> usernames =  xml.getSimilarXMLData("LOGIN_INFO","USER_NAME");
		List<String> pwd =  xml.getSimilarXMLData("LOGIN_INFO","PASSWORD");

		String[][] dataArray = new String[usernames.size()][2];
		for(int i=0;i<usernames.size();i++)
		{
			dataArray[i][0]= usernames.get(i);
			dataArray[i][1]= pwd.get(i);
		}
		return dataArray;
	}


}
