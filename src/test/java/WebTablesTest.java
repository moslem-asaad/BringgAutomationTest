import com.moslemasaad.DriverFactory;
import com.moslemasaad.pages.WebTablesPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebTablesTest extends TestBase {
    private WebTablesPage webTablesPage;


    @BeforeMethod
    public void setUp(){
        super.setUp();
        String baseURL = "https://demoqa.com/webtables";
        driver.get(baseURL);
        webTablesPage = new WebTablesPage(driver).get();
    }

    @Test(priority = 1)
    public void verifyTitle() {
        webTablesPage.isLoaded();
    }

    @Test(priority = 2)
    public void deleteRecord(){
        String firstRecordEmail = webTablesPage.getRecordEmail(0);
        int emptyRecordsCountBefore = webTablesPage.countEmptyRecords();
        webTablesPage.deleteGeneralRecord(0);
        int emptyRecordsCountAfter = webTablesPage.countEmptyRecords();
        Assert.assertTrue(emptyRecordsCountAfter>emptyRecordsCountBefore, "Record was not deleted successfully");
        Assert.assertNotEquals(firstRecordEmail,webTablesPage.getRecordEmail(0),"Record was not deleted successfully");
    }

    @Test(dependsOnMethods = "deleteRecord")
    public void deleteRecordOnlyOnce(){
        webTablesPage.deleteGeneralRecord(0);
        Assert.assertThrows(Exception.class,() -> {webTablesPage.deleteGeneralRecord(0);});
    }

    @Test(priority = 3)
    public void deleteAllRecords(){
        int i = 0;
        while (webTablesPage.getNonEmptyRecords() > 0) {
            webTablesPage.deleteGeneralRecord(i);
            i++;
        }
        Assert.assertEquals(webTablesPage.countAllRecords(), webTablesPage.countEmptyRecords(),
                "All records should be deleted.");
    }

}

