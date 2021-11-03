package DataDem;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class DataProviderListener extends Base implements ITestListener {
    @Attachment
    public byte[] saveFailureScreenShot(WebDriver d) {
        return ((TakesScreenshot)d).getScreenshotAs(OutputType.BYTES);
    }
    @Attachment(value = "{0}",type = "text/plain")
    public static String saveTextLog(String message){
        return message;
    }

    @Override
    public void onTestFailure(ITestResult result){
        System.out.println("Test case failed");
        saveFailureScreenShot(getWebDriver());
        saveTextLog(result.getMethod().getConstructorOrMethod().getName());
    }
}



