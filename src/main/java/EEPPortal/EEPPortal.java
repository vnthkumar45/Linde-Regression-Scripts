package EEPPortal;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.apache.bcel.generic.Select;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
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
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Reporting.ZendeskExtentReport;
import jdk.internal.org.jline.utils.Log;
import screen.ScreenShotter;
import testrail.APIException;
import testrail.Testrail;



	public class EEPPortal extends ZendeskExtentReport {
		public static File scrFile;
		public static String screenshotFilepath = null;
		public static String firstHandle = null;
		public static Logger log = Logger.getLogger("ZendeskLogger");
		public static FileInputStream fileInputStream;
		public static XSSFWorkbook workbook;
		public static WebDriver driver;
		public String RandomShrtDesc; //added by Praveena
		public String RandomBodyDesc; //added by Praveena
		public String Request_Id;     //added by Hemant
		public String Request_Number; //added by Hemant
		public static String sheetName = null;
		WebDriverWait wait;
		public static int errorcount = 0;
		static ScreenShotter shot;	
		static String CaseID;
		static String Run = "R2076";
		static String RunId = Run.substring(1, 5);
		String getAttribute;
		String text;
		
		
		public void eepportal(WebDriver driver1, String InputfilePath, String sheet_name, String ScreenshotPath,
				ExtentTest test) throws APIException, Exception {
		
			log.info("1.Running function is--------------" + sheet_name);
			this.test = test;
			this.driver = driver1;
			sheetName = sheet_name;
			shot = new ScreenShotter(ScreenshotPath);
			//log.info("Running function is " + sheet_name);
			screenshotFilepath = ScreenshotPath;
			fileInputStream = new FileInputStream(InputfilePath);
			workbook = new XSSFWorkbook(fileInputStream);
			final DataFormatter dataformat = new DataFormatter();

			XSSFSheet sheet = workbook.getSheet(sheet_name);
			int row_number = 0;
			Iterator rows = sheet.rowIterator();
			ArrayList<String> data = new ArrayList();

			while (rows.hasNext()) {

				XSSFRow row = (XSSFRow) rows.next();
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

				performAction(names, ++row_number); // performAction function is
				// called & String array is
				// passed as parameter
				data.clear();

			}
		}

		private void performAction(String[] fetchedData, int row_number) throws APIException, Exception {

			Actions actions = new Actions(driver);
			
			try {
				if (fetchedData[0].equalsIgnoreCase("Header row") 
						|| fetchedData[0].equalsIgnoreCase("URL Test") 
						|| fetchedData[0].equalsIgnoreCase("Logo Test")
						|| fetchedData[0].equalsIgnoreCase("Language Selection Test") 
						|| fetchedData[0].equalsIgnoreCase("Search Bar Functionality Test")
						|| fetchedData[0].equalsIgnoreCase("Apps Test")
						|| fetchedData[0].equalsIgnoreCase("Home Page Validation Test")
						|| fetchedData[0].equalsIgnoreCase("My Profile EEP Validation Test")
						|| fetchedData[0].equalsIgnoreCase("Footer Section Test")
						|| fetchedData[0].equalsIgnoreCase("Login to Portal Test")
						|| fetchedData[0].equalsIgnoreCase("News and Events Section Test")
						|| fetchedData[0].equalsIgnoreCase("My Actions Section Test")
						|| fetchedData[0].equalsIgnoreCase("My Requests Section Test")
						|| fetchedData[0].equalsIgnoreCase("Raise Ask a Question Ticket Test")
						|| fetchedData[0].equalsIgnoreCase("Navigation to Support Portal Test")
						|| fetchedData[0].equalsIgnoreCase("Views Test")
						|| fetchedData[0].equalsIgnoreCase("Search Bar Test")) { 
					// First Row in the Sheet and rows with only data in first column will be ignored, no action to be performed
					log.info("________________________________________________________________________________________________");
					log.info(row_number +":"+ fetchedData[0]); 
					log.info("________________________________________________________________________________________________");
					
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
						driver.get(fetchedData[4]); 
						log.info(row_number + ":" + fetchedData[5]);
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
						driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
						log.info(row_number + ":" + fetchedData[5]);
				}
				else if (fetchedData[2].equalsIgnoreCase("id") && fetchedData[3].equalsIgnoreCase("click")) 
				{
						//To Click on Element Based on ID
						driver.findElement(By.id((fetchedData[1]))).click();
						log.info(row_number + ":" + fetchedData[5]);
				} 
				else if (fetchedData[0].equalsIgnoreCase("Screenshot") && fetchedData[3].equalsIgnoreCase("getScreenshotAs")) 
				{
						//Takes Screenshot
						waitForLoad(driver);
						shot.getScreenShot(driver, "Scenario_" + fetchedData[4]);					
				}
			    else if (fetchedData[2].equalsIgnoreCase("id") && fetchedData[3].equalsIgnoreCase("sendkeys")) 
				{
						 	//To Pass Value 
			    	if(fetchedData[0].equalsIgnoreCase("Search"))
			    	{
			    			WebElement searchAgent = driver.findElement(By.id(fetchedData[1]));
			    			searchAgent.sendKeys(fetchedData[4]); 
			    			searchAgent.sendKeys(Keys.ENTER);
			    			log.info(row_number + ":" + fetchedData[5]);
			    	}
			    	else 
			    	{
			    		driver.findElement(By.id(fetchedData[1])).sendKeys(fetchedData[4]); 
			    		log.info(row_number + ":" + fetchedData[5]);
			    	}
							
				 }
				 else if(fetchedData[2].equalsIgnoreCase("list") && fetchedData[3].equalsIgnoreCase("size"))
				 {
						    //For Working with multiple records
						 	List<WebElement> rows = driver.findElements(By.xpath(fetchedData[1]));
							int count = rows.size();
							log.info(fetchedData[5]+"Count::" + count);
							for (int i = 1; i <= count; i++) 
							{
								if(fetchedData[0].equalsIgnoreCase("apps") && i >= 6 && i < count)
									
								{
									driver.findElement(By.xpath(fetchedData[6])).click();
									String value = driver.findElement(By.xpath(fetchedData[1]+ "[" + i + "]")).getText();
									log.info(fetchedData[5]+i+"::"+ value);
									log.info("-------------------------------------------------------------------------");
									Thread.sleep(2000);
								}
								else 
								{
									String value = driver.findElement(By.xpath(fetchedData[1]+ "[" + i + "]")).getText();
									log.info(fetchedData[5]+i+"::"+ value);
									log.info("-------------------------------------------------------------------------");
								}
														
							}
				 }
					
				 else if(fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("getAttribute"))
				 {
						 //For Getting the Attribute of any element
						if(fetchedData[0].equalsIgnoreCase("Search Bar"))
						{
							WebElement searchBar = driver.findElement(By.xpath(fetchedData[1]));
							String searchBarText = searchBar.getAttribute(fetchedData[4]);
							log.info(fetchedData[5] +searchBarText);
							searchBar.sendKeys("Benefit");
							searchBar.sendKeys(Keys.ENTER);
						}
						else
						{
							getAttribute = driver.findElement(By.xpath(fetchedData[1])).getAttribute(fetchedData[4]);
							log.info(fetchedData[5] +getAttribute);
						}
						
				 }
				 else if(fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("click"))
				 {
						//For Clicking on any element based on xpath
					 if(fetchedData[0].equalsIgnoreCase("Select Category Options"))
					 {
						// Select selectCategoryOptions = new Select(driver.findElement(By.xpath(fetchedData[1])));
						 WebElement selectCategory = driver.findElement(By.xpath(fetchedData[1]));
						 selectCategory.click();
						 log.info(fetchedData[5]);
						 selectCategory.sendKeys(Keys.DOWN);		
						 selectCategory.sendKeys(Keys.ENTER);
						 
					 }
						 driver.findElement(By.xpath(fetchedData[1])).click();
						 log.info(fetchedData[5]);
				 }
				 else if(fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("getText"))
				 {
						//For Getting the Text
					 	text = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info(fetchedData[5] + text);
				 }
				 else if(fetchedData[0].equalsIgnoreCase("Change Window"))
				 {
						
						    String mainWindowHandle = driver.getWindowHandle();
						    Set<String> allWindowHandles = driver.getWindowHandles();
						    Iterator<String> iterator = allWindowHandles.iterator();
					        while (iterator.hasNext()) 
					        {
					        		String ChildWindow = iterator.next();
					        	    if (!mainWindowHandle.equalsIgnoreCase(ChildWindow))
					                {
					                driver.switchTo().window(ChildWindow);
					                }
					         }
					    
					    log.info(fetchedData[5]);
				}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("switch")) {
						driver.switchTo().frame(0); // For switching onto any
						// frame in the page
						log.info(row_number + ":" + fetchedData[5]);
					}else if ((fetchedData[1].equalsIgnoreCase("defaultContent")
							&& fetchedData[3].equalsIgnoreCase("switch"))) {
						driver.switchTo().defaultContent(); // For switching onto any
						// frame in the page
						log.info(row_number + ":" + fetchedData[5]);
					}
					//added by Praveena
					else if ( fetchedData[3].equalsIgnoreCase("windowhandles")) {
						ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
					    driver.switchTo().window(tabs2.get(1));
					  
					}
					//added by Praveena
					else if (fetchedData[3].equalsIgnoreCase("stayOnTicketHover"))
					{
						String stayonticketValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Status values are " +stayonticketValues);   
					//	Actions actions = new Actions(driver);
						    actions.sendKeys(Keys.UP).build().perform();//press down arrow key
						   
						    
						    actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
						
				
					}
					//added by Praveena
					else if (fetchedData[3].equalsIgnoreCase("solvedHover"))
					{
						String statusValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Status values are " +statusValues);   
					//	Actions actions = new Actions(driver);
						    actions.sendKeys(Keys.UP).build().perform();//press down arrow key
						   
						    
						    actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
						
				
					}
					else if (fetchedData[3].equalsIgnoreCase("pendingHover"))
					{
						String statusValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Status values are " +statusValues);   
					//	Actions actions = new Actions(driver);
						    actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						   
						    
						    actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
							
				
					}
				
				//Agent To submit as Open (Checked the status of ticket as New and now submitting as Open) 
					else if (fetchedData[3].equalsIgnoreCase("Open"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.UP).sendKeys(Keys.UP).sendKeys(Keys.UP).sendKeys(Keys.UP).sendKeys(Keys.UP).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				//Added by Hemant
					else if (fetchedData[3].equalsIgnoreCase("Macro-close and redirect to topics"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Macros options are " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						   //actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						   
						    
						    actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
						
				
					}
				
				//Added by Hemant
					else if (fetchedData[3].equalsIgnoreCase("Macro-customer not responding"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Macros options are " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						   actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						   
						    
						    actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
						
				
					}
				
				//Added by Hemant
					else if (fetchedData[3].equalsIgnoreCase("Macro-downgrade and inform"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Macros options are " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						   
						 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						    Thread.sleep(10000);
				
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
						
				
					}
				
					//added by Praveena - modified by Hemant for Macro(Search)
					else if (fetchedData[2].equalsIgnoreCase("xpath") && (fetchedData[3].equalsIgnoreCase("actions")))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));
						    
						   actions.moveToElement(comment).click().sendKeys(fetchedData[4]).build().perform();
						   Thread.sleep(5000);
						   actions.sendKeys(Keys.ENTER).build().perform();//press enter 
						   
						    Thread.sleep(5000);
						    
					       log.info(fetchedData[5]);
						 
					}
				
				
				//Added by Hemant - Ask a Question(Compensation and Benefits Inquiry)
					else if (fetchedData[3].equalsIgnoreCase("Compensation and Benefits Enquiry"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						    log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(General HR Enquiry)
					else if (fetchedData[3].equalsIgnoreCase("General HR Enquiry"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Learning and Personal Development)
					else if (fetchedData[3].equalsIgnoreCase("Learning and Personal Development"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Mobility or Exit Enquiry)
					else if (fetchedData[3].equalsIgnoreCase("Mobility or Exit Enquiry"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Payroll)
					else if (fetchedData[3].equalsIgnoreCase("Payroll"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Performance and Management)
					else if (fetchedData[3].equalsIgnoreCase("Performance and Management"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Recruiting or Employee Referral)
					else if (fetchedData[3].equalsIgnoreCase("Recruiting or Employee Referral"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				//Added by Hemant - Ask a Question(Others)
					else if (fetchedData[3].equalsIgnoreCase("Others"))
					{
						WebElement CategoryValue = driver.findElement(By.xpath(fetchedData[1]));
						
					//	log.info("Category selected is " +CategoryValue);   
					//	Actions actions = new Actions(driver);
						
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(6000);
				            
						   log.info(fetchedData[5]);
						    Thread.sleep(2000);
						 
					}
				
				
				//Added by Hemant - Job Transfer(Office Location-USA, Boston)
					else if (fetchedData[3].equalsIgnoreCase("USA-Boston"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					      actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				
				//Added by Hemant - Job Transfer(Office Location-USA, Chicago)
					else if (fetchedData[3].equalsIgnoreCase("USA-Chicago"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						  actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				//Added by Hemant - Job Transfer(Office Location-USA, Coloumbus)
					else if (fetchedData[3].equalsIgnoreCase("USA-Coloumbus"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					      actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				//Added by Hemant - Job Transfer(Office Location-Canada, Calgary)
					else if (fetchedData[3].equalsIgnoreCase("CANADA-Calgary"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					      actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				//Added by Hemant - Job Transfer(Office Location-Canada, Vancouver)
					else if (fetchedData[3].equalsIgnoreCase("CANADA-Vancouver"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					      actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				//Added by Hemant - Job Transfer(Office Location-Canada, Montreal)
					else if (fetchedData[3].equalsIgnoreCase("CANADA-Montreal"))
					{
						String MacrosValues = driver.findElement(By.xpath(fetchedData[1])).getText();
						log.info("Office Location is " +MacrosValues);   
					//	Actions actions = new Actions(driver);
						 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				         actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				         
						    Thread.sleep(3000);
				
						  actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					      actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					      
							 Thread.sleep(3000);
						    log.info(fetchedData[5]);
						    Thread.sleep(5000);
						
					}
				
				//added by Hemant
					else if (fetchedData[3].equalsIgnoreCase("textEquals") && fetchedData[2].equalsIgnoreCase("xpath")) {

						WebElement fieldElement = driver.findElement(By.xpath(fetchedData[1]));
						String fieldValue = fieldElement.getText().trim();

						if (fieldValue.equals(fetchedData[0])) {
							
							Thread.sleep(3000);
							
							 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
							
							 
							 Thread.sleep(3000);
							//log.info(fetchedData[5] + ":" + fieldValue);
							// test.log(Status.PASS, MarkupHelper.createLabel(fetchedData[5],
							// ExtentColor.GREEN));
							 
						} else {
							//log.error("Error.... " + fetchedData[6]);
							log.error("Expected field Value to be displayed ---->" + fetchedData[0]
									+ "Actual Value displayed--->" + fieldValue);
							// test.log(Status.FAIL, MarkupHelper.createLabel(fetchedData[5],
							// ExtentColor.RED));
							Assert.assertEquals(fieldValue, (fetchedData[0]), "Not present ");

						}
					}
				
					//added by Praveena
					else if (fetchedData[3].equalsIgnoreCase("switchTab"))
					{
						((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");
						// String a = "window.open('about:blank','_blank');";
						// ((JavascriptExecutor)driver).executeScript(a);

						ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
						driver.switchTo().window(tabs.get(1));
					       log.info(fetchedData[5]);
						 
					}
					//added by Praveena
					else if (fetchedData[3].equalsIgnoreCase("switchtoTab1")) {
						ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
						driver.switchTo().window(tabs.get(0));
					
					}
					//added by Praveena
					else if (fetchedData[3].equalsIgnoreCase("switchtoTab2")) {
						ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
						driver.switchTo().window(tabs.get(1));
						
					}
					//added by Praveena
					else if (fetchedData[0].equalsIgnoreCase("symantecVIP")) {
						try {
							Runtime.getRuntime().exec("./src/main/resources/vip.exe");
							Thread.sleep(5000);
							driver.findElement(By.id("otpInput")).click();
							// Thread.sleep(4000);
							driver.findElement(By.id("otpInput")).sendKeys(Keys.chord(Keys.CONTROL, "v"));
							driver.findElement(By.id("vipSubmitOTP")).click();
							// Runtime.getRuntime().exec("taskkill /F /IM vip.exe");
							Thread.sleep(7000);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						log.info(fetchedData[5]);
	}

	
				//added by Hemant
					else if (fetchedData[3].equalsIgnoreCase("Back-Button")) {
						driver.navigate().back();
						log.info(fetchedData[5]);
						}

				
					 else if(fetchedData[2].equalsIgnoreCase("list") && fetchedData[3].equalsIgnoreCase("size"))
					 {
						    //For Working with multiple records
						 	List<WebElement> rows = driver.findElements(By.xpath(fetchedData[1]));
							int count = rows.size();
							log.info("COUNT : " + count);
							for (int i = 1; i <= count; i++) 
							{
								if(fetchedData[0].equalsIgnoreCase("apps") && i >= 6 && i < count)
									
								{
									driver.findElement(By.xpath(fetchedData[6])).click();
									String value = driver.findElement(By.xpath(fetchedData[1]+ "[" + i + "]")).getText();
									log.info(fetchedData[5] + value);
									Thread.sleep(2000);
								}
								else 
								{
									String value = driver.findElement(By.xpath(fetchedData[1]+ "[" + i + "]")).getText();
									log.info(fetchedData[5] + value);
								}
							}
					 }
				
				// Added by Hemant for Actions
					 else if(fetchedData[2].equalsIgnoreCase("list") && fetchedData[0].equalsIgnoreCase("Actions") && fetchedData[3].equalsIgnoreCase("size"))
					 {
						    //For Working with multiple records
						 	List<WebElement> rows = driver.findElements(By.xpath(fetchedData[1]));
							int count = rows.size();
							log.info(fetchedData[5] + count);
							for (int i = 1; i <= count; i++) 
							{
								
									String value = driver.findElement(By.xpath(fetchedData[1]+ "[" + i + "]")).getText();
									log.info(fetchedData[6] + value);
								}
							
					 }
				
				
				//Activities (Hiring Manager)
					 else if(fetchedData[3].equalsIgnoreCase("list") && fetchedData[4].equalsIgnoreCase("size"))
						{
							
							List<WebElement> rows = driver.findElements(By.xpath(fetchedData[1]));
							int count = rows.size();
							log.info("COUNT : " + count);
							for (int i = 1; i <= 10; i++) 
							{  
								if(fetchedData[0].equalsIgnoreCase("activities") && i <= 10)

								{ 
									
									String value = driver.findElement(By.xpath(fetchedData[1])).getText();
									log.info(fetchedData[5] + value);
									Thread.sleep(2000);
									
								}
								
							}
						}
				
				
					 else if (fetchedData[3].equalsIgnoreCase("solved"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
				
				//Added by Kavana
					 else if (fetchedData[2].equalsIgnoreCase("xpath") && (fetchedData[3].equalsIgnoreCase("delete")))
					 {

					 driver.findElement(By.xpath((fetchedData[1]))).clear();
					 Thread.sleep(5000);
					 log.info(fetchedData[5]);

					 }
				
					 else if (fetchedData[2].equalsIgnoreCase("xpath") && (fetchedData[3].equalsIgnoreCase("onhover")))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));     
					    actions.moveToElement(comment).build().perform();
					    Thread.sleep(5000);
					   // actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					    
					     Thread.sleep(5000);
					     
					        log.info(fetchedData[5]);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("TwoWheelers"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("open"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("Time off"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
				
					 else if (fetchedData[3].equalsIgnoreCase("benifit"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("Relative"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					    
					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("mobility"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("Leave of absence"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
					 else if (fetchedData[3].equalsIgnoreCase("Personal Leave"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }

					 else if (fetchedData[3].equalsIgnoreCase("Vacation/Annual Leave"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
					 else if (fetchedData[3].equalsIgnoreCase("Sick Leave"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
				
					 else if (fetchedData[3].equalsIgnoreCase("Jury Leave"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				
				//Added by Anusha
				
				
                //To clear Text Box
				else if (fetchedData[2].equalsIgnoreCase("xpath") && (fetchedData[3].equalsIgnoreCase("delete")))
				{

				driver.findElement(By.xpath((fetchedData[1]))).clear();
				Thread.sleep(5000);
				log.info(fetchedData[5]);

				}
				
				
				//OB Activities
			       //Let us know how to pay you
			
					else if (fetchedData[3].equalsIgnoreCase("BIC"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
				
				//Onboarding mailing address
					else if (fetchedData[3].equalsIgnoreCase("USACountry"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

					}
					

					else if (fetchedData[3].equalsIgnoreCase("Texas"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						

					}
				
				//Update address in Onboarding portal
				
					else if (fetchedData[3].equalsIgnoreCase("Canada"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
					
					
					
					else if (fetchedData[3].equalsIgnoreCase("ProfileStateAlberta"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.UP).build().perform();
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
					
					
					
					else if (fetchedData[3].equalsIgnoreCase("ProfileCityCalgary"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.UP).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					
					actions.sendKeys(Keys.UP).sendKeys(Keys.UP).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					
					
					/*actions.sendKeys(Keys.UP).build().perform();//back country
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);*/
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//choose country
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					
					actions.sendKeys(Keys.DOWN).build().perform();//choose state
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					
					actions.sendKeys(Keys.DOWN).build().perform();//choose state
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}

					
					else if (fetchedData[3].equalsIgnoreCase("Austin"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);
						actions.sendKeys(Keys.UP).build().perform();//back state
						actions.sendKeys(Keys.ENTER).build().perform();
						
						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						

					}
				
					else if (fetchedData[3].equalsIgnoreCase("Mobile Phone Number/Email Change"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
				
				
					else if (fetchedData[3].equalsIgnoreCase("addresschange"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
				
				else if (fetchedData[3].equalsIgnoreCase("countrychange"))
				{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info(""""Category selected is """" +CategoryValue);
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

				}

				else if (fetchedData[3].equalsIgnoreCase("statechange"))
				{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info(""""Category selected is """" +CategoryValue);
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					

				}

				
				else if (fetchedData[3].equalsIgnoreCase("citychange"))
				{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info(""""Category selected is """" +CategoryValue);
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);
					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter
					Thread.sleep(3000);
					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter
					log.info(fetchedData[5]);
					Thread.sleep(2000);

				}
				
				
				
				//Confirm your identity
					else if (fetchedData[3].equalsIgnoreCase("No"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
			
					else if (fetchedData[3].equalsIgnoreCase("Yes"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
			
					else if (fetchedData[3].equalsIgnoreCase("Ms"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
			
					else if (fetchedData[3].equalsIgnoreCase("Male"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);

					actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
					actions.sendKeys(Keys.ENTER).build().perform();//press enter

					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);

					}
				
				//Tell me who you are
					else if (fetchedData[3].equalsIgnoreCase("USAcountry"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);

					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				
					 else if (fetchedData[3].equalsIgnoreCase("Very Dissatisfied"))
						{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info("Category selected is " +CategoryValue); 
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						}
						
						else if (fetchedData[3].equalsIgnoreCase("Dissatisfied"))
						{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info("Category selected is " +CategoryValue); 
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						}
						
						else if (fetchedData[3].equalsIgnoreCase("Neutral"))
						{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info("Category selected is " +CategoryValue); 
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						}
						
						else if (fetchedData[3].equalsIgnoreCase("Satisfied"))
						{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info("Category selected is " +CategoryValue); 
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						}
			
						else if (fetchedData[3].equalsIgnoreCase("Extremely Satisfied"))
						{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info("Category selected is " +CategoryValue); 
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

						}
				
				else if(fetchedData[2].equalsIgnoreCase("mousehover") && (fetchedData[3].equalsIgnoreCase("action")))
				{
					//Actions actions = new Actions(driver);
					WebElement option = driver.findElement(By.xpath(fetchedData[1]));
					log.info(option);
					/*actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
					log.info(option);
					actions.sendKeys(Keys.ARROW_DOWN);
					log.info(option);*/
					option.sendKeys(Keys.ARROW_DOWN);
					WebElement optionclick= driver.findElement(By.xpath("//*[@id='new_request']//div[3]//a[@aria-activedescendant='compensation_and_benefits_enquiry]")); 
					optionclick.sendKeys(Keys.ENTER);
					
				}
				else if(fetchedData[2].equalsIgnoreCase("name") && (fetchedData[3].equalsIgnoreCase("text")))
				{
					 log.info(fetchedData[5]+" "+driver.getTitle());
						 
				}
				else if (fetchedData[2].equalsIgnoreCase("id") || fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("elementvisible")) 
				{
					int elementVisible = elementVisibilty(fetchedData[2], fetchedData[1]);
					if (elementVisible!=0)
					{
						log.info(fetchedData[5] + " is present");
					}
					
					else
					{
						log.info(fetchedData[5] + " is not present");
					}
				}
				
				
				else if (fetchedData[2].equalsIgnoreCase("id") || fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("elementclickable")) 
				{
					int elementClickable = elementClickable(fetchedData[2], fetchedData[1]);
					if (elementClickable!=0)
					{
						log.info(fetchedData[5] + " is clickable");
					}
					
					else
					{
						log.info(fetchedData[5] + " is not clickable");
					}
				}
				
				else if (fetchedData[2].equalsIgnoreCase("id") && fetchedData[3].equalsIgnoreCase("click")) {
					//elementClickable(fetchedData[2], fetchedData[1]);
					driver.findElement(By.id(fetchedData[1])).click();
					log.info(row_number + ":" + fetchedData[5]);
				} 
				
				//Added By Hemant
				else if (fetchedData[2].equalsIgnoreCase("value") && fetchedData[3].equalsIgnoreCase("click")) {
					//elementClickable(fetchedData[2], fetchedData[1]);
					
					driver.findElement(By.id(fetchedData[1])).click();
					log.info(row_number + ":" + fetchedData[5]);
				} 
				
				
				//added by Praveena
				else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("click")) {
					//elementClickable(fetchedData[2], fetchedData[1]);
					driver.findElement(By.xpath(fetchedData[1])).click();
					log.info(row_number + ":" + fetchedData[5]);
				} 
				
				else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("getText")) {
					//elementClickable(fetchedData[2], fetchedData[1]);
				String value=driver.findElement(By.xpath(fetchedData[1])).getText();
					log.info(fetchedData[5] +"" +value);
				} 
				 
				
				else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("getAttribute")) {

						String element = driver.findElement(By.xpath(fetchedData[1])).getAttribute("value");
						log.info(fetchedData[5] + " : " + element);

					}
				 else if (fetchedData[2].equalsIgnoreCase("ID") && fetchedData[3].equalsIgnoreCase("Enter")) {
						driver.findElement(By.id(fetchedData[1])).sendKeys(Keys.ENTER);
						log.info(fetchedData[5]);
					}
				 else if (fetchedData[2].equalsIgnoreCase("id") && fetchedData[3].equalsIgnoreCase("sendkeys")) {

						driver.findElement(By.id(fetchedData[1])).sendKeys(fetchedData[4]); // for sending values to
						// the field by locating
						// it with ID
						log.info(row_number + ":" + fetchedData[5]);
					}
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("sendkeys")) {

						driver.findElement(By.xpath(fetchedData[1])).sendKeys(fetchedData[4]); // for sending values to
						// the field by locating
						// it with xpath
						log.info(row_number + ":" + fetchedData[5]);
					}
				
				
				//Added By Hemant to extract Request Id and send it
				
				/* else if (fetchedData[2].equalsIgnoreCase("xpath") &&  fetchedData[3].equalsIgnoreCase("Request ID"))
				 {
					 
					 String Request_Id = driver.findElement(By.xpath(fetchedData[1])).getText();
					 String Request_Number = Request_Id.substring(13, 19);
					 
					 if (fetchedData[6].equalsIgnoreCase("getText")) 
						{
						 
							log.info(fetchedData[5] + Request_Id);
							log.info(Request_Number);
						}
				 
						else if (fetchedData[6].equalsIgnoreCase("sendkeys"));
					 {
						 driver.findElement(By.xpath(fetchedData[1])).sendKeys(Request_Number);
						 
						}
					
					} */
				
				//Added by Hemant to extract and Request Id and search for it
				
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("Request ID"))
				 {
					 
					 Request_Id = driver.findElement(By.xpath(fetchedData[1])).getText();
					 Thread.sleep(3000);
					 
					 Request_Number = Request_Id.substring(13, 19);
					 Thread.sleep(3000);
					 
					 log.info(fetchedData[5] + Request_Id);
					 
					 log.info(Request_Number);
				 }
				
				//Added by Hemant to extract and Request Id and search for it
				
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("Request Number"))
				 {
					 
					 driver.findElement(By.xpath(fetchedData[1])).sendKeys(Request_Number);
					 Thread.sleep(2000);
					 
					 actions.sendKeys(Keys.ENTER).build().perform();//press enter
					 Thread.sleep(3000);
					 
					 log.info(fetchedData[5] + Request_Number);
				 }
				
				//Added by Hemant to extract and Request Id and search for it
				
				else if (fetchedData[2].equalsIgnoreCase("ID") && fetchedData[3].equalsIgnoreCase("Request Number"))
				 {
				 	 
				 	 driver.findElement(By.id(fetchedData[1])).sendKeys(Request_Number);
				 	 Thread.sleep(1000);
				 	 
				 	 actions.sendKeys(Keys.ENTER).build().perform();//press enter
				 	 Thread.sleep(3000);
				 	 
				 	 log.info(fetchedData[5] + Request_Number);
				 }
				
				
				
				//added by Kavana
				 else if (fetchedData[3].equalsIgnoreCase("New-Solved"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);
					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("Open-Solved"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);
					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("Pending"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);
					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("solved1"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);

				 }
				
				
				 else if (fetchedData[3].equalsIgnoreCase("on-hold1"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);

				 }
				
                 else if (fetchedData[3].equalsIgnoreCase("Maternity Leave"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);

				 }
				
				
				 else if (fetchedData[3].equalsIgnoreCase("OnLeave"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
				          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);

				 }
				
				
				 else if (fetchedData[3].equalsIgnoreCase("Preleave"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);

				 }
				
				
				 else if (fetchedData[3].equalsIgnoreCase("Onhold"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);
					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
					else if (fetchedData[3].equalsIgnoreCase("submit-solved"))
					{
					WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					// log.info("Category selected is " +CategoryValue); 
					// Actions actions = new Actions(driver);
					
					actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//back state
					actions.sendKeys(Keys.ENTER).build().perform();
					
					Thread.sleep(3000);
					log.info(fetchedData[5]);
					Thread.sleep(2000);
					}
				
				
				 else if (fetchedData[3].equalsIgnoreCase("solved"))
				 {
				 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

				 // log.info("Category selected is " +CategoryValue);   
				 // Actions actions = new Actions(driver);

				 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
				 actions.sendKeys(Keys.ENTER).build().perform();//press enter 
				          
				     Thread.sleep(3000);
				             
				     log.info(fetchedData[5]);
				     Thread.sleep(2000);
				     
				 }
				
				//added by Anusha
				
				 else if (fetchedData[3].equalsIgnoreCase("InvoluntaryExit"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);	

					}
			
			
					else if (fetchedData[3].equalsIgnoreCase("UnprofessionalConduct"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);	
						
						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter


					}
				
					else if (fetchedData[3].equalsIgnoreCase("SelectYes"))
					{
						WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

						// log.info(""""Category selected is """" +CategoryValue);
						// Actions actions = new Actions(driver);

						actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
						actions.sendKeys(Keys.ENTER).build().perform();//press enter

						Thread.sleep(3000);

						log.info(fetchedData[5]);
						Thread.sleep(2000);

					}
				
				//Agent to submit as solved
				
					else if (fetchedData[3].equalsIgnoreCase("solved"))
					 {
					 WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

					 // log.info("Category selected is " +CategoryValue);   
					 // Actions actions = new Actions(driver);

					 actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
					          actions.sendKeys(Keys.ENTER).build().perform();//press enter 
					          
					     Thread.sleep(3000);
					             
					     log.info(fetchedData[5]);
					     Thread.sleep(2000);

					 }
				

				//Hover for Agent section( Add new ticket - Death )
						else if (fetchedData[2].equalsIgnoreCase("xpath") && (fetchedData[3].equalsIgnoreCase("onhover")))
	                     {
	                     WebElement comment = driver.findElement(By.xpath(fetchedData[1]));     
	                        actions.moveToElement(comment).build().perform();
	                        Thread.sleep(5000);
	                       // actions.sendKeys(Keys.ENTER).build().perform();//press enter 
	                        
	                         Thread.sleep(5000);
	                         
	                            log.info(fetchedData[5]);

	 

	                     }
					
				
				//Agent - Select New Ticket
						else if (fetchedData[3].equalsIgnoreCase("AddTicket"))
						{
							WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

							// log.info(""""Category selected is """" +CategoryValue);
							// Actions actions = new Actions(driver);

							actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
							actions.sendKeys(Keys.ENTER).build().perform();//press enter

							Thread.sleep(3000);

							log.info(fetchedData[5]);
							Thread.sleep(2000);	

						}
				
				//Agent - Requester
						 else if(fetchedData[3].equalsIgnoreCase("Levi_Joseph"))
								{
									WebElement searchBar = driver.findElement(By.xpath(fetchedData[1]));
									actions.sendKeys(Keys.DOWN).build().perform();
									actions.sendKeys(Keys.ENTER).build().perform();
								}
				
				//Agent- Select Form - Offboarding Exit Request
							else if (fetchedData[3].equalsIgnoreCase("OffboardingExitRequest"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);	

							}
					
					//Agent - Select Type Of Exit - Voluntary 

							else if (fetchedData[3].equalsIgnoreCase("VoluntaryType"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);	

							}
					
					//Agent - Select Reason For Exit - Death
							else if (fetchedData[3].equalsIgnoreCase("ReasonDeath"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);	
								
								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter


							}
				
				//Agent - Select Reason For Exit - Decline To Start
				
							else if (fetchedData[3].equalsIgnoreCase("Decline"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);	
								
								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter


							}
				
				//Agent SignOut
							else if (fetchedData[3].equalsIgnoreCase("SignOut"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);

							}
				
				//Offboarding - Submitting from EndUser
				
							else if (fetchedData[3].equalsIgnoreCase("Voluntary"))
							{
								WebElement comment = driver.findElement(By.xpath(fetchedData[1]));

								// log.info(""""Category selected is """" +CategoryValue);
								// Actions actions = new Actions(driver);

								actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								Thread.sleep(3000);

								log.info(fetchedData[5]);
								Thread.sleep(2000);

								actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
								actions.sendKeys(Keys.ENTER).build().perform();//press enter

								

							}
				
				
			
				//added by Praveena
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("RandomShortDesc")) {
						Random randomGenerator = new Random();
						int randomInt = randomGenerator.nextInt(10000);
						RandomShrtDesc = fetchedData[4] + " " + randomInt;
						driver.findElement(By.xpath(fetchedData[1])).sendKeys(RandomShrtDesc);
					}
				
				//Added By Hemant so to give random subject
				 else if (fetchedData[2].equalsIgnoreCase("ID") && fetchedData[3].equalsIgnoreCase("RandomSubject")) {
						Random randomGenerator = new Random();
						int randomInt = randomGenerator.nextInt(10000);
						String RandomSubject = fetchedData[4] + " " + randomInt;
						driver.findElement(By.id(fetchedData[1])).sendKeys(RandomSubject);
					}
				
				//added by Praveena
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("RandomBodyDesc")) {
						// String RandomBodyDesc = UUID.randomUUID().toString();
						Random randomGenerator = new Random();
						int randomInt = randomGenerator.nextInt(1000);
						RandomBodyDesc = fetchedData[4] + " " + randomInt;
						driver.findElement(By.xpath(fetchedData[1])).sendKeys(RandomBodyDesc);
					}
				
				
				//added by Praveena
				 else if (fetchedData[2].equalsIgnoreCase("id") && fetchedData[3].equalsIgnoreCase("SearchShrtDesc1")) {
						driver.findElement(By.id(fetchedData[1])).sendKeys(RandomShrtDesc);
					}
				
				
				//added by Praveena
				 else if (fetchedData[2].equalsIgnoreCase("xpath") && fetchedData[3].equalsIgnoreCase("SearchShrtDesc1")) {
						driver.findElement(By.xpath(fetchedData[1])).sendKeys(RandomShrtDesc);
					}
				
				
				 else if(fetchedData[3].equalsIgnoreCase("popup"))
						 {
						 String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
						 String subWindowHandler = null;

						 Set<String> handles = driver.getWindowHandles(); // get all window handles
						 Iterator<String> iterator = handles.iterator();
						 while (iterator.hasNext()){
						 subWindowHandler = iterator.next();
						 }
						 driver.switchTo().window(subWindowHandler);
						 						
						 log.info(row_number + ":" + fetchedData[5]);
						 					 
						  }
			
			
				
			}
		
			catch (Exception e) {
				log.error("Following action not performed");
				// log.error(e.printStackTrace());
				SoftAssert softAssert = new SoftAssert();
				String exceptionName = e.getClass().getSimpleName();
				System.out.println("Exception Occured at line :" + row_number + " " + exceptionName);
				if (exceptionName.equals("NoSuchElementException")) {
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					log.error("Error occured at line: " + row_number + sheetName + "And Below action not performed:   "
							+ fetchedData[5]);
					log.error("NoSuchElementException: " + row_number);
					test.log(Status.FAIL, "NoSuchElementException" + " : " + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					Assert.fail("NoSuchElementException" + " : " + row_number + " : " + fetchedData[1]);
				}

				else if (exceptionName.equals("UnhandledAlertException")) {
					log.error("Alert occured at line:" + row_number);
					Alert alert = driver.switchTo().alert(); // for Accepting the alert pop
					// up
					alert.accept();
					log.error("Error occured at line: " + row_number);
					log.error("UnhandledAlertException: " + row_number);
					log.error("UnhandledAlertException row------" + fetchedData[0]);
					log.error("Following action not performed: " + fetchedData[5]);
					log.error(e.toString());
					test.log(Status.INFO, "UnhandledAlertException" + " : " + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					// Keywords.getScreenshot(selectedRow1.getCell(4).getStringCellValue());
				} else if (exceptionName.equals("ArrayIndexOutOfBoundsException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ArrayIndexOutOfBoundsException" + row_number);
					log.error("Error occured at line : " + row_number + "---" + sheetName
							+ "And Below action not performed: " + fetchedData[5]);
					log.error("ArrayIndexOutOfBoundsException row------" + fetchedData[0] + "--" + fetchedData[1] + "--"
							+ fetchedData[2] + "--" + fetchedData[3] + "--" + fetchedData[4] + "--" + fetchedData[5]);
					test.log(Status.FAIL, "ArrayIndexOutOfBoundsException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					Assert.fail("ArrayIndexOutOfBoundsException" + " : " + row_number + " : " + fetchedData[1]);
				} else if (exceptionName.equals("org.openqa.selenium.TimeoutException")) {
					log.error("Following action not performed: " + fetchedData[5]);
					log.error("Error occured at line:" + row_number);
					log.error("org.openqa.selenium.TimeoutException" + row_number);
					log.error("Please increase the waittime to overcome this error at line :" + row_number + "---"
							+ sheetName);
					test.log(Status.ERROR,
							"org.openqa.selenium.TimeoutException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName.equals("TimeoutException")) {
					log.error("Following action not performed: " + fetchedData[5]);
					log.error("Error occured at line:" + row_number);
					log.error("TimeoutException" + row_number);
					log.error("Please increase the waittime to overcome this error at line :" + row_number + "---"
							+ sheetName);
					test.log(Status.ERROR, "TimeoutException" + ":" + row_number + " : " + fetchedData[1]);
					 Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName.equals("ElementNotVisibleException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ElementNotVisibleException" + row_number);
					log.error("Element not visible: " + fetchedData[1] + "\"Following action not performed: "
							+ fetchedData[5]);
					test.log(Status.FAIL, "ElementNotVisibleException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					Assert.fail("ElementNotVisibleException" + " : " + row_number + " : " + fetchedData[1]);

				} else if (exceptionName.equals("ElementNotFoundException")) {
					log.error("Error occured at line:" + row_number);
					log.error("ElementNotFoundException" + row_number);
					log.error("ElementNotFoundException " + fetchedData[1] + "\"Following action not performed: "
							+ fetchedData[5]);
					test.log(Status.FAIL, "ElementNotFoundException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
					Assert.fail("ElementNotFoundException" + " : " + row_number + " : " + fetchedData[1]);
				
				} else if (exceptionName.equals("NoAlertPresentException")) {
					log.error("Error occured at line:" + row_number);
					log.error("NoAlertPresentException" + row_number);
					test.log(Status.ERROR, "NoAlertPresentException" + ":" + row_number + " : " + fetchedData[1]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);
				} else if (exceptionName == exceptionName) {
					log.error("Error occured at line:" + row_number);
					log.error(exceptionName + row_number);
					test.log(Status.ERROR, exceptionName + ":" + row_number + " : " + fetchedData[0]);
					Testrail.addResultForTestCase(RunId, CaseID, 5);

				}

			}

		}
		public static void safeJavaScriptClick(WebElement element) throws Exception {
			try {
				if (element.isEnabled() && element.isDisplayed()) {
					// log.trace("Clicking on element with using java script click");

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
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
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(pageLoadCondition);
		}

		public void jsExecutorClick(WebElement element) {

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		}	
		public int elementVisibilty(String path, String element) 
		{
			wait = new WebDriverWait(driver, 30);
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
					log.info("id element is missing:" + pathElement);
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
					log.info("xpath element is missing:" + pathElement);
				}
			}
			return i;
			
		}
		public int elementClickable(String path, String element) 
		{
			wait = new WebDriverWait(driver, 30);
			int i=0;
			By pathElement;
			if (path.equalsIgnoreCase("id")) {
				pathElement = By.id(element);
				try {
					wait.until(ExpectedConditions.elementToBeClickable(pathElement));
					i=1;
				} catch (Exception e) {
					log.info("id element is missing:" + pathElement);
				}
			} else if (path.equalsIgnoreCase("xpath")) {
				pathElement = By.xpath(element);
				try {
					wait.until(ExpectedConditions.elementToBeClickable(pathElement));
					i=1;
				} catch (Exception e) {
					log.info("xpath element is missing:" + pathElement);
				}
			}
			return i;
		}
		public void categoryNotEmpty(String element, String element1) throws Exception {
			WebElement textnotnull = driver.findElement(By.xpath(element));
			String value = textnotnull.getText();
			if (value != null) {
				driver.findElement(By.xpath(element1)).click();
			}
		}
		public void elementInVisibilty(String path, String element, String msg) {
			wait = new WebDriverWait(driver, 30);
			By pathElement;
			if (path.equalsIgnoreCase("id")) {
				pathElement = By.id(element);
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(pathElement));
					// log.info("Testcase passed - Error message not displayed");
				} catch (Exception e) {
					// log.info("id element is missing:" + msg);
					Assert.fail();
				}
			} else if (path.equalsIgnoreCase("xpath")) {
				pathElement = By.xpath(element);
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(pathElement));
					// log.info("Testcase passed - Error message not displayed");
				} catch (Exception e) {
					log.info("xpath element is missing:" + msg);
				}
			}
		}
		
	}
	

