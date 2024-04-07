package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import tests.BaseClass;

public class ExtentListenerClass implements ITestListener {

	ExtentSparkReporter htmlReporter;
	ExtentReports reports;
	ExtentTest test;

	public void configureReport() {
		ReadConfig readConfig = new ReadConfig();
		String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		String reportName = "DataDrivenFrameworkReport" + timestamp + ".html";
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\Reports\\" + reportName);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		// add system information/environment info to reports
		reports.setSystemInfo("Machine:", "testpc1");
		reports.setSystemInfo("OS", "windows 11");
		reports.setSystemInfo("browser:", readConfig.getBrowser());
		reports.setSystemInfo("user name:", "Sumil");

		// configuration to change look and feel of report
		htmlReporter.config().setDocumentTitle("Extent Listener Report Demo");
		htmlReporter.config().setReportName("This is my First Report");
		htmlReporter.config().setTheme(Theme.DARK);

	}

	public void onStart(ITestContext context) {
		configureReport();
		System.out.println("On Start method invoked....");
	}

	public void onFinish(ITestContext context) {
		System.out.println("On Finished method invoked....");
		reports.flush();
	}

	public void onTestFailure(ITestResult Result) {
		System.out.println("Name of test method failed:" + Result.getName());
		test = reports.createTest(Result.getName());// create entry in html report
		test.log(Status.FAIL,MarkupHelper.createLabel("Name of the failed test case is: " + Result.getName(), ExtentColor.RED));
		BaseClass bs =new BaseClass();
		try {
			bs.captureScreenShot(Result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// When Test case get Skipped, this method is called.

	public void onTestSkipped(ITestResult Result) {
		System.out.println("Name of test method skipped:" + Result.getName());

		test = reports.createTest(Result.getName());
		test.log(Status.SKIP,
				MarkupHelper.createLabel("Name of the skip test case is: " + Result.getName(), ExtentColor.YELLOW));
	}

	public void onTestStart(ITestResult Result) {
		System.out.println("Name of test method started:" + Result.getName());

	}

	public void onTestSuccess(ITestResult Result) {
		System.out.println("Name of test method sucessfully executed:" + Result.getName());
		test = reports.createTest(Result.getName());
		test.log(Status.PASS,
				MarkupHelper.createLabel("Name of the passed test case is: " + Result.getName(), ExtentColor.GREEN));
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result)					
	{		

	}	

}
