Feature: Validate login functionality of amazon website

  @Smoke
  Scenario: Login with valid credentials
    Given user is on amazon sign in page
    When user enters username and password
    And user clicks on sign in button
    Then user lands on amazon homepage successfully

  @Regression
  Scenario Outline: Login with invalid credentials
    Given user is on amazon sign in page
    When user enters invalid <username> or <password>
    And user clicks on sign in button
    Then user fails to land on amazon homepage

    Examples:
      | username               | password      |
      | invalidEmail@gmail.com | duplicate@123 |

  @DatabaseTesting
  Scenario: Validate login functionality with database credentials
    Given user is on amazon sign in page
    When user enters username and password from database
    And user clicks on sign in button
    Then user lands on amazon homepage successfully

  @APITesting
  Scenario: Validate API Testing
    Given user is on amazon sign in page
    When user enters username and password
    And user clicks on sign in button
    Then user lands on amazon homepage successfully
    And user clicks on all menu button
    When user clicks on Bestsellers link in menu section
    And user clicks on first product in best sellers page
    Then user verifies eligibility API call is successful

  @Regression
  Scenario: Validate update Location in homepage without signing in
    Given user is on amazon homepage
    When user clicks on location section in homepage
    And user enters pincode and clicks on apply button in choose location popup
    Then user verifies location is updated successfully
