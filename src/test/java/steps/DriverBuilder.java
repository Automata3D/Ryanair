package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import general.PropertiesLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.concurrent.TimeUnit;
import static general.PropertiesLoader.getBrowserDriverPath;
import static org.testng.internal.Utils.error;

public class DriverBuilder {
  private static WebDriver driver;
  private static long implicitWaitTimeout;

  @Before
  public void setup(){

    if(System.getProperty("browser") != null ) {
      PropertiesLoader.loadPropertiesFile(System.getProperty("browser")+".properties");
      driverSetup();
    }
  }

  public void driverSetup(){
    try {
      createWebDriver();
      setImplicitWaitTimeoutSeconds(PropertiesLoader.getBrowserExecutionTimeout());
    } catch (WebDriverException exception) {
        exception.getMessage();
    }
  }

  public void createWebDriver(){
    DesiredCapabilities capabilities;

    switch (PropertiesLoader.getBrowserName()) {
      case BrowserType.FIREFOX:
        capabilities = DesiredCapabilities.firefox();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", getBrowserDriverPath());
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        driver = new FirefoxDriver(firefoxOptions);
        break;
      case BrowserType.CHROME:
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", getBrowserDriverPath());
        chromeOptions.addArguments("--start-maximized");
        driver = new ChromeDriver(chromeOptions);
        break;
      default:
        error("Browser not supported: local.browser.name property = " + PropertiesLoader.getBrowserName());
        break;
    }
  }

  public static WebDriver getDriver() {
    return driver;
  }

  public static long getImplicitWaitTimeout() {
    return implicitWaitTimeout;
  }

  public static void setImplicitWaitTimeoutSeconds(long timeoutSeconds) {
    implicitWaitTimeout = timeoutSeconds;
    driver.manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
  }

  @After
  public void quit() throws InterruptedException{
    Thread.sleep(2000);
    if(getDriver() != null)
      getDriver().close();
  }

}
