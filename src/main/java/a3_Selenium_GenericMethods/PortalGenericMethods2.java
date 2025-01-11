package a3_Selenium_GenericMethods;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


import io.github.bonigarcia.wdm.WebDriverManager;

public class PortalGenericMethods2 {

	public static WebDriver driverA;
	public static WebDriver Agentdriver;
	// public static WebDriver HdriverA;
	public static Properties Config = new Properties();
	public static Properties ObjRep = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(PortalGenericMethods2.class.getName());
	public static WebDriverWait wait;
	public static ExtentTest test1;
	public static String browser;
	public static WebElement element;
	public static FileInputStream fileInputStream = null; // declared variable for FileInputStream as global and hence
															// can be used across classes
	public static String firstHandle = null; // declared variable for Workbook as global and hence can be used across
												// classes
	public static XSSFWorkbook workbook = null;// workbook object is declared.
	public static XSSFSheet sheet = null;
	public static XSSFRow row = null;
	public static XSSFCell cell;
	public static String username;
	public static WebElement dropdown;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent1;
	public static String TESTRAIL_USERNAME = "v.dhatchinamoorthy@accenture.com";
	public static String TESTRAIL_PASSWORD = "Mechatron@123";
	public static String RAILS_ENGINE_URL = "https://sqe.testrail.net";
	public static final int TEST_CASE_PASSED_STATUS = 1;
	public static final int TEST_CASE_FAILED_STATUS = 5;
	public static List<WebDriver> drivers = new ArrayList<>();

	public static String FirstName;
	public static String LastName;
	public static String Employeenumber;
	public static String url1;
	public static String HRName;
	public static String HR_EmailID;
	public static String ManagerName;
	public static String Manager_EmailID;
	public static String StartDate;
	public static String EndDate;
	public static String Ticket_ID;
	public static String TicketSubject;
	public static String ApproverEmailID;
	public static String ApproverName;
	public static boolean ParentTicket_Created = false;

	public static void IntiateDriver(String browser, String url) throws Exception {

		System.out.println("1Browser--------"+browser +url);
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Config.load(fis);
			log.debug("Config file loaded !!!");
			System.out.println("Config file loaded !!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\ObjRep.properties");
		} catch (FileNotFoundException e) {
			// e.printStackTrace();

		}
		try {
			ObjRep.load(fis);
			log.debug("OR file loaded !!!");
			System.out.println("object file loaded !!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "src\\main\\resources\\chromedriver.exe");

		} else if (browser.equalsIgnoreCase("Chrome")) {

			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to Windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--disable-in-process-stack-traces");
			options.addArguments("--disable-logging");
			options.addArguments("--log-level=3");
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--incognito");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--disable-notifications");
			options.addArguments("debuggerAddress", "localhost:9515");
			options.addArguments("user-data-dir=C:\\environments\\selenium");
			driverA = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driverA = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("Edge")) {
			System.out.println("--------4");
			System.out.println("2Browser--------"+browser +url);
			WebDriverManager.edgedriver().clearDriverCache();
			WebDriverManager.edgedriver().setup();
			System.out.println("3Browser--------"+browser +url);
			EdgeOptions options = new EdgeOptions();
			options.setCapability("se:logLevel", "ALL");
			driverA = new EdgeDriver(options);
			System.out.println("--------5");

		}

		driverA.manage().deleteAllCookies();
		driverA.get(url);
		driverA.manage().window().maximize();
		System.out.println("Intiated driver 2");

	}
	
	/*<--------------------------------Switch to Frame-------------------------------------------->*/
	public static void SwitchToFrame(String Locators) throws InterruptedException, IOException {

		wait = new WebDriverWait(driverA, Duration.ofSeconds(10, 1));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Locators));

	}
	
	/*<--------------------------------Switch to Default frame-------------------------------------->*/
	public static void SwitchTodefault() throws InterruptedException, IOException {
		driverA.switchTo().defaultContent();

	}
	/*<--------------------------------Refresh WebPage-------------------------------------->*/
	public static void RefreshPage() throws InterruptedException, IOException {
		driverA.navigate().refresh();

	}
	
	/*<--------------------------------Click on the Element-------------------------------------->*/
	public static void Click(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "",
				"className=" + Locators + "", "cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}
	
	
	/*<--------------------------------Click on the Element-------------------------------------->*/
	public static void SafeClick(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			safeJavaScriptClick(element);
			//System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}

	/*<--------------------------------Enter the value in the Element-------------------------------------->*/
	public static void EnterValue(String Locators, String Value) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(Value);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}

	/*<--------------------------------Clear the value in the Element-------------------------------------->*/
	public static void ClearValue(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}
	
	/*<--------------------------------SafeClear the value in the Element-------------------------------------->*/
	public static void SafeClearValue(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}


	/*<--------------------------------Wait for the Element to be visible-------------------------------------->*/
	public static void WaitForElement(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""};

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}
	
	/*<--------------------------------Select dropdown value-------------------------------------->*/
	public static void SelectDropdown(String Value) throws InterruptedException, IOException {

		wait = new WebDriverWait(driverA, Duration.ofSeconds(5));

		List<WebElement> elements = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']//li")));
		elements.stream().filter(option -> option.getText().equals(Value)).findFirst().ifPresent(WebElement::click);

	}
	
	/*<--------------------------------Press Enter key-------------------------------------->*/
	public static void KeyEnter() throws AWTException, InterruptedException {

		Actions action = new Actions(driverA);
		action.sendKeys(Keys.ENTER).build().perform();

	}

	/*<--------------------------------Press Escape key-------------------------------------->*/
	public static void KeyEscape() throws AWTException, InterruptedException {

		Actions action = new Actions(driverA);
		action.sendKeys(Keys.ESCAPE).build().perform();

	}

	/*<--------------------------------Wait for the Element to be visible-------------------------------------->*/
	public static boolean IsDisplayed(String Locators) throws Exception {

		boolean Visible=false;
		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""};

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			Visible= element.isDisplayed();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return Visible;
	}
	
	/*<--------------------------------Extract Numeric value from the String-------------------------------------->*/
	public static String extractNumericValue(String text) {
		String numericValue = "";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			numericValue = matcher.group();
		}

		return numericValue;
	}

	/*<--------------------------------Extract String value Before Special character-------------------------------------->*/
	public static String extractStringBeforeSpecialCharacter(String text, char specialChar) {
		int indexOfSpecialChar = text.indexOf(specialChar);

		if (indexOfSpecialChar != -1) {
			return text.substring(0, indexOfSpecialChar);
		}

		return text;
	}

	/*<--------------------------------Get text of Element-------------------------------------->*/
	public static String GetText(String Locators) throws Exception {

		String text = null;
		ScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""};

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			text=element.getText();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return text;
	}
	
	/*<--------------------------------Get text of Element-------------------------------------->*/
	public static String GetTextByAttribute(String Locators,String AttributeType) throws Exception {

		String text = null;
		ScrolltoElement(Locators);
		String[] locatorslist = {"xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			text=element.getAttribute(AttributeType);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return text;
	}
	
	/*<--------------------------------Open in New Tab-------------------------------------->*/
	public static void OpenInNewTab(String Locators) throws Exception {

		ScrolltoElement(Locators);
		String[] locatorslist = {"xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""};

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
			
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	
	/*<--------------------------------Verify the GetTextValue-------------------------------------->*/
	public static boolean VerifyGetText(String ActualText, String ExpectedText, String fieldName) throws Exception {

		try {

			if (ActualText.equalsIgnoreCase(ExpectedText)) {

				LogPASS(fieldName + "---- is auto-populated with the value----" + ExpectedText
						+ " - Working as Expected");
				return true;

			} else {

				LogFAIL(fieldName + "---- is not auto-populated with the value ----" + ExpectedText
						+ " - Not Working as Expected. The Actual value populated is ---- " + ActualText);
				return false;

			}

		} catch (org.openqa.selenium.NoSuchElementException e) {
			String exceptionName = e.getClass().getSimpleName();
			if (exceptionName.equals("NoSuchElementException")) {
				Assert.fail("NoSuchElementException" + ActualText);
			}

		}
		return false;
	}
	
	/*<--------------------------------Switch to tab-------------------------------------->*/
	public static void SwitchTab(int n) {
		ArrayList<String> tabs = new ArrayList<String>(driverA.getWindowHandles());
		driverA.switchTo().window(tabs.get(n)); // switches to new tab
	}

	/*<--------------------------------Switch to new window-------------------------------------->*/
	public static void switchToNewWindow() {
		// String handles=driverA.getWindowHandles();

		Set<String> handles = driverA.getWindowHandles();
		System.out.println(handles.size());
		Iterator<String> itr = handles.iterator();
		firstHandle = itr.next();
		String lastHandle = firstHandle;
		if (itr.hasNext()) {
			lastHandle = itr.next();
		}
		driverA.switchTo().window(firstHandle.toString());
	}

	/*<--------------------------------Retrun to Parent window-------------------------------------->*/
	public static void ReturnToWindow() {
		driverA.getWindowHandles();

		Set<String> handles = driverA.getWindowHandles();
		System.out.println(handles.size());
		Iterator<String> itr = handles.iterator();
		firstHandle = itr.next();
		String lastHandle = firstHandle;
		if (itr.hasNext()) {
			lastHandle = itr.next();
		}
		driverA.switchTo().window(lastHandle.toString());
	}
	
	/*<--------------------------------Scroll to the visible element-------------------------------------->*/
	public static void ScrolltoElement(String Locators) throws Exception {

		String[] locatorslist = {"xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""};

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driverA).executeScript("arguments[0].scrollIntoView();", element);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	/*<--------------------------------Select value from dropdown by tag or value-------------------------------------->*/
	public static void Select(String Locators,String value) throws Exception {

		waitUntilCountChanges1(Locators);
		String[] locatorslist = {"xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + "" };

		// Iterate through the locators to find the element
		for (String IndividualLocater : locatorslist) {
			try {
				// System.out.println(IndividualLocater);
				element = findElement(IndividualLocater);
				if (element != null) {
					break;
				}
			} catch (Exception ignored) {
				// If an exception occurs, try the next locator
			}
		}

		if (element != null) {
			// Wait for the element to be clickable and click it
			WebDriverWait wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
			dropdown=wait.until(ExpectedConditions.visibilityOf(element));
			Select select = new Select(dropdown);
			select.selectByVisibleText(value);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	public static void ReadExcel(String Filepath) throws FileNotFoundException, IOException {
		try {
			fileInputStream = new FileInputStream(Filepath);
			workbook = new XSSFWorkbook(fileInputStream);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String Getdata(String colName, int rowNum) {

		String CellValue = "";
		try {
			int col_Num = -1;

			sheet = workbook.getSheetAt(1);
			row = sheet.getRow(0);
			int totalCells = row.getLastCellNum();
			// System.out.println("Total cells "+totalCells );
			for (int i = 0; i < totalCells; i++) {
				System.out.println(row.getCell(i).getStringCellValue());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					// System.out.println("Column number is "+i);
				}
			}

			// System.out.println("Row number is "+rowNum);

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);
			if (cell.getStringCellValue().isEmpty()) {
				System.out.println("Cell value is emplty");

			} else {
				String value = cell.getStringCellValue();
				System.out.println(value);
				CellValue = value;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			// return "column "+colName +" does not exist in xls";
		}

		return CellValue;

	}

	public static String GetNumbericdata(String colName, int rowNum) {

		String CellValue = "";
		try {
			int col_Num = -1;

			sheet = workbook.getSheetAt(1);
			row = sheet.getRow(0);
			int totalCells = row.getLastCellNum();
			// System.out.println("Total cells "+totalCells );
			for (int i = 0; i < totalCells; i++) {
				System.out.println(row.getCell(i).getStringCellValue());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					// System.out.println("Column number is "+i);
				}
			}

			// System.out.println("Row number is "+rowNum);

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);
			cell.setCellType(CellType.STRING);
			if (cell.getStringCellValue().isEmpty()) {
				System.out.println("Cell value is emplty");

			} else {
				String value = cell.getStringCellValue();
				System.out.println(value);
				CellValue = value;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// return "column "+colName +" does not exist in xls";
		}

		return CellValue;

	}

	public static String GetdataFromSheet(String SheetName, String colName, int rowNum) {

		try {
			int col_Num = -1;
			sheet = workbook.getSheet(SheetName);
			row = sheet.getRow(0);
			int totalCells = row.getLastCellNum();
			for (int i = 0; i < totalCells; i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
				// System.out.println("Column number is"+i);
			}

			row = sheet.getRow(rowNum);
			Cell cell = row.getCell(col_Num);
			if (cell.getStringCellValue().isEmpty()) {
				return "";
			}

			else {
				String value = cell.getStringCellValue();
				return value;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			// return "column "+colName +" does not exist in xls";
		}

		return "";

	}

	public static void WriteToExcel2(String Filepath, String ColName, String Rowname, String Result)
			throws FileNotFoundException, IOException {
		try {

			// workbook = new XSSFWorkbook(new FileInputStream(Filepath));//declaring the
			// xssf workbook
			// sheet = workbook.getSheetAt(1);//declaring sheet. First sheet in the input
			// file is guidelines sheet hence pointing to the second one.
			// CellStyle origStyle = workbook.getCellStyleAt(1);
			String Sheetname = sheet.getSheetName();
			System.out.println(Sheetname);
			System.out.println(Rowname);
			int ColNum = -1;
			XSSFRow row1 = sheet.getRow(0);

			for (int j = 0; j < row1.getLastCellNum(); j++) {
				// System.out.println(row1.getLastCellNum());
				if (row1.getCell(j).getStringCellValue().trim().equals(ColName.trim())) {
					// System.out.println(ColName);
					ColNum = j;

				}
			}

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				row1 = sheet.getRow(i);

				if (!Result.equalsIgnoreCase("Failed") || !Result.equalsIgnoreCase("Not Visible")) {

					row1.getCell(0).setCellType(CellType.STRING);

					if (row1.getCell(0).getStringCellValue().equalsIgnoreCase(Rowname)) {

						XSSFCellStyle style1 = workbook.createCellStyle();
						style1.setWrapText(true);
						style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style1.setBorderTop(BorderStyle.MEDIUM);
						style1.setBorderBottom(BorderStyle.MEDIUM);
						style1.setBorderLeft(BorderStyle.MEDIUM);
						style1.setBorderRight(BorderStyle.MEDIUM);
						row1 = sheet.getRow(i);
						// LogPASS(ColNum);
						XSSFCell cell1 = row1.getCell(ColNum);
						if (cell1 == null)
							cell1 = row1.createCell(ColNum);
						cell1.setCellType(CellType.STRING);
						cell1.setCellValue(Result);
						cell1.setCellStyle(style1);
					}
				} else {
					if (row1.getCell(0).getStringCellValue().equalsIgnoreCase(Rowname)) {
						XSSFCellStyle style4 = workbook.createCellStyle();
						/*
						 * style4.setBorderTop(CellStyle.BORDER_MEDIUM);
						 * style4.setBorderBottom(CellStyle.BORDER_MEDIUM);
						 * style4.setBorderLeft(CellStyle.BORDER_MEDIUM);
						 * style4.setBorderRight(CellStyle.BORDER_MEDIUM);
						 */
						style4.setWrapText(true);
						style4.setFillForegroundColor(IndexedColors.RED.getIndex());
						// style4.setFillPattern(CellStyle.SOLID_FOREGROUND);

						row1 = sheet.getRow(i);
						System.out.println(ColNum);
						Cell cell = row1.getCell(ColNum);
						if (cell == null)
							cell = row.createCell(ColNum);
						cell.setCellType(CellType.STRING);
						cell.setCellValue(Result);
						cell.setCellStyle(style4);
					}

				}
				FileOutputStream output_file = new FileOutputStream(new File(Filepath)); // Open FileOutputStream to
																							// write updates
				workbook.write(output_file); // write changes*/
				output_file.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void WriteToExcelBySheetName(String Filepath, String Sheet_name, String Columnname, String Rowname,
			String Result) throws FileNotFoundException, IOException {
		try {
			workbook = new XSSFWorkbook(new FileInputStream(Filepath));// declaring the xssf workbook
			sheet = workbook.getSheet(Sheet_name);// declaring sheet. First sheet in the input file is guidelines sheet
													// hence pointing to the second one.
			// System.out.println(Sheet_name);
			int ColNum = -1;
			XSSFRow row1 = sheet.getRow(0);

			for (int j = 0; j < row1.getLastCellNum(); j++) {

				if (row1.getCell(j).getStringCellValue().trim().equals(Columnname.trim())) {
					ColNum = j;
				}
			}

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				row1 = sheet.getRow(i);
				if (!Result.equalsIgnoreCase("Failed") || (!Result.equalsIgnoreCase("Not Visible"))
						|| (!Result.equalsIgnoreCase("False"))) {
					if (row1.getCell(0).getStringCellValue().equalsIgnoreCase(Rowname)) {

						XSSFCellStyle style1 = workbook.createCellStyle();
						style1.setWrapText(true);
						style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style1.setBorderTop(BorderStyle.MEDIUM);
						style1.setBorderBottom(BorderStyle.MEDIUM);
						style1.setBorderLeft(BorderStyle.MEDIUM);
						style1.setBorderRight(BorderStyle.MEDIUM);
						row1 = sheet.getRow(i);
						// LogPASS(ColNum);
						XSSFCell cell1 = row1.getCell(ColNum);
						if (cell1 == null)
							cell1 = row1.createCell(ColNum);
						cell1.setCellType(CellType.STRING);
						cell1.setCellValue(Result);
						cell1.setCellStyle(style1);
					}
				} else {
					if (row1.getCell(0).getStringCellValue().equalsIgnoreCase(Rowname)) {
						XSSFCellStyle style2 = workbook.createCellStyle();
						/*
						 * style2.setBorderTop(CellStyle.BORDER_MEDIUM);
						 * style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
						 * style2.setBorderLeft(CellStyle.BORDER_MEDIUM);
						 * style2.setBorderRight(CellStyle.BORDER_MEDIUM);
						 */
						style2.setWrapText(true);
						style2.setFillForegroundColor(IndexedColors.RED.getIndex());
						// style2.setFillPattern(CellStyle.SOLID_FOREGROUND);

						row1 = sheet.getRow(i);
						// System.out.println(ColNum);
						Cell cell = row1.getCell(ColNum);
						if (cell == null)
							cell = row.createCell(ColNum);
						cell.setCellType(CellType.STRING);
						cell.setCellValue(Result);
						cell.setCellStyle(style2);
					}

				}
				FileOutputStream output_file = new FileOutputStream(new File(Filepath)); // Open FileOutputStream to
																							// write updates
				workbook.write(output_file); // write changes*/
				output_file.close();

			}

		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	/*
	 * public static String GetName(String xpath) throws Exception {
	 * 
	 * 
	 * driverA.findElement(By.xpath(xpath)).click(); Thread.sleep(3000);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "html/body/div[2]/div[4]/div[2]/h4")));
	 * 
	 * String
	 * GetEmail=GetTextByAttribute(".//*[@id='sys_readonly.sys_user.email']");
	 * System.out.println(GetEmail); return GetEmail; }
	 */

	public static void captureScreenshot(String MethodName, String screenshotName) throws IOException {

		try {

			Thread.sleep(4000);
			File scrFile = ((TakesScreenshot) driverA).getScreenshotAs(OutputType.FILE);

			//DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			//String CurrentDate = dateFormat.format(date);

			//DateFormat dateFormat1 = new SimpleDateFormat("yyyy_MM_dd");
			//Date date1 = new Date();
			//String CurrentDate1 = dateFormat1.format(date1);
			String username = System.getProperty("user.name");
			//File Destination = new File("C:\\Users\\" + username + "\\Videos\\Automation_Screenshots\\" + MethodName
			//		+ "_" + CurrentDate1 + "\\" + screenshotName + ".png");
			//FileUtils.copyFile(scrFile, Destination);
			// errflpath = Destination.getAbsolutePath();
			//test1.addScreenCaptureFromPath(errflpath);
			//test1.log(Status.INFO, screenshotName +" - Screenshot taken: " + test1.addScreenCaptureFromPath(errflpath));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void captureScreenshot2(String MethodName, String screenshotName) throws IOException {

		try {

			Thread.sleep(4000);
			File scrFile = ((TakesScreenshot) driverA).getScreenshotAs(OutputType.FILE);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			//String CurrentDate = dateFormat.format(date);

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy_MM_dd");
			Date date1 = new Date();
			String CurrentDate1 = dateFormat1.format(date1);
			String username = System.getProperty("user.name");
			File Destination = new File("C:\\Users\\" + username + "\\Videos\\Automation_Screenshots\\" + MethodName
					+ "_" + CurrentDate1 + "\\" + screenshotName + ".png");
			FileUtils.copyFile(scrFile, Destination);
			String errflpath = Destination.getAbsolutePath();
			test1.addScreenCaptureFromPath(errflpath);
		test1.log(Status.INFO, screenshotName +" - Screenshot taken: " + test1.addScreenCaptureFromPath(errflpath));
		
			// FileUtils.copyFile(scrFile,Destination);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void FullPageScreenshot(String ScreenshotName) throws Exception {

		try {
			String username = System.getProperty("user.name");
			String screenshot_name = ScreenshotName + ".png";
			// Screenshot screenshot=new
			// AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driverA);
			BufferedImage image = Shutterbug.shootPage(driverA, Capture.FULL, 1000, true).withName(screenshot_name)
					.getImage();
			;
			// BufferedImage
			// image=Shutterbug.shootPage(driverA).withName(screenshot_name).getImage();
			File file = new File("C:\\Users\\" + username + "\\Videos\\Automation_Screenshots\\" + screenshot_name);
			ImageIO.write(image, "png", file);
			String ScreenshotPath = file.getAbsolutePath();
			// System.out.println(ScreenshotPath);
			test1.log(Status.INFO, "Screenshot" + test1.addScreenCaptureFromPath(ScreenshotPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void GotoEmailLogs() throws Exception {

//	EnterValue("filter_xpath", "sys_email.LIST");
		KeyEnter();
		Thread.sleep(4000);

		// Click(".//*[@id='sys_email_breadcrumb']/a/b");
		Thread.sleep(4000);
	}

	public static void ConfigureReport(String Reportname) throws Exception {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			//System.out.println(dateFormat.format(date));
			String CurrentDate = dateFormat.format(date);

			String ReportName1 = Reportname + "_" + CurrentDate;
			String username = System.getProperty("user.name");

			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/" + ReportName1 + ".html");
			htmlReporter.config().setDocumentTitle("Avanade ServiceNow");
			htmlReporter.config().setReportName(Reportname);
			htmlReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
			
			
			extent1 = new ExtentReports();
			extent1.attachReporter(htmlReporter);
			extent1.setSystemInfo("Host Name", "AutomationRegression");
			extent1.setSystemInfo("User Name", username);

			// Filepath="C:\\Users\\"+username+"\\Videos\\Automation_Screenshots\\Recorded_Execuion";
			// ScreenRecorderUtil.startRecord(Filepath,Reportname);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void RecordVideo(String MethodName) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		//String CurrentDate = dateFormat.format(date);

		// String ReportName1 =Reportname+"_"+ CurrentDate;
		String username = System.getProperty("user.name");
		try {

			// recorder= new
			// ATUTestRecorder("C:\\Users\\"+username+"\\Videos\\Automation_Screenshots\\",MethodName,false);
			// recorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void CreateTest(String Info) throws IOException {

		test1 = extent1.createTest(Info);

	}

	public static void LogSTATUS(String Info) throws IOException {

		test1.log(Status.INFO, Info);
		log.info(Info);
		Reporter.log(Info);

	}

	public static void LogPASS(String Info) throws IOException {

		test1.log(Status.PASS, MarkupHelper.createLabel(Info, ExtentColor.GREEN));
		System.out.println(Info);
		log.info(Info);
		Reporter.log(Info);

	}

	public static void LogFAIL(String Info) throws IOException {

		test1.log(Status.FAIL, MarkupHelper.createLabel(Info, ExtentColor.RED));
		System.err.println(Info);
		log.info(Info);
		Reporter.log(Info);

	}

	public static void CloseReport() throws Exception {

		/*
		 * ScreenRecorderUtil.stopRecord();
		 * 
		 * Thread.sleep(10000); System.out.println(); String username =
		 * System.getProperty("user.name"); SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd"); String
		 * Filepath="C:\\Users\\"+username+"\\Videos\\Automation_Screenshots\\
		 * Recorded_Execuion\\"+VideoName+"-"+dateFormat.format(new Date())+".avi";
		 * System.out.println(Filepath); File VideoFile=new File(Filepath); String
		 * Absolutepath=VideoFile.getAbsolutePath(); System.out.println(Absolutepath);
		 * test.addScreencastFromPath(Absolutepath);
		 */
		extent1.flush();

	}

	public static void addResultForTestCase(String testCaseId, int status) throws IOException, APIException {

		try {
			// String testRunId = ObjRep.getProperty("TestRunID");
			String testRunId = "1023";
			APIClient client = new APIClient(RAILS_ENGINE_URL);
			client.setUser(TESTRAIL_USERNAME);
			client.setPassword(TESTRAIL_PASSWORD);

			Map data = new HashMap();
			data.put("status_id", status);
			data.put("comment", "Test Executed - Status updated automatically from Selenium test automation.");

			JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + testRunId + "/" + testCaseId + "",
					data);

			System.out.println("Result added in testtrail");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeExistingDrivers(ChromeDriverService service) {
		if (service.isRunning()) {
			service.stop();
			System.out.println("Existing WebDriver instances closed.");
		} else {
			System.out.println("No existing WebDriver instances found.");
		}
	}

	public static void CloseDriver() throws InterruptedException {
		driverA.close();

	}

	private static WebElement findElement(String locator) {
		String[] parts = locator.split("=", 2);
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid locator format. Expected format: type=value");
		}

		String type = parts[0];
		String value = parts[1];

		

		By by = null;
		switch (type) {
		case "xpath":
			System.out.println(type + "--------------" + value);
			by = By.xpath(value);
			break;
		case "id":
			System.out.println(type + "--------------" + value);
			by = By.id(value);
			break;
		case "name":
			System.out.println(type + "--------------" + value);
			by = By.name(value);
			break;
		case "className":
			System.out.println(type + "--------------" + value);
			by = By.className(value);
			break;
		case "cssSelector":
			System.out.println(type + "--------------" + value);
			by = By.cssSelector(value);
			break;

		default:
			throw new IllegalArgumentException("Unknown locator type: " + type);
		}

		return driverA.findElement(by);
	}

	public static void safeJavaScriptClick(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) driverA).executeScript("arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to click on element " + e.getStackTrace());
		}
	}

	public static void waitUntilCountChanges1(String Locator) {

		try {
			WebDriverWait wait2 = new WebDriverWait(driverA, Duration.ofSeconds(10, 1));

			if (Locator.endsWith("xpath")) {
				wait2.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						Select dropdownlist1 = new Select(wait2.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjRep.getProperty(Locator)))));
						List<WebElement> dropdown2 = dropdownlist1.getOptions();
						int elementCount = dropdown2.size();
						if (elementCount > 1) {
							return true;
						} else {
							return false;
						}
					}
				});
			} else if (Locator.endsWith("id")) {
				System.out.println("into the selection");
				wait2.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						Select dropdownlist1 = new Select(wait2.until(
								ExpectedConditions.visibilityOfElementLocated(By.id(ObjRep.getProperty(Locator)))));
						List<WebElement> dropdown2 = dropdownlist1.getOptions();
						int elementCount = dropdown2.size();
						if (elementCount > 1) {
							return true;
						}

						else {
							return false;
						}
					}
				});
			} else if (Locator.endsWith("name")) {

				wait2.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						Select dropdownlist1 = new Select(wait2.until(
								ExpectedConditions.visibilityOfElementLocated(By.name(ObjRep.getProperty(Locator)))));
						// int elementCount = driver.findElement(By.xpath("xxxx")).size();
						List<WebElement> dropdown2 = dropdownlist1.getOptions();
						int elementCount = dropdown2.size();
						if (elementCount > 1)
							return true;
						else
							return false;
					}
				});
			}

		} catch (org.openqa.selenium.TimeoutException e) {
			// TODO: handle exception
		}
	}
}
