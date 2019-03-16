package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static general.Constants.*;

public class LuggagePage extends _Page {

    private By luggageTitle = By.className("priority-boarding-view__options-title");
    @FindBy(xpath = "//div[@class='pb-cb-standalone-card__footer']/div/span[@class='pb-cb-standalone-card__free']") private WebElement freeLuggage;
    @FindBy(xpath = "//button[@data-ref='same-for-all-form__yes-button']") private WebElement sameForAll;
    private By sameForAllBy = By.className("same-for-all-form__button");
    @FindBy(xpath = "//button[@class='core-btn-primary' and @data-ref='priority-boarding-view__button' and not(@disabled)]/span") private WebElement continueButton;

    public LuggagePage(WebDriver driver){
        super(driver);
    }

    public boolean isLuggagePageLoaded(){
        return waitForElementPresence(luggageTitle,LOADING_PAGE_TIMEOUT_MS);
    }

    public void chooseFreeLuggage(){
        click(freeLuggage);
    }

    public void applySameLuggageToAllPassengers(){
        if(waitForElementPresence(sameForAllBy, WAIT_FOR_ELEMENT_TIME_MS))
            click(sameForAll);
    }

    public void continueBooking() throws InterruptedException{
        Thread.sleep(WAIT_FOR_ELEMENT_TIME_MS);
        click(continueButton);
    }

}
