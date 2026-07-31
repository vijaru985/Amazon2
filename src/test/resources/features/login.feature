Feature: Validate login functionality of amazon website
  @Smoke
  Scenario Outline: Verify login functionality with valid credentials
    Given user is on amazon sign in page
    When user enters <username> and <password>
    And user clicks on sign in button
    Then user lands on amazon homepage successfully

    Examples:
      | username                   | password  |
      | vijayruttala9851@gmail.com | Vijay@985 |

  @Regression
  Scenario Outline: Verify login functionality with invalid credentials
    Given user is on amazon sign in page
    When user enters <username> and <password>
    And user clicks on sign in button
    Then user fails to land on amazon homepage

    Examples:
      | username                   | password   |
      | vijayruttala9851@gmail.com | Vijay@9857 |
