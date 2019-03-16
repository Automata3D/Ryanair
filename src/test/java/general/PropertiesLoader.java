package general;

import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.testng.internal.Utils.error;

public class PropertiesLoader {
  protected static Properties prop;
  private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

  private PropertiesLoader(){}

  public static void loadPropertiesFile(String fileName){
    prop = new Properties();
    InputStream input = null;
    String path =
        CURRENT_DIRECTORY + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + fileName;

    try {
      input = new FileInputStream(path);
      // load a properties file
      prop.load(input);
    } catch (IOException ex) {
        error(ex.getMessage());
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
            error(e.getMessage());
        }
      }
    }
  }


  public static String getBrowserName() {
    final String BROWSER_NAME = "browser.name";
    return prop.getProperty(BROWSER_NAME);
  }

  public static String getBrowserDriverPath() {
    String path = null;
    switch (getBrowserName()){
      case BrowserType.FIREFOX:
        path = CURRENT_DIRECTORY + File.separator + "drivers" + File.separator + "geckodriver.exe";
        break;
      case BrowserType.CHROME:
        path = CURRENT_DIRECTORY + File.separator + "drivers" + File.separator + "chromedriver.exe";
        break;
        default:
          return path;
    }
    return path;
  }

  public static long getBrowserExecutionTimeout() {
    final String BROWSER_EXECUTION_WAIT_TIMEOUT = "browser.execution.wait.timeout";
    return Long.valueOf(prop.getProperty(BROWSER_EXECUTION_WAIT_TIMEOUT));
  }

}
