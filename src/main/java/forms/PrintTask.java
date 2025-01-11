package forms;

import java.io.File;
import java.io.FileOutputStream;
import a3_Selenium_GenericMethods.PortalGenericMethods2;

public class PrintTask extends PortalGenericMethods2 {

public static void readFromExcel(String ActualAppName,String ActualAppDescr,String Sheet_name)throws InterruptedException{
	

try {
	
		String Filepath="src\\main\\resources\\Excel_Data\\MyApps_Verification.xlsx";
		ReadExcel(Filepath);
	
		sheet = workbook.getSheet(Sheet_name);
		int totalRows=sheet.getLastRowNum();
		
		for(int i=1;i<=totalRows;i++){
		String ExpectedAppName= GetdataFromSheet(Sheet_name, "App Name", i);
		String ExpectedAppDescr=GetdataFromSheet(Sheet_name,"App Description",i);
		
		if(ExpectedAppName.equalsIgnoreCase(ActualAppName)) {
			WriteToExcelBySheetName(Filepath, Sheet_name, "Actual App Name", ExpectedAppName, ActualAppName);
			
			if(ExpectedAppDescr.equalsIgnoreCase(ActualAppDescr)) {
				WriteToExcelBySheetName(Filepath, Sheet_name, "Actual App Description", ExpectedAppName, ActualAppDescr);
				
			}
		}
		

	
		FileOutputStream output_file =new FileOutputStream(new File(Filepath));  //Open FileOutputStream to write updates
		workbook.write(output_file); //write changes
		}
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	

}

}
			 



		

	

