package forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import a3_Selenium_GenericMethods.PortalGenericMethods2;


/* this class hosts the actions required to create a case using Exit Management Eform */
public class FormsVerification extends PortalGenericMethods2 {
	
	
	public void Verifyforms_UK(WebDriver driverA,String Country,String FormsInputFilePath,String sheet_name)throws Exception{

		Thread.sleep(5000);
		driverA.findElement(By.xpath("//strong[text()='Forms']")).click();
		
		
		String CategoryVariable=null;
		ReadExcel(FormsInputFilePath);
		try {
			
			sheet = workbook.getSheet(sheet_name);
			int totalRows=sheet.getLastRowNum();
			
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows

				String Categories=GetdataFromSheet(sheet_name, "Categories", i);
				String Subcategories=GetdataFromSheet(sheet_name, "SubCategories", i);
				System.out.println(Categories);
				System.out.println(Subcategories);
				
				//if the Return value is empty
				if (Categories.isEmpty()) {
					break;	
				}
			
				else {
					//WebDriverWait wait = new WebDriverWait(driverA, 10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Please select the type of transaction']//following-sibling::a[@class='nesty-input']")));
							
					System.out.println("in to the else loop");
					//driverA.findElement(By.xpath("//*[@id='new_request']/div[9]/a")).click();
					//Thread.sleep(5000);
					WebElement elmt = driverA.findElement(By.xpath("//input[@id='request_custom_fields_4921678150161']"));
					//driverA.findElement(By.xpath("//*[@id='new_request']/div[9]/a")).sendKeys("Request a flexible work arrangement"+Keys.ENTER);
					
					System.out.println("in ----1");
					Select select = new Select(elmt);
					System.out.println("in ----2");
					select.selectByIndex(1);
					System.out.println("in ----3");
					select.selectByValue("register_your_interest_in_a_study_support_arrangement_gb_emp");
					System.out.println("in ----3");
					
				}
							}
	}catch (Exception e) {
		// TODO: handle exception
	}
	}
				
}
		
			


	

			
			            	        
				