package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static general.Constants.*;

public class LoginFormPage extends _Page {

    @FindBy(xpath = "//div[@class='form-field']/input[@type='email']") private WebElement emailAddress;
    @FindBy(xpath = "//div/*/input[@name='password']") private WebElement password;
    @FindBy(xpath = "//div[@class='modal-form-group']//button[@type='submit']") private WebElement loginButton;

    public LoginFormPage(WebDriver driver){
        super(driver);
    }

    public void login() throws InterruptedException{
        setText(emailAddress,EMAIL);
        setText(password,PASSWORD);
        click(loginButton);
        Thread.sleep(LOADING_PAGE_TIMEOUT_MS);
    }
}
