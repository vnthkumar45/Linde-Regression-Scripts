package ZendeskAutomationMain;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Reporting.ZendeskExtentReport;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import EEPPortal.EEPPortal;

public class EEPPortal_InitialTest extends ZendeskExtentReport{
	public static WebDriver driver;
	public static Logger log = Logger.getLogger("EEPPortal_InitialTest.class");
	public static EEPPortal eep = new EEPPortal();



@BeforeTest
@Parameters({ "Browser" })
public static void driverInitiation(String browser) throws IOException {

	extent = ZendeskExtentReport.getInstance();

	if (browser.equalsIgnoreCase("IE")) {
		System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
	} else if (browser.equalsIgnoreCase("Chrome")) {
		
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications"); 
		driver=new ChromeDriver(options);

	}

	driver.manage().deleteAllCookies();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	log.info("Browser loaded successfully");
	
}

//Onboarding Portal Test Cases
/*
@Test(priority = 1)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void loginAppsNE(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Verify Home Page Functionalities");
		eep.eepportal(driver, InputFilePath, "Home Page Validation - I", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Functionalities - Failed");
		test.log(Status.FAIL, exceptionName + " : " + "Home Page Functionalities - Failed");
		Assert.fail("Home Page Functionalities - Failed" + e.toString());

	}
}

@Test(priority = 2)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void leftnav(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Verify Home Page Functionalities-II");
		eep.eepportal(driver, InputFilePath, "Home Page Validation - ||", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Functionalities-II - Failed");
		test.log(Status.FAIL, exceptionName + " : " + "Home Page Functionalities-II - Failed");
		Assert.fail("Home Page Functionalities-II - Failed" + e.toString());

	}
}
@Test(priority = 3)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void myActions(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Verify Required Activities - I");
		eep.eepportal(driver, InputFilePath, "Required Activities - I", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Verification of Required Activities - I : failed");
		test.log(Status.FAIL, exceptionName + " : " + "Verification of Required Activities - I : failed");
		Assert.fail("Verification of Required Activities - I : failed" + e.toString());

	}
}
@Test(priority = 4)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void myRequest(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Verify Required Activities - II");
		eep.eepportal(driver, InputFilePath, "Required Activities - II", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Verification of Required Activities - II : failed");
		test.log(Status.FAIL, exceptionName + " : " + "Verification of Required Activities - II : failed");
		Assert.fail("Verification of Required Activities - II : failed" + e.toString());

	}
}
@Test(priority = 5)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void askAQuestion (String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Verify Additional Activities");
		eep.eepportal(driver, InputFilePath, "Additional Activities", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Verification of Additional Activities - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Verification of Additional Activities - failed");
		Assert.fail("Verification of Additional Activities - failed" + e.toString());

	}
}
@Test(priority = 6)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void myAgentUser(String ScreenshotPath, String InputFilePath) throws Exception 
{
	try {

		test = extent.createTest("Verify Ask a Question Functionality");
		eep.eepportal(driver, InputFilePath, "Ask a Question", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Verification of Ask a Question Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Verification of Ask a Question Functionality - failed");
		Assert.fail("Verification of Ask a Question Functionality - failed" + e.toString());

	}
}
@Test(priority = 7)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void myAgentTicket(String ScreenshotPath, String InputFilePath) throws Exception 
{
	try {

		test = extent.createTest("Verify My Request Functionality");
		eep.eepportal(driver, InputFilePath, "My Requests", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Verification of My Request Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Verification of My Request Functionality - failed");
		Assert.fail("Verification of My Request Functionality - failed" + e.toString());

	}
}
*/
//EEP Portal Test Cases

@Test(priority = 1)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Verify_Home_Page(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Checking the functionality on Home Page");
		eep.eepportal(driver, InputFilePath, "Home Page Validation - I", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Validation - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Home Page Validation - failed");
		Assert.fail("Home Page Validation - failed" + e.toString());

	}
}

/*@Test(priority = 2)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Verify_Home_PageII(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Checking the functioanality on Home Page(continue)");
		eep.eepportal(driver, InputFilePath, "Home Page Validation - II", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Home Page Validation(II) - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Home Page Validation(II) - failed");
		Assert.fail("Home Page Validation(II) - failed" + e.toString());

	}
}
@Test(priority = 3)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Topics(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Checking the Topics functionality");
		eep.eepportal(driver, InputFilePath, "Topics", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Topics functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Topics functionality - failed");
		Assert.fail("Topics functionality - failed" + e.toString());

	}
}

@Test(priority = 4)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Ask_a_Question(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Ask a Question Functionality");
		eep.eepportal(driver, InputFilePath, "Ask a Question", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Ask a Question Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Ask a Question Functionality - failed");
		Assert.fail("Ask a Question Functionality - failed" + e.toString());

	}
}

@Test(priority = 5)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Moments(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Moments Functionality");
		eep.eepportal(driver, InputFilePath, "Moments", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Moments Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Moments - failed");
		Assert.fail("Moments - failed" + e.toString());

	}
}

@Test(priority = 6)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Action_Center(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Action Center Functionality");
		eep.eepportal(driver, InputFilePath, "Action Center(HR & Manager)", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Action Center Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Action Center Functionality - failed");
		Assert.fail("Action Center Functionality - failed" + e.toString());

	}
}

@Test(priority = 7)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void My_Requests(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("My Requests Functionality");
		eep.eepportal(driver, InputFilePath, "My Requests", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "My Requests Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "My Requests Functionality - failed");
		Assert.fail("My Requests Functionality - failed" + e.toString());

	}
}

@Test(priority = 8)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void My_Documents(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("My Documents Functionality");
		eep.eepportal(driver, InputFilePath, "My Documents", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "My Documents Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "My Documents Functionality - failed");
		Assert.fail("My Documents Functionality - failed" + e.toString());

	}
}

@Test(priority = 9)
@Parameters({ "ScreenshotPath", "InputFilePath" })
public static void Apps(String ScreenshotPath, String InputFilePath) throws Exception {
	try {

		test = extent.createTest("Apps Functionality");
		eep.eepportal(driver, InputFilePath, "Apps", ScreenshotPath, test);

	} catch (Exception e) {
		String exceptionName = e.getClass().getSimpleName();

		log.error(exceptionName + " : " + "Apps Functionality - failed");
		test.log(Status.FAIL, exceptionName + " : " + "Apps Functionality - failed");
		Assert.fail("Apps Functionality - failed" + e.toString());

	}
}
/*
@AfterTest
public void CLOSE() {
	driver.close();
	driver.quit();
	extent.flush();
}*/
}