package GenericLibrary;

import java.time.LocalDateTime;

import org.bouncycastle.crypto.ExtendedDigest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ListenerImplementationClass implements ITestListener {
	ExtentTest test;
	ExtentReports report;
	
	public void onTestStart(ITestResult result) 
	{
		
		test=report.createTest(result.getMethod().getMethodName());
	}
		
    public void onTestSuccess(ITestResult result)
    {
    
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
		test.log(Status.PASS, result.getThrowable());
		
    }
		
    public void onTestFailure(ITestResult result)
    {
    	//Step 6 :- Log Fail method, take Screenshot,attach screenshot to extent report,add exception log
    	test.log(Status.FAIL, result.getMethod().getMethodName());
    	test.log(Status.FAIL, result.getThrowable());
		String path=null;
		 try {
			path=new TakeSnapShot().takeScreenshot(BaseClass.sDriver, result.getMethod().getMethodName());
		     } catch (Throwable e) 
		       {
			e.printStackTrace();
		       }
		 test.addScreenCaptureFromPath(path);		
	  }
    
    public void onTestSkipped(ITestResult result) 
    {
		test.log(Status.SKIP, result.getMethod().getMethodName());
		test.log(Status.SKIP, result.getThrowable());
    }
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {	
	        }

	public void onTestFailedWithTimeout(ITestResult result) {
			}
	
     public void onStart(ITestContext context)
     {
    	 
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("./extentReport" + LocalDateTime.now().toString().replace(":", "-")+".html");
    	htmlReporter.config().setReportName("Online Gifts Delivery: Buy/Send Gifts to India, Unique Gift Shop");
    	htmlReporter.config().setDocumentTitle("IGP Application"); 
    	htmlReporter.config().setTheme(Theme.DARK);
    	
        report=new ExtentReports();
        report.attachReporter(htmlReporter);
        report.setSystemInfo("OS", "Windows 10");
        report.setSystemInfo("Environment", "Testing Environment");
        report.setSystemInfo("URL", "https://www.igp.com/");
        report.setSystemInfo("Reporter Name", "Chandana");
        
     }
		
    public void onFinish(ITestContext context)
    {
		report.flush();
	}
	
}
