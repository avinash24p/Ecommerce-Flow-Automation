package com.puma.TestBase;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class TestContext implements ITestListener{

		
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("PASSED-->" +result.getMethod().getMethodName());
		Log.endTestCase(result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		Log.startTestCase(result.getMethod().getMethodName());
		System.out.println("\nSTARTING-->" +result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("\033[1mSKIPPED-->" +result.getMethod().getMethodName()+"\033[0m");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("\nFAIL-->" +result.getMethod().getMethodName());	
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}
	
	@Override
	public void onStart(ITestContext context) {
		System.out.println("\nOutput Directory is --> "+context.getOutputDirectory());
	}
	
	@Override
	public void onFinish(ITestContext context) {
		System.out.println(context.getFailedTests());
	}
	
	
	
}
