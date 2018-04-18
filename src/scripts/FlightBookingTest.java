package scripts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.BookFlightPage;
import pages.FindAFlightPage;
import pages.HomePage;
import pages.SelectFlightPage;
import utilities.Constants;
import utilities.MyThreadLocal;
import utilities.SeleniumHelper;
import utilities.XMLParser;

/**
 * The class contains test method for verifying Flight Booking functionality
 */
public class FlightBookingTest extends SeleniumHelper {

	public XMLParser xml = new XMLParser(Constants.SCRIPTS + "/data/FlightData.xml");
	private static Logger logger = Logger.getLogger(FlightBookingTest.class.getName());


	/**
	 * Test method for verifying booking a flight using the parameters specified
	 * 
	 * @param firstName
	 * @param lastName
	 * @param serviceClass
	 * @param airlines
	 * @param passengersCount
	 * @param username
	 * @param password
	 * @param creditCardNumber
	 */
	@Test(testName = "Flight Booking",dataProvider = "FlightBookingData")
	public void testBookFlight(String firstName , String lastName , String serviceClass, String airlines,String passengersCount,String username,String password ,String creditCardNumber) {
		try{
			HomePage homePage = new HomePage();
			homePage.signIn(username, password);
			verifyTitle(xml.getXMLData("PAGE_TITLE_VALIDATION","SIGN_IN"));
			FindAFlightPage findFlightPage=  new FindAFlightPage();
			findFlightPage.selectPassengers(passengersCount);
			findFlightPage.selectServiceClass(serviceClass);
			findFlightPage.selectAirlines(airlines);
			findFlightPage.clickContinueButton();
			SelectFlightPage selectFlightPage = new SelectFlightPage();
			selectFlightPage.clickContinueButton();
			BookFlightPage bookFlight = new BookFlightPage();
			bookFlight.fillUpPassengersInfo(firstName,lastName,creditCardNumber);
			bookFlight.bookFlight();
			verifyTitle(xml.getXMLData("PAGE_TITLE_VALIDATION","FLIGHT_CONFIRMATION"));

			String bodyText = MyThreadLocal.getDriver().findElement(By.tagName("tbody")).getText();
			Assert.assertTrue(bodyText.contains("Your itinerary has been booked!"), "Verified flight confirmation message");
			report("Flight booking done successfully");
		}
		catch(Exception e){
			logger.error("Flight Booking unsuccessful");
			fail("Error occured in execution", false);
		}
	}


	/**
	 * @return
	 */
	@DataProvider(name="FlightBookingData")
	public Object[][] getFlightBookingData(){

		List<String> firstName =  xml.getSimilarXMLData("CONTACT_INFO","FIRST_NAME");
		List<String> lastName =  xml.getSimilarXMLData("CONTACT_INFO","LAST_NAME");
		List<String> serviceClass =  xml.getSimilarXMLData("FLIGHT_PREFERENCES","SERVICE_CLASS");
		List<String> airlines =  xml.getSimilarXMLData("FLIGHT_PREFERENCES","AIRLINE");
		List<String> passengersCount =  xml.getSimilarXMLData("PASSENGERS_INFO","NO_OF_PASSENGERS");
		List<String> username =  xml.getSimilarXMLData("LOGIN_INFO","USER_NAME");
		List<String> password =  xml.getSimilarXMLData("LOGIN_INFO","PASSWORD");
		List<String> creditCardNumber =  xml.getSimilarXMLData("CREDIT_CARD_INFO","CREDIT_CARD_NO");

		String[][] dataArray = new String[username.size()][8];
		for(int i=0;i<username.size();i++)
		{
			dataArray[i][0]= firstName.get(i);
			dataArray[i][1]= lastName.get(i);
			dataArray[i][2]= serviceClass.get(i);
			dataArray[i][3]= airlines.get(i);
			dataArray[i][4]= passengersCount.get(i);
			dataArray[i][5]= username.get(i);
			dataArray[i][6]= password.get(i);
			dataArray[i][7]= creditCardNumber.get(i);
		}
		return dataArray;
	}

}
