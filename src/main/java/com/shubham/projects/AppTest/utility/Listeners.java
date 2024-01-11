package com.shubham.projects.AppTest.utility;

import java.io.FileWriter;
import java.io.IOException;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
import kong.unirest.Unirest;

public class Listeners extends AppiumUtils implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReporterObject();
	AppiumDriver driver;
	
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		 test= extent.createTest(result.getMethod().getMethodName());
//		 try {
//			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
//						.get(result.getInstance());
//		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS, "Test Passed");
		
//		Unirest.post("http://localhost:4723/setTestInfo")
//        .header("Content-Type", "application/json")
//        .body("{\"sessionId\":\"" + driver.getSessionId() + "\",\"testName\":\"" + result.getName() + "\",\"testStatus\":\"PASSED\"}")
//        .asJson();
}



	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		//screenshot code
		test.fail(result.getThrowable());
		
		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try {
			test.addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(),driver), result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Unirest.post("http://localhost:4723/setTestInfo")
//        .header("Content-Type", "application/json")
//        .body("{\"sessionId\":\"" + driver.getSessionId() + "\",\"testName\":\"" + result.getName() + "\",\"testStatus\":\"FAILED\",\"error\":\"" + result.getThrowable().getMessage() + "\"}")
//        .asJson();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
//		Unirest.post("http://localhost:4723/setTestInfo")
//        .header("Content-Type", "application/json")
//        .body("{\"sessionId\":\"" + driver.getSessionId() + "\",\"testName\":\"" + result.getName() + "\",\"testStatus\":\"SKIPPED\"}")
//        .asJson();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
		
//	    try {
//	        String reportData = getReport();
//	        createReportFile(reportData, "report");
//	        deleteReportData();
//	    } catch (IOException | InterruptedException e) {
//	        e.printStackTrace();
//	    }
	}
	
//	 public String getReport() throws IOException, InterruptedException {
//	        String url = "http://localhost:4723/getReport";
//	        String s = Unirest.get(url).asString().getBody();
//	        return s;
//	    }
//
//	    public void deleteReportData() throws IOException, InterruptedException {
//	        String url = "http://localhost:4723/deleteReportData";
//	         Unirest.delete(url).asEmpty();
//	    }
//
//	    public void createReportFile(String data, String fileName) throws IOException {
//	        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"/reports"+"/" + fileName + ".html");
//	        fileWriter.write(data);
//	        fileWriter.close();
//	    }
//
	
}
