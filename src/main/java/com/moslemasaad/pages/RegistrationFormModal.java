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

    private boolean isEdit;

    public RegistrationFormModal(WebDriver driver,boolean isEdit) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(formTile));
        this.isEdit = isEdit;
    }

    public boolean isInForm(){
        return formTile.getText().equals("Registration Form");
    }

    public void fillFirstName(String firstName) throws IOException {
        validFirstName(firstName);
        if (!isEdit){
            firstNameElement.sendKeys(firstName);
        }
    }
    public void fillLastName(String lastName) throws IOException {
        validLastName(lastName);
        if (!isEdit){
            lastNameElement.sendKeys(lastName);
        }
    }
    public void fillUserEmail(String userEmail) throws IOException {
        validUserEmail(userEmail);
        if (!isEdit){
            userEmailElement.sendKeys(userEmail);
        }
    }
    public void fillAge(String age) throws IOException {
        validAge(age);
        if (!isEdit){
            ageElement.sendKeys(age);
        }
    }
    public void fillSalary(String salary) throws IOException {
        validSalary(salary);
        if (!isEdit){
            salaryElement.sendKeys(salary);
        }
    }
    public void fillDepartment(String department) throws IOException {
        validDepartment();
        if (!isEdit){
            departmentElement.sendKeys(department);
        }
    }

    public void fillForm(String firstName,String lastName, String userEmail,String age, String salary,String department) throws IOException {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillUserEmail(userEmail);
        fillAge(age);
        fillSalary(salary);
        fillDepartment(department);
    }

    public WebTablesPage submitForm(){
        submitButton.click();
        return new WebTablesPage(driver);
    }

    private void validFirstName(String firstName) throws IOException {
        if (!isEdit) {
            if (!firstNameElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field First Name should be empty");
            }
        }
        else{
            if (firstNameElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field First Name should not be empty");
            }
        }
        if (firstName.isEmpty()){
            throw new IllegalArgumentException("First Name should contain at least one letter");
        }
    }
    private void validLastName(String lastName) throws IOException {
        if (!isEdit) {
            if (!lastNameElement.getDomProperty("value").isEmpty()) {
                throw new IOException("The field Last Name should be empty");
            }
        }
        else{
            if (lastNameElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Last Name should not be empty");
            }
        }
        if (lastName.isEmpty()){
            throw new IllegalArgumentException("Last Name should contain at least one letter");
        }
    }

    private void validUserEmail(String email) throws IOException {
        if (!isEdit) {
            if (!userEmailElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field User Email should be empty");
            }
        }
        else{
            if (userEmailElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field User Email should not be empty");
            }
        }
        if (!FieldsValidator.isValidEmail(email)){
            throw new IllegalArgumentException("The email is Nor valid");
        }
    }

    private void validAge(String age) throws IOException {
        if (!isEdit) {
            if (!ageElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Age should be empty");
            }
        }
        else{
            if (ageElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Age should not be empty");
            }
        }
        if (age.length()>2){
            throw new IllegalArgumentException("Age should be at most two digits");
        }
        if (!FieldsValidator.isValidNumber(age)){
            throw new IllegalArgumentException("The Age should be positive integer number");
        }
        double ageNum = getNumericValue(age);
        if (ageNum<0){
            throw new IllegalArgumentException("The employee age should be positive");
        }
    }

    private void validSalary(String salary) throws IOException {
        if (!isEdit) {
            if (!salaryElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Salary should be empty");
            }
        }
        else{
            if (salaryElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Salary should not be empty");
            }
        }
        if (!FieldsValidator.isValidNumber(salary)){
            throw new IllegalArgumentException("The Salary should be positive integer number");
        }
        double salaryNum = getNumericValue(salary);
        if (salaryNum<=0){
            throw new IllegalArgumentException("The Salary should be positive");
        }else{
            if (salaryNum - (int) salaryNum !=0){
                throw new IllegalArgumentException("The Salary should be an integer value");
            }
        }
    }
    private void validDepartment(String department) throws IOException {
        if (!isEdit) {
            if (!departmentElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Department should be empty");
            }
        }
        else{
            if (departmentElement.getDomProperty("value").isEmpty()){
                throw new IOException("The field Department should not be empty");
            }
        }
        if (department.isEmpty()){
            throw new IllegalArgumentException("Department should contain at least one letter");
        }
    }

    private double getNumericValue(String number){
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("The Field Should be a number");
        }
    }



}
