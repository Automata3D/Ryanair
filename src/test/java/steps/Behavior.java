package steps;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.*;

import static general.Constants.RYANAIR_WEBSITE;

public class Behavior {
    protected static WebDriver driver;

    public Behavior(WebDriver driver){
        this.driver = driver;
    }

    public boolean isRyanairWebsiteLoaded() throws InterruptedException {
        LandingPage landingPage = new LandingPage(driver);
        driver.get(RYANAIR_WEBSITE);
        landingPage.goLogin();
        LoginFormPage loginFormPage = new LoginFormPage(driver);
        loginFormPage.login();
        return landingPage.isLandingPageLoaded();
    }

    public void setOneWayFlightBookingDetails(String departureAirport, String destinationAirport, String flyOutDate, int adults, int children) throws Exception{
        LandingPage landingPage = new LandingPage(driver);
        landingPage.setOneWayFlight();
        Assert.assertEquals("User must set an existing departure airport",
                true,landingPage.setDepartureAirport(departureAirport));
        Assert.assertEquals("User must set an existing destination airport",
                true,landingPage.setDestinationAirport(destinationAirport));
        landingPage.setFlyOutDate(flyOutDate);
        addPassengers(adults,children);
        Assert.assertEquals("User must accept terms and conditions",
                true,landingPage.acceptTermsAndConditions());
        Assert.assertEquals("Error while trying to search for flight:" +
                            "Departure Airport: " + departureAirport + "\n" +
                            "Destination Airport: " + destinationAirport + "\n" +
                            "Fly out Date: " + flyOutDate, true,landingPage.goSearchFlights());

    }

    public void addPassengers(int adults, int children) {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.openPassengersDropdown();
        landingPage.addAdultsPassengers(adults);
        landingPage.addChildrenPassengers(children);
    }

    public void chooseFlight(){
        AvailableFlightsPage availableFlightsPage = new AvailableFlightsPage(driver);
        Assert.assertEquals("There should be available flights to proceed with the booking",
                true,availableFlightsPage.areAvailableFlights());
        availableFlightsPage.chooseAvailableFlight();
        availableFlightsPage.chooseStandardFare();
        availableFlightsPage.closeCookies();
        availableFlightsPage.continueBooking();
    }

    public void chooseFreeLuggageForAllPassengers() throws InterruptedException{
        LuggagePage luggagePage = new LuggagePage(driver);
        Assert.assertEquals("Luggage options should be shown",
                true,luggagePage.isLuggagePageLoaded());
        luggagePage.chooseFreeLuggage();
        luggagePage.applySameLuggageToAllPassengers();
        luggagePage.continueBooking();
    }

    public void chooseAllocation(int totalPassengers){
        SeatsPage seatsPage = new SeatsPage(driver);
        Assert.assertEquals("Seats options should be shown",
                true, seatsPage.isSeatsPageLoaded());
        seatsPage.chooseRandomAllocation();
        seatsPage.chooseFamilySeatsAllocation(totalPassengers);
        seatsPage.checkout();
    }

    public void fillPassengersInfoAndPaymentDetails(String cardNumber, String expirationDate, String securityCode) throws Exception{
        PaymentPage paymentPage = new PaymentPage(driver);
        Assert.assertEquals("Passengers' information and payment options should be shown",
                true, paymentPage.isPaymentPageLoaded());

        paymentPage.fillPassengersInformation();
        paymentPage.fillCardInformation(cardNumber, expirationDate, securityCode);
        paymentPage.fillBillingInformation();
        paymentPage.goPay();
    }

    public void verifyDeclinedPayment(){
        PaymentPage paymentPage = new PaymentPage(driver);
        Assert.assertEquals("Declined Payment message properly shown",
                true, paymentPage.verifyDeclinedPayment());
    }

}
