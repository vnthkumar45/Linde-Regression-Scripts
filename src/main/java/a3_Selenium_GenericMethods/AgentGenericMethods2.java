package a3_Selenium_GenericMethods;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AgentGenericMethods2 extends PortalGenericMethods2 {

	public static String AgentUserID = "v.dhatchinamoorthy@accenture.com";
	public static String AgentPassword = "welcome";
	public static String Ticket_Assignee;
	public static String Ticket_FormName;
	public static String Ticket_Priority;
	public static String Ticket_HRTicketType;
	public static String Ticket_OpsCat1;
	public static String Ticket_OpsCat2;
	public static String Ticket_OpsCat3;
	public static String Ticket_EscalatedTicket;
	public static String ApprovalStatus;
	public static boolean First_Approval_Triggered_flag ;
	public static boolean Second_Approval_Triggered_flag ;
	public static boolean First_Approval_Completed_flag ;
	public static boolean Second_Approval_Completed_flag ;
	public static boolean is_Approver_Details_correct;
	
	public static String TypeOfRequestfor = "null";
	

	public static void IntiateAgentDriver(String browser, String url) throws Exception {

		if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "src\\main\\resources\\chromedriver.exe");

		} else if (browser.equalsIgnoreCase("Chrome")) {

			System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("user-data-dir=C:\\environments\\selenium");
			options.addArguments("--remote-allow-origins=*");
			// options.addArguments("--incognito");
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			Agentdriver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.edgedriver().setup();
			Agentdriver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--guest");
			Agentdriver = new EdgeDriver(options);

		} else if (browser.equalsIgnoreCase("HB")) {
			Agentdriver = new HtmlUnitDriver();
		}

		Agentdriver.manage().deleteAllCookies();
		Agentdriver.get(url);
		Agentdriver.manage().window().maximize();
		System.out.println("Intiated driver 2");

	}

	/*<--------------------------------Switch to Frame-------------------------------------------->*/
	public static void AgentSwitchToFrame(String Locators) throws InterruptedException, IOException {

		wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10, 1));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Locators));

	}
	
	/*<--------------------------------Switch to Default frame-------------------------------------->*/
	public static void AgentSwitchTodefault() throws InterruptedException, IOException {
		Agentdriver.switchTo().defaultContent();

	}
	/*<--------------------------------Refresh WebPage-------------------------------------->*/
	public static void AgentRefreshPage() throws InterruptedException, IOException {
		Agentdriver.navigate().refresh();

	}
	
	/*<--------------------------------Click on the Element-------------------------------------->*/
	public static void AgentClick(String Locators) throws Exception {

		AgentScrolltoElement(Locators);
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			//System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}
	
	/*<--------------------------------Click on the Element-------------------------------------->*/
	public static void AgentSafeClick(String Locators) throws Exception {

		AgentScrolltoElement(Locators);
		String[] locatorslist = {  "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			safeJavaScriptClick(element);
			//System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}

	/*<--------------------------------Enter the value in the Element-------------------------------------->*/
	public static void AgentEnterValue(String Locators, String Value) throws Exception {

		AgentScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(Value);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}

	/*<--------------------------------Wait for the Element to be visible-------------------------------------->*/
	public static void AgentWaitForElement(String Locators) throws Exception {

		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

	}
	
	/*<--------------------------------Select dropdown value-------------------------------------->*/
	public static void AgentSelectDropdown(String Value) throws InterruptedException, IOException {

		wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(5));

		List<WebElement> elements = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']//li")));
		elements.stream().filter(option -> option.getText().equals(Value)).findFirst().ifPresent(WebElement::click);

	}
	
	/*<--------------------------------Press Enter key-------------------------------------->*/
	public static void AgentKeyEnter() throws AWTException, InterruptedException {

		Actions action = new Actions(Agentdriver);
		action.sendKeys(Keys.ENTER).build().perform();

	}

	/*<--------------------------------Press Escape key-------------------------------------->*/
	public static void AgentKeyEscape() throws AWTException, InterruptedException {

		Actions action = new Actions(Agentdriver);
		action.sendKeys(Keys.ESCAPE).build().perform();

	}

	/*<--------------------------------Wait for the Element to be visible-------------------------------------->*/
	public static boolean AgentIsDisplayed(String Locators) throws Exception {

		boolean Visible=false;
		AgentScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			Visible= element.isDisplayed();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return Visible;
	}
	
	/*<--------------------------------Extract Numeric value from the String-------------------------------------->*/
	public static String AgentextractNumericValue(String text) {
		String numericValue = "";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			numericValue = matcher.group();
		}

		return numericValue;
	}

	/*<--------------------------------Extract String value Before Special character-------------------------------------->*/
	public static String AgentextractStringBeforeSpecialCharacter(String text, char specialChar) {
		int indexOfSpecialChar = text.indexOf(specialChar);

		if (indexOfSpecialChar != -1) {
			return text.substring(0, indexOfSpecialChar);
		}

		return text;
	}

	/*<--------------------------------Get text of Element-------------------------------------->*/
	public static String AgentGetText(String Locators) throws Exception {

		AgentScrolltoElement(Locators);
		String text = null;
		String[] locatorslist = { "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
				"cssSelector=" + Locators + ""  };

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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			text=element.getText();
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return text;
	}
	
	/*<--------------------------------Get text of Element-------------------------------------->*/
	public static String AgentGetTextByAttribute(String Locators,String AttributeType) throws Exception {

		String text = null;
		AgentScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			text=element.getAttribute(AttributeType);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		return text;
	}
	
	/*<--------------------------------Open in New Tab-------------------------------------->*/
	public static void AgentOpenInNewTab(String Locators) throws Exception {

		AgentScrolltoElement(Locators);
		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
			
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	
	/*<--------------------------------Verify the GetTextValue-------------------------------------->*/
	public static boolean AgentVerifyGetText(String ActualText, String ExpectedText, String fieldName) throws Exception {

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
				//Assert.fail("NoSuchElementException" + ActualText);
			}

		}
		return false;
	}
	
	/*<--------------------------------Switch to tab-------------------------------------->*/
	public static void AgentSwitchTab(int n) {
		ArrayList<String> tabs = new ArrayList<String>(Agentdriver.getWindowHandles());
		Agentdriver.switchTo().window(tabs.get(n)); // switches to new tab
	}

	/*<--------------------------------Switch to new window-------------------------------------->*/
	public static void AgentswitchToNewWindow() {
		// String handles=Agentdriver.getWindowHandles();

		Set<String> handles = Agentdriver.getWindowHandles();
		System.out.println(handles.size());
		Iterator<String> itr = handles.iterator();
		firstHandle = itr.next();
		String lastHandle = firstHandle;
		if (itr.hasNext()) {
			lastHandle = itr.next();
		}
		Agentdriver.switchTo().window(firstHandle.toString());
	}

	/*<--------------------------------Retrun to Parent window-------------------------------------->*/
	public static void ReturnToWindow() {
		Agentdriver.getWindowHandles();

		Set<String> handles = Agentdriver.getWindowHandles();
		System.out.println(handles.size());
		Iterator<String> itr = handles.iterator();
		firstHandle = itr.next();
		String lastHandle = firstHandle;
		if (itr.hasNext()) {
			lastHandle = itr.next();
		}
		Agentdriver.switchTo().window(lastHandle.toString());
	}
	
	/*<--------------------------------Scroll to the visible element-------------------------------------->*/
	public static void AgentScrolltoElement(String Locators) throws Exception {

		String[] locatorslist = { "xpath=" + Locators + "", "id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) Agentdriver).executeScript("arguments[0].scrollIntoView();", element);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	/*<--------------------------------Select value from dropdown by tag or value-------------------------------------->*/
	public static void AgentSelect(String Locators,String value) throws Exception {

		waitUntilCountChanges1(Locators);
		String[] locatorslist = {  "xpath=" + Locators + "","id=" + Locators + "", "name=" + Locators + "", "className=" + Locators + "",
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
			WebDriverWait wait = new WebDriverWait(Agentdriver, Duration.ofSeconds(10));
			dropdown=wait.until(ExpectedConditions.visibilityOf(element));
			Select select = new Select(dropdown);
			select.selectByVisibleText(value);
			// System.out.println("Element clicked successfully!");
		} else {
			System.out.println("Element not found with any provided locator.");
		}

		
	}
	
	

	public static void AgentcaptureScreenshot(String MethodName, String screenshotName) throws IOException {

		try {

			Thread.sleep(4000);
			File scrFile = ((TakesScreenshot) Agentdriver).getScreenshotAs(OutputType.FILE);

			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			String CurrentDate = dateFormat.format(date);

			DateFormat dateFormat1 = new SimpleDateFormat("yyyy_MM_dd");
			Date date1 = new Date();
			String CurrentDate1 = dateFormat1.format(date1);
			String username = System.getProperty("user.name");
			File Destination = new File("C:\\Users\\" + username + "\\Videos\\Automation_Screenshots\\" + MethodName
					+ "_" + CurrentDate1 + "\\" + screenshotName + ".png");
			FileUtils.copyFile(scrFile, Destination);
			String errflpath = Destination.getAbsolutePath();
			test1.addScreenCaptureFromPath(errflpath);
			// FileUtils.copyFile(scrFile,Destination);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void AgentFullPageScreenshot(String ScreenshotName) throws Exception {

		try {
			String username = System.getProperty("user.name");
			String screenshot_name = ScreenshotName + ".png";
			// Screenshot screenshot=new
			// AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(Agentdriver);
			BufferedImage image = Shutterbug.shootPage(Agentdriver, Capture.FULL, 1000, true).withName(screenshot_name)
					.getImage();
			;
			// BufferedImage
			// image=Shutterbug.shootPage(Agentdriver).withName(screenshot_name).getImage();
			File file = new File("C:\\Users\\" + username + "\\Videos\\Automation_Screenshots\\" + screenshot_name);
			ImageIO.write(image, "png", file);
			String ScreenshotPath = file.getAbsolutePath();
			// System.out.println(ScreenshotPath);
			test1.log(Status.INFO, "Screenshot" + test1.addScreenCaptureFromPath(ScreenshotPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void AgentGotoEmailLogs() throws Exception {

//	EnterValue("filter_xpath", "sys_email.LIST");
		AgentKeyEnter();
		Thread.sleep(4000);

		// Click(".//*[@id='sys_email_breadcrumb']/a/b");
		Thread.sleep(4000);
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
		Agentdriver.close();

	}

	private static WebElement findElement(String locator) {
		String[] parts = locator.split("=", 2);
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid locator format. Expected format: type=value");
		}

		String type = parts[0];
		String value = parts[1];

		System.out.println(type + "--------------" + value);

		By by = null;
		switch (type) {
		case "xpath":
			by = By.xpath(value);
			break;
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "className":
			by = By.className(value);
			break;
		case "cssSelector":
			by = By.cssSelector(value);
			break;

		default:
			throw new IllegalArgumentException("Unknown locator type: " + type);
		}

		return Agentdriver.findElement(by);
	}

	public static void safeJavaScriptClick(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) Agentdriver).executeScript("arguments[0].click();", element);
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
			WebDriverWait wait2 = new WebDriverWait(Agentdriver, Duration.ofSeconds(10, 1));

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
