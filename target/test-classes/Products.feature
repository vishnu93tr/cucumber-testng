Feature: Product Scenarios
  Scenario Outline:
    Given I'm Logged in
    Then Product is listed with title "<title>" and price "<price>"
    Examples:
      | title | price |
      |Sauce Labs Backpack|$29.99|
      |Sauce Labs Bike Light|$9.99|
     # |Sauce Labs Bolt T-Shirt|$15.99|
  Scenario Outline:
    Given I'm Logged in
    When I click product title "<title>"
    Then I should be on Product Details Page with title "<title>", price "<price>" and description "<description>"
    Examples:
      | title | price | description |
      |Sauce Labs Backpack|$29.99|carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.|