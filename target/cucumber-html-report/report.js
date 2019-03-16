$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/CardPayment.feature");
formatter.feature({
  "name": "Card Payment Suite",
  "description": "  Tests Card Payment failures",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "name": "Invalid card information when purchasing a flight",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I make a booking from \u003corigin\u003e to \u003cdestination\u003e on \u003cfly out date\u003e for 2 adults and 2 children",
  "keyword": "Given "
});
formatter.step({
  "name": "I pay for booking with card details \u003ccard number\u003e, \u003cexpiration date\u003e and \u003cCVV\u003e",
  "keyword": "When "
});
formatter.step({
  "name": "I should get payment declined message",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "origin",
        "destination",
        "fly out date",
        "card number",
        "expiration date",
        "CVV"
      ]
    },
    {
      "cells": [
        "Madrid",
        "Dublin",
        "15/05/2019",
        "4189 6616 7415 4921",
        "10/19",
        "1234"
      ]
    }
  ]
});
formatter.background({
  "name": "",
  "description": "",
  "keyword": "Background"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User has access to Ryanair website",
  "keyword": "Given "
});
formatter.match({
  "location": "StepDefinitions.userHasAccessToRyanairWebsite()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Invalid card information when purchasing a flight",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I make a booking from Madrid to Dublin on 15/05/2019 for 2 adults and 2 children",
  "keyword": "Given "
});
formatter.match({
  "location": "StepDefinitions.iMakeABookingFromOriginToDestinationOnDateForAdultPassenger(String,String,String,int,int)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I pay for booking with card details 4189 6616 7415 4921, 10/19 and 1234",
  "keyword": "When "
});
formatter.match({
  "location": "StepDefinitions.iPayForBookingWithCardDetailsCardNumberExpirationDateAndCVC(String,String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should get payment declined message",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.iShouldGetPaymentDeclinedMessage()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});