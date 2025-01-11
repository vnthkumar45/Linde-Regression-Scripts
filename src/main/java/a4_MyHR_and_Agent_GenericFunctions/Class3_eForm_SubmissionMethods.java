package a4_MyHR_and_Agent_GenericFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import a3_Selenium_GenericMethods.PortalGenericMethods2;
 


/* this class hosts the actions required to create a case using Exit Management Eform */
public class Class3_eForm_SubmissionMethods extends PortalGenericMethods2 {
	
	static String username1 = System.getProperty("user.name");
	static String LabelsFilePath= "C:\\Users\\"+username1+"\\eclipse-workspace\\Linde_EEP_Project\\src\\main\\resources\\Excel_Data\\MyHR_Eform_Labels.xlsx";
	static boolean VisibilityCheck;
	static boolean InstructionCheck;
	static String MandatoryCheck;
	static String Readonlycheck;
	
	public static void Verify_Labels(String sheet_name,String MethodName)throws Exception{

		LogPASS(LabelsFilePath);
		ReadExcel(LabelsFilePath);
		try {
			//String sheet_name = "SecondEmployer";
			sheet = workbook.getSheet(sheet_name);
			int totalRows=sheet.getLastRowNum();
			LogSTATUS("Field Label Verification is ----------------------------------------------> In Progress ");
			for(int i=1;i<=totalRows;i++){//declaring while loop to iterate in sheet untill we have rows

				String FieldLabels=GetdataFromSheet(sheet_name, "FieldLabels", i);
				String TypeOfField=GetdataFromSheet(sheet_name, "Type of Field", i);
				String Visible =GetdataFromSheet(sheet_name, "Visible", i);
				String Mandatory=GetdataFromSheet(sheet_name, "Mandatory", i);
				String Readonly=GetdataFromSheet(sheet_name, "Read-only", i);
				
				
				//if the Return value is empty
				if (FieldLabels.isEmpty()) {
					break;	
				}
			
				else {
					
					
					String xpath ="//label[text()='"+FieldLabels+"']//parent::div";
			
					String Readonly_xpath= "//label[text()='"+FieldLabels+"']//following-sibling::input";
					
					
					//Field Visibility Check -----------------------------------------------------------------------------------------------------
					if(TypeOfField.equalsIgnoreCase("Instruction")==false) {
						
					try {
						
					 VisibilityCheck= driverA.findElement(By.xpath(xpath)).isDisplayed();
					
					//Verify the visibility of the field
					if((VisibilityCheck=true) && (Visible.equalsIgnoreCase("True")==true)){
						
						LogPASS("The Field "+FieldLabels+" is visible");
						//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "True");
					}
					
					if((VisibilityCheck=true) && (Visible.equalsIgnoreCase("False")==true)) {
						LogFAIL("The Field "+FieldLabels+" is visible. It should not be visible as per FDS");
						//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "True");
					}
					
					}catch(org.openqa.selenium.NoSuchElementException e) {
					
						if((VisibilityCheck=false) && (Visible.equalsIgnoreCase("False")==true)) {
							LogPASS("The Field "+FieldLabels+" is not visible.");
							//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "False");
						}
						else {
						LogFAIL("The Field "+FieldLabels+" is not visible");
						//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "False");
						}
						}
					}
					else {
						
						try {
							
							 InstructionCheck=driverA.findElement(By.xpath("//div[@class='dynamicArticle']//p")).isDisplayed();
							//Verify the visibility of the field
							if(InstructionCheck=true){
								
								LogPASS("The Instruction "+FieldLabels+" is visible");
								//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "True");
							}
							
							}catch(org.openqa.selenium.NoSuchElementException e) {
							
								LogFAIL("The Instruction "+FieldLabels+" is not visible");
								//PrintResults(LabelsFilePath, sheet_name, "Visible - Actual", "FieldLabels", "False");
							}
					
						}
						
					
					
					//Verify the Mandatory of thr field------------------------------------------------------------------------------------
					if(TypeOfField.equalsIgnoreCase("HelpText")==false || TypeOfField.equalsIgnoreCase("Instruction")==false ) {
					try {
						MandatoryCheck = driverA.findElement(By.xpath(xpath)).getAttribute("class");
						//LogPASS(MandatoryCheck);
						//LogPASS(Mandatory);
					if((MandatoryCheck.contains("required")) && (Mandatory.equalsIgnoreCase("True")) ) {
						
						LogPASS("The Field "+FieldLabels+" is Mandatory");
						//PrintResults(LabelsFilePath, sheet_name, "Mandatory - Actual", "FieldLabels", "True");
					}
					else if((MandatoryCheck.contains("required")) && (Mandatory.equalsIgnoreCase("false") )) {
						
						LogFAIL("The Field "+FieldLabels+" is Mandatory. But as per the FDS it should not be mandatory");
						//PrintResults(LabelsFilePath, sheet_name, "Mandatory - Actual", "FieldLabels", "True");
					}
					else if((MandatoryCheck.contains("optional")) && (Mandatory.equalsIgnoreCase("false"))){
						LogPASS("The Field "+FieldLabels+" is not Mandatory");
						//PrintResults(LabelsFilePath, sheet_name, "Mandatory - Actual", "FieldLabels", "False");
					}
					else {
						LogFAIL("The Field "+FieldLabels+" is not Mandatory. It should be mandatory as per FDS");
						//PrintResults(LabelsFilePath, sheet_name, "Mandatory - Actual", "FieldLabels", "False");
					}
					}catch(org.openqa.selenium.NoSuchElementException e) {
						
					}
					}
					
					//Verify the field is readonly or editable
					if(TypeOfField.equalsIgnoreCase("HelpText")==false ) {
					try {
					Readonlycheck= driverA.findElement(By.xpath(Readonly_xpath)).getAttribute("readonly");
					if((TypeOfField.equalsIgnoreCase("Dropdown")==false) || ((TypeOfField.equalsIgnoreCase("Instruction")==false))){
						//LogPASS("Read only check is ------ "+Readonlycheck);
						if((Readonlycheck.equalsIgnoreCase("true"))&&(Readonly.equalsIgnoreCase("true"))) {
							LogPASS("The Field "+FieldLabels+" is Readonly");
							//PrintResults(LabelsFilePath, sheet_name, "Readonly - Actual", "FieldLabels", "True");
						}
						else if((Readonlycheck.equalsIgnoreCase("true"))&&(Readonly.equalsIgnoreCase("false"))) {
							LogFAIL("The Field "+FieldLabels+" is Readonly. But as per the FDS It should be editable");
							//PrintResults(LabelsFilePath, sheet_name, "Readonly - Actual", "FieldLabels", "True");
						}
						
					}
					}catch(org.openqa.selenium.NoSuchElementException e) {
						
					}catch(NullPointerException f) {
						
						if((Readonlycheck=="null")&& (Readonly.equalsIgnoreCase("True"))) {
							LogFAIL("The Field "+FieldLabels+" is not Readonly. But as per the FDS It should be readonly");
							//PrintResults(LabelsFilePath, sheet_name, "Readonly - Actual", "FieldLabels", "False");
						}
						else {
							LogPASS("The Field "+FieldLabels+" is not readonly");
							//PrintResults(LabelsFilePath, sheet_name, "Readonly - Actual", "FieldLabels", "False");
						}
						
					}
				}
				}
	}
				
}catch (Exception F) {
			F.printStackTrace();
	}
		LogSTATUS("Field Label Verification is ----------------------------------------------> Completed ");	
}
	
	public static void PrintResults(String Filepath,String Sheet_name,String Row0,String Rowname,String Result) throws FileNotFoundException, IOException {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(Filepath));//declaring the xssf workbook
			XSSFSheet  sheet = workbook.getSheet(Sheet_name);//declaring sheet. First sheet in the input file is guidelines sheet hence pointing to the second one.
		//LogPASS(Sheet_name);	
		int ColNum=-1;
		XSSFRow row1 = sheet.getRow(0);
		 
		for (int j=0;j<row1.getLastCellNum();j++) {
			
			if(row1.getCell(j).getStringCellValue().trim().equals(Row0.trim())) {
				ColNum=j;
			}
		}
		
		for (int i=1;i<=sheet.getLastRowNum();i++) {
			
			row1=sheet.getRow(i);
			String CellNumber0=row1.getCell(0).getStringCellValue();
			String CellNumber2=row1.getCell(2).getStringCellValue();
			String CellNumber4=row1.getCell(4).getStringCellValue();
			String CellNumber6=row1.getCell(6).getStringCellValue();
			if((Result.equalsIgnoreCase("True") && (CellNumber2.equalsIgnoreCase("true")||CellNumber4.equalsIgnoreCase("true")||CellNumber6.equalsIgnoreCase("true")))||
			   (Result.equalsIgnoreCase("False") && (CellNumber2.equalsIgnoreCase("False")||CellNumber4.equalsIgnoreCase("False")||CellNumber6.equalsIgnoreCase("False")))) {
				
				if(CellNumber0.equalsIgnoreCase(Rowname)) { 
					XSSFCellStyle style1 = workbook.createCellStyle();
				    style1.setWrapText(true);
				    style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				    style1.setBorderTop(BorderStyle.MEDIUM);
				    style1.setBorderBottom(BorderStyle.MEDIUM);
				    style1.setBorderLeft(BorderStyle.MEDIUM);
				    style1.setBorderRight(BorderStyle.MEDIUM);
					row1=sheet.getRow(i);
					//LogPASS(ColNum);
					XSSFCell cell=row1.getCell(ColNum);
					if(cell==null)
					cell=row1.createCell(ColNum);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(Result);
					cell.setCellStyle(style1);
				}
			}
			else {
				if(CellNumber0.equalsIgnoreCase(Rowname)) {
				XSSFCellStyle style2 = workbook.createCellStyle();
				 style2.setBorderTop(BorderStyle.MEDIUM);
				 style2.setBorderBottom(BorderStyle.MEDIUM);
				 style2.setBorderLeft(BorderStyle.MEDIUM);
				 style2.setBorderRight(BorderStyle.MEDIUM);
			    style2.setWrapText(true);
			    style2.setFillForegroundColor(IndexedColors.RED.getIndex());
			    //style2.setFillPattern(CellStyle.SOLID_FOREGROUND); 
			    
				row1=sheet.getRow(i);
				//LogPASS(ColNum);
				XSSFCell cell=row1.getCell(ColNum);
				if(cell==null)
				cell=row1.createCell(ColNum);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(Result);
				cell.setCellStyle(style2);
			}
				
			}
			FileOutputStream output_file =new FileOutputStream(new File(Filepath));  //Open FileOutputStream to write updates
			workbook.write(output_file); //write changes*/
			output_file.close();
			
		}
		
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}	

	}

	
	}


	

			
			            	        
				