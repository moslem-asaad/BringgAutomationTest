import com.moslemasaad.DriverFactory;
import com.moslemasaad.pages.WebTablesPage;
import org.openqa.selenium.By;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    protected WebDriver driver;

    @BeforeMethod(groups = {"positive","negative"})
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method method) {
        String methodName = method.getName();
        String[] groups = method.getAnnotation(org.testng.annotations.Test.class).groups();
        System.out.println("STARTING TEST: " + methodName + " | Groups: " + Arrays.toString(groups));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestEnd(ITestResult result) {
        String status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP    -> "SKIPPED";
            default -> "UNKNOWN";
        };
        System.out.println("FINISHED TEST: " + result.getMethod().getMethodName() + " --> " + status);
    }

    @AfterMethod(groups = {"positive","negative"})
    public void tearDown(){
        driver.quit();
    }



}
