package Gmail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import a3_Selenium_GenericMethods.PortalGenericMethods2; 

public class AMSEmailRouting extends PortalGenericMethods2 {
	
	static String AgentURL;
	static String ToEmailID ="support@myhr-lindeuat.zendesk.com"; 
	static String FromEmailID ="lindept05@gmail.com";
	static String Password="ekodjypkpfctzpzm";
	static String Login="Not Done";
	static String TicketCreated;
	static String Email_Subject;
	static String AssignmentGroup;
	static String Country;
	static String Linde_Employee_Entity;
	static String Ticket_Category;
	static String AMS_Subcategory;
	static String Ops_cat_1;
	static String QC_Status;
	static String SRT_Ticket;
	static String Migrated_from_IBM;
	static String Priority;
	
	public static void VerifyAMSTickets(String URL,String AMSfilepath,String ScreenshotPath, String Methodname ) throws Exception {

	
		AgentURL = URL+"/agent";
		//ConfigureReport("My Apps Verification for different countries");
		//System.out.println("Mthod name is"+AMSfilepath);
		ReadExcel(AMSfilepath);
		

		try {
			
			 sheet = workbook.getSheetAt(1);
			//System.out.println("------in to the Verfy AMS1 "+sheet1.getSheetName());
			int totalRows=sheet.getLastRowNum();
			//System.out.println("------in to the Verfy AMS2 "+totalRows);
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows
				
				 System.out.println(i);
				 Email_Subject=Getdata("Email Subject", i);
				 AssignmentGroup=Getdata("Ticket Queue", i);
				 Country=Getdata("Country", i);
				 Linde_Employee_Entity= Getdata("Linde Employee Entity", i);
				 Ticket_Category= Getdata("Ticket Category", i);
				 AMS_Subcategory= Getdata("AMS Subcategory", i);
				 Ops_cat_1= Getdata("Ops cat 1", i);
				 QC_Status= Getdata("QC Status", i);
				 SRT_Ticket=Getdata("SRT Ticket", i);
				 Migrated_from_IBM=Getdata("Migrated from IBM", i);
				 Priority=Getdata("Priority", i);
				
				//if the Return value is empty
				if (Email_Subject.isEmpty()==true) {
					//System.out.println("breaking it");
					break;
				}
				
				else{
					String Subject= "Test Automation Keyword verification - "+Email_Subject;
					
					//Send Email
					SendEmail.main(Subject,ToEmailID,FromEmailID,Password);
					Thread.sleep(25000);				
					VerifyTicketcreation(Subject,AMSfilepath,ScreenshotPath,Methodname);
					VerifyTicketDetails(AMSfilepath, Methodname);
					
				}
						
			}
			//CloseReport();
			
		}catch (FileNotFoundException e) {
			log.fatal("Input File not found. The execution is stopped.");
		} catch (IOException e) {
				log.fatal("IO Exception recieved. The execution is stopped");
		} catch (Exception F) {
				//F.printStackTrace();
		}
		       
}
	public static void VerifyTicketcreation(String Subject,String AMSfilepath,String ScreenshotPath,String Methodname ) throws Exception {
		
		//Login as agent
		if(Login.equalsIgnoreCase("Not Done")){
			
		Agent_Login(Methodname);
		Login="Done";
		
		}
		
		try {
			
			element=driverA.findElement(By.xpath("(//button[@data-test-id='close-button'])[2]"));
			element.click();
			Thread.sleep(4000);
			element=driverA.findElement(By.xpath("(//button[@data-test-id='close-button'])[1]"));
			element.click();
			Thread.sleep(4000);

		}catch (org.openqa.selenium.NoSuchElementException e) {
			//System.out.println("------Close button not clicked");
			//e.printStackTrace();
		}
		
		//Click on search Icon
		wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test-id='header-toolbar-search-button']")));
		element.click();
		
		//Type the subject and click Enter
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='search Zendesk Support']")));
		element.sendKeys(Subject);
		Thread.sleep(3000);
		element.sendKeys(Keys.ENTER);
		
		try {
			
		Thread.sleep(6000);
		TicketCreated="false";
		//WebDriverWait WaitforElement=new WebDriverWait(driverA, 10);
		//Verify the search result and click on it
		String SearchResult= "//a[text()='"+Subject+"']";
		element=driverA.findElement(By.xpath(SearchResult));
		System.out.println("------Into check element");
		if(element.isDisplayed()) {
	
			System.out.println("------Element visible");
			TicketCreated="true";
		}
	
		
		}catch (org.openqa.selenium.NoSuchElementException e) {
			
			e.printStackTrace();
			if(!element.isDisplayed()) {
			for (int i=0;i<10;i++) {
				System.out.println("------Element not visible2");
					driverA.navigate().refresh();
					Thread.sleep(6000);
					driverA.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[1]/div/div/div/div/div[1]/div[1]/div/div[1]/input")).sendKeys(Subject);
					Thread.sleep(7000);
					
					if(element.isDisplayed()) {
						System.out.println("------Element visible2");
						TicketCreated="true";
						break;
				
				
			}
			}
			}
		}
			
		}
	
	
	
	public static void VerifyTicketDetails(String AMSfilepath,String Methodname)  {
		
		try {
			if(TicketCreated.equalsIgnoreCase("true")){
			
			wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
			//click on the ticket
			element.click();
			
			//Get Ticket number
			element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[3]/div[2]/div[4]/header/div[1]/nav/span[3]")));
			String TicketText = element.getText();
			System.out.println(TicketText);
			String Ticket=TicketText.substring(12);
			System.out.println(Ticket);
			WriteToExcel2(AMSfilepath, "Ticket ID", Email_Subject, Ticket);
			Thread.sleep(3000);
			
			//<----------------------Verify Auto populated details----------------------------->
			
		
			//Verify Assignee is populated
			String Assignee = GetText("(//div[@data-test-id='assignee-field-selected-group-tag'])[1]");
			VerifyGetText(Assignee,AssignmentGroup,"Assignee field" );
			
			//Verify form is populated
			String Forms=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div/div/div/div");
			VerifyGetText(Forms,"Ask a question","Forms field" );
			
			//Verify Country is populated
			String ActualCountry=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[6]/div/div/div/div/span/div");
			VerifyGetText(ActualCountry,Country,"Country field" );
			
			//Verify LindeEmployeeEntity
			String ActualEntity=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[7]/div/div/div/div/span/div");
			VerifyGetText(ActualEntity,Linde_Employee_Entity,"Linde Employee Entity field" );
			
			//Verify Priority
			String ActualPriority=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[15]/div/div/div[1]/div/div");
			VerifyGetText(ActualPriority,Priority,"Priority field" );
			
			//Verify Ticket_Category_de
			String ActualTicket_Category_de=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[23]/div/div/div/div/span/div");
			VerifyGetText(ActualTicket_Category_de,Ticket_Category,"Ticket_Category field" );
			
			//Verify AMS_Ticket_Sub_Category
			String AMS_Ticket_Sub_Category=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[31]/div/div/div/div/span/div");
			VerifyGetText(AMS_Ticket_Sub_Category,AMS_Subcategory,"AMS_Ticket_Sub_Category field" );
			
			//Verify Ops Cat 1
			String ActualOps_Cat_1=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[53]/div/div/div/div/span/div");
			VerifyGetText(ActualOps_Cat_1,Ops_cat_1,"Ops_cat_1 field" );
			
			//Verify QC_Status
			String Actual_QC_Status=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[56]/div/div/div/div/span/div");
			VerifyGetText(Actual_QC_Status,QC_Status,"QC_Status field" );
			
			//Verify SRT_Ticket
			String Actual_SRT_Ticket=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[67]/div/div/div/div/span/div");
			VerifyGetText(Actual_SRT_Ticket,SRT_Ticket,"SRT_Ticket field" );
			
			//Verify Migrated_from_IBM
			String Actual_MigratedFromIBM=GetText("/html/body/div[1]/div[3]/div[2]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[66]/div/div/div/div/span/div");
			VerifyGetText(Actual_MigratedFromIBM,Migrated_from_IBM,"Migrated_from_IBM field" );
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void Agent_Login(String Methodname) throws InterruptedException, IOException {
		
		driverA.get(AgentURL);
		Thread.sleep(3000);
		driverA.switchTo().frame(driverA.findElement(By.tagName("iframe")));
		
		wait = new WebDriverWait(driverA, Duration.ofSeconds(30));
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_email")));
		element.sendKeys("manimozhi.vairavan@accenture.com");
		
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password")));
		element.sendKeys("Germanyagent");
		
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-in-submit-button")));
		element.click();
		Thread.sleep(6000);
		captureScreenshot(Methodname, "Agent_Support_page");
		driverA.switchTo().defaultContent();
		
	}
}