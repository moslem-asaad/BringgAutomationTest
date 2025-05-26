# BringgAutomationTest — Web Table UI Automation Suite

This project automates the test scenarios for [DemoQA Web Tables](https://demoqa.com/webtables) using **Java 21**, **Selenium WebDriver**, **TestNG**, and **Maven**, structured with the Page Object Model.

It also includes GitHub Actions workflows and modular TestNG XML suites for smoke, regression, and positive/negative testing.

---
## Project Structure

### .github/workflows
GitHub Actions workflows:
- `negative_group_testing.yaml`
- `positive_group_testing.yaml`
- `regression_testing.yaml`
- `smoke_testing.yaml`
### src/main/java/com.moslemasaad
Main source code:
- `Main.java` - Entry point (if any).
- `DriverFactory.java` - WebDriver setup and management.
- `FieldsValidator.java` - Utility for validating form fields.

- #### /pages/
    Page Object Model classes:
- `WebTablesPage.java` - 
  Represents the main Web Tables page.
  - Encapsulates interactions like:
      - Adding/editing/deleting records
      - Searching/filtering
      - Accessing record details
      - Removing ads before actions (custom utility)
    
- `RegistrationFormModal.java` - Represents the form modal for adding or editing employee records.
  - Encapsulates interactions like:
      - Filling and clearing form fields
      - Validating field states
      - Submitting or closing the form


### src/main/resources
- `allure.properties` - Allure configuration for further implementation.

### src/test/java
Test classes:
- `TestBase.java` - Base test class with setup and teardown.
- `WebTablesTest.java` - Contains actual test cases.

### testng-suites
TestNG suite XML files for grouping tests:
- `testng-master.xml`
- `testng-negative.xml`
- `testng-positive.xml`
- `testng-regression.xml`
- `testng-smoke.xml`

### Root Files
- `.gitignore`
- `pom.xml` - Maven configuration.
- `README.md` - Project documentation.
- `RETRO.md` - Retrospective.

####  Example Flow

1. `WebTablesTest` loads `WebTablesPage`
2. Calls `addEmployee()` → returns `RegistrationFormModal`
3. Fills data using `clearAndFill...()` or `appendAndFill...()`
4. Submits via `submitForm()` → returns back to `WebTablesPage`
5. More verification happens 


## Features & Scenarios Covered
- Add new employee record
- Edit salary of an existing record
- Delete a specific record
- Search/filter by name or email
- Group-based execution: smoke, regression, positive, negative

## Tech Stack

| Tool     | Version     |
|----------|-------------|
| Java     | 21          |
| Selenium | 4.27.0      |
| TestNG   | 7.9.0       |
| Maven    | ✔️          |
| GitHub Actions | ✔️     |

## POM Structure

[pom structure.pdf](https://github.com/user-attachments/files/20421510/pom.structure.pdf)

## Test Cases

| Test Case ID | Title                                      | Steps                                                                                                                                         | Expected Result                                                  |
|--------------|--------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|
| TC1          | Add a new employee record                  | 1. Open the web tables page<br>2. Click "Add" button<br>3. Fill all required fields with valid data<br>4. Submit form                        | New record appears in the table with the entered details         |
| TC1.1        | Add new record with missing mandatory fields | 1. Click "Add" button<br>2. Leave required fields (e.g., First Name or Email) empty<br>3. Submit form                                         | Form validation error displayed, record is **NOT** added         |
| TC1.2        | Add new record with invalid email format   | 1. Click "Add" button<br>2. Enter invalid email (e.g., `abc@@example`)<br>3. Submit form                                                      | Email format validation error displayed, record is **NOT** added |
| TC1.3        | Add new record with negative age           | 1. Click "Add" button<br>2. Enter negative number in Age field<br>3. Submit form                                                              | Validation error or rejected input, record is **NOT** added      |
| TC2          | Edit an existing record’s salary           | 1. Locate a record<br>2. Click "Edit" button<br>3. Change the salary field<br>4. Save changes                                                  | Updated salary value is visible in the record row                |
| TC3          | Delete a record                            | 1. Locate the first row<br>2. Click the trash icon<br>3. Confirm deletion if required                                                          | The record is removed and replaced by an empty row               |
| TC4          | Filter records by name                     | 1. Type a valid name (e.g., "Cie") into the search box<br>2. Count visible non-empty rows                                                     | Only matching records are displayed                              |
| TC5          | Add a record, then close the form          | 1. Click "Add"<br>2. Fill out the form<br>3. Click the X button (close form)<br>4. Return to table view                                       | No record is added                                               |
| TC6          | Fill form partially, close, then reopen    | 1. Fill some fields<br>2. Close the form<br>3. Reopen<br>4. Submit directly                                                                   | Behavior currently inconsistent (see RETRO.md bug)               |



---

## How to Run the Tests

You can run the test suite either **locally** or through **GitHub Actions (CI/CD)**.

### Default configurations:
- Browser: Chrome

By default, the tests will run on **Chrome browser**.To run the tests on **Firefox**, set the `BROWSER` environment variable to `firefox`.

##### Driver Configuration Logic
The `DriverFactory` class handles WebDriver creation using the following logic:
1. If the `GRID_URL` environment variable is set:
    - A remote WebDriver instance will be created using either **Chrome** or **Firefox**.
    - Headless mode is enabled for remote execution.

2. If `GRID_URL` is not set:
    - A local WebDriver instance will be created on the machine.

### Supported Browsers

- `chrome`
- `firefox`

> If an unsupported browser is passed, an `IllegalArgumentException` will be thrown.

This setup ensures flexibility for both **local testing** and **remote CI runs** with minimal configuration.

## Run Locally

To run with Selenium Grid, 
you can launch a Selenium Grid standalone server by downloading and executing the jar file from the [latest release](https://github.com/SeleniumHQ/selenium/releases/latest).

```bash
java -jar selenium-server-<version>.jar standalone
```

### Option 1: Using IntelliJ (or any IDE)
1. **Clone the repo**:
   ```bash
   git clone https://github.com/moslem-asaad/BringgAutomationTest.git
2. **Open the project in IntelliJ or any Java IDE.**
3. **Right-click and run any of the suite files under testng-suites/:**
- testng-master.xml
- testng-smoke.xml
- testng-positive.xml
- testng-negative.xml
- testng-regression.xml

4. To run with selenium grid
   * execute ```java -jar selenium-server-<version>.jar standalone```
   * set `GRID_URL` environment variable to http://localhost:4444


### Option 2: Using Maven

**Run all tests:**
1. **you can run**:
   ```bash
   mvn clean test -DsuiteXmlFile=testng-suites/testng-master.xml
    ```
   >If you're on Windows and this command doesn't work in Command Prompt or PowerShell, try running it in the Git Bash terminal instead.
3. **Or**:
   ```bash
   mvn clean test
**To run a specific suit you can run**
```bash
   mvn clean test -DsuiteXmlFile=testng-suites/${suiteName}.xml
```
you can override the browser by adding to the command `BROWSER=firefox`. 

3.To run with selenium grid, run:
   
   * ```bash 
     java -jar selenium-server-<version>.jar standalone
   
   then,
   * ```bash
     GRID_URL=http://localhost:4444 mvn clean test
     ```
     
### Reports In Local Environment
#### TestNG reports: 
After test execution, the report would be
**located in**

    target/surefire-reports/index.html

#### Allure reports:
**Note:**
if Allure is not installed in your environment, download it via this link
https://allurereport.org/docs/install/

After test execution, run:
```bash
allure serve target/allure-results -o allure-report --clean
```
and a html report would be opened.

## Run via GitHub Actions (CI/CD)

The project includes automated workflows for test execution in the cloud via **GitHub Actions**.  
CI is configured in the `.github/workflows/` directory with the following suites:

- `smoke_testing.yaml`
- `regression_testing.yaml`
- `positive_group_testing.yaml`
- `negative_group_testing.yaml`

#### To run CI tests:

1. Fork the repository.
2. Go to the **Actions** tab of the forked repository.
2. Select the desired workflow (e.g., **Smoke Testing**).
3. Click **“Run workflow”** and choose the browser to run the tests on.
4. View the execution results and logs under the job run.
5. Reports generated automatically and provided after the flow finished in the workflow artifacts. 

#### Each workflow:

- Installs dependencies
- Executes the designated test suite
- Generate reports

### Reports In GitHub Actions Environment
#### TestNG reports:
After test execution, reports generated automatically and provided after the flow finished in the workflow artifacts. 

---
## Issues
- Ads on the page may block buttons. These are handled via JS code during test setup.
- Registration form retains values after closing, leading to unexpected behavior when reopened.

---
## Thank you
**Moslem Asaad**

[LinkedIn] | [GitHub]


[LinkedIn]: https://www.linkedin.com/in/moslem-asaad-20b8a1219/
[GitHub]: http://github.com/moslem-asaad 
