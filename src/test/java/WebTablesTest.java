import com.moslemasaad.DriverFactory;
import com.moslemasaad.pages.RegistrationFormModal;
import com.moslemasaad.pages.WebTablesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
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
        webTablesPage.filterRecords("Cie");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),1);
    }

    @Test(priority = 4)
    public void filterRecordsByEmail(){
        webTablesPage.filterRecords("alden@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),1);
    }

    @Test(priority = 4)
    public void filterRecordsByEmail2(){
        webTablesPage.filterRecords("@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-3,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),3);
    }

    @Test(priority = 5)
    public void filterRecordByName_DeleteRecord(){
        webTablesPage.filterRecords("Cie");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5)
    public void filterRecordByEmail_DeleteRecord(){
        webTablesPage.filterRecords("alden@example.com");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5)
    public void filterRecordByEmail2_DeleteRecords(){
        webTablesPage.filterRecords("@example.com");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
       Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test
    public void addEmployeeSuccess() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "Moslem";
        String lastName = "Asaad";
        String userEmail = "moslem.asaad@example.com";
        String age = "25";
        String salary = "10000";
        String department = "Engineering";
        formModal.fillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
    }

    @Test
    public void addEmployeeMissDepartmentField() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "Moslem";
        String lastName = "Asaad";
        String userEmail = "moslem.asaad@example.com";
        String age = "25";
        String salary = "10000";
        formModal.fillFirstName(firstName);
        formModal.fillLastName(lastName);
        formModal.fillUserEmail(userEmail);
        formModal.fillAge(age);
        formModal.fillSalary(salary);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test
    public void addEmployeeMissSalaryField() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "Moslem";
        String lastName = "Asaad";
        String userEmail = "moslem.asaad@example.com";
        String age = "25";
        String department = "Engineering";
        formModal.fillFirstName(firstName);
        formModal.fillLastName(lastName);
        formModal.fillUserEmail(userEmail);
        formModal.fillAge(age);
        formModal.fillDepartment(department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test
    public void addEmployeeMissAllFields() {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test
    public void addEmployeeSuccess_FilterByName() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "Moslem";
        String lastName = "Asaad";
        String userEmail = "moslem.asaad@example.com";
        String age = "25";
        String salary = "10000";
        String department = "Engineering";
        formModal.fillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
        webTablesPage.filterRecords(firstName);
        Assert.assertEquals(1,webTablesPage.getNonEmptyRecords());
    }

    @Test
    public void addEmployeeSuccess_FilterByNameAndDelete() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "Moslem";
        String lastName = "Asaad";
        String userEmail = "moslem.asaad@example.com";
        String age = "25";
        String salary = "10000";
        String department = "Engineering";
        formModal.fillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
        webTablesPage.filterRecords(firstName);
        Assert.assertEquals(1,webTablesPage.getNonEmptyRecords());
        webTablesPage.deleteFirstVisibleFilteredRecord();
        Assert.assertEquals(0,webTablesPage.getNonEmptyRecords());
    }




}

