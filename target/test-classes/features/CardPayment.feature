Feature: Card Payment Suite
  Tests Card Payment failures

  Background:
    Given User has access to Ryanair website

  Scenario Outline: Invalid card information when purchasing a flight
    Given I make a booking from <origin> to <destination> on <fly out date> for 2 adults and 2 children
    When I pay for booking with card details <card number>, <expiration date> and <CVV>
    Then I should get payment declined message

    Examples:
      |   origin       |   destination     | fly out date   |   card number             |    expiration date    |    CVV    |
      |   Madrid       |   Dublin          | 15/05/2019     |   4189 6616 7415 4921     |    10/19              |    1234   |