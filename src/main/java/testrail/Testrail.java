package testrail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import testrail.APIClient;
import testrail.APIException;



public class Testrail{
	
//	public static final int TEST_CASE_PASSED_STATUS = 1;
//	public static final int TEST_CASE_FAILED_STATUS = 5;
//	
//	public static void testrail(String testRunId,String case_id) throws IOException, APIException, Exception {
//        
//		System.out.println("Report status is: " + test.getStatus());
//        Status actualresult = test.getStatus(); 
//        Status expectedresult = Status.PASS;
//        StringBuffer verificationErrors = new StringBuffer();
//        try {
//            Assert.assertEquals(actualresult, expectedresult);
//            Testrail.addResultForTestCase(testRunId,case_id,TEST_CASE_PASSED_STATUS);
//        } catch (Error e) {
//            // Log fail results along with error
//            Testrail.addResultForTestCase(testRunId,case_id,TEST_CASE_FAILED_STATUS);
//            verificationErrors.append(e);
//        }
//    }
	
	
public static void addResultForTestCase( String testRunId,String case_id, int status) throws IOException, Exception, APIException {
	
        String dateName = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
 
        APIClient client = new APIClient("https://sqe.testrail.net/");
        client.setUser("Provide your email id");
        client.setPassword("test rail password");
         Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", "Test Executed - Status updated automatically from Selenium test automation on " + dateName);
       // System.out.println("Test RunId:"+" " +testRunId +" "+"CaseId:"+" " +case_id);
       
      //  JSONObject c = (JSONObject)
                client.sendPost("add_result_for_case/"+testRunId+"/"+case_id+"",data );
    
    }
	}

