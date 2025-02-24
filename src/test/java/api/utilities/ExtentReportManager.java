package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {
    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test; // Thread-safe for parallel execution
    
    String repName;

    public void onStart(ITestContext context) {
    	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp repName="Test-Report-"+timeStamp+".html";

    	sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);//specify location of the report

    	sparkReporter.config().setDocumentTitle("RestAssured AutomationProject"); // Title of report sparkReporter.config().setReportName("Pet Store Users API"); // name of the report

    	sparkReporter.config().setTheme (Theme. DARK);

    	extent=new ExtentReports();

    	extent.attachReporter (sparkReporter);

    	extent.setSystemInfo("Application", "Pest Store Users API");

    	extent.setSystemInfo("Operating System", System.getProperty("os.name"));

    	extent.setSystemInfo("User Name", System.getProperty("user.name"));

    	extent.setSystemInfo("Environemnt", "QA");

    	extent.setSystemInfo("user", "pavan");
    }


    public void onTestSuccess(ITestResult result) {
    	test=extent.createTest(result.getName());

    	test.assignCategory(result.getMethod().getGroups());

    	test.createNode(result.getName());

    	test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
    	test = extent.createTest(result.getName());

 
    	test.createNode(result.getName());
    	test.assignCategory(result.getMethod().getGroups());

    	test.log(Status.FAIL, "Test Failed");

    	test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result) {
    	test  = extent.createTest(result.getName());

    	test.createNode (result.getName());

    	test.assignCategory (result.getMethod().getGroups());

    	test.log(Status.SKIP, "Test Skipped");

    	test.log(Status.SKIP, result.getThrowable().getMessage());    }

    public void onFinish(ITestContext context) {
        extent.flush(); // Ensure all logs are written
    }

}