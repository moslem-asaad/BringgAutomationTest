package com.moslemasaad.pages;

import com.moslemasaad.FieldsValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class RegistrationFormModal {
    private WebDriver driver;

    @FindBy(id = "registration-form-modal")
    private WebElement formTile;

    @FindBy(id = "firstName")
    private WebElement firstNameElement;
    @FindBy(id = "lastName")
    private WebElement lastNameElement;
    @FindBy(id = "userEmail")
    private WebElement userEmailElement;
    @FindBy(id = "age")
    private WebElement ageElement;
    @FindBy(id = "salary")
    private WebElement salaryElement;
    @FindBy(id = "department")
    private WebElement departmentElement;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(css = ".modal-header .close")
    private WebElement closeButton;

    private boolean isEdit;

    public RegistrationFormModal(WebDriver driver, boolean isEdit) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(formTile));
        this.isEdit = isEdit;
    }

    public boolean isInForm() {
        return formTile.getText().equals("Registration Form");
    }

    public void clearAndFillFirstName(String firstName) throws IOException {
        validFirstName();
        firstNameElement.clear();
        firstNameElement.sendKeys(firstName);

    }

    public void clearAndFillLastName(String lastName) throws IOException {
        validLastName();
        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);

    }

    public void clearAndFillUserEmail(String userEmail) throws IOException {
        validUserEmail();
        userEmailElement.clear();
        userEmailElement.sendKeys(userEmail);

    }

    public void clearAndFillAge(String age) throws IOException {
        validAge();
        ageElement.clear();
        ageElement.sendKeys(age);

    }

    public void clearAndFillSalary(String salary) throws IOException {
        validSalary();
        salaryElement.clear();
        salaryElement.sendKeys(salary);

    }

    public void clearAndFillDepartment(String department) throws IOException {
        validDepartment();
        departmentElement.clear();
        departmentElement.sendKeys(department);

    }

    public void clearAndFillForm(String firstName, String lastName, String userEmail, String age, String salary, String department) throws IOException {
        clearAndFillFirstName(firstName);
        clearAndFillLastName(lastName);
        clearAndFillUserEmail(userEmail);
        clearAndFillAge(age);
        clearAndFillSalary(salary);
        clearAndFillDepartment(department);
    }

    public void appendAndFillFirstName(String firstName) throws IOException {
        validFirstName();
        firstNameElement.sendKeys(firstName);

    }

    public void appendAndFillLastName(String lastName) throws IOException {
        validLastName();
        lastNameElement.sendKeys(lastName);

    }

    public void appendAndFillUserEmail(String userEmail) throws IOException {
        validUserEmail();
        userEmailElement.sendKeys(userEmail);

    }

    public void appendAndFillAge(String age) throws IOException {
        validAge();
        ageElement.sendKeys(age);

    }

    public void appendAndFillSalary(String salary) throws IOException {
        validSalary();
        salaryElement.sendKeys(salary);

    }

    public void appendAndFillDepartment(String department) throws IOException {
        validDepartment();
        departmentElement.sendKeys(department);

    }

    public void appendAndFillForm(String firstName, String lastName, String userEmail, String age, String salary, String department) throws IOException {
        appendAndFillFirstName(firstName);
        appendAndFillLastName(lastName);
        appendAndFillUserEmail(userEmail);
        appendAndFillAge(age);
        appendAndFillSalary(salary);
        appendAndFillDepartment(department);
    }

    public WebTablesPage submitForm() {
        submitButton.click();
        return new WebTablesPage(driver);
    }

    public WebTablesPage closeForm() {
        closeButton.click();
        return new WebTablesPage(driver);
    }

    private void validFirstName() throws IOException {
        if (isEdit) {
            if (firstNameElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field First Name should not be empty");
            }
        }
    }

    private void validLastName() throws IOException {
        if (isEdit) {
            if (lastNameElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field Last Name should not be empty");
            }
        }
    }

    private void validUserEmail() throws IOException {
        if (isEdit) {
            if (userEmailElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field User Email should not be empty");
            }
        }
    }

    private void validAge() throws IOException {
        if (isEdit) {
            if (ageElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field Age should not be empty");
            }
        }
    }

    private void validSalary() throws IOException {
        if (isEdit) {
            if (salaryElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field Salary should not be empty");
            }
        }
    }

    private void validDepartment() throws IOException {
        if (isEdit) {
            if (departmentElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field Department should not be empty");
            }
        }
    }

    private double getNumericValue(String number) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("The Field Should be a number");
        }

    }


}
