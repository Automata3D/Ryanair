package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static general.Constants.*;

public class AvailableFlightsPage extends _Page {

    @FindBy(xpath = "//div[contains(@class,'flight-header__min-price')]//div[@class='flights-table-price']") private WebElement availableFlight;
    private By availableFlightBy = By.xpath("//div[contains(@class,'flight-header__min-price')]//div[@class='flights-table-price']");

    @FindBy(className = "standard") private WebElement standardFare;
    @FindBy(xpath = "//button[@id='continue' and not(@disabled)]") private WebElement continueButton;
    @FindBy(className = "cookie-popup__close") private WebElement closeCookies;


    public AvailableFlightsPage(WebDriver driver){
        super(driver);
    }

    public boolean areAvailableFlights(){
        return waitForElementPresence(availableFlightBy,LOADING_PAGE_TIMEOUT_MS);
    }

    public void chooseAvailableFlight() {
        click(availableFlight);
    }

    public void chooseStandardFare() {
        click(standardFare);
    }

    public void continueBooking() {
        click(continueButton);
    }

    public void closeCookies() {
        click(closeCookies);
    }

}
