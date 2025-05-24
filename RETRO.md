# RETRO.md — Retrospective

## What went well
Overall, the project setup and implementation wend smoothly. 
Page Object Model was effectively used to structure the code,
and the main test scenarios (add, edit, delete, and search)
were implemented without major issues. Although this was my first
time using TestNG (I previously worked with JUnit), 
I quickly adapted and used this opportunity to gain hands-on
experience with it.

## Challenges Faced
- **Ad interference**: At the beginning, the UI was affected 
by dynamic advertisements that overlapped table elements,
causing an exception.
  >Solution: I used both a **JavaScript-based workaround** to hide both `iframe` and `div` elements that were blocking user interactions and explicit waits. This resolved the flakiness in the tests.
- **CI/CD report generation**: I encountered a minor challenge
integrating report generation into a CI/CD pipeline, especially using 
TestNG, which has slightly different conventions from JUnit.
  >Solution: For the CI/CD issue, I adjusted the TestNG setup to correctly generate reports, learning that while TestNG and JUnit have different configurations, their underlying concepts are similar and transferable.

  
## Improvements for next time
If I had more time, I would expand the test coverage by:
- Adding more test cases
- Testing responsiveness or mobile views
- Add an Allure in the CI/CD for more detailed test results (At this time this works only locally).

## Noted Issues / Flaky Behavior
I identified a UI bug in the registration form:
> If a field is filled and then the form is closed (using the close button), reopening the form retains the old input.  
This leads to **inconsistent behavior** when trying to add a new record — especially if the remaining fields are filled while the previously entered values remain in hidden form controls. This could confuse end users and resulted an invalid record submission.

## Key Takeaways
- This project gave me the opportunity to deal with **dynamic UI elements like ads**, to understand how it can impact test reliability and how to mitigate that with DOM manipulation
- I have learned to effectively use **TestNG**, especially test suite structuring which I found it an amazing and very useful for a cleaner structure and CI integration.
- I have gained confidence in debugging UI test failures and **isolating flaky behavior**
- I have reinforced the value of **explicit waits** and page object separation for maintainability

