package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 *CustomListener - This class is used customizing TestNG reports or logs
 * 
 * @author M1035208
 *
 */
public class CustomListener implements ITestListener {

	@Override		
	public void onFinish(ITestContext Result) 					
	{		

	}		

	@Override		
	public void onStart(ITestContext Result)					
	{		

	}		

	@Override		
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result)					
	{		

	}		

	// When Test case get failed, this method is called.		
	@Override		
	public void onTestFailure(ITestResult result) 					
	{		
		Reporter.log("The name of the testcase failed is :"+result.getName() +"\n");
	}		

	// When Test case get Skipped, this method is called.		
	@Override		
	public void onTestSkipped(ITestResult Result)					
	{		
		System.out.println("The name of the testcase Skipped is :"+Result.getName());					
	}		

	// When Test case get Started, this method is called.		
	@Override		
	public void onTestStart(ITestResult result)					
	{		
		Reporter.log(result.getName()+" test case started");
	}		

	// When Test case get passed, this method is called.		
	@Override		
	public void onTestSuccess(ITestResult result)					
	{		
		Reporter.log("The name of the testcase passed is :"+result.getName() +"\n");

	}		
}
