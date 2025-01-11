package a4_MyHR_and_Agent_GenericFunctions;

import a3_Selenium_GenericMethods.PortalGenericMethods2;

/* this class hosts the actions required to create a case using Exit Management Eform */
public class testfile extends PortalGenericMethods2 {

	public static void main(String Methodname) throws Exception {

		try {
			CreateTest("Test 1");
			//IntiateDriver("Edge", "https://myhr-lindeuat.zendesk.com");
			Thread.sleep(4000);
			LogPASS("URL accessed");
			captureScreenshot2(Methodname, "Login page");
			EnterValue("//input[@id='user_email']", "henryeliasDE.end@gmail.com");
			EnterValue("//input[@id='user_password']", "welcome");
			Click("//button[@id='sign-in-submit-button']");
			LogPASS("Navigated to Home page successfully");
			captureScreenshot2(Methodname, "Home page");

		} catch (org.openqa.selenium.NoSuchElementException a) {
			a.printStackTrace();
		}
		CloseReport();
	}

}