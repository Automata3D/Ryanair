package pages;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import steps.DriverBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static general.Constants.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.testng.internal.Utils.error;
import static org.testng.internal.Utils.log;

public abstract class _Page {
    protected WebDriver driver;

    public _Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    public void click(WebElement element){
        timeDelay(GUARD_WAIT_TIME);
        element.click();
        timeDelay(MIN_WAIT_TIME);
    }

    public void setText(WebElement element, String text) {
        waitForElementVisibility(element, DriverBuilder.getImplicitWaitTimeout() * 1_000);
        element.clear();
        element.sendKeys(text);
        timeDelay(GUARD_WAIT_TIME);
    }

    protected boolean waitForElementPresence(By by) {
        return waitForElementPresence(by, DriverBuilder.getImplicitWaitTimeout() * 1000);
    }

    protected boolean waitForElementPresence(By by, long timeoutMilliseconds) {
        long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log("[WARN]"+ MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        final long NULLIFY_TIME = 0;
        long defaultTimeout = DriverBuilder.getImplicitWaitTimeout();
        DriverBuilder.setImplicitWaitTimeoutSeconds(NULLIFY_TIME);

        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeoutMilliseconds, MILLISECONDS)
                .pollingEvery(POLLING_TIME, MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(Exception.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException exception) {
            error(exception.getLocalizedMessage());
        }
        long stopTime = System.currentTimeMillis();
        long timeElapsed = stopTime - startTime;
        boolean result = timeElapsed < timeoutMilliseconds;
        DriverBuilder.setImplicitWaitTimeoutSeconds(defaultTimeout);
        return result;
    }

    protected boolean waitForElementVisibility(WebElement element, long timeoutMilliseconds) {
        long startTime = System.currentTimeMillis();
        if (timeoutMilliseconds < MIN_WAIT_TIME) {
            log("[WARN]" + MIN_TIME_MESSAGE + MIN_WAIT_TIME);
        }
        final long NULLIFY_TIME = 0;
        long defaultTimeout = DriverBuilder.getImplicitWaitTimeout();
        DriverBuilder.setImplicitWaitTimeoutSeconds(NULLIFY_TIME);
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeoutMilliseconds, MILLISECONDS)
                .pollingEvery(POLLING_TIME, MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(Exception.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException | UnreachableBrowserException exception) {
            error(exception.getLocalizedMessage());
        }
        long endTime = System.currentTimeMillis();
        DriverBuilder.setImplicitWaitTimeoutSeconds(defaultTimeout);
        return (endTime - startTime) < timeoutMilliseconds;
    }

    protected WebElement getElement(By by) {
        WebElement element = null;
        try {
            waitForElementPresence(by);
            element = driver.findElement(by);
        } catch (NoSuchElementException noSuchElementException) {
            error(NO_SUCH_ELEMENT_EXCEPTION);
        } catch (StaleElementReferenceException staleElementReferenceException) {
            error(STALE_ELEMENT_REFERENCE_EXCEPTION);
        } catch (Exception exception) {
            error(EXCEPTION);
        }
        return element;
    }

    protected String[] dateFormatter(String rawDate) throws Exception{
        String[] date = null;
        try {
            Date formattedDate;
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formattedDate = formatter.parse(rawDate);
            date = rawDate.split("/");
        } catch (Exception e) {
            log("[ERROR] Date with incorrect format: " + rawDate );
            log("[INFO] Please set a proper date format: dd/MM/yyyy");
            throw new Exception(e);
        }
        return date;
    }

    protected String[] expirationDateFormatter(String rawDate) throws Exception{
        String[] date = null;
        try {
            Date formattedDate;
            DateFormat formatter = new SimpleDateFormat("MM/yy");
            formattedDate = formatter.parse(rawDate);
            date = rawDate.split("/");
            int month = Integer.parseInt(date[0]);
            int year = Integer.parseInt(date[1]);
            Assert.assertTrue("Expiration month is out of range: " + month, 1 <= month && month <= 12);
            Assert.assertTrue("Expiration year is out of range: " + year, 19 <= year && year <= 29);
        } catch (Exception e) {
            log("[ERROR] Date with incorrect format: " + rawDate );
            log("[INFO] Please set a proper date format: MM/yy");
            throw new Exception(e);
        }
        return date;
    }

    protected String stringGenerator(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        return generator.generate(5);
    }

    protected String cardNumberValidator(String cardNumber){
        String aux = cardNumber.replaceAll("\\s","");
        Assert.assertEquals("Valid Card Number length is 16 characters",
                16, aux.length());
        return aux;
    }

    private void timeDelay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {}
    }
}
