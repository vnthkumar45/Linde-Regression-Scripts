package a4_MyHR_and_Agent_GenericFunctions;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import a3_Selenium_GenericMethods.AgentGenericMethods2;

/* this class hosts the actions required to create a case using Exit Management Eform */
public class Class2_Agent_genericMethods2 extends AgentGenericMethods2 {
	static String Ticket_ID;
	static String TicketSubject;
	static boolean TicketFound;

	static String ApprovalWorkflow;
	static String ApprovalTicket_ID;
	static String url1;

	/*
	 * <-----------------------------------------------------Agent Login
	 * Method-----------------------------------------------------------------------
	 * ------>
	 */
	public static void Agent_Login(String URL, String MethodName) throws Exception {

		try {
			IntiateAgentDriver("Edge", URL);
			Agentdriver.findElement(By.id("user_email")).sendKeys(AgentUserID);
			Agentdriver.findElement(By.id("user_password")).sendKeys(AgentPassword);
			AgentcaptureScreenshot(MethodName, "7_Agent_Login Page");
			Agentdriver.findElement(By.id("sign-in-submit-button")).click();
			Thread.sleep(4000);
			Agentdriver.get("https://myhr-lindeuat.zendesk.com" + "/agent");
			AgentcaptureScreenshot(MethodName,"Support_HomePage");
		} catch (org.openqa.selenium.NoSuchElementException a) {
			a.printStackTrace();
			
		}

	}

	/*
	 * <-----------------------------------------------------Verify the Parent
	 * Ticket After Eform
	 * Submission--------------------------------------------------->
	 */

	public static String VerifyParentTicket_AfterIntiation(String Ticket_ID, String HasApproval,
			String Number_of_Approval, String Country, String TypeOfApproval, String MethodName) throws Exception {

		try {

			Search_and_GoTo_Ticket(Ticket_ID, MethodName);
			AgentcaptureScreenshot(MethodName, "8_Agent Parent Ticket Details Page");
			
			String Get_GetApprovalStatus = AgentGetText("//label[text()='Approval Status']//following-sibling::div//span/div");
			ApprovalTicket_ID = AgentGetTextByAttribute("//label[text()='Approval Ticket ID']//following-sibling::input","Value");
			
			AgentcaptureScreenshot(MethodName, "9_Approval Ticket field");
			String Get_TicketStatus = AgentGetText("//button[@data-test-id='submit_button-button']/div/span/strong");

			if (ParentTicket_Created == true) {
				
			

				String Get_RequestorName = AgentGetText("//div[@data-test-id='ticket-system-field-requester-select']");
				String Get_AssigneeName = AgentGetText("//div[@data-test-id='assignee-field-autocomplete-trigger']");
				String Get_FormName = AgentGetText("//div[@data-test-id='ticket-fields-ticket-forms']/div/div");
				String Get_country = AgentGetText(
						"//label[text()='Country*']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_Priority = AgentGetText("//div[@data-test-id='ticket-fields-priority-select']");
				String Get_HRTicketType = AgentGetText(
						"//label[text()='HR Ticket Type*']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_OpsCat1 = AgentGetText(
						"//label[text()='Ops Cat 1 (New)']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_OpsCat2 = AgentGetText(
						"//label[text()='Ops Cat 2 (New)']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_OpsCat3 = AgentGetText(
						"//label[text()='Ops Cat 3 (New)*']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_EscalatedTicket = AgentGetText(
						"//label[text()='Escalated Ticket?*']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
				String Get_ApprovalWorkflow = AgentGetText(
						"//label[text()='Approval workflow']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");

				System.out.println(Get_RequestorName + "-" + Get_AssigneeName + "-" + Get_FormName + "-" + Get_country
						+ "-" + Get_Priority + "-" + Get_HRTicketType + "-" + Get_OpsCat1 + "-" + Get_OpsCat2 + "-"
						+ Get_OpsCat3 + "-" + Get_EscalatedTicket + "-" + Get_ApprovalWorkflow + "-"
						+ Get_GetApprovalStatus + "-" + ApprovalTicket_ID + "-" + Get_TicketStatus);

				
				if (Get_AssigneeName.equalsIgnoreCase(Ticket_Assignee))
					LogPASS("Assignee is correctly populated --->" + Get_AssigneeName);
				else
					LogFAIL("Assignee is not Correctly populated --->" + Get_AssigneeName);

				if (Get_Priority.equalsIgnoreCase(Ticket_Priority))
					LogPASS("Priority is correctly populated --->" + Get_Priority);
				else
					LogFAIL("Priority is not Correctly populated --->" + Get_Priority);

				if (Get_HRTicketType.equalsIgnoreCase(Ticket_HRTicketType))
					LogPASS("HRTicketType is correctly populated --->" + Get_HRTicketType);
				else
					LogFAIL("HRTicketType is not Correctly populated --->" + Get_HRTicketType);

				if (Get_OpsCat1.equalsIgnoreCase(Ticket_OpsCat1))
					LogPASS("OpsCat1 is correctly populated --->" + Get_OpsCat1);
				else
					LogFAIL("OpsCat1 is not Correctly populated --->" + Get_OpsCat1);

				if (Get_OpsCat2.equalsIgnoreCase(Ticket_OpsCat2))
					LogPASS("OpsCat2 is correctly populated --->" + Get_OpsCat2);
				else
					LogFAIL("OpsCat2 is not Correctly populated --->" + Get_OpsCat2);

				if (Get_OpsCat3.equalsIgnoreCase(Ticket_OpsCat3))
					LogPASS("OpsCat3 is correctly populated --->" + Get_OpsCat3);
				else
					LogFAIL("OpsCat3 is not Correctly populated --->" + Get_HRTicketType);

				if (Get_EscalatedTicket.equalsIgnoreCase(Ticket_EscalatedTicket))
					LogPASS("EscalatedTicket is correctly populated --->" + Get_EscalatedTicket);
				else
					LogFAIL("EscalatedTicket is not Correctly populated --->" + Get_EscalatedTicket);

				if (Get_ApprovalWorkflow.equalsIgnoreCase(Number_of_Approval))
					LogPASS("ApprovalWorkflow is correctly populated --->" + Get_ApprovalWorkflow);
				else
					LogFAIL("ApprovalWorkflow is not Correctly populated --->" + Get_ApprovalWorkflow);

				ParentTicket_Created = false;
			}

			if (HasApproval.equalsIgnoreCase("Yes")) {

				if (ApprovalTicket_ID.isEmpty() == false) {

					First_Approval_Triggered_flag = true;

					if (First_Approval_Triggered_flag == true && First_Approval_Completed_flag == false) {
						ApprovalStatus = "Awaiting approver 1 Approval";
						if (Get_GetApprovalStatus.equalsIgnoreCase(ApprovalStatus)) {

							LogPASS("First Approval Ticket is created --->" + ApprovalTicket_ID);
							LogPASS("Parent Ticket Approval Status field is correctly populated --->"
									+ Get_GetApprovalStatus);

						} else {

							LogFAIL("Parent Ticket Approval Status field is not Correctly populated --->"
									+ Get_GetApprovalStatus);

						}
					}

				} else {

					LogFAIL("First Approval Ticket is not created ");
					First_Approval_Triggered_flag = false;

				}

			}

			AgentClick("(//button[@data-test-id='close-button'])[1]");

		} catch (org.openqa.selenium.NoSuchElementException a) {
			// TODO: handle exception
		}

		return ApprovalTicket_ID;

	}

	public static String VerifyParentTicket_AfterApproval(String Ticket_ID, String HasApproval,
			String Number_of_Approval, String Country, String TypeOfApproval, String MethodName) throws Exception {

		try {
			AgentSwitchTab(0);
			Search_and_GoTo_Ticket(Ticket_ID, MethodName);
			
			String Get_GetApprovalStatus = AgentGetText("//label[text()='Approval Status']//following-sibling::div[@data-garden-id='dropdowns.faux_input']//div//span/div");
			ApprovalTicket_ID = AgentGetTextByAttribute(
					"//label[text()='Approval Ticket ID']//following-sibling::input[@data-garden-id='forms.input']",
					"value");
			AgentcaptureScreenshot(MethodName, "9_Approval Ticket field");
			String Get_TicketStatus = AgentGetText("//button[@data-test-id='submit_button-button']/div/span/strong");

			// Verify 2nd Approval Ticket is created and get the Ticket ID
			if (First_Approval_Completed_flag == true && Second_Approval_Triggered_flag == false
					&& Number_of_Approval.equalsIgnoreCase("2 - Approval")) {

				
				if (TypeOfApproval.equalsIgnoreCase("Approve")) {
					
					String Approval_Comment1=Get_Comments_from_ParentTicket(Ticket_ID, 2, MethodName);
					
					String Actual_Comment1 = "Approval 1 "+ApproverName+" approved the ticket";
					
					if(Approval_Comment1.contains(Actual_Comment1)) 
						LogPASS("Internal note added---->"+Actual_Comment1);
					else 
						LogFAIL("Internal note not correctly added---->"+Approval_Comment1);
					

					String ApprovalTicketValue = AgentGetTextByAttribute(
							"//label[text()='Approval Ticket ID']//following-sibling::input[@data-garden-id='forms.input']",
							"value");

					AgentcaptureScreenshot(MethodName, "10_Approval Ticket field");

					if (ApprovalTicketValue.contains(",")) {
						String[] SecondApprovalTicket = ApprovalTicketValue.split(",");
						ApprovalTicket_ID = SecondApprovalTicket[1];

						if (ApprovalTicket_ID.isEmpty() == false) {

							LogPASS("Second Approval Ticket is also created --->" + ApprovalTicket_ID);
							Second_Approval_Triggered_flag = true;
						} else {
							Second_Approval_Triggered_flag = false;
							LogFAIL("Second Approval Ticket is not created ");
						}
					}

					else {
						Second_Approval_Triggered_flag = false;
						LogFAIL("Second Approval Ticket is not created ");
					}

					if ((Get_GetApprovalStatus.equalsIgnoreCase("Awaiting approver 2 Approval"))) {

						ApprovalStatus = Get_GetApprovalStatus;
						LogPASS("Parent Ticket Approval Status field is correctly populated --->"
								+ Get_GetApprovalStatus);
					} else {

						LogFAIL("Parent Ticket ApprovalStatus is not Correctly populated --->" + Get_GetApprovalStatus);

					}

					if (Get_TicketStatus.equalsIgnoreCase("Awating Approval"))
						LogPASS("Parent Ticket Status is correctly populated --->" + Get_TicketStatus);
					else
						LogFAIL("Parent Ticket Status is not Correctly populated --->" + Get_TicketStatus);

				} else {

					if (Get_TicketStatus.equalsIgnoreCase("Solved"))
						LogPASS("Parent Ticket Status is correctly populated --->" + Get_TicketStatus);
					else
						LogFAIL("Parent Ticket Status is not Correctly populated --->" + Get_TicketStatus);

					if ((Get_GetApprovalStatus.equalsIgnoreCase("Approver 1 Rejected"))) {

						ApprovalStatus = Get_GetApprovalStatus;
						LogPASS("Parent Ticket Approval Status field is correctly populated --->"
								+ Get_GetApprovalStatus);
					} else {

						LogFAIL("Parent Ticket ApprovalStatus is not Correctly populated --->" + Get_GetApprovalStatus);

					}
				}

			}

			if (((First_Approval_Completed_flag == true) && (Number_of_Approval.equalsIgnoreCase("1 - Approval")))
					|| (Second_Approval_Completed_flag == true) || (HasApproval.equalsIgnoreCase("No"))) {


				if (TypeOfApproval.equalsIgnoreCase("Approve") || (HasApproval.equalsIgnoreCase("No"))) {

					
					if (Get_TicketStatus.equalsIgnoreCase("Open")) {

						if (TypeOfApproval.equalsIgnoreCase("Approve")) {

							String Approval_Comment2 = Get_Comments_from_ParentTicket(Ticket_ID, 3, MethodName);

							String Actual_Comment2 = "Approval 1 " + ApproverName + " approved the ticket";

							if (Approval_Comment2.contains(Actual_Comment2))
								LogPASS("Internal note added---->" + Actual_Comment2);
							else
								LogFAIL("Internal note not correctly added---->" + Approval_Comment2);

						}
						LogPASS("Parent Ticket Status is correctly populated --->" + Get_TicketStatus);
						
					} else {
						LogFAIL("Parent Ticket Status is not Correctly populated --->" + Get_TicketStatus);
					}

					if (Get_GetApprovalStatus.equalsIgnoreCase("Awaiting Agent check")) {

						LogPASS("Parent Ticket Approval Status field is correctly populated --->"
								+ Get_GetApprovalStatus);

					} else {

						LogFAIL("Parent Ticket Status is not Correctly populated --->" + Get_TicketStatus);
					}
					//
				}
				if (TypeOfApproval.equalsIgnoreCase("Reject")) {
					
					String Approval_Comment2 = Get_Comments_from_ParentTicket(Ticket_ID, 3, MethodName);

					String Actual_Comment2 = "Approval 1 " + ApproverName + " approved the ticket";

					if (Approval_Comment2.contains(Actual_Comment2))
						LogPASS("Internal note added---->" + Actual_Comment2);
					else
						LogFAIL("Internal note not correctly added---->" + Approval_Comment2);

					if (Get_TicketStatus.equalsIgnoreCase("Solved"))
						LogPASS("Parent Ticket Status is correctly populated --->" + Get_TicketStatus);
					else
						LogFAIL("Parent Ticket Status is not Correctly populated --->" + Get_TicketStatus);

					if ((Get_GetApprovalStatus.equalsIgnoreCase("Approver 1 Rejected"))) {

						ApprovalStatus = Get_GetApprovalStatus;
						LogPASS("Parent Ticket Approval Status field is correctly populated --->"
								+ Get_GetApprovalStatus);
					} else {

						LogFAIL("Parent Ticket ApprovalStatus is not Correctly populated --->" + Get_GetApprovalStatus);

					}

				}
			}
			AgentClick("(//button[@data-test-id='close-button'])[1]");
		} catch (org.openqa.selenium.NoSuchElementException a) {
			// TODO: handle exception
		}
		return ApprovalTicket_ID;
	}

	/*
	 * <-----------------------------------------------------Callable Function-Agent
	 * Side Approval Ticket Validation------------------------------------------>
	 */

	public static void Verify_Agent_ApprovalTicket(String URL, String Ticket_ID, String EformName,
			String EmployeeEmailAddress, String MethodName) throws Exception {

		Search_and_GoTo_Ticket(Ticket_ID, MethodName);

		AgentcaptureScreenshot(MethodName, "10_Agent Approval Ticket Page");
		String Get_RequestorName = AgentGetText("//div[@data-test-id='ticket-system-field-requester-select']");
		String Get_AssigneeName = AgentGetText("//div[@data-test-id='assignee-field-autocomplete-trigger']");
		String Get_TicketStatus = AgentGetText("//button[@data-test-id='submit_button-button']/div/span/strong");

		VerifyApprover(URL, EmployeeEmailAddress, EformName, Get_RequestorName);
		Thread.sleep(2000);

		if (Get_AssigneeName.equalsIgnoreCase(Ticket_Assignee)) {
			LogPASS("Assignee for Approval ticket is correctly populated --->" + Get_AssigneeName);
			is_Approver_Details_correct = true;
		} else {
			LogFAIL("Assignee for Approval ticket is not Correctly populated --->" + Get_AssigneeName);
			is_Approver_Details_correct = false;
		}

		if (Get_TicketStatus.equalsIgnoreCase("Awating Approval"))
			LogPASS("Ticket Status for Approval ticket is correctly populated --->" + Get_TicketStatus);
		else
			LogFAIL("Ticket Status for Approval ticket is not Correctly populated --->" + Get_TicketStatus);

		AgentClick("(//button[@data-test-id='close-button'])[1]");
	}

	/*
	 * <-----------------------------------------------------Callable
	 * Function-Verify Approver and set the Approver for Approval
	 * process----------------------->
	 */

	public static void VerifyApprover(String URL, String EmployeeEmailAddress, String EformName,
			String Get_RequestorName) throws Exception {

		String UserRole = Class1_MyHR_genericMethods.Get_UserRole(URL, EmployeeEmailAddress);

		if (EformName.equalsIgnoreCase("Second Employer")) {

			if (UserRole.equalsIgnoreCase("Employee")
					&& (ApprovalStatus.equalsIgnoreCase("Awaiting approver 1 Approval"))) {

				if (Get_RequestorName.equalsIgnoreCase(ManagerName)) {
					ApproverEmailID = Manager_EmailID;
					ApproverName = ManagerName;
					LogPASS("Approver Name for Approval ticket is correctly populated --->" + Get_RequestorName);
				} else
					LogFAIL("Approver Name for Approval ticket is not Correctly populated --->" + Get_RequestorName);
			}

			else if (UserRole.equalsIgnoreCase("Employee")
					&& (ApprovalStatus.equalsIgnoreCase("Awaiting approver 2 Approval"))) {

				if (Get_RequestorName.equalsIgnoreCase(HRName)) {
					System.out.println(HRName + " " + ApproverEmailID);
					ApproverEmailID = HR_EmailID;
					ApproverName = HRName;
					LogPASS("Approver Name for Approval ticket is correctly populated --->" + Get_RequestorName);
				} else
					LogFAIL("Approver Name for Approval ticket is not Correctly populated --->" + Get_RequestorName);
			}

			else if (UserRole.equalsIgnoreCase("Manager")) {

				if (Get_RequestorName.equalsIgnoreCase(HRName)) {
					ApproverEmailID = Manager_EmailID;
					ApproverName = ManagerName;
					LogPASS("Approver Name for Approval ticket is correctly populated --->" + Get_RequestorName);
				} else
					LogFAIL("Approver Name for Approval ticket is not Correctly populated --->" + Get_RequestorName);

			}

			else if (UserRole.equalsIgnoreCase("HR")) {

				if (Get_RequestorName.equalsIgnoreCase(ManagerName)) {
					ApproverEmailID = HR_EmailID;
					LogPASS("Approver Name is correctly populated --->" + Get_RequestorName);
				} else
					LogFAIL("Approver Name is not Correctly populated --->" + Get_RequestorName);

			}

		}

	}

	/*
	 * <-----------------------------------------------------Callable Function-Go to
	 * ticket via Search------------------------------------------>
	 */

	public static void Search_and_GoTo_Ticket(String Ticket_ID, String MenthodName) throws Exception {

		try {

			// AgentClick on search Icon
			AgentClick("//div[@data-test-id='toolbar-search-box']");

			// Type the subject and AgentClick Enter
			AgentEnterValue("//input[@placeholder='search Zendesk Support']", Ticket_ID + Keys.RETURN);
			Thread.sleep(3000);

		} catch (org.openqa.selenium.NoSuchElementException a) {
			// TODO: handle exception
		}

	}

	public static String Get_Comments_from_ParentTicket(String TicketID,int n,String MenthodName) throws Exception {

		String url1 = "https://myhr-lindeuat.zendesk.com/api/v2/tickets/";
		 String plainBody="";
		URL url = new URL(url1);
		// Create a Basic Authentication header
		String authHeader = createBasicAuthHeader("v.dhatchinamoorthy@accenture.com", "welcome");

		// Create an HTTP client
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// Create an HTTP GET request with the URL
			System.out.println(url+TicketID+"/comments");
			HttpGet httpGet = new HttpGet(url+TicketID+"/comments");

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
			System.out.println(responseBody);
			JSONObject jsonObject = new JSONObject(responseBody);
	        JSONArray comments = jsonObject.getJSONArray("comments");
	        
	        JSONObject thirdComment = comments.getJSONObject(n);  // Index 2 for the 3rd comment
            plainBody = thirdComment.getString("plain_body");
            System.out.println("3rd Comment Plain Body: " + plainBody);
            
		}
		return plainBody;
	}
		
		// Create a Basic Authentication header
		private static String createBasicAuthHeader(String username, String password) {
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			return BasicScheme.authenticate(credentials, "UTF-8", false).getValue();

		}

}