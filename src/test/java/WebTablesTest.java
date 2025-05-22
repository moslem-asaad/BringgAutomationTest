import com.moslemasaad.DriverFactory;
import com.moslemasaad.pages.WebTablesPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

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

    @Test(priority = 4)
    public void filterRecordsByName(){
        List<WebElement> elements =  webTablesPage.filterRecords("Cie");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(elements.size(),1);
    }

    @Test(priority = 4)
    public void filterRecordsByEmail(){
        List<WebElement> elements =  webTablesPage.filterRecords("alden@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(elements.size(),1);
    }

    @Test(priority = 4)
    public void filterRecordsByEmail2(){
        List<WebElement> elements =  webTablesPage.filterRecords("@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-3,webTablesPage.countEmptyRecords());
        Assert.assertEquals(elements.size(),3);
    }

    @Test(priority = 5)
    public void filterRecordByName_DeleteRecord(){
        List<WebElement> elements = webTablesPage.filterRecords("Cie");
        webTablesPage.deleteRecordFromList(elements,0);
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5)
    public void filterRecordByEmail_DeleteRecord(){
        List<WebElement> elements = webTablesPage.filterRecords("alden@example.com");
        webTablesPage.deleteRecordFromList(elements,0);
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5)
    public void filterRecordByEmail2_DeleteRecords(){
        List<WebElement> elements = webTablesPage.filterRecords("@example.com");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteRecordFromList(elements,i);
        }
       Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }




}

