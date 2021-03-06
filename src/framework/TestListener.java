package framework;

import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListener implements ITestListener {
    private static Logger _logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        TestSetup.methodInfo = TestSetup.testInfo.createNode(getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TestSetup.methodInfo.pass("Test Case : " + getTestName(result) + " is passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        TestSetup.methodInfo.log(Status.FAIL, "Test Case \"" + getTestName(result) + "\"" + " is failed");
        TestSetup.methodInfo.log(Status.FAIL, "Test failure : " + result.getThrowable().getMessage());
        TestSetup.methodInfo.log(Status.FAIL, "Test failure : " + TestSetup.lastException.getMessage());
        String screenshotPath = TestSetup.getScreenshot(TestSetup.webDriver, result.getName());
//        TestSetup.methodInfo.log(Status.FAIL, "<a href='" + screenshotPath + "'>" + "<img src='" + screenshotPath + "' width='75%'></a>");
        try {
            TestSetup.methodInfo.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestSetup.methodInfo = TestSetup.testInfo.createNode(getTestName(result));
        TestSetup.methodInfo.log(Status.SKIP, "Test Case : " + getTestName(result) + " is skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //_logger.info("onTestFailedButWithinSuccessPercentage");
    }

    @Override
    public void onStart(ITestContext context) {
        TestSetup.testInfo = TestSetup.reports.createTest(context.getName()).assignCategory(context.getName());
        TestSetup.testName = context.getName();
        _logger.info("Test Case : {} ", context.getName());
        _logger.info("\n");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (context.getFailedConfigurations().size() > 0 || context.getFailedTests().size() > 0)
            _logger.info("Status : Failed");
        else
            _logger.info("Status : Pass");
    }

    private String getTestName(ITestResult result) {
        String testCaseDescription = result.getMethod().getDescription();
        return (testCaseDescription == null || testCaseDescription.isEmpty()) ? result.getName() : testCaseDescription;
    }

}