package Gmail;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import a3_Selenium_GenericMethods.PortalGenericMethods2; 

public class Keyword_cased_OpsCat_Population extends PortalGenericMethods2 {
	
	static String AgentURL;
	static String ToEmailID ="support@myhr-lindeuat.zendesk.com"; 
	static String FromEmailID ="lindept05@gmail.com";
	static String Password="ekodjypkpfctzpzm";
	static String Login="Not Done";
	static String TicketCreated;
	static String Keyword;
	static String AssignmentGroup;
	static String Country;
	static String Ops_cat_1;
	static String Ops_cat_2;
	static String Ops_cat_3;
	static String Ticket_ID;
	static String Ticket_Subject;
	static String SearchResult;
	static String Scenario;
	
	
	public static void Verify_Ticket_Population(String URL,String sheet_name,String ScreenshotPath ,String Keywordfilepath, String Methodname ) throws Exception {

	
		AgentURL = URL+"/agent";
		//System.out.println("Mthod name is"+Keywordfilepath);
		ReadExcel(Keywordfilepath);
		

		try {
			
			 sheet = workbook.getSheet(sheet_name);
			//System.out.println("------in to the Verfy AMS1 "+sheet.getSheetName());
			int totalRows=sheet.getLastRowNum();
			//System.out.println("------in to the Verfy AMS2 "+totalRows);
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows
				
				// System.out.println(i);
				Scenario=GetdataFromSheet(sheet_name,"Scenario", i);
				 Keyword=GetdataFromSheet(sheet_name,"Keyword", i);
				 AssignmentGroup=GetdataFromSheet(sheet_name,"Ticket Queue", i);
				 Country=GetdataFromSheet(sheet_name,"Country", i);
				 Ops_cat_1= GetdataFromSheet(sheet_name,"Ops Cat1", i);
				 Ops_cat_2= GetdataFromSheet(sheet_name,"Ops Cat2", i);
				 Ops_cat_3=GetdataFromSheet(sheet_name,"Ops Cat3", i);
				
					if(Country.equalsIgnoreCase("United Kingdom")) {
						FromEmailID="lindept02@gmail.com";
						Password = "rrktmewvmyhwfbed";
					}
					
					if(Country.equalsIgnoreCase("USA")) {
						FromEmailID="lindept04@gmail.com";
						Password = "wyxrcmigwqrabcpr";
					}
					
					if(Country.equalsIgnoreCase("Germany")) {
						FromEmailID="lindept05@gmail.com";
						Password = "ekodjypkpfctzpzm";
					}
					
					if(Country.equalsIgnoreCase("Australia")) {
						FromEmailID="lindept06@gmail.com";
						Password = "fxephnmxnriohnee";
					}
					
					if(Country.equalsIgnoreCase("New Zealand")) {
						FromEmailID="lindept07@gmail.com";
						Password = "mzkcvpjguzgwtcnc";
					}
				
				//if the Return value is empty
				if (Keyword.isEmpty()==true) {
					//System.out.println("breaking it");
					break;
				}
				
				else{
					String Subject= "Test Automation1 Keyword "+Scenario+" - "+Keyword;
					System.out.println(FromEmailID +"--"+Password+"--"+ Subject);
					
									
					//Send Email
					SendEmail.main(Subject,ToEmailID,FromEmailID,Password);
					Thread.sleep(5000);
					WriteToExcelBySheetName(Keywordfilepath, sheet_name,"Ticket Subject", Scenario, Subject);
					
					System.out.println(Subject +"-----> Completed");
				}
						
			}
			//CloseReport();
			
		}catch (Exception F) {
				//F.printStackTrace();
		}
		       
}
	
	public static void Verify_Ticket_Population2(String URL,String sheet_name,String ScreenshotPath ,String Keywordfilepath, String Methodname ) throws Exception {

		
		AgentURL = URL+"/agent";
		System.out.println("Mthod name is"+Keywordfilepath);
		ReadExcel(Keywordfilepath);
		

		try {
			
			 sheet = workbook.getSheet(sheet_name);
			System.out.println("------in to the Verfy AMS1 "+sheet.getSheetName());
			int totalRows=sheet.getLastRowNum();
			System.out.println("------in to the Verfy AMS2 "+totalRows);
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows
				
				Scenario=GetdataFromSheet(sheet_name,"Scenario", i);
				 Keyword=GetdataFromSheet(sheet_name,"Keyword", i);
				 AssignmentGroup=GetdataFromSheet(sheet_name,"Ticket Queue", i);
				 Country=GetdataFromSheet(sheet_name,"Country", i);
				 Ops_cat_1= GetdataFromSheet(sheet_name,"Ops Cat1", i);
				 Ops_cat_2= GetdataFromSheet(sheet_name,"Ops Cat2", i);
				 Ops_cat_3=GetdataFromSheet(sheet_name,"Ops Cat3", i);
				 Ticket_Subject=GetdataFromSheet(sheet_name,"Ticket Subject", i);
				 Ticket_ID=GetdataFromSheet(sheet_name,"Ticket ID", i);
				
					
				//if the Return value is empty
				if (Keyword.isEmpty()==true) {
					//System.out.println("breaking it");
					break;
				}
				
				else{
					CreateTest(Ticket_Subject);
					System.out.println("---------------------------------------------------------------------"+i);
					VerifyTicketcreation("External:"+Ticket_Subject, Keywordfilepath,sheet_name, ScreenshotPath, Methodname);
					VerifyTicketDetails(Keywordfilepath, sheet_name, Methodname);
					//WriteToExcelBySheetName(Keywordfilepath, sheet_name,"Ticket Subject", Keyword, "");
					
				}
						
			}
			CloseReport();
			
		}catch (Exception F) {
				//F.printStackTrace();
		}
		       
}
	public static void VerifyTicketcreation(String Subject,String Keywordfilepath,String sheet_name,String ScreenshotPath,String Methodname ) throws Exception {
		
		//Login as agent
		if(Login.equalsIgnoreCase("Not Done")==true){	
		Agent_Login(Methodname);
		Login="Done";
		}
		
		else if(Login.equalsIgnoreCase("Done") && (TicketCreated.equalsIgnoreCase("true"))) {
		
		try {
			
			Click("(//button[@data-test-id='close-button'])[2]");
			Click("(//button[@data-test-id='close-button'])[1]");
			Thread.sleep(3000);
		}catch (org.openqa.selenium.NoSuchElementException e) {
			//System.out.println("------Close button not clicked");
			//e.printStackTrace();
		}
		}
		
		try {
		//Click on search Icon
		Click("/html/body/div[1]/div[8]/header/div[2]/div/div/div[2]/div[1]/div/div");
		//Type the subject and click Enter
		EnterValue("//input[@placeholder='search Zendesk Support']", Subject);
		Thread.sleep(3000);
		KeyEnter();
		
		TicketCreated="false";
		//Verify the search result and click on it
		SearchResult= "//a[text()='"+Subject+"']";
		wait = new WebDriverWait(driverA, Duration.ofSeconds(15));
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SearchResult)));
		
		if(element.isDisplayed()) {
	
			System.out.println("------Element visible");
			TicketCreated="true";
		}
	
		
		}catch (org.openqa.selenium.NoSuchElementException e) {
			
			e.printStackTrace();
			
		}catch (Throwable t) {
			
			TicketCreated="false";
			System.out.println("Ticket Not Created"+TicketCreated);
			LogFAIL("Ticket itself Not created");
			WriteToExcelBySheetName(Keywordfilepath,sheet_name, "Ticket ID", Scenario, "Ticket Not Created");
			Click("(//button[@data-test-id='close-button'])[1]");
			Thread.sleep(3000);
			t.printStackTrace();
            
        }
			
		}
	
	
	
	public static void VerifyTicketDetails(String Keywordfilepath,String sheet_name,String Methodname)  {
		
		try {
			if(TicketCreated.equalsIgnoreCase("true")){
			
			//click on the ticket
			element.click();
			
			//Get Ticket number
			element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-test-id='tabs-nav-item-users']//following-sibling::span")));
			String TicketText = element.getText();
			String Ticket=extractNumericValue(TicketText);
			System.out.println(Ticket);
			WriteToExcelBySheetName(Keywordfilepath,sheet_name, "Ticket ID", Scenario, Ticket);
			Thread.sleep(2000);
			
			//<----------------------Verify Auto populated details----------------------------->
			
			boolean Assignee_Verification;
			//Verify Assignee is populated
			String Assignee = GetText("//label[text()='Assignee*']//following-sibling::div");
			System.out.println(Assignee);
			if (Assignee.contains("/")) {
				
				String splitter[] = Assignee.split("/");
				String Group = splitter[0].trim();
				System.out.println(Group);
				Assignee_Verification=VerifyGetText(Group,AssignmentGroup,"Assignee field" );
			}
			else {
				Assignee_Verification=VerifyGetText(Assignee,AssignmentGroup,"Assignee field" );
			}
			
			
			//Verify form is populated
			String Forms=GetText("//label[text()='Form']//following-sibling::div");
			System.out.println(Forms);
			boolean Forms_Verification=VerifyGetText(Forms,"Ask a question","Forms field" );
			
			//Verify Country is populated
			/*String ActualCountry=GetText("_xpath","/html/body/div[1]/div[8]/div[3]/div[3]/section/div[3]/div[1]/div/div[1]/div[3]/div[3]/div[6]/div/div/div");
			System.out.println(ActualCountry);
			boolean Country_Verification=VerifyGetText(ActualCountry,Country,"Country field" );*/

			//Verify Ops Cat 1
			String ActualOps_Cat_1=GetText("//label[text()='Ops Cat 1 (New)']//following-sibling::div");
			System.out.println(ActualOps_Cat_1);
			boolean OpsCat1_Verification=VerifyGetText(ActualOps_Cat_1,Ops_cat_1,"Ops_cat_1 field" );
			
			//Verify Ops Cat 2
			String ActualOps_Cat_2=GetText("//label[text()='Ops Cat 2 (New)']//following-sibling::div");
			System.out.println(ActualOps_Cat_2);
			boolean OpsCat2_Verification=VerifyGetText(ActualOps_Cat_2,Ops_cat_2,"Ops_cat_2 field" );
			
			//Verify Ops Cat 3
			String ActualOps_Cat_3=GetText("//label[text()='Ops Cat 3 (New)*']//following-sibling::div");
			System.out.println(ActualOps_Cat_3);
			boolean OpsCat3_Verification=VerifyGetText(ActualOps_Cat_3,Ops_cat_3,"Ops_cat_3 field" );
			
			if((Assignee_Verification==true)&&(Forms_Verification==true)&&
				(OpsCat1_Verification==true)&&(OpsCat2_Verification==true)&&(OpsCat3_Verification==true)) {
				
				WriteToExcelBySheetName(Keywordfilepath,sheet_name, "Overall Result", Scenario, "Passed");
			}
			else {
				WriteToExcelBySheetName(Keywordfilepath,sheet_name, "Overall Result", Scenario, "Failed");
			}

		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void Agent_Login(String Methodname) throws InterruptedException, IOException {
		
		driverA.get(AgentURL);
		Thread.sleep(3000);
		//driverA.switchTo().frame(driverA.findElement(By.tagName("iframe")));
		
		wait = new WebDriverWait(driverA, Duration.ofSeconds(25));
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_email")));
		element.sendKeys("v.dhatchinamoorthy@accenture.com");
		
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password")));
		element.sendKeys("welcome");
		
		element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-in-submit-button")));
		element.click();
		captureScreenshot(Methodname, "Agent_Support_page");
		//driverA.switchTo().defaultContent();
		
	}
}