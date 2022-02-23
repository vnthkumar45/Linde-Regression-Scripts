package Reporting;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ZendeskExtentReport {
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	
	public static ExtentReports getInstance() {
		
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date(0));
		
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\Reports\\ZendeskReport" + ".html");
		htmlReporter.config().setDocumentTitle("Automation Report"); // Title of report
		htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "provide your credentials");
		
		return extent;
	}

}