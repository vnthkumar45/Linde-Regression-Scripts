package my_Links;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import a3_Selenium_GenericMethods.PortalGenericMethods2;


/* this class hosts the actions required to create a case using Exit Management Eform */
public class myLinks_Verification extends PortalGenericMethods2 {
	
	
	public void Verify_Links(String Country,String FormsInputFilePath,String sheet_name)throws Exception{
		
		

        // Retrieve app titles and descriptions from portal using Selenium
        HashMap<String, String> portalData = getAppDataFromPortal(driverA);

        // Retrieve app titles and descriptions from Excel
        String excelFilePath = "src\\main\\resources\\Excel_Data\\MyApps_Verification.xlsx";  // Update with your file path
        HashMap<String, String> excelData = getAppDataFromExcel(excelFilePath);

        // Compare the data
        HashMap<String, String> missingInPortal = new HashMap<>();
        HashMap<String, String> extraInPortal = new HashMap<>();
        HashMap<String, String> mismatched = new HashMap<>();

        // Compare both hashmaps
        for (String title : excelData.keySet()) {
            if (portalData.containsKey(title)) {
                if (!portalData.get(title).equals(excelData.get(title))) {
                    mismatched.put(title, "Mismatch between portal and Excel");
                }
            } else {
                missingInPortal.put(title, excelData.get(title));
            }
        }

        for (String title : portalData.keySet()) {
            if (!excelData.containsKey(title)) {
                extraInPortal.put(title, portalData.get(title));
            }
        }

        // Write the comparison results into the Excel file
        writeComparisonToExcel(excelFilePath, missingInPortal, extraInPortal, mismatched);

        // Close Selenium WebDriver
        driverA.quit();
    }

    // Method to extract app titles and descriptions from the portal using Selenium
    private static HashMap<String, String> getAppDataFromPortal(WebDriver driver) {
        HashMap<String, String> portalData = new HashMap<>();

        // Locate elements for app titles and descriptions (update the locators as per your portal)
        List<WebElement> appTitles = driver.findElements(By.cssSelector(".app-title"));
        List<WebElement> appDescriptions = driver.findElements(By.cssSelector(".app-description"));

        // Assume each title corresponds to the same index in description list
        for (int i = 0; i < appTitles.size(); i++) {
            String title = appTitles.get(i).getText();
            String description = appDescriptions.get(i).getText();
            portalData.put(title, description);
        }

        return portalData;
    }

    // Method to read app titles and descriptions from an Excel file
    private static HashMap<String, String> getAppDataFromExcel(String excelFilePath) throws IOException {
        HashMap<String, String> excelData = new HashMap<>();
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Assuming first column is titles and second column is descriptions
        for (Row row : sheet) {
            String title = row.getCell(0).getStringCellValue();
            String description = row.getCell(2).getStringCellValue();
            excelData.put(title, description);
        }

        workbook.close();
        fis.close();

        return excelData;
    }

    // Method to write comparison results to Excel
    private static void writeComparisonToExcel(String excelFilePath, HashMap<String, String> missingInPortal,
                                               HashMap<String, String> extraInPortal, HashMap<String, String> mismatched) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.createSheet("Comparison Results");

        // Write headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("App Title");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Comparison Result");

        // Write missing in portal
        int rowIndex = 1;
        for (String title : missingInPortal.keySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue(missingInPortal.get(title));
            row.createCell(2).setCellValue("Missing in Portal");
        }

        // Write extra in portal
        for (String title : extraInPortal.keySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue(extraInPortal.get(title));
            row.createCell(2).setCellValue("Extra in Portal");
        }

        // Write mismatched descriptions
        for (String title : mismatched.keySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue("N/A");
            row.createCell(2).setCellValue("Description Mismatch");
        }

        // Write the results to the file
        fis.close();
        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }


}
		
			


	

			
			            	        
				