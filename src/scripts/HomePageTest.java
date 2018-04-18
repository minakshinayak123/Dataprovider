package scripts;

import org.testng.annotations.*;

import pages.HomePage;
import utilities.SeleniumHelper;

/**
 * The class contains test methods for verifying links and image on the HomePage
 *
 */
public class HomePageTest extends SeleniumHelper {

	@Test
	public void testVerifyHeaderRegionLinks() {
		HomePage homePage = new HomePage();
		homePage.verifyAllLinksOnHomePage();
	}

	@Test
	public void testVerifyFeaturedDestinationImage() {
		HomePage homePage = new HomePage();
		verifyElementDisplayed(homePage.featuredDestinationImage, "Featured Destination Image");
	}


}
