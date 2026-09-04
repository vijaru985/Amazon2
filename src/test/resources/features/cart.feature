Feature: Validate cart functionality

  @Smoke
  Scenario: Add product to cart
    Given user logged into amazon website
    And user clicks on all menu button
    When user clicks on Bestsellers link in menu section
    And user clicks on first product in best sellers page
    And user clicks on add to cart button
    
    
