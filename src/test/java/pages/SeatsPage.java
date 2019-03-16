package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static general.Constants.*;

public class SeatsPage extends _Page {

    private By dialogTitle = By.className("dialog-title");
    private By familySeatsPopup = By.className("same-seats");

    //Random allocation
    private By randomAllocationBy = By.className("footer-message-content__button--link");
    @FindBy(xpath = "//button[contains(@class,'footer-message-content__button--link')]/span") private WebElement randomAllocation;
    private By randomAllocationPopupBy = By.className("popup-msg__button-wrapper");
    @FindBy(className = "popup-msg__button--cancel") private WebElement randomAllocationPopup;
    //Family seats
    @FindBy(xpath = "//div[contains(@class,'seat-group')]/span[not(contains(@class,'reserved'))]/span/img/../..") private List<WebElement> availableSeatsList;
    @FindBy(className = "dialog-overlay-footer__ok-button") private WebElement reviewSeats;
    private By reviewSeatsBy = By.className("dialog-overlay-footer__ok-button");
    //Checkout
    @FindBy(xpath = "//button/span[@translate='trips.summary.buttons.btn_checkout']") private WebElement checkoutButton;
    private By checkoutButtonBy = By.xpath("//button/span[@translate='trips.summary.buttons.btn_checkout']");

    public SeatsPage(WebDriver driver){
        super(driver);
    }

    public boolean isSeatsPageLoaded(){
        return waitForElementPresence(dialogTitle,LOADING_PAGE_TIMEOUT_MS);
    }

    public void chooseRandomAllocation(){
        if(waitForElementPresence(randomAllocationBy,WAIT_FOR_POPUP_TIME_MS))
            click(randomAllocation);
    }

    public void chooseFamilySeatsAllocation(int totalPassengers) {
        if(waitForElementPresence(familySeatsPopup,WAIT_FOR_POPUP_TIME_MS)) {
            click(getElement(familySeatsPopup));
            int i = 1;
            while(i <= totalPassengers){
                click(availableSeatsList.get(availableSeatsList.size()-i));
                i++;
            }
            while(waitForElementPresence(reviewSeatsBy,WAIT_FOR_ELEMENT_TIME_MS))
                try{
                    click(reviewSeats);
                    Thread.sleep(WAIT_FOR_ELEMENT_TIME_MS);
                }catch (Exception e){}
        }
    }

    //Checkout avoiding car rental or allocation
    public void checkout(){
        click(checkoutButton);
        if(waitForElementPresence(randomAllocationPopupBy,WAIT_FOR_POPUP_TIME_MS))
            click(randomAllocationPopup);
    }

}
