package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import static general.Constants.LOADING_PAGE_TIMEOUT_MS;
import static general.Constants.WAIT_FOR_SEARCHING_TIME_MS;

public class PaymentPage extends _Page {

    private By passengerInformation = By.xpath("//div[contains(@class,'payment-passenger-first-name')]/input");
    //Passengers Information
    @FindBy(xpath = "//div[contains(@class,'payment-passenger-first-name')]/input") private List<WebElement> firstNameList;
    @FindBy(xpath = "//div[contains(@class,'payment-passenger-last-name')]/input") private List<WebElement> lastNameList;
    @FindBy(xpath = "//div[@class='core-select']/select[contains(@name,'title')]") private List<WebElement> titleList;
    @FindBy(xpath = "//div[@class='core-select']/select[contains(@name,'title')]/option[@label='Mr']") private List<WebElement> titleLabelList;

    //Card Information
    @FindBy(xpath = "//input[contains(@id,'cardNumber')]") private WebElement cardNumber;
    @FindBy(xpath = "//select[contains(@name,'expiryMonth')]") private WebElement expiryMonthDropdown;
    @FindBy(xpath = "//select[contains(@name,'expiryYear')]") private WebElement expiryYearDropdown;
    @FindBy(xpath = "//input[@name='securityCode']") private WebElement securityCode;
    @FindBy(xpath = "//input[@name='cardHolderName']") private WebElement cardHolderName;
    //Billing Information
    @FindBy(id = "billingAddressAddressLine1") private WebElement billingAddressAddress;
    @FindBy(id = "billingAddressCity") private WebElement billingAddressCity;
    //Accept terms and Pay Now buttons
    @FindBy(xpath = "//div[@class='terms']//span[@class='core-checkbox-label--icon']") private WebElement acceptTerms;
    @FindBy(className = "core-btn-medium") private WebElement payNow;
    //Error payment dialog
    private By failedPaymentDialog = By.className("failed-payment-dialog");
    private By paymentError = By.xpath("//prompt[contains(@class,'error')]");


    public PaymentPage(WebDriver driver){
        super(driver);
    }

    public boolean isPaymentPageLoaded(){
        return waitForElementPresence(passengerInformation,LOADING_PAGE_TIMEOUT_MS);
    }

    //Sets random first/last names for all passengers
    public void fillPassengersInformation(){
        for(int i=0; i < firstNameList.size(); i++){
            setText(firstNameList.get(i),stringGenerator());
            setText(lastNameList.get(i),stringGenerator());
            if(i < titleList.size()) {
                click(titleList.get(i));
                click(titleLabelList.get(i));
            }
        }
    }

    //Sets card information fields according to test data & card holder name with random value
    public void fillCardInformation(String cardNumber, String expirationDate, String securityCode) throws Exception{
        String[] date = expirationDateFormatter(expirationDate);
        By expiryMonth = By.xpath("//option[(@label='" + date[0] + "')]");
        By expiryYear = By.xpath("//option[(@label='20" + date[1] + "')]");

        setText(this.cardNumber,cardNumberValidator(cardNumber));
        click(expiryMonthDropdown);
        click(getElement(expiryMonth));
        click(expiryYearDropdown);
        click(getElement(expiryYear));
        setText(this.securityCode, securityCode);
        setText(cardHolderName,stringGenerator());
    }

    //Sets random billing address and 'Dublin' as default city
    public void fillBillingInformation() {
        setText(billingAddressAddress, stringGenerator());
        setText(billingAddressCity, "Dublin");
    }

    //Accepts terms and conditions and clicks on Pay Now button
    public void goPay(){
        click(acceptTerms);
        click(payNow);
    }

    //Verifies declined error payment
    public boolean verifyDeclinedPayment(){
        return waitForElementPresence(failedPaymentDialog,LOADING_PAGE_TIMEOUT_MS) || waitForElementPresence(paymentError,WAIT_FOR_SEARCHING_TIME_MS);
    }
}
