package a4_MyHR_and_Agent_GenericFunctions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import a3_Selenium_GenericMethods.AgentGenericMethods2;


/* this class hosts the actions required to create a case using Exit Management Eform */
public class Class4_DatePicker_Methods extends AgentGenericMethods2 {
	
	static String MonthName;
	public static void selectDate(String DateToSelect) throws Exception{ 
		
		
		//String DateValue=DateToSelect;
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		//String date = formatter.format(d);
		String splitter[] = DateToSelect.split("/");
		String day = splitter[0];
		String month = splitter[1];
		String Year = splitter[2];
		System.out.println(day);
		System.out.println(month);
		System.out.println(Year);
		
		if(month.equalsIgnoreCase("01")) {
			MonthName="January";
		
		}
		if(month.equalsIgnoreCase("02")) {
			MonthName="February";
		
		}
		if(month.equalsIgnoreCase("03")) {
			MonthName="March";
		
		}
		if(month.equalsIgnoreCase("04")) {
			MonthName="April";
		
		}
		if(month.equalsIgnoreCase("05")) {
			MonthName="May";
		
		}
		if(month.equalsIgnoreCase("06")) {
			MonthName="June";
		
		}
		if(month.equalsIgnoreCase("07")) {
			MonthName="July";
		
		}
		if(month.equalsIgnoreCase("08")) {
			MonthName="August";
		
		}
		if(month.equalsIgnoreCase("09")) {
			MonthName="September";
		
		}
		if(month.equalsIgnoreCase("10")) {
			MonthName="October";
		
		}
		if(month.equalsIgnoreCase("11")) {
			MonthName="November";
		
		}
		if(month.equalsIgnoreCase("12")) {
			MonthName="December";
		
		}
		
		
		String DateValue =day+"-"+MonthName+"-"+Year;
		
		
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		 LocalDateTime now = LocalDateTime.now();
		 //System.out.println(dtf.format(now));
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
		 Date d1= sdf.parse(DateValue);
		 Date d2= sdf.parse(dtf.format(now));
		 long difference_In_Time= d1.getTime() - d2.getTime();
		 long difference_In_Days= (difference_In_Time/ (1000 * 60 * 60 * 24))% 365;
		 
		 if(difference_In_Days >0) {
		
				String GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
				Select select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
				String GetYear= select.getFirstSelectedOption().getAttribute("value");
				
				while(!(GetMonth.equalsIgnoreCase(MonthName)) &&(!(GetYear.equalsIgnoreCase(Year)))) {
					
					Click("//a[@title='Next']");
					GetMonth = GetText( "//div[@class='ui-datepicker-title']/span[1]");
					//System.out.println(GetMonth);
					
					select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
					GetYear= select.getFirstSelectedOption().getAttribute("value");
					
				}
				Click("//a[text()='"+day+"']");
		 }
		 else {
			 
			 	String GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
				Select select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
				String GetYear= select.getFirstSelectedOption().getAttribute("value");
				
				while(!(GetMonth.equalsIgnoreCase(MonthName)) &&(!(GetYear.equalsIgnoreCase(Year)))) {
					
					Click("//a[@title='Prev']");
					GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
					//System.out.println(GetMonth);
					
					select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
					GetYear= select.getFirstSelectedOption().getAttribute("value");
					
				}
				Click( "//a[text()='"+day+"']");
		 }
}
	
	public static void selectDateofBirth(String DateToSelect) throws Exception{ 
		
		//String DateValue=DateToSelect;
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		//String date = formatter.format(d);
		String splitter[] = DateToSelect.split("/");
		String day = splitter[0];
		String month = splitter[1];
		String Year = splitter[2];
		System.out.println(day);
		System.out.println(month);
		System.out.println(Year);
		
		if(month.equalsIgnoreCase("01")) {
			MonthName="January";
		
		}
		if(month.equalsIgnoreCase("02")) {
			MonthName="February";
		
		}
		if(month.equalsIgnoreCase("03")) {
			MonthName="March";
		
		}
		if(month.equalsIgnoreCase("04")) {
			MonthName="April";
		
		}
		if(month.equalsIgnoreCase("05")) {
			MonthName="May";
		
		}
		if(month.equalsIgnoreCase("06")) {
			MonthName="June";
		
		}
		if(month.equalsIgnoreCase("07")) {
			MonthName="July";
		
		}
		if(month.equalsIgnoreCase("08")) {
			MonthName="August";
		
		}
		if(month.equalsIgnoreCase("09")) {
			MonthName="September";
		
		}
		if(month.equalsIgnoreCase("10")) {
			MonthName="October";
		
		}
		if(month.equalsIgnoreCase("11")) {
			MonthName="November";
		
		}
		if(month.equalsIgnoreCase("12")) {
			MonthName="December";
		
		}
		
		String DateValue =day+"-"+MonthName+"-"+Year;
		
		String GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
		Select select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
		String GetYear= select.getFirstSelectedOption().getAttribute("value");
		
		while(!(GetMonth.equalsIgnoreCase("January")) ){
			
			Click("//a[@title='Prev']");
			GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
			//System.out.println(GetMonth);
			
			//select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
			//GetYear= select.getFirstSelectedOption().getAttribute("value");
			
		}
		Click("//a[text()='1']");
	
		select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
		select.selectByValue(Year);
		
		while(!(GetMonth.equalsIgnoreCase(MonthName))&&(!(GetYear.equalsIgnoreCase(Year)))) {
			
			Click("//a[@title='Next']");
			GetMonth = GetText("//div[@class='ui-datepicker-title']/span[1]");
			//System.out.println(GetMonth);
			
			select = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']//select[@class='ui-datepicker-year']")));
			GetYear= select.getFirstSelectedOption().getAttribute("value");
		
			
		}
		Click("//a[text()='"+day+"']");
	}


	public static void SelectMonth_and_Year(String DateToSelect) throws Exception {
		
		String splitter[] = DateToSelect.split("/");
		String monthToSelect = splitter[0];
		String yearToSelect = splitter[1];
		System.out.println(monthToSelect);
		System.out.println(yearToSelect);
		
		if(monthToSelect.equalsIgnoreCase("01")) {
			MonthName="1";
		
		}
		if(monthToSelect.equalsIgnoreCase("02")) {
			MonthName="2";
		
		}
		if(monthToSelect.equalsIgnoreCase("03")) {
			MonthName="3";
		
		}
		if(monthToSelect.equalsIgnoreCase("04")) {
			MonthName="4";
		
		}
		if(monthToSelect.equalsIgnoreCase("05")) {
			MonthName="5";
		
		}
		if(monthToSelect.equalsIgnoreCase("06")) {
			MonthName="6";
		
		}
		if(monthToSelect.equalsIgnoreCase("07")) {
			MonthName="7";
		
		}
		if(monthToSelect.equalsIgnoreCase("08")) {
			MonthName="8";
		
		}
		if(monthToSelect.equalsIgnoreCase("09")) {
			MonthName="9";
		
		}
		if(monthToSelect.equalsIgnoreCase("10")) {
			MonthName="10";
		
		}
		if(monthToSelect.equalsIgnoreCase("11")) {
			MonthName="11";
		
		}
		if(monthToSelect.equalsIgnoreCase("12")) {
			MonthName="12";
		
		}
		
		Select Month = new Select(driverA.findElement(By.xpath( "//div[@class='ui-datepicker-title']/select[1]")));
		Select Year = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']/select[2]")));
		String GetYear= Year.getFirstSelectedOption().getAttribute("value");
		String GetMonth=Month.getFirstSelectedOption().getAttribute("value");
		
		if(!(GetYear.equalsIgnoreCase("yearToSelect"))) {
			
			Select Year1 = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']/select[2]")));
			Year1.selectByValue(yearToSelect);
			
		}
		
		if(!(GetMonth.equalsIgnoreCase(MonthName))) {
			
			Select Month1 = new Select(driverA.findElement(By.xpath("//div[@class='ui-datepicker-title']/select[1]")));
			Month1.selectByValue(MonthName);
			
		}
		
		
	}
}
	

			
			            	        
				