@test
Feature: Login scenarios for Sauce Labs App
  Scenario Outline: Login with invalid user name
    When I enter  username as "<username>"
    And I enter password as "<password>"
    And I Login
    Then Login should fail with an error "<error>"
    Examples:
      | username | password|error|
      |dummy_user|secret_sauce|Username and password do not match any user in this service.|
  Scenario Outline: Login with invalid password
    When I enter  username as "<username>"
    And I enter password as "<password>"
    And I Login
    Then Login should fail with an error "<error>"
    Examples:
      | username | password|error|
      |standard_user|dummy_sauce|Username and password do not match any user in this service.|
  Scenario Outline: Login with valid username and password
    When I enter  username as "<username>"
    And I enter password as "<password>"
    And I Login
    Then I should see Products page with title "<title>"
    Examples:
      | username | password|title|
      |standard_user|secret_sauce|PRODUCTS|