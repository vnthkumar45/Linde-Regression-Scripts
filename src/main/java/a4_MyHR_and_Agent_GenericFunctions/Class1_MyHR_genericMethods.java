package a4_MyHR_and_Agent_GenericFunctions;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import a3_Selenium_GenericMethods.AgentGenericMethods2;

/* this class hosts the actions required to create a case using Exit Management Eform */
public class Class1_MyHR_genericMethods extends AgentGenericMethods2 {

	static String Status;
	static boolean TicketFound;
	static String username1 = System.getProperty("user.name");
	static String EformFieldsFilePath = "C:\\Users\\" + username1
			+ "\\eclipse-workspace\\Linde_EEP_Project\\src\\main\\resources\\Excel_Data\\MyHR_Eform_Labels.xlsx";

	public static void Eform_Login(String UserID, String Password, String MethodName) throws Exception {

		try {

			
			wait = new WebDriverWait(driverA, Duration.ofSeconds(15));
			// 
			EnterValue("user_email", UserID);
			EnterValue("user_password", Password);
			captureScreenshot(MethodName,"1_Linde_MyHR_Login_Page");
			Click("sign-in-submit-button");
			captureScreenshot(MethodName,"2_Linde_MyHR_HomePage");

		} catch (org.openqa.selenium.NoSuchElementException a) {
			a.printStackTrace();
		}

	}

	public static void GoToEform_Via_Search(String Eformname) throws Exception {

		try {

			EnterValue("query", Eformname);
			KeyEnter();

			Click("//a[text()='" + Eformname + "']");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void GoToEform(String Eformname, String Country, String MethodName) throws Exception {

		try {

			// Access the Forms section and Select the Entity
			wait = new WebDriverWait(driverA, Duration.ofSeconds(15));
			Click("//div[@class='form menuitem']//span");
			LogSTATUS("Forms Icon ----> Clicked");
			captureScreenshot(MethodName,"3_Forms_Tiles_Page");

			Click("//a[text()='" + Eformname + "']");
			LogSTATUS("Eform " + Eformname + " ----> Clicked");
			SwitchTab(1);
			

		} catch (org.openqa.selenium.NoSuchElementException a) {
			a.printStackTrace();
		}

	}

	public static void Get_and_set_UserDetails(String URL, String EmailID) throws Exception {

		try {
			
				First_Approval_Triggered_flag = false;
				Second_Approval_Triggered_flag = false;
				First_Approval_Completed_flag = false;
				Second_Approval_Completed_flag = false;
				is_Approver_Details_correct=false;
				
			Employeenumber = Fetch_value(URL, EmailID, "employee_number");
			FirstName = Fetch_value(URL, EmailID, "first_name");
			LastName = Fetch_value(URL, EmailID, "last_name");
			HRName = Fetch_value(URL, EmailID, "hr_name");
			HR_EmailID = Fetch_value(URL, EmailID, "hr_email_id");
			ManagerName = Fetch_value(URL, EmailID, "manager_name");
			Manager_EmailID = Fetch_value(URL, EmailID, "manager_email_id");
			

			System.out.println(Employeenumber + " " + FirstName + " " + LastName + " " + HRName + " " + HR_EmailID + " "
					+ ManagerName + " " + Manager_EmailID);

		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Populate_fields_and_Submit_Eform(String Scenario,String sheet_name, String RequestRaisedFor, String MethodName)
			throws Exception {

		wait = new WebDriverWait(driverA, Duration.ofSeconds(10));
		Thread.sleep(5000);
		captureScreenshot(MethodName,"4_Eform Page");
		ReadExcel(EformFieldsFilePath);

		try {
			// String sheet_name = "SecondEmployer";
			sheet = workbook.getSheet(sheet_name);
			int totalRows = sheet.getLastRowNum();

			LogSTATUS("Field Label Verification is ----------------------------------------------> In Progress ");
			for (int i = 1; i <= totalRows; i++) {// declaring while loop to iterate in sheet untill we have rows

				String FieldLabels = GetdataFromSheet(sheet_name, "FieldLabels", i);
				String TypeOfField = GetdataFromSheet(sheet_name, "Type of Field", i);
				String Values = GetdataFromSheet(sheet_name, Scenario, i);
				// if the Return value is empty
				if (FieldLabels.isEmpty()) {
					break;
				} else if (TypeOfField.equalsIgnoreCase("Agent Field")) {

					if (FieldLabels.equalsIgnoreCase("Assignee"))
						Ticket_Assignee = Values;
					else if (FieldLabels.equalsIgnoreCase("Form Name"))
						Ticket_FormName = Values;
					else if (FieldLabels.equalsIgnoreCase("Priority"))
						Ticket_Priority = Values;
					else if (FieldLabels.equalsIgnoreCase("HR Ticket type"))
						Ticket_HRTicketType = Values;
					else if (FieldLabels.equalsIgnoreCase("Ops Cat 1"))
						Ticket_OpsCat1 = Values;
					else if (FieldLabels.equalsIgnoreCase("Ops Cat 2"))
						Ticket_OpsCat2 = Values;
					else if (FieldLabels.equalsIgnoreCase("Ops Cat 3"))
						Ticket_OpsCat3 = Values;
					else if (FieldLabels.equalsIgnoreCase("Escalated Ticket"))
						Ticket_EscalatedTicket = Values;

				}

				else {

					if ((TypeOfField.equalsIgnoreCase("Instruction")) || (TypeOfField.equalsIgnoreCase("HelpText"))) {

					}

					else if (TypeOfField.equalsIgnoreCase("Dropdown") == true) {
						
						if(FieldLabels.equalsIgnoreCase("Please select the type of involuntary separation request")&& (Values.equalsIgnoreCase("NA"))){
							
							Click("//label[text()='" + FieldLabels + "']//following-sibling::a[@class='nesty-input']");
							SelectDropdown(TypeOfRequestfor);
							
						}
						else if(FieldLabels.equalsIgnoreCase("Please specify your request:")&& (Values.equalsIgnoreCase("NA"))) {
							
							Click("//label[text()='" + FieldLabels + "']//following-sibling::a[@class='nesty-input']");
							SelectDropdown(TypeOfRequestfor);
						}
						else if(FieldLabels.equalsIgnoreCase("Reason for contract change")&& (Values.equalsIgnoreCase("NA"))) {
							
							Click("//label[text()='" + FieldLabels + "']//following-sibling::a[@class='nesty-input']");
							SelectDropdown(TypeOfRequestfor);
						}
						else {
							
							Click("//label[text()='" + FieldLabels + "']//following-sibling::a[@class='nesty-input']");
							SelectDropdown(Values);
						}

						

					} else if (TypeOfField.equalsIgnoreCase("Checkbox") == true) {
						
						if(Values.equalsIgnoreCase("TRUE")) {
							
							Click("//label[text()='" + FieldLabels + "']//following-sibling::input[@type='checkbox']");
							
						}

						
					} else if (TypeOfField.contains("Date") == true) {

						Thread.sleep(2000);
						Click("//label[text()='" + FieldLabels
								+ "']//following-sibling::input[@class='hasDatepicker']");
						Class4_DatePicker_Methods.selectDate(Values);

						if (TypeOfField.contains("Start Date")) {
							StartDate = Values;
						}

						if (TypeOfField.contains("End Date")) {
							EndDate = Values;
						}

					} else if (TypeOfField.contains("Year") == true) {

						Thread.sleep(2000);
						Click("//label[text()='" + FieldLabels
								+ "']//following-sibling::input[@class='hasDatepicker']");
						Class4_DatePicker_Methods.SelectMonth_and_Year(Values);

						if (TypeOfField.contains("Start year")) {
							StartDate = Values;
						}

						if (TypeOfField.contains("End year")) {
							EndDate = Values;
						}

					} else if (TypeOfField.equalsIgnoreCase("Textarea") == true) {

						EnterValue("//label[text()='" + FieldLabels + "']//following-sibling::textarea",
								Values);

					} else if (TypeOfField.equalsIgnoreCase("CC field") == true) {
						EnterValue("//li//input", Values);
						KeyEnter();

					}

					else {

						String TextfieldXpath = "//label[text()='" + FieldLabels + "']//following-sibling::input";

						if ((RequestRaisedFor.equalsIgnoreCase("self")||RequestRaisedFor.equalsIgnoreCase("For myself")) && ((FieldLabels.contains("Employee number"))
								|| (FieldLabels.equalsIgnoreCase("First Name"))
								|| (FieldLabels.equalsIgnoreCase("Last Name")))) {

							String ActualFieldValue = GetTextByAttribute(TextfieldXpath, "value");
							System.out.println(ActualFieldValue);

							if (FieldLabels.contains("Employee Number")) {

								if (ActualFieldValue.equalsIgnoreCase(Employeenumber)) {

									LogPASS(FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
									System.out.println(
											FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
								} else {
									LogFAIL(FieldLabels + "is auto-populated with incorrect value " + ActualFieldValue
											+ " or Empty value");
									System.err.println(FieldLabels + "is auto-populated with incorrect value "
											+ ActualFieldValue + " or Empty value");
								}
							}

							if (FieldLabels.equalsIgnoreCase("First name")) {

								if (ActualFieldValue.equalsIgnoreCase(FirstName)) {

									LogPASS(FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
									System.out.println(
											FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
								} else {
									LogFAIL(FieldLabels + "is auto-populated with incorrect value " + ActualFieldValue
											+ " or Empty value");
									System.err.println(FieldLabels + "is auto-populated with incorrect value "
											+ ActualFieldValue + " or Empty value");
								}

							}

							if (FieldLabels.contains("Last name")) {

								if (ActualFieldValue.equalsIgnoreCase(LastName)) {

									LogPASS(FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
									System.out.println(
											FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
								} else {
									LogFAIL(FieldLabels + "is auto-populated with incorrect value " + ActualFieldValue
											+ " or Empty value");
									System.err.println(FieldLabels + "is auto-populated with incorrect value "
											+ ActualFieldValue + " or Empty value");
								}

							}

						}

						else if ((RequestRaisedFor.equalsIgnoreCase("Direct Reports")||RequestRaisedFor.equalsIgnoreCase("direct reports / own support area"))
								&& ((FieldLabels.contains("Employee number"))
										|| (FieldLabels.equalsIgnoreCase("First Name"))
										|| (FieldLabels.equalsIgnoreCase("Last Name")))) {
							String ActualFieldValue = GetText( TextfieldXpath);

							if (FieldLabels.contains("Employee number")) {

								EnterValue(TextfieldXpath, Employeenumber);
								Click("searchnameger");
							}

							if (FieldLabels.equalsIgnoreCase("First name")) {

								if (ActualFieldValue.equalsIgnoreCase(FirstName)) {

									LogPASS(FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
									System.out.println(
											FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
								} else {
									LogFAIL(FieldLabels + "is auto-populated with incorrect value " + ActualFieldValue
											+ " or Empty value");
									System.err.println(FieldLabels + "is auto-populated with incorrect value "
											+ ActualFieldValue + " or Empty value");
								}

							}

							if (FieldLabels.contains("Last name")) {

								if (ActualFieldValue.equalsIgnoreCase(LastName)) {

									LogPASS(FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
									System.out.println(
											FieldLabels + "is auto-populated with correct value " + ActualFieldValue);
								} else {
									LogFAIL(FieldLabels + "is auto-populated with incorrect value " + ActualFieldValue
											+ " or Empty value");
									System.err.println(FieldLabels + "is auto-populated with incorrect value "
											+ ActualFieldValue + " or Empty value");
								}

							}

						} 
						
						else if ((RequestRaisedFor.equalsIgnoreCase("Others")||RequestRaisedFor.equalsIgnoreCase("For another person")||RequestRaisedFor.equalsIgnoreCase("others / other support area"))
								&& ((FieldLabels.contains("Employee number"))
										|| (FieldLabels.equalsIgnoreCase("First Name"))
										|| (FieldLabels.equalsIgnoreCase("Last Name")))) {
							

							if (FieldLabels.contains("Employee number")) {

								EnterValue(TextfieldXpath, Employeenumber);
								
							}

							if (FieldLabels.equalsIgnoreCase("First name")) {

								EnterValue(TextfieldXpath, FirstName);

							}

							if (FieldLabels.contains("Last name")) {

								EnterValue(TextfieldXpath, LastName);

							}

						}else {
							EnterValue(TextfieldXpath, Values);
							System.out.println(FieldLabels + "-----" + Values);
						}

					}

				}

			}
			captureScreenshot(MethodName,"5_Linde_MyHR_Eform fields_Populated");
			//Click("xpath", "//input[@value='Submit']");
			WebElement Submit = driverA.findElement(By.xpath("//input[@value='Submit']"));
			((JavascriptExecutor) driverA).executeScript("arguments[0].click();", Submit);

		} catch (Exception F) {
			F.printStackTrace();
		}

	}

	public static String My_Request_details_Page_Verification(String EformName, String HasApproval, String Country,
			String MethodName) throws Exception {

		try {

			Thread.sleep(9000);
			driverA.navigate().refresh();

			String GetTicketID = GetText("//ol//li[3]");
			Ticket_ID = GetTicketID;
			System.out.println(Ticket_ID);
			captureScreenshot(MethodName,"6_Ticket details_page");
			
			if(Ticket_ID.isEmpty()==false) {
				ParentTicket_Created = true;
			}
			System.out.println(ParentTicket_Created);

			for (int j = 1; j < 5; j++) {

				Status = GetText(
						"//div[@class='home-page-badge request-status-badge desktop-content']//label");
				if (Status.isEmpty()) {
					driverA.navigate().refresh();
					Thread.sleep(3000);
					System.out.println(Status);
				} else {
					System.out.println(Status);
					break;
				}

			}

			if (HasApproval.equalsIgnoreCase("Yes")) {
				LogPASS("The Ticket Status populated is correct ----->" + Status);
			} else {
				LogFAIL("The Ticket Status populated is incorrect ----->" + Status);
			}

			if (Country.equalsIgnoreCase("Germany LG/LCO") || Country.equalsIgnoreCase("Germany LE")) {

				TicketSubject = LastName + ", " + FirstName + "; " + Employeenumber + "; " + EformName + "; "
						+ StartDate + "; " + EndDate;
				String GetTicket_Subject = GetText("//div[@class='request-page-title']");
				System.out.println(GetTicket_Subject);
				System.out.println(TicketSubject);

				if (GetTicket_Subject.contains(TicketSubject) == true) {
					LogPASS("The Ticket subject populated is correct--->" + GetTicket_Subject);
				} else {
					LogFAIL("The Ticket subject populated is not correct--->" + GetTicket_Subject);
				}

			} else {

				TicketSubject = EformName + "Request for " + EformName;
				String GetTicket_Subject = GetText("//div[@class='ticid']");

				if (GetTicket_Subject.contains(TicketSubject) == true) {
					LogPASS("The Ticket subject populated is correct--->" + GetTicket_Subject);
				} else {
					LogFAIL("The Ticket subject populated is not correct--->" + GetTicket_Subject);
				}
			}
		} catch (org.openqa.selenium.NoSuchElementException a) {
			// TODO: handle exception
		}
		return Ticket_ID;
	}

			
	public static void Sign_out_of_MyHR() throws Exception {
		
		 // Count the number of tabs
		Set<String> handles = driverA.getWindowHandles();
        int numTabs = handles.size();
        System.out.println("Number of tabs open: " + numTabs);

        if (numTabs > 1) {
        // Close the tab if there's more than one tab
		close_tabs(numTabs,handles);
        }
        
        try {
        	WebElement Message = driverA.findElement(By.xpath("//div[@data-test-id='notification']"));
        	
        	if(Message.isDisplayed()==true)
        		driverA.findElement(By.xpath("//div[@data-test-id='notification']//div")).click();
        	System.out.println("Closed the popup");
        	
        }catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("no popup visible");
		}
        driverA.navigate().refresh();
        Thread.sleep(3000);
		Click("//div[@class='user-info dropdown dropdown-toggle-main']/button");
		Click("//a[contains(@href,'/access/logout')]");
 
	}
	
	public static String Approver_Action_on_ApprovalTicket(String URL,String ApproverUsername, String Password, String Approval_TicketID, String Country,String ApprovalAction,String MethodName) throws Exception {
		
		String Approval_Action ="Not Done";
		//Login as Approver
		driverA.get(URL);
		
		//Login as Approver
		Eform_Login(ApproverUsername, Password, MethodName);
		
		try {
		// Click on Action Center
		Click("//div[@class='actions menuitem']//a");
		Thread.sleep(4000);
		
		//Verify Ticket ID and Subject
		String Ticket_xpath="//a[@data-name='"+Approval_TicketID+"']";
		ScrolltoElement(Ticket_xpath);
		
		wait = new WebDriverWait(driverA, Duration.ofSeconds(10, 1));
		WebElement Visible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Ticket_xpath)));
		captureScreenshot(MethodName,"11_My Approval Tickets_list view_Page");
		
		String Expected_Subject_de = "Subject:Approval required for Second Employer - "+LastName+", "+FirstName+"; "+Employeenumber+"; "+StartDate+"; "+EndDate;
		System.out.println(Expected_Subject_de);
		String Expected_Subject= "";
		
		if (Visible.isDisplayed()) {
			LogPASS("The Approval ticket is displayed---"+Approval_TicketID);
			
			String Actual_Subject = GetText(Ticket_xpath+"/div/div");
			
			
			if((Country.contains("Germany")==true) && (Actual_Subject.equalsIgnoreCase(Expected_Subject_de))) {
				
				LogPASS("The Approval ticket Subject is displayed correctly for Ticket ID "+Approval_TicketID +" -- "+Actual_Subject);
			}
			else if((Country.contains("Germany")==false)&& (Actual_Subject.equalsIgnoreCase(Expected_Subject))) {
				
				LogPASS("The Approval ticket Subject is displayed correctly for Ticket ID "+Approval_TicketID +" -- "+Actual_Subject);
				
			}
			else {
				LogFAIL("The Approval ticket Subject is not displayed correctly for Ticket ID "+Approval_TicketID +" -- "+Actual_Subject);
			}
			
			//Click on the Approval Ticket
			Click (Ticket_xpath );
			
		}
		else {
			LogFAIL("The Approval ticket is not displayed---"+Approval_TicketID);
			Approval_Action = "Not Done";
		}
		
		
		
		Thread.sleep(4000);

		
		// Check View more is visible
		try {

			captureScreenshot(MethodName,"12_"+Approval_TicketID+"_Approval Ticket Page");
			Click("//a[@class='Viewmore_link']");
			Thread.sleep(4000);
			captureScreenshot(MethodName, "View More Popup");
			Click("//a[@data-dismiss='modal']");

		} catch (org.openqa.selenium.NoSuchElementException e) {
			// TODO: handle exception
		}
		System.out.println(ApprovalAction);
		// Click Approve/Reject and submit
					if (ApprovalAction.equalsIgnoreCase("Approve")) {

						
						Click("//input[@class='option-input radio approve']");
						captureScreenshot(MethodName, "13_"+Approval_TicketID+"_Approved");
					} else {
						Click("//input[@class='option-input radio reject']");
						
						EnterValue("//textarea[@class='userview Rejected Approved']", "Not Satisfied");
						EnterValue("//textarea[@class='userview Rejected']", "Not Satisified with the request");
						captureScreenshot(MethodName, "13a_"+Approval_TicketID+"_Rejected");
					}

					SafeClick("//input[@type='submit']");
					Thread.sleep(4000);
				
					// Verify the status
					String ApprovalStatus = GetText("//div[@class='home-page-badge request-status-badge desktop-content']//label");
					if (ApprovalStatus.equalsIgnoreCase("Solved") || ApprovalStatus.equalsIgnoreCase("Geschlossen") ) {
						LogPASS("The Status of the Approval Ticket is --" + ApprovalStatus);
						Approval_Action = "Done";
						captureScreenshot(MethodName, "14_"+Approval_TicketID+"_Status after Approved or Rejected");
						Sign_out_of_MyHR();
					}else {
						
						LogFAIL("The Status of the Approval Ticket is --" + ApprovalStatus + " which is not correct");
						captureScreenshot(MethodName, "14a_"+Approval_TicketID+"_Status after Approved or Rejected");
						Approval_Action = "Not Done";
					}
					
		}catch (org.openqa.selenium.NoSuchElementException a) {
			
			Approval_Action = "Not Done";
			// TODO: handle exception
		}
		
		return Approval_Action;
	}
	
	

	// Create a Basic Authentication header
	private static String createBasicAuthHeader(String username, String password) {
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		return BasicScheme.authenticate(credentials, "UTF-8", false).getValue();

	}

	public static String Fetch_value(String Instance, String EmailID, String getFieldvalue) throws Exception {

		// System.out.println(Instance);
		String ProfileValue = "null";
		if (Instance.contains("uat")) {

			url1 = "https://myhr-lindeuat.zendesk.com/api/v2/search.json?&page=1&query=email:";
		}
		if (Instance.contains("test")) {

			url1 = "https://myhr-lindetest.zendesk.com/api/v2/search.json?&page=1&query=email:";
		}
		if (Instance.contains("dev")) {

			url1 = "https://myhr-lindedev.zendesk.com/api/v2/search.json?&page=1&query=email:";
		}

		// System.out.println(url1);
		URL url = new URL(url1);

		// Create a Basic Authentication header
		String authHeader = createBasicAuthHeader("v.dhatchinamoorthy@accenture.com", "welcome");

		// Create an HTTP client
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// Create an HTTP GET request with the URL
			// System.out.println(url + EmailID);
			HttpGet httpGet = new HttpGet(url + EmailID);

			// Add the Basic Authentication header to the request

			httpGet.addHeader("Authorization", authHeader);
			httpGet.addHeader("Accept", "*/*");
			httpGet.addHeader("content-type", "application/merge-patch+json");

			// Execute the HTTP request
			HttpResponse response = httpClient.execute(httpGet);

			// Handle the response as needed (e.g., parse JSON or validate status code)
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("Response Status Code: " + statusCode);

			if (statusCode != 200) {
				throw new RuntimeException(" HTTP error code : " + response.getStatusLine());
			}

			// Read the entire response body
			String responseBody = EntityUtils.toString(response.getEntity());
			//System.out.println(responseBody);

			// Parse JSON
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(responseBody);

			// Extract is_regular_employee or value based on its type
			if (jsonNode.has("results") && jsonNode.get("results").isArray() && jsonNode.get("results").size() > 0) {
				JsonNode userNode = jsonNode.get("results").get(0);
				JsonNode userFieldsNode = userNode.get("user_fields");

				if (userFieldsNode != null && userFieldsNode.isObject()) {
					// Check for boolean value
					if (userFieldsNode.has(getFieldvalue) && userFieldsNode.get(getFieldvalue).isBoolean()) {
						boolean flag = userFieldsNode.get(getFieldvalue).asBoolean();
						//System.out.println(getFieldvalue + flag);

						if (flag == true) {
							ProfileValue = "true";
						} else {
							ProfileValue = "false";
						}

					}
					// Check for string value
					else {

						ProfileValue = userFieldsNode.get(getFieldvalue).asText();
					}
				}
			} else {
				System.out.println("No results found in the JSON.");
			}
		}
		System.out.println("Zendesk ID of the User " + EmailID + " is:------" + ProfileValue);
		return ProfileValue;
	}

	public static String Get_UserRole(String URL, String Username) throws Exception {

		String UserRole = "Null";
		try {
			String fetch_EmployeeFlag = Class1_MyHR_genericMethods.Fetch_value(URL, Username, "is_regular_employee");
			String fetch_HRFlag = Class1_MyHR_genericMethods.Fetch_value(URL, Username, "is_worker_hr");
			String fetch_ManagerFlag = Class1_MyHR_genericMethods.Fetch_value(URL, Username, "is_manager");

			if ((fetch_EmployeeFlag.equalsIgnoreCase("true")) && (fetch_HRFlag.equalsIgnoreCase("true"))
					&& fetch_ManagerFlag.equalsIgnoreCase("true")) {

				UserRole = "HR Manager";
			}

			else if ((fetch_EmployeeFlag.equalsIgnoreCase("true")) && (fetch_HRFlag.equalsIgnoreCase("true"))
					&& fetch_ManagerFlag.equalsIgnoreCase("false")) {

				UserRole = "HR";
			}

			else if ((fetch_EmployeeFlag.equalsIgnoreCase("true")) && (fetch_HRFlag.equalsIgnoreCase("false"))
					&& fetch_ManagerFlag.equalsIgnoreCase("true")) {

				UserRole = "Manager";
			}

			else if ((fetch_EmployeeFlag.equalsIgnoreCase("true")) && (fetch_HRFlag.equalsIgnoreCase("false"))
					&& fetch_ManagerFlag.equalsIgnoreCase("false")) {

				UserRole = "Employee";
			}

			// System.out.println(fetch_EmployeeFlag);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return UserRole;
	}

	public static void close_tabs(int numTabs,Set<String> handles) {
		
		
        
        	
                // Close the last opened tab
                String lastTab = (String) handles.toArray()[numTabs - 1];
                driverA.switchTo().window(lastTab);
                driverA.close();
                System.out.println("Closed one tab.");
     // Switch back to the original tab
        driverA.switchTo().window((String) handles.toArray()[0]);
        System.out.println("Switched back to the original tab.");
		
	}
}
