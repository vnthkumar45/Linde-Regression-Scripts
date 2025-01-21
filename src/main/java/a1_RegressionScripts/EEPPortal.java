package a1_RegressionScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import a3_Selenium_GenericMethods.PortalGenericMethods2;
import screen.ScreenShotter;
import testrail.APIException;
import testrail.Testrail;



	public class EEPPortal extends PortalGenericMethods2 {
		public static File scrFile;
		public static String screenshotFilepath = null;
		public static String firstHandle = null;
		public static Logger log = Logger.getLogger("ZendeskLogger");
		public static FileInputStream fileInputStream1;
		public static XSSFWorkbook workbook1;
	
		public String RandomShrtDesc; //added by Praveena
		public String RandomBodyDesc; //added by Praveena
		public String Request_Id;     //added by Hemant
		public String Request_Number; //added by Hemant
		public static String sheetName = null;
		public static int errorcount = 0;
		static ScreenShotter shot;	
		static String CaseID;
		static String Run = "R2076";
		static String RunId = Run.substring(1, 5);
		String getAttribute;
		String text;
		public static String GetFieldvalue;
		public static WebElement element1;
		
		
		public void eepportal(String InputfilePath, String sheet_name, String ScreenshotPath,String MethodName) throws APIException, Exception {
		
			LogSTATUS("1.Running function is--------------" + sheet_name);
			//this.test = test;
			//this.driverA = driverA1;
			sheetName = sheet_name;
			shot = new ScreenShotter(ScreenshotPath);
			//LogSTATUS("Running function is " + sheet_name);
			screenshotFilepath = ScreenshotPath;
			     
			fileInputStream1 = new FileInputStream(InputfilePath);
			workbook1 = new XSSFWorkbook(fileInputStream1);
			final DataFormatter dataformat = new DataFormatter();

			XSSFSheet sheet2 = workbook1.getSheet(sheet_name);
			int row_number = 0;
			Iterator<Row> rows2 = sheet2.rowIterator();
			ArrayList<String> data = new ArrayList();

			while (rows2.hasNext()) {

				XSSFRow row = (XSSFRow) rows2.next();
				String names[] = null;
				Iterator cells = row.cellIterator(); // Each cell in the current row
				// will be iterated through
				// CellIterator().
				while (cells.hasNext()) {

					XSSFCell cell = (XSSFCell) cells.next();
					data.add(dataformat.formatCellValue(cell));
				}
				names = data.toArray(new String[data.size()]); // Array list
				// converted to
				// String array

				performAction(names, ++row_number,MethodName); // performAction function is
				// called & String array is
				// passed as parameter
				data.clear();

			}
		}

		private void performAction(String[] fetchedData, int row_number,String MethodName) throws APIException, Exception {

			Actions actions = new Actions(driverA);
			
			try {
				if (fetchedData[0].equalsIgnoreCase("Header row")) 
					 { 
					// First Row in the Sheet and rows with only data in first column will be ignored, no action to be performed
					LogSTATUS("________________________________________________________________________________________________");
					LogSTATUS(row_number +":"+ fetchedData[0]); 
					LogSTATUS("________________________________________________________________________________________________");
					
				}
				else if (fetchedData[0].equalsIgnoreCase("TestRail") && fetchedData[1].equalsIgnoreCase("GetCaseId")) 
				{

					CaseID = fetchedData[2].substring(1, 7);
					System.out.println(CaseID);
					System.out.println(RunId);
				}
				
				else if (fetchedData[0].equalsIgnoreCase("TestRail") && fetchedData[1].equalsIgnoreCase("GetCaseIdLegnthy")) 
				{

					CaseID = fetchedData[2].substring(1, 8);
					System.out.println(CaseID);
					System.out.println(RunId);
				}
				
				else if (fetchedData[0].equalsIgnoreCase("TestRail") && fetchedData[1].equalsIgnoreCase("UpdateResult")) 
				{
					Testrail.addResultForTestCase(RunId, CaseID, 1);
				}
				
				else if (fetchedData[2].equalsIgnoreCase("URL") && fetchedData[3].equalsIgnoreCase("Get")) 
				{
						//To Launch URL
						driverA.get(fetchedData[4]); 
						LogSTATUS(row_number + ":" + fetchedData[5]);
				}
				
				else if (fetchedData[3].equalsIgnoreCase("sleep")) 
				{
						//Wait for Loading
						String sleep_time = fetchedData[4];
						Thread.sleep((Integer.parseInt(sleep_time))); 
						
				}
				else if (fetchedData[3].equalsIgnoreCase("switch")) 
				{
						//To Switch Frame
						driverA.switchTo().frame(driverA.findElement(By.tagName("iframe")));
						LogSTATUS(row_number + ":" + fetchedData[5]);
				}
				
				else if (fetchedData[0].equalsIgnoreCase("Screenshot") && fetchedData[3].equalsIgnoreCase("getScreenshotAs")) 
				{
						//Takes Screenshot
						waitForLoad(driverA);
						captureScreenshot(MethodName, fetchedData[4]);
						//shot.getScreenShot(driverA, "Scenario_" + fetchedData[4]);					
				}
				
//<--------------------------Click functionality----------------------------------------------------------------------------------->
				
				else if (fetchedData[3].equalsIgnoreCase("click")) {
					
					ScrolltoElement(fetchedData[1]);
					Click(fetchedData[1]);
					
				}	
				
				else if (fetchedData[3].equalsIgnoreCase("SafeClick")) {
					
					ScrolltoElement(fetchedData[1]);
					SafeClick(fetchedData[1]);
				}
				
//<--------------------------SendKeys functionality----------------------------------------------------------------------------------->
				
				
				else if (fetchedData[3].equalsIgnoreCase("sendkeys")) {

					ScrolltoElement(fetchedData[1]);
					EnterValue(fetchedData[1], fetchedData[4]);
				
				}  
				
//<--------------------------Press Enter functionality----------------------------------------------------------------------------------->
				
			    else if (fetchedData[3].equalsIgnoreCase("pressEnter")) {
			    	
			    	KeyEnter();
			    	
				}

//<--------------------------Clear functionality----------------------------------------------------------------------------------->			
				else if (fetchedData[3].equalsIgnoreCase("Clear")) {
					
					ClearValue(fetchedData[1]);
												
				}
				
				else if (fetchedData[3].equalsIgnoreCase("Safeclear")) {
				
					SafeClearValue(fetchedData[1]);
			}

		
//<--------------------------Select dropdown value functionality----------------------------------------------------------------------------------->			
				 
		
				
				
//<--------------------------Get Attribute functionality----------------------------------------------------------------------------------->
				
					else if(fetchedData[3].equalsIgnoreCase("getText"))
					 {
							GetText(fetchedData[1]);
					 }
					
					else if(fetchedData[3].equalsIgnoreCase("getAttribute"))
					 {
						 ScrolltoElement(fetchedData[1]);
						 GetTextByAttribute(fetchedData[1], fetchedData[2]);
					 }
				
				
				
//<--------------------------Get text and verify----------------------------------------------------------------------------------->	
				
				 else if(fetchedData[3].equalsIgnoreCase("Check_field_visible"))
				 {
							
					 
				 }
				
//<--------------------------and verify----------------------------------------------------------------------------------->	
				
				 
				 else if(fetchedData[0].equalsIgnoreCase("Change Window"))
				 {
						
						    String mainWindowHandle = driverA.getWindowHandle();
						    Set<String> allWindowHandles = driverA.getWindowHandles();
						    Iterator<String> iterator = allWindowHandles.iterator();
					        while (iterator.hasNext()) 
					        {
					        		String ChildWindow = iterator.next();
					        	    if (!mainWindowHandle.equalsIgnoreCase(ChildWindow))
					                {
					        	    	driverA.switchTo().window(ChildWindow);
					        	    	driverA.close();
					        	    	driverA.switchTo().window(mainWindowHandle);
					                }
					         }
					    
					    LogSTATUS(fetchedData[5]);
				}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("switch")) {
						driverA.switchTo().frame(0); // For switching onto any
						// frame in the page
						LogSTATUS(row_number + ":" + fetchedData[5]);
					}
				 else if ((fetchedData[1].equalsIgnoreCase("defaultContent")
							&& fetchedData[3].equalsIgnoreCase("switch"))) {
						driverA.switchTo().defaultContent(); // For switching onto any
						// frame in the page
						LogSTATUS(row_number + ":" + fetchedData[5]);
					}
					//added by Praveena
					else if ( fetchedData[3].equalsIgnoreCase("windowhandles")) {
						ArrayList<String> tabs2 = new ArrayList<String> (driverA.getWindowHandles());
					    driverA.switchTo().window(tabs2.get(1));
					  
					}
								
			}
		
			catch (Exception e) {
				log.error("Following action not performed");
				// log.error(e.printStackTrace());
				
				String exceptionName = e.getClass().getSimpleName();
				System.out.println("Exception Occured at line :" + row_number + " " + exceptionName);
				if (exceptionName.equals("NoSuchElementException")) {
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					log.error("Error occured at line: " + row_number + sheetName + "And Below action not performed:   "
							+ fetchedData[5]);
					log.error("NoSuchElementException: " + row_number);
					LogFAIL( "NoSuchElementException" + " : " + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					
				}

				else if (exceptionName.equals("UnhandledAlertException")) {
					log.error("Alert occured at line:" + row_number);
					Alert alert = driverA.switchTo().alert(); // for Accepting the alert pop
					// up
					alert.accept();
					log.error("Error occured at line: " + row_number);
					log.error("UnhandledAlertException: " + row_number);
					log.error("UnhandledAlertException row------" + fetchedData[0]);
					log.error("Following action not performed: " + fetchedData[5]);
					log.error(e.toString());
					LogSTATUS( "UnhandledAlertException" + " : " + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					// Keywords.getScreenshot(selectedRow1.getCell(4).getStringCellValue());
				} else if (exceptionName.equals("ArrayIndexOutOfBoundsException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ArrayIndexOutOfBoundsException" + row_number);
					log.error("Error occured at line : " + row_number + "---" + sheetName
							+ "And Below action not performed: " + fetchedData[5]);
					log.error("ArrayIndexOutOfBoundsException row------" + fetchedData[0] + "--" + fetchedData[1] + "--"
							+ fetchedData[2] + "--" + fetchedData[3] + "--" + fetchedData[4] + "--" + fetchedData[5]);
					LogFAIL(  "ArrayIndexOutOfBoundsException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					
				} else if (exceptionName.equals("org.openqa.selenium.TimeoutException")) {
					log.error("Following action not performed: " + fetchedData[5]);
					log.error("Error occured at line:" + row_number);
					log.error("org.openqa.selenium.TimeoutException" + row_number);
					log.error("Please increase the waittime to overcome this error at line :" + row_number + "---"
							+ sheetName);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName.equals("TimeoutException")) {
					log.error("Following action not performed: " + fetchedData[5]);
					log.error("Error occured at line:" + row_number);
					log.error("TimeoutException" + row_number);
					log.error("Please increase the waittime to overcome this error at line :" + row_number + "---"
							+ sheetName);
					 Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName.equals("ElementNotVisibleException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ElementNotVisibleException" + row_number);
					log.error("Element not visible: " + fetchedData[1] + "\"Following action not performed: "
							+ fetchedData[5]);
					LogFAIL("ElementNotVisibleException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					

				} else if (exceptionName.equals("ElementNotFoundException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ElementNotFoundException" + row_number);
					log.error("ElementNotFoundException " + fetchedData[1] + "\"Following action not performed: "
							+ fetchedData[5]);
					LogFAIL( "ElementNotFoundException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					
				
				} else if (exceptionName.equals("NoAlertPresentException")) {
					log.error("Error occured at line:" + row_number);
					log.error("NoAlertPresentException" + row_number);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName == exceptionName) {
					log.error("Error occured at line:" + row_number);
					log.error(exceptionName + row_number);
					Testrail.addResultForTestCase(RunId, CaseID, 5);

				}

			}

		}
		public static void safeJavaScriptClick(WebElement element) throws Exception {
			try {
				if (element.isEnabled() && element.isDisplayed()) {
					// log.trace("Clicking on element with using java script click");

					((JavascriptExecutor) driverA).executeScript("arguments[0].click();", element);
				} else {
					log.trace("Unable to click on element---->" + element);
				}
			} catch (StaleElementReferenceException e) {
				log.trace("Element is not attached to the page document ---->" + element + "---" + e.getStackTrace());
			} catch (NoSuchElementException e) {
				log.trace("Element was not found in DOM ---->" + element + "---" + e.getStackTrace());
			} catch (Exception e) {
				log.trace("Unable to click on element ---->" + element + "----" + e.getStackTrace());
			}

		}

		public static void waitForLoad(WebDriver driver) {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));;
			wait.until(pageLoadCondition);
		}

		public void jsExecutorClick(WebElement element) {

			JavascriptExecutor executor = (JavascriptExecutor) driverA;
			executor.executeScript("arguments[0].click();", element);
		}	
		public int elementVisibilty(String path, String element) throws IOException 
		{
			wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
			By pathElement;
			int i=0;
			if (path.equalsIgnoreCase("id")) 
			{
				pathElement = By.id(element);
				try 
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(pathElement));
					i=1;
				} 
				catch (Exception e) 
				{
					LogSTATUS("id element is missing:" + pathElement);
				}
				
			} 
			else if (path.equalsIgnoreCase("xpath")) 
			{
				pathElement = By.xpath(element);
				try
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(pathElement));
					i=1;
				} 
				catch (Exception e) 
				{
					LogSTATUS("xpath element is missing:" + pathElement);
				}
			}
			return i;
			
		}
		public int elementClickable(String path, String element) throws IOException 
		{
			wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
			int i=0;
			By pathElement;
			if (path.equalsIgnoreCase("id")) {
				pathElement = By.id(element);
				try {
					wait.until(ExpectedConditions.elementToBeClickable(pathElement));
					i=1;
				} catch (Exception e) {
					LogSTATUS("id element is missing:" + pathElement);
				}
			} else if (path.equalsIgnoreCase("xpath")) {
				pathElement = By.xpath(element);
				try {
					wait.until(ExpectedConditions.elementToBeClickable(pathElement));
					i=1;
				} catch (Exception e) {
					LogSTATUS("xpath element is missing:" + pathElement);
				}
			}
			return i;
		}
		public void categoryNotEmpty(String element, String element1) throws Exception {
			WebElement textnotnull = driverA.findElement(By.xpath(element));
			String value = textnotnull.getText();
			if (value != null) {
				driverA.findElement(By.xpath(element1)).click();
			}
		}
		public void elementInVisibilty(String path, String element, String msg) throws IOException {
			wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
			By pathElement;
			if (path.equalsIgnoreCase("id")) {
				pathElement = By.id(element);
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(pathElement));
					// LogSTATUS("Testcase passed - Error message not displayed");
				} catch (Exception e) {
					// LogSTATUS("id element is missing:" + msg);
					
				}
			} else if (path.equalsIgnoreCase("xpath")) {
				pathElement = By.xpath(element);
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(pathElement));
					// LogSTATUS("Testcase passed - Error message not displayed");
				} catch (Exception e) {
					LogSTATUS("xpath element is missing:" + msg);
				}
			}
		}
		

	}
	

