package com.moslemasaad.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import java.time.Duration;
import java.util.List;

public class WebTablesPage extends LoadableComponent<WebTablesPage> {

    private final String baseURL = "https://demoqa.com/webtables";
    private WebDriver driver;
    @FindBy(className = "text-center")
    private WebElement title;

    @FindBy(id = "addNewRecordButton")
    private WebElement addRecordButton;

    @FindBy(id = "edit-record-1")
    private WebElement firstEditRecord;

    @FindBy(id = "delete-record-1")
    private WebElement firstDeleteRecord;

    @FindBy(css = ".rt-tbody .rt-tr-group")
    private List<WebElement> records;


    @FindBy(css = ".rt-tbody .-padRow")
    private List<WebElement> emptyRecord;

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    @FindBy(xpath = "//h1[contains(text(),'Web Tables')]")
    private WebElement webTableTitle;

    private final String deleteId = "delete-record-";
    private final String editId = "edit-record-";

    public WebTablesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL);
        if (driver.getPageSource().contains("502 Bad Gateway")) {
            throw new SkipException("502 Bad Gateway encountered. Skipping test.");
        }
        System.out.println(driver.getCurrentUrl());
    }

    @Override
    public void isLoaded() throws Error {
        Assert.assertTrue(driver.getTitle().contains("DEMOQA"));
    }

    public boolean isInWebTables(){
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return webTableTitle.getText().equals("Web Tables");
    }

    private void removeAds() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("""
                    document.querySelectorAll('iframe, [id^="google_ads"], .ad-banner, .adsbygoogle').forEach(el => {
                        el.remove();
                    });
                """);
    }

    public int countEmptyRecords() {
        return emptyRecord.size();
    }

    public int countAllRecords() {
        return records.size();
    }

    public int getNonEmptyRecords() {
        return countAllRecords() - countEmptyRecords();
    }

    public String getRecordEmail(int indx) {
        removeAds();
        recordsWait();

        WebElement element = records.get(indx);
        WebElement emailSection = element.findElement(By.cssSelector(".rt-td:nth-child(4)"));
        return emailSection.getAccessibleName();
    }


    public void deleteGeneralRecord(int indx) {
        removeAds();
        recordsWait();

        String idRecord = deleteId + (indx + 1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id(idRecord)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

//        deleteButton.click();
    }

    public void deleteFirstVisibleFilteredRecord() {
        recordsWait();
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        for (WebElement row : rows) {
            try {
                WebElement deleteButton = row.findElement(By.cssSelector("span[title='Delete']"));
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView(true);", deleteButton);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
                deleteButton.click();
                break;
            } catch (StaleElementReferenceException e) {
                rows = driver.findElements(By.cssSelector(".rt-tr-group"));
            }
        }
    }

    public List<WebElement> filterRecords(String prefix){
        removeAds();

        searchBox.clear();
        searchBox.sendKeys(prefix);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".rt-tr-group"), 0));

        return driver.findElements(By.className("rt-tr-group"));
    }

    public RegistrationFormModal addEmployee(){
        removeAds();
        addRecordButton.click();
        return new RegistrationFormModal(driver,false);
    }

    public RegistrationFormModal editGeneralRecord(int indx) {
        removeAds();
        String idRecord = editId + (indx + 1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id(idRecord)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(editButton)).click();

//        editButton.click();
        return new RegistrationFormModal(driver,true);
    }

    public WebElement getARecord(int idx){
        return records.get(idx);
    }

    public String getRecordFirstName(WebElement record){
        WebElement firstNameElement = record.findElement(By.cssSelector(".rt-td:first-child"));
        return firstNameElement.getText();
    }

    public String getRecordSalary(WebElement record){
        WebElement salaryElement = record.findElement(By.cssSelector(".rt-td:nth-child(5)"));
        return salaryElement.getText();
    }

    private void recordsWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".rt-tbody .rt-tr-group")));
    }
}






