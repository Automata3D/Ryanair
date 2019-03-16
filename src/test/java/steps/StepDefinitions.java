package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class StepDefinitions {
    private Behavior behavior;

    @Given("User has access to Ryanair website")
    public void userHasAccessToRyanairWebsite() throws InterruptedException {
        behavior = new Behavior(DriverBuilder.getDriver());
        Assert.assertEquals("User has access to Ryanair website",
                true, behavior.isRyanairWebsiteLoaded());
    }

    @Given("I make a booking from ([^\"]*) to ([^\"]*) on ([^\"]*) for (\\d+) adults and (\\d+) children$")
    public void iMakeABookingFromOriginToDestinationOnDateForAdultPassenger(String origin, String destination, String date, int adults, int children) throws Exception {
        behavior.setOneWayFlightBookingDetails(origin , destination, date, adults, children);
        behavior.chooseFlight();
        behavior.chooseFreeLuggageForAllPassengers();
        behavior.chooseAllocation(adults+children);
    }


    @When("I pay for booking with card details ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void iPayForBookingWithCardDetailsCardNumberExpirationDateAndCVC(String cardNumber, String expirationDate, String cvv) throws Exception{
        behavior.fillPassengersInfoAndPaymentDetails(cardNumber, expirationDate, cvv);
    }

    @Then("I should get payment declined message")
    public void iShouldGetPaymentDeclinedMessage() {
        behavior.verifyDeclinedPayment();
    }

}
