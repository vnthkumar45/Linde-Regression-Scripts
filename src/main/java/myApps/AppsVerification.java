package myApps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import a3_Selenium_GenericMethods.PortalGenericMethods2;


/* this class hosts the actions required to create a case using Exit Management Eform */
public class AppsVerification extends PortalGenericMethods2 {
	static WebDriverWait wait2;
	
	public static void Login(String userID,String Password)throws Exception{
		try {
		wait2=new WebDriverWait(driverA, Duration.ofSeconds(30));
		//captureScreenshot(MethodName,"Linde_MyHR_Login_Page");
		driverA.switchTo().frame(driverA.findElement(By.tagName("iframe")));
		//EnterValue("Username_id", userID);
		//EnterValue("Password_id", Password);
		//Click("LoginButton_id");
		Thread.sleep(2000);
		//captureScreenshot(MethodName,"PING_HomePage");
		}catch (org.openqa.selenium.NoSuchElementException a) {
			a.printStackTrace();
			// TODO: handle exception
		}
	}

	
	public void VerifyApps(String url,String Appfilepath,String Methodname)throws Exception{
		System.out.println(Appfilepath);
		ConfigureReport("My Apps Verification for different countries");
		//System.out.println("------in to the Verfy apps");
		ReadExcel(Appfilepath);
		try {
			
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			//System.out.println("------in to the Verfy apps1");
			int totalRows=sheet1.getLastRowNum();
			//System.out.println("------in to the Verfy apps2");
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows
				
				String Country=Getdata("Country", i);
				String Username=Getdata("UserID", i);
				String Password=Getdata("Password", i);
				
				
				//if the Return value is empty
				if (Country.isEmpty()==true) {
					//System.out.println("breaking it");
					break;
				}
				
				else{
					
					CreateTest("Apps verification is in progress");
					
					LogSTATUS(Country+"---- is in Progress");
					System.out.println("----------------inside of login");
					
					System.out.println("Country is-----"+ Country);
					System.out.println("username is-----"+ Username);
					System.out.println("password is-----"+ Password);
					
					//Perform Login functionality
					Login(Username,Password);
					//System.out.println("----------------out of login");
					Thread.sleep(5000);
					driverA.findElement(By.xpath("//*[@id='myapps']/a")).click();					
					Thread.sleep(2000);
					
					//Perform All Type Task verification
					AppsTest(Country,Appfilepath);
					
					//Perform Logout Functionality
				//	Logout();
					Thread.sleep(2000);
					//driverA.get(url);//launch the URL
				}
						
			}
			CloseReport();
			
		}catch (FileNotFoundException e) {
			log.fatal("Input File not found. The execution is stopped.");
		} catch (IOException e) {
				log.fatal("IO Exception recieved. The execution is stopped");
		} catch (Exception F) {
				F.printStackTrace();
		}
				
		}

	
	public void AppsTest(String UserCountry,String Appfilepath) throws Exception{
		System.out.println("into--------App test");
		wait2=new WebDriverWait(driverA, Duration.ofSeconds(30));
		//CreateTest(UserCountry);
		LogPASS("Tasks for the Country-"+UserCountry+"....................................................");
		String Task_no;
		String Task_name;
		String Task_descr;
		int i=0,j=0,k=0;
		//captureScreenshot(UserCountry,"OB Home Page");
		for (i=1;i<=40;i++){
			System.out.println("into--------App test1");
			Task_no="(//span[@id='app_tile'])["+i+"]";
			Task_name="(//span[@class='appTitle'])["+i+"]";
			Task_descr="(//span[@class='appDesc'])["+i+"]";
			try{
				System.out.println("i value is----"+i);
				String GetAppname=driverA.findElement(By.xpath(Task_name)).getText();
				String GetAppDescr=driverA.findElement(By.xpath(Task_descr)).getText();
				System.out.println(GetAppname +" - "+GetAppDescr);
				LogPASS(GetAppname +" - "+GetAppDescr);
				forms.PrintTask.readFromExcel(GetAppname,GetAppDescr,UserCountry);
				j++;
							
			}catch(org.openqa.selenium.NoSuchElementException e){
				//e.printStackTrace();
				break;
			}catch (org.openqa.selenium.WebDriverException a) {
				//a.printStackTrace();	
			}
		}
		
		
	}
				
}
		
			


	

			
			            	        
				