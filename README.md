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
- `WebTablesPage.java`
- `RegistrationFormModal.java`


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

## How to Run the Tests

You can run the test suite either **locally** or through **GitHub Actions (CI/CD)**.

---

## Run Locally

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

### Option 2: Using Maven

**Run all tests:**
1. **you can run**:
   ```bash
   mvn clean test -DsuiteXmlFile=testng-suites/testng-master.xml
2. **Or**:
   ```bash
   mvn clean test
**To run as specific suit you can run**
```bash
   mvn clean test -DsuiteXmlFile=testng-suites/${suiteName}.xml
```
## Reports In Local Environment
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

### To run CI tests:

1. Go to the **Actions** tab of the repository.
2. Select the desired workflow (e.g., **Smoke Testing**).
3. Click **“Run workflow”** and choose the browser to run the tests on.
4. View the execution results and logs under the job run.
5. Reports generated automatically and provided after the flow finished in the workflow artifacts. 

### Each workflow:

- Installs dependencies
- Executes the designated test suite
- Generate reports

## Reports In GitHub Actions Environment
#### TestNG reports:
After test execution, reports generated automatically and provided after the flow finished in the workflow artifacts. 

## Issues
- Ads on the page may block buttons. These are handled via JS code during test setup.
- Registration form retains values after closing, leading to unexpected behavior when reopened.


## Thank you
**Moslem Asaad**

[LinkedIn] | [GitHub]


[LinkedIn]: https://www.linkedin.com/in/moslem-asaad-20b8a1219/
[GitHub]: http://github.com/moslem-asaad 