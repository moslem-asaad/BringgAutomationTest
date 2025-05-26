import com.moslemasaad.pages.RegistrationFormModal;
import com.moslemasaad.pages.WebTablesPage;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.IOException;

@Listeners({ AllureTestNg.class })
public class WebTablesTest extends TestBase {
    private WebTablesPage webTablesPage;

    private static final String firstName = "Moslem";
    private static final String lastName = "Asaad";
    private static final String userEmail = "moslem.asaad@example.com";
    private static final String age = "25";
    private static final String salary = "10000";
    private static final String department = "Engineering";


    @BeforeMethod(groups = {"positive","negative"})
    public void setUp(){
        super.setUp();
        String baseURL = "https://demoqa.com/webtables";
        driver.get(baseURL);
        if (driver.getPageSource().contains("502 Bad Gateway")) {
            throw new SkipException("502 Bad Gateway encountered. Skipping test.");
        }
        webTablesPage = new WebTablesPage(driver).get();
    }

    @Test(priority = 1,groups = {"positive"})
    public void verifyTitle() {
        webTablesPage.isLoaded();
    }

    @Test(priority = 2,groups = {"positive"})
    public void deleteRecord(){
        String firstRecordEmail = webTablesPage.getRecordEmail(0);
        int emptyRecordsCountBefore = webTablesPage.countEmptyRecords();
        webTablesPage.deleteGeneralRecord(0);
        int emptyRecordsCountAfter = webTablesPage.countEmptyRecords();
        Assert.assertTrue(emptyRecordsCountAfter>emptyRecordsCountBefore, "Record was not deleted successfully");
        Assert.assertNotEquals(firstRecordEmail,webTablesPage.getRecordEmail(0),"Record was not deleted successfully");
    }

    @Test(dependsOnMethods = "deleteRecord",groups = {"positive"})
    public void deleteRecordOnlyOnce(){
        webTablesPage.deleteGeneralRecord(0);
        Assert.assertThrows(Exception.class,() -> {webTablesPage.deleteGeneralRecord(0);});
    }

    @Test(priority = 3,groups = {"positive"})
    public void deleteAllRecords(){
        int i = 0;
        while (webTablesPage.getNonEmptyRecords() > 0) {
            webTablesPage.deleteGeneralRecord(i);
            i++;
        }
        Assert.assertEquals(webTablesPage.countAllRecords(), webTablesPage.countEmptyRecords(),
                "All records should be deleted.");
    }

    @Test(priority = 4,groups = {"positive"})
    public void filterRecordsByName(){
        webTablesPage.filterRecords("Cie");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),1);
    }

    @Test(priority = 4,groups = {"positive"})
    public void filterRecordsByEmail(){
        webTablesPage.filterRecords("alden@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-1,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),1);
    }

    @Test(priority = 4,groups = {"positive"})
    public void filterRecordsByEmail2(){
        webTablesPage.filterRecords("@example.com");
        Assert.assertEquals(webTablesPage.countAllRecords()-3,webTablesPage.countEmptyRecords());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),3);
    }

    @Test(priority = 5,groups = {"positive"})
    public void filterRecordByName_DeleteRecord(){
        webTablesPage.filterRecords("Cie");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5,groups = {"positive"})
    public void filterRecordByEmail_DeleteRecord(){
        webTablesPage.filterRecords("alden@example.com");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
        Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(priority = 5,groups = {"positive"})
    public void filterRecordByEmail2_DeleteRecords(){
        webTablesPage.filterRecords("@example.com");
        int records = webTablesPage.getNonEmptyRecords();
        for (int i = 0;i< records ;i++){
            webTablesPage.deleteFirstVisibleFilteredRecord();
        }
       Assert.assertEquals(webTablesPage.countAllRecords(),webTablesPage.countEmptyRecords());
    }

    @Test(groups = {"positive"})
    public void addEmployeeSuccess() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"negative"})
    public void addEmployeeMissDepartmentField() throws Exception {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillFirstName(firstName);
        formModal.clearAndFillLastName(lastName);
        formModal.clearAndFillUserEmail(userEmail);
        formModal.clearAndFillAge(age);
        formModal.clearAndFillSalary(salary);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"negative"})
    public void addEmployeeMissSalaryField() throws Exception {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());

        formModal.clearAndFillFirstName(firstName);
        formModal.clearAndFillLastName(lastName);
        formModal.clearAndFillUserEmail(userEmail);
        formModal.clearAndFillAge(age);
        formModal.clearAndFillDepartment(department);

        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"negative"})
    public void addEmployeeMissAllFields() {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"positive"})
    public void addEmployeeSuccess_FilterByName() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
        webTablesPage.filterRecords(firstName);
        Assert.assertEquals(1,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"positive"})
    public void addEmployeeSuccess_FilterByNameAndDelete() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records+1,webTablesPage.getNonEmptyRecords());
        webTablesPage.filterRecords(firstName);
        Assert.assertEquals(1,webTablesPage.getNonEmptyRecords());
        webTablesPage.deleteFirstVisibleFilteredRecord();
        Assert.assertEquals(0,webTablesPage.getNonEmptyRecords());
    }

    @Test(groups = {"negative"})
    public void addEmployeeEmptyFirstName() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        String firstName = "";
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][] {
                {"", false},
                {"invalid@", false},
                {"test@.com", false}
        };
    }

    @Test(dataProvider = "invalidEmails",groups = {"negative"})
    public void addEmployeeInvalidEmailTest(String email, boolean shouldAdd) throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        formModal.clearAndFillForm(firstName,lastName, email, age, salary, department);
        webTablesPage = formModal.submitForm();
        if (shouldAdd) {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records+1);
        } else {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
        }
    }

    @DataProvider(name = "invalidAges")
    public Object[][] invalidAges() {
        return new Object[][] {
                {"ab", false},
                {"-1", false},
        };
    }

    @Test(dataProvider = "invalidAges",groups = {"negative"})
    public void addEmployeeInvalidAgeTest(String age, boolean shouldAdd) throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        if (shouldAdd) {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records+1);
        } else {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
        }
    }

    @DataProvider(name = "invalidSalary")
    public Object[][] invalidSalary() {
        return new Object[][] {
                {"abc", false},
                {"-10000", false},
                {"10000.5", false},
        };
    }

    @Test(dataProvider = "invalidSalary",groups = {"negative"})
    public void addEmployeeInvalidSalaryTest(String salary, boolean shouldAdd) throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.submitForm();
        if (shouldAdd) {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records+1);
        } else {
            Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
        }
    }


    @Test(groups = {"positive"})
    public void addEmployeeCloseForm() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());
        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);
        webTablesPage = formModal.closeForm();
        Assert.assertTrue(webTablesPage.isInWebTables());
        Assert.assertEquals(records,webTablesPage.getNonEmptyRecords());
    }

    @Test(priority = 6, groups = {"positive"})
    public void addEmployeeFillFormCloseForm_submitForm() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());


        formModal.clearAndFillForm(firstName,lastName,userEmail,age,salary,department);

        webTablesPage = formModal.closeForm();
        formModal = webTablesPage.addEmployee();
        webTablesPage = formModal.submitForm();

        Assert.assertTrue(webTablesPage.isInWebTables());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records+1);
    }

    @Test(priority = 6, groups = {"positive"})
    public void addEmployeeFillName_CloseForm_FillFields_SubmitForm() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.addEmployee();
        Assert.assertTrue(formModal.isInForm());

        formModal.clearAndFillFirstName(firstName);

        webTablesPage = formModal.closeForm();

        formModal = webTablesPage.addEmployee();

        formModal.clearAndFillLastName(lastName);
        formModal.clearAndFillUserEmail(userEmail);
        formModal.clearAndFillAge(age);
        formModal.clearAndFillSalary(salary);
        formModal.clearAndFillDepartment(department);

        webTablesPage = formModal.submitForm();
        Assert.assertTrue(webTablesPage.isInWebTables());
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records+1);
    }


    @Test(groups = {"positive"})
    public void editFirstEmployee_Salary() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.editGeneralRecord(0);
        String salary = "20000";
        formModal.clearAndFillSalary(salary);
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(webTablesPage.getRecordSalary(webTablesPage.getARecord(0)),salary);
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
    }

    @Test(dataProvider = "invalidSalary",groups = {"negative"})
    public void editFirstEmployee_InvalidSalary(String salary, boolean shouldAdd) throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.editGeneralRecord(0);
        formModal.clearAndFillSalary(salary);
        webTablesPage = formModal.submitForm();
        if (!shouldAdd) {
            Assert.assertNotEquals(webTablesPage.getRecordSalary(webTablesPage.getARecord(0)), salary);
            Assert.assertThrows(Exception.class, () -> webTablesPage.addEmployee());
        }else{
            Assert.assertEquals(webTablesPage.getRecordSalary(webTablesPage.getARecord(0)), salary);
        }
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(), records);
    }


    @Test(groups = {"positive"})
    public void editFirstEmployee_FirstName() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.editGeneralRecord(0);
        String firstName = "Moslem";
        formModal.clearAndFillFirstName("Moslem");
        webTablesPage = formModal.submitForm();
        Assert.assertEquals(webTablesPage.getRecordFirstName(webTablesPage.getARecord(0)),firstName);
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
    }

    @Test(groups = {"negative"})
    public void editFirstEmployee_FirstName_Empty() throws IOException {
        int records = webTablesPage.getNonEmptyRecords();
        RegistrationFormModal formModal = webTablesPage.editGeneralRecord(0);
        String firstName = "";
        formModal.clearAndFillFirstName(firstName);
        webTablesPage = formModal.submitForm();
        Assert.assertNotEquals(webTablesPage.getRecordFirstName(webTablesPage.getARecord(0)),firstName);
        Assert.assertEquals(webTablesPage.getNonEmptyRecords(),records);
    }




}

