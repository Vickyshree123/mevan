package CoverFoxUtility;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import CoverFoxUtility.Utility;
import coverFoxBase.Base;

public class Listner extends Base implements ITestListener {

//ublic class Listener extends Base implements ITestListener{
	
	@Override
	public void onTestStart(ITestResult result)
	{
		Reporter.log(" TC "+ result.getName()+  "execution started",true);
	}
	@Override
	public void onTestFailure(ITestResult result) {
	Reporter.log("TC " + result.getName() + " is failed", true);
	try {
	Utility.TakeScreenShot(driver, result.getName());
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
	}
}

