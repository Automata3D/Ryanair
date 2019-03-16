package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static general.Constants.*;

public class LandingPage extends _Page {

    @FindBy(id = "myryanair-auth-login") private WebElement loginButton;
    private By searchContainer = By.id("search-container");
    //Flight type
    @FindBy(className = "one-way") private WebElement oneWayFlight;
    //Origin & destination airports
    @FindBy(xpath = "//input[contains(@placeholder,'Departure airport')]") private WebElement departureAirport;
    @FindBy(xpath = "//input[contains(@placeholder,'Destination airport')]") private WebElement destinationAirport;
    //Dates
    @FindBy(xpath = "//div[contains(@class,'date-input')][1]/input[1]") private WebElement flyOutDateDay;
    @FindBy(xpath = "//div[contains(@class,'date-input')][1]/input[2]") private WebElement flyOutDateMonth;
    @FindBy(xpath = "//div[contains(@class,'date-input')][1]/input[3]") private WebElement flyOutDateYear;
    //Passengers
    @FindBy(className = "dropdown-handle") private WebElement passengersDropdown;
    @FindBy(xpath = "//div[@label='Adults']//core-inc-dec/button/core-icon[contains(@icon-id,'glyphs.plus-circle')]/..") private WebElement addAdultPassenger;
    @FindBy(xpath = "//div[@label='Children']//core-inc-dec/button/core-icon[contains(@icon-id,'glyphs.plus-circle')]/..") private WebElement addChildrenPassenger;
    //Reject group
    private By rejectGroupPromotion = By.className("group-booking-promotion__reject-label");
    //Continue button & Terms and conditions
    @FindBy(className = "col-flight-search-right") private WebElement searchFlight;
    @FindBy(className = "terms-conditions-checkbox-span") private WebElement termsAndConditions;
    private By termsAndConditionsChecked = By.xpath("//div[@class='terms-conditions']/input[@checked='checked']");
    // Some elements for error handling
    @FindBy(className = "ryanair-error-tooltip") private WebElement ryanairErrorTooltip;


    public LandingPage(WebDriver driver){
        super(driver);
    }

    public boolean isLandingPageLoaded(){
        return waitForElementPresence(searchContainer,LOADING_PAGE_TIMEOUT_MS);
    }

    public void goLogin(){
        click(loginButton);
    }

    public void setOneWayFlight() {
        click(oneWayFlight);
    }

    public boolean setDepartureAirport(String departureAirport) {
        setText(this.departureAirport,departureAirport);
        return !waitForElementVisibility(ryanairErrorTooltip, WAIT_FOR_ELEMENT_TIME_MS);
    }

    public boolean setDestinationAirport(String destinationAirport) {
        setText(this.destinationAirport,destinationAirport);
        click(searchFlight);
        return !waitForElementVisibility(ryanairErrorTooltip, WAIT_FOR_ELEMENT_TIME_MS);
    }

    public void setFlyOutDate(String flyOutDate) throws Exception{
        String[] date = dateFormatter(flyOutDate);
        setText(this.flyOutDateDay,date[0]);
        setText(this.flyOutDateMonth,date[1]);
        setText(this.flyOutDateYear,date[2]);
    }

    public void openPassengersDropdown(){
        click(passengersDropdown);
    }

    public void addAdultsPassengers(int adults){
        Assert.assertTrue("Number of Adults is out of range: " + adults, 1 <= adults && adults <= 10);
        int counter = 1;
        while (counter < adults) {
            click(addAdultPassenger);
            counter++;
        }
    }

    public void addChildrenPassengers(int children){
        Assert.assertTrue("Number of Children is out of range: " + children, 0 <= children && children <= 10);
        int counter = 0;
        while (counter < children) {
            click(addChildrenPassenger);
            counter++;
        }
    }

    public boolean acceptTermsAndConditions(){
        click(termsAndConditions);
        return waitForElementPresence(termsAndConditionsChecked,MIN_WAIT_TIME);
    }

    public boolean goSearchFlights(){
        click(searchFlight);
        if(waitForElementPresence(rejectGroupPromotion,MIN_WAIT_TIME))
            click(getElement(rejectGroupPromotion));
        return !waitForElementVisibility(ryanairErrorTooltip, WAIT_FOR_ELEMENT_TIME_MS);
    }

}
