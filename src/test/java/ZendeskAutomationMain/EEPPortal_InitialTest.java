package ZendeskAutomationMain;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import a1_RegressionScripts.EEPPortal;
import a3_Selenium_GenericMethods.PortalGenericMethods2;
import a4_MyHR_and_Agent_GenericFunctions.testfile;
import a5_Eform_Germany_LGLCO.e1_Second_Employer_Eform;


public class EEPPortal_InitialTest extends PortalGenericMethods2  {
	
	public static Logger log = Logger.getLogger("EEPPortal_InitialTest.class");
	public static EEPPortal eep = new EEPPortal();
	public static String GeneratedReportName;
	

@BeforeTest
@Parameters({ "Browser", "URL" })
public static void driverInitiation(String Browser,String URL) throws Exception {

	try {
		System.out.println("EEPPortal_InitialTest constructor invoked.");
	System.out.println("--------1");
	//System.out.println(BrowserName+" "+URL	);
	GeneratedReportName="Linde PLC EEP";
	ConfigureReport(GeneratedReportName);
	//CreateTest("Browser Initiation");
	System.out.println("--------2");
	IntiateDriver(Browser, URL);
	}catch (Exception e) {
		e.printStackTrace();
	}
}


//EEP Portal Test Cases
/*@Test(priority = 1)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Portal_Login(String ScreenshotPath, String InputFilePath) throws Exception {
	System.out.println("0.1-------");
	try {
		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Login to the Portal");
		eep.eepportal(InputFilePath, "Portal_Login", ScreenshotPath,Methodname);

	} catch (Exception e) {
		e.printStackTrace();
		String exceptionName = e.getClass().getSimpleName();
		log.error(exceptionName + " : " + "Portal_Login - failed");
		LogFAIL( exceptionName + " : " + "Portal_Login - failed");
		Assert.fail("Portal_Login - failed" + e.toString());

	}
}

@Test(priority = 2)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Verify_Home_Page(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Checking the functionality on Home Page");
		eep.eepportal(InputFilePath, "Home Page Validation - I", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Validation - failed");
		LogFAIL( exceptionName + " : " + "Home Page Validation - failed");
		Assert.fail("Home Page Validation - failed" + e.toString());

	}
}

@Test(priority = 3)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Verify_Home_PageII(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Checking the functioanality on Home Page(continue)");
		eep.eepportal(InputFilePath, "Home Page Validation - II", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Validation(II) - failed");
		LogFAIL( exceptionName + " : " + "Home Page Validation(II) - failed");
		Assert.fail("Home Page Validation(II) - failed" + e.toString());

	}
}

@Test(priority = 4)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void News(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("News Functionality");
		eep.eepportal(InputFilePath, "News", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "News Functionality - failed");
		LogFAIL(exceptionName + " : " + "News Functionality - failed");
		Assert.fail("News Functionality - failed" + e.toString());

	}
}

@Test(priority = 5)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Topics(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Checking the Topics functionality");
		eep.eepportal(InputFilePath, "Topics", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Topics functionality - failed");
		LogFAIL(exceptionName + " : " + "Topics functionality - failed");
		Assert.fail("Topics functionality - failed" + e.toString());

	}
}

@Test(priority = 6)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Ask_a_Question(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Ask a Question Functionality");
		eep.eepportal(InputFilePath, "Ask a Question", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Ask a Question Functionality - failed");
		LogFAIL(exceptionName + " : " + "Ask a Question Functionality - failed");
		Assert.fail("Ask a Question Functionality - failed" + e.toString());

	}
}
/*
@Test(priority = 7)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void My_Requests(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("My Requests Functionality");
		eep.eepportal(InputFilePath, "My Requests", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "My Requests Functionality - failed");
		LogFAIL(exceptionName + " : " + "My Requests Functionality - failed");
		Assert.fail("My Requests Functionality - failed" + e.toString());

	}
}



@Test(priority = 8)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Apps(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Apps Functionality");
		eep.eepportal(InputFilePath, "Apps", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Apps Functionality - failed");
		LogFAIL(exceptionName + " : " + "Apps Functionality - failed");
		Assert.fail("Apps Functionality - failed" + e.toString());

	}
}

@Test(priority = 10)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Portal_Logout(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Portal_Logout Functionality");
		eep.eepportal(InputFilePath, "Portal_Logout", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Portal_Logout Functionality - failed");
		LogFAIL(exceptionName + " : " + "Portal_Logout Functionality - failed");
		Assert.fail("Portal_Logout Functionality - failed" + e.toString());

	}
}
/*
@Test(priority = 11)
@Parameters({ "ScreenshotPath", "Formsfilepath" })
public static void FormVerification_Countrywise(String ScreenshotPath, String Formsfilepath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		test = extent.createTest("Apps Functionality");
		FormsVerification forms=new FormsVerification();
		forms.Verifyforms_UK(driver, ScreenshotPath, Formsfilepath, "UK_Employee",Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Apps Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Apps Functionality - failed");
		Assert.fail("Apps Functionality - failed" + e.toString());

	}
}

@Test(priority = 12)
@Parameters({ "URL","ScreenshotPath","Appfilepath" })
public static void AppVerification_Countrywise(String URL,String ScreenshotPath,String Appfilepath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("--------in priority 11");
		//test = extent.createTest("Apps Functionality");
		AppsVerification Apps=new AppsVerification();
		Apps.VerifyApps(URL, Appfilepath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Apps Functionality - failed");
		//test.log(Status.FAIL, exceptionName + " : " + "Apps Functionality - failed");
		Assert.fail("Apps Functionality - failed" + e.toString());

	}
}

@Test(priority = 13)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Agent_Login(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Agent_Login Functionality");
		eep.eepportal(InputFilePath, "Agent Login", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Agent_Login Functionality - failed");
		LogFAIL(exceptionName + " : " + "Agent_Login Functionality - failed");
		Assert.fail("Agent_Login Functionality - failed" + e.toString());

	}
}

/*
@Test(priority = 14)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Agent_Create_Ticket(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Agent_Create_Ticket Functionality");
		eep.eepportal(InputFilePath, "Agent_Create_Ticket", ScreenshotPath,Methodname);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Agent_Create_Ticket Functionality - failed");
		LogFAIL(exceptionName + " : " + "Agent_Create_Ticket Functionality - failed");
		Assert.fail("Agent_Create_Ticket Functionality - failed" + e.toString());

	}
}

@Test(priority = 15)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Agent_field_validation(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
		CreateTest("Agent field validation Functionality");
		eep.eepportal(InputFilePath, "Agent field validation_AU", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_DE", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_IE", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_NZ", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_PG", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_SB", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_ZA", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_UK", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		eep.eepportal(InputFilePath, "Agent field validation_USA", ScreenshotPath,Methodname);
		Thread.sleep(2000);
		

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Agent field validation Functionality - failed");
		LogFAIL(exceptionName + " : " + "Agent field validation Functionality - failed");
		Assert.fail("Agent field validation Functionality - failed" + e.toString());

	}
}

@Test(priority = 16)
@Parameters({ "URL","ScreenshotPath","AMSFilePath" })
	public static void AMS_Email_routing_verification(String URL, String ScreenshotPath, String AMSFilePath) throws Exception {
		try {

			
			String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			CreateTest("AMS_Email_routing_verification Functionality");
			System.out.println("-----------1");
			
			AMSEmailRouting.VerifyAMSTickets(URL,AMSFilePath, ScreenshotPath, Methodname);
			
			

		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();

			log.error(exceptionName + " : " + "AAMS_Email_routing_verification Functionality - failed");
			LogFAIL(exceptionName + " : " + "AMS_Email_routing_verification Functionality - failed");
			Assert.fail("AMS_Email_routing_verification Functionality - failed" + e.toString());

		}
}

@Test(priority = 17)
@Parameters({ "URL","ScreenshotPath","AMSFilePath" })
	public static void AMS_Email_routing_verification(String URL, String ScreenshotPath, String AMSFilePath) throws Exception {
		try {

			
			String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			CreateTest(" Printing Ticket Functionality");
			System.out.println("-----------1");
			
			Second_Employer_Eforms(URL,AMSFilePath, ScreenshotPath, Methodname);
			
			

		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();

			log.error(exceptionName + " : " + "AAMS_Email_routing_verification Functionality - failed");
			LogFAIL(exceptionName + " : " + "AMS_Email_routing_verification Functionality - failed");
			Assert.fail("AMS_Email_routing_verification Functionality - failed" + e.toString());

		}
}*/
/*@Test(priority = 18)
@Parameters({ "URL","ScreenshotPath","EformPath" })
	public static void Second_Employer_Eform_Verification(String URL, String ScreenshotPath, String EformPath) throws Exception {
		try {

			
			String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ConfigureReport("Second Employer Eform Verification");
			//e4_Second_Employer_Eform2.Second_Employer_Eform_Verification_and_Submission(URL, ScreenshotPath,EformPath, Methodname);	
			//a5_Eform_Germany.e1_Second_Employer_Eform.Second_Employer_Eform_Verification_and_Submission(URL, ScreenshotPath, EformPath, Methodname);
			String filepath= "src\\main\\resources\\Excel_Data\\DynamicContent.xlsx";
			a4_MyHR_and_Agent_GenericFunctions.testfile.main(Methodname);
			
			
		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();

			log.error(exceptionName + " : " + "Second_Employer_Eform Functionality - failed");
			//LogFAIL(exceptionName + " : " + "Second_Employer_Eform Functionality - failed");
			//Assert.fail("Second_Employer_Eform Functionality - failed" + e.toString());

		}
}

/*
@Test(priority = 17)
@Parameters({ "URL","ScreenshotPath","KeywordFilePath" })
	public static void Keyword_Email_routing_verification(String URL, String ScreenshotPath, String KeywordFilePath) throws Exception {
		try {

			
			String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ConfigureReport("Keyword based Ops Cat Verification");
			
			//Keyword_cased_OpsCat_Population.Verify_Ticket_Population(URL,"Sample",ScreenshotPath, KeywordFilePath, Methodname);
			Keyword_cased_OpsCat_Population.Verify_Ticket_Population2(URL,"Sample",ScreenshotPath, KeywordFilePath, Methodname);
			//VerifyEmail.GmailUtils();

		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();

			log.error(exceptionName + " : " + "AAMS_Email_routing_verification Functionality - failed");
			LogFAIL(exceptionName + " : " + "AMS_Email_routing_verification Functionality - failed");
			Assert.fail("AMS_Email_routing_verification Functionality - failed" + e.toString());

		}
}
/*
@Test(priority = 18)
@Parameters({ "URL","ScreenshotPath","TriggerPath" })
	public static void Trigger_testing(String URL, String ScreenshotPath, String TriggerPath) throws Exception {
		try {

			
			String Methodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			CreateTest("Eform Verification");
			System.out.println("Eform Verification Starts");
		
			Trigger.Find_Trigger(URL, ScreenshotPath, TriggerPath, Methodname);

		} catch (Exception e) {
			String exceptionName = e.getClass().getSimpleName();
			e.printStackTrace();
			log.error(exceptionName + " : " + "Trigger_testing_verification Functionality - failed");
			LogFAIL(exceptionName + " : " + "Trigger_testing_verification Functionality - failed");
			Assert.fail("Trigger_testing_verification Functionality - failed" + e.toString());

		}
}
*/
@AfterTest
public void CLOSE() throws Exception {
	CloseReport();
	//CloseDriver();
	
}
}