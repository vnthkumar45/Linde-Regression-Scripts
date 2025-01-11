package myApps;

import java.io.File;
import java.io.FileOutputStream;
import a3_Selenium_GenericMethods.PortalGenericMethods2;

public class PrintTask extends PortalGenericMethods2{

public static void readFromExcel(String Tasklist,String Sheet_name,String Portal)throws InterruptedException{
	

try {
	
		String Filepath="src\\main\\resources\\OnboardingTasks.xlsx";
		String Filepath2="src\\main\\resources\\OnboardingTasks_PING.xlsx";
		String NetherlandsFilePath= "src\\main\\resources\\Dependent_Information_EForm.xlsx";
		
		if(Portal.equalsIgnoreCase("Onboarding")) {
			ReadExcel(Filepath);
		}else if(Portal.equalsIgnoreCase("PINGOnboarding")){
			ReadExcel(Filepath2);	
		}
		else {
			ReadExcel(NetherlandsFilePath);
		}
	
		sheet = workbook.getSheet(Sheet_name);
		int totalRows=sheet.getLastRowNum();
		
		for(int i=1;i<=totalRows;i++){
		String ExpectedTask= GetdataFromSheet(Sheet_name, "List of Tasks-Expected", i);
		
		if(ExpectedTask.equalsIgnoreCase(Tasklist)) {
			WriteToExcelBySheetName(Filepath, Sheet_name, "Visibility", ExpectedTask, "Visible");
		}
		

		if(Portal.equalsIgnoreCase("Onboarding")) {
		FileOutputStream output_file =new FileOutputStream(new File(Filepath));  //Open FileOutputStream to write updates
		workbook.write(output_file); //write changes
		}
		else if(Portal.equalsIgnoreCase("PINGOnboarding")) {
			FileOutputStream output_file =new FileOutputStream(new File(Filepath2));  //Open FileOutputStream to write updates
			workbook.write(output_file); //write changes
		}
		else {
			FileOutputStream output_file =new FileOutputStream(new File(NetherlandsFilePath));  //Open FileOutputStream to write updates
			workbook.write(output_file); //write changes
		}	
		}
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	

}

}
			 



		

	

