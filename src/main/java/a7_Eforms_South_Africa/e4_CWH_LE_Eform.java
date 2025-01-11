package a7_Eforms_South_Africa;

import a3_Selenium_GenericMethods.AgentGenericMethods2;
import a4_MyHR_and_Agent_GenericFunctions.Class1_MyHR_genericMethods;
import a4_MyHR_and_Agent_GenericFunctions.Class2_Agent_genericMethods;

/* this class hosts the actions required to create a case using Exit Management Eform */
public class e4_CWH_LE_Eform extends AgentGenericMethods2 {
	
	public static void CWH_LE_Eform_Verification_and_Submission(String URL, String ScreenshotPath,
			String EformPath, String MethodName) throws Exception {

		String TypeOfApproval;
		boolean Agentlogged_session = false;
		ReadExcel(EformPath);
		try {
			String Login_sheet_name = "DE_CWH_LE";
			sheet = workbook.getSheet(Login_sheet_name);
			int totalRows = sheet.getLastRowNum();

			for (int i = 1; i <= totalRows; i++) {// declaring while loop to iterate in sheet untill we have rows

				String Scenario = GetdataFromSheet(Login_sheet_name, "Scenario name", i);
				String EformName = GetdataFromSheet(Login_sheet_name, "Eform Name", i);
				String Country = GetdataFromSheet(Login_sheet_name, "Country", i);
				String UserID = GetdataFromSheet(Login_sheet_name, "User ID", i);
				String Password = GetdataFromSheet(Login_sheet_name, "Password", i);
				String RequestRaisedFor = GetdataFromSheet(Login_sheet_name, "Request Raised for", i);
				String EmployeeEmailAddress = GetdataFromSheet(Login_sheet_name,
						"Employee Email Address (To whom the request has been raised)", i);
				String HasApproval = GetdataFromSheet(Login_sheet_name, "Has Approval", i);
				String Number_Of_Approval = GetdataFromSheet(Login_sheet_name, "Number of Approval", i);
				TypeOfApproval = GetdataFromSheet(Login_sheet_name, "Approval1 Action", i);
				String TypeOfApproval2 = GetdataFromSheet(Login_sheet_name, "Approval2 Action", i);

				CreateTest(Scenario);

				// if the Return value is empty
				if (EformName.isEmpty()) {
					break;
				}

				else {

					
					// Get Required user details
					Class1_MyHR_genericMethods.Get_and_set_UserDetails(URL, EmployeeEmailAddress);
					
					
					// MyHR - Login as Specified User
					Class1_MyHR_genericMethods.Eform_Login(UserID, Password, MethodName);
					Thread.sleep(5000);

					// MyHR - Go To Second Employer Eform
					Class1_MyHR_genericMethods.GoToEform(EformName, Country,MethodName);
					Thread.sleep(6000);

					//MyHR - Submit Second Employer Eform
					Class1_MyHR_genericMethods.Populate_fields_and_Submit_Eform(Scenario,"CWH_LE", RequestRaisedFor, MethodName);

					//MyHR - Request Page Verification
					String TicketID = Class1_MyHR_genericMethods.My_Request_details_Page_Verification(EformName, HasApproval, Country, MethodName);
					
					//MyHR - Sign out off Employee Portal
					Class1_MyHR_genericMethods.Sign_out_of_MyHR();
				
					if(Agentlogged_session =false){
						//Agent Login
						Class2_Agent_genericMethods.Agent_Login(URL, MethodName);
						Agentlogged_session=true;
					}
					
					System.out.println(ParentTicket_Created);
					String ApprovalTicket_ID = "null";
					String Approval_Action = "Not Done";
					String Approval_Action2= "Not Done";
					
					if(ParentTicket_Created==true) {
					//Agent - Parent Ticket Verification and get Approval Ticket
					ApprovalTicket_ID= Class2_Agent_genericMethods.VerifyParentTicket_AfterIntiation(TicketID, HasApproval, Number_Of_Approval, Country, TypeOfApproval,MethodName);
					System.out.println(ApprovalTicket_ID);
					}
					
					System.out.println("First Approval triggered flag =" +Second_Approval_Triggered_flag);
					if((First_Approval_Triggered_flag==true)&&((Number_Of_Approval.equalsIgnoreCase("2 - Approval"))|| (Number_Of_Approval.equalsIgnoreCase("1 - Approval")))) {
					//Agent -Verify the Approval Ticket ID 1
						Class2_Agent_genericMethods.Verify_Agent_ApprovalTicket(URL, ApprovalTicket_ID, EformName, EmployeeEmailAddress, MethodName);
					}
					
					System.out.println(is_Approver_Details_correct);
					
					if(is_Approver_Details_correct==true) {
						
					//MyHR -Login as Approver in MyHR and Verify the Approval Ticket and then approve or reject the first approval
					Approval_Action=Class1_MyHR_genericMethods.Approver_Action_on_ApprovalTicket(URL, ApproverEmailID, Password, ApprovalTicket_ID, Country, TypeOfApproval, MethodName);
					is_Approver_Details_correct=false;
					}
					
					if(Approval_Action.equalsIgnoreCase( "Done")) {
						
						First_Approval_Completed_flag = true;
						String ApprovalTicket_ID2= Class2_Agent_genericMethods.VerifyParentTicket_AfterApproval(TicketID, HasApproval, Number_Of_Approval, Country,TypeOfApproval, MethodName);
						//System.out.println(ApprovalTicket_ID2);
						Approval_Action = "Not Done";
						
						System.out.println("Second Approval triggered flag =" +Second_Approval_Triggered_flag);
						if(Second_Approval_Triggered_flag==true) {
							//Agent -Verify the Approval Ticket ID2
							Class2_Agent_genericMethods.Verify_Agent_ApprovalTicket(URL, ApprovalTicket_ID2, EformName, EmployeeEmailAddress, MethodName);
									
							if(is_Approver_Details_correct==true) {
								
							
							//MyHR -Login as Approver in MyHR and Verify the Approval Ticket and then approve or reject the 2nd Approval
							Approval_Action2=Class1_MyHR_genericMethods.Approver_Action_on_ApprovalTicket(URL, ApproverEmailID, Password, ApprovalTicket_ID2, Country, TypeOfApproval2, MethodName);
							
							if(Approval_Action2.equalsIgnoreCase("Done")) {
								Second_Approval_Completed_flag = true;
								Class2_Agent_genericMethods.VerifyParentTicket_AfterApproval(TicketID, HasApproval, Number_Of_Approval, Country,TypeOfApproval, MethodName);
							
						}
					
					}
					}
					
					}	
					
					
				}

			}

		} catch (Exception F) {
			F.printStackTrace();
		}
		CloseReport();
	}


}
