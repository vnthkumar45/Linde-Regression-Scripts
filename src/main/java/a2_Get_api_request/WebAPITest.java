package a2_Get_api_request;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import a3_Selenium_GenericMethods.PortalGenericMethods2;

/* this class hosts the actions required to create a case using Exit Management Eform */
public class WebAPITest extends PortalGenericMethods2{

	static String url1 = "https://myhr-lindeuat.zendesk.com/api/v2/search.json?&page=1&query=email:";
	static String Username = "v.dhatchinamoorthy@accenture.com";
	static String Password = "welcome";
	static String numericValue;
	static String Employee_name;
	static String Employee_Email; 
	static String Employee_empno; 
	static String Employee_Country;
	static String Manager_Name; 
	static String Manager_Email; 
	static String HR_Name;
	static String HR_Email;

	// Create a Basic Authentication header
	private static String createBasicAuthHeader(String username, String password) {
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		return BasicScheme.authenticate(credentials, "UTF-8", false).getValue();

	}

	public static String FetchuserDetails(String EmailID) throws IOException, JSONException {

		URL url = new URL(url1);
		// Create a Basic Authentication header
		String authHeader = createBasicAuthHeader(Username, Password);

		// Create an HTTP client
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// Create an HTTP GET request with the URL
			System.out.println(url+EmailID);
			HttpGet httpGet = new HttpGet(url+EmailID);

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
	
			// Initialize Jackson's ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            
            //To get name and email from "results"
                  
            JsonNode resultsArray = jsonNode.get("results");

            // Loop through the array and extract "name" and "email"
            for (JsonNode result : resultsArray) {
            	Employee_name = result.get("name").asText();
            	Employee_Email = result.get("email").asText();
            }
            
            // Get the "user_fields" object
            JsonNode userFields = jsonNode.get("results").get(0).get("user_fields");
			
			// Get Employee details (For whom the request is raised
			Employee_empno = userFields.get("employee_number").asText();
			Employee_Country = userFields.get("user_country").asText();

			// Get Manager name and email ID
			Manager_Name = userFields.get("manager_name").asText();
			Manager_Email = userFields.get("manager_email_id").asText();

			// Get HR Name and Email ID
			HR_Name = userFields.get("hr_name").asText();
			HR_Email = userFields.get("hr_email_id").asText();

			System.out.println(Employee_name+"/"+Employee_Email+"/"+Employee_empno+"/"+Employee_Country+"/"+Manager_Name+"/"+Manager_Email+"/"+HR_Name+"/"+HR_Email);
		
			return Employee_name+"/"+Employee_Email+"/"+Employee_empno+"/"+Employee_Country+"/"+Manager_Name+"/"+Manager_Email+"/"+HR_Name+"/"+HR_Email;
		
		}
		
	}
	
}
