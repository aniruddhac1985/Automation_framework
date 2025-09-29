package utilities;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.time.Duration;
import java.net.URL;
import java.net.MalformedURLException;
import utilities.Constants;

/**
 * DriverFactory class manages WebDriver instances in a thread-safe manner
 * Supports multiple browsers and execution modes (local/remote/headless)
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> browserName = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on browser name
     * @param browserName Name of the browser (chrome/firefox/edge/safari)
     */
    public static void initializeDriver(String browserName) {
        initializeDriver(browserName, false);
    }

    /**
     * Initialize WebDriver with headless option
     * @param browserName Name of the browser
     * @param headless true for headless mode, false for normal mode
     */
    public static void initializeDriver(String browserName, boolean headless) {
        if (browserName == null || browserName.trim().isEmpty()) {
            throw new RuntimeException("Browser name cannot be null or empty");
        }

        setBrowserName(browserName.toLowerCase().trim());

        try {
            switch (getBrowserName()) {
                case Constants.CHROME:
                    initializeChromeDriver(headless);
                    break;

                case Constants.FIREFOX:
                    initializeFirefoxDriver(headless);
                    break;

                case Constants.EDGE:
                    initializeEdgeDriver(headless);
                    break;

                case Constants.SAFARI:
                    initializeSafariDriver();
                    break;

                default:
                    throw new RuntimeException("Browser not supported: " + browserName +
                            ". Supported browsers: chrome, firefox, edge, safari");
            }

            configureDriver();

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver for browser: " + browserName, e);
        }
    }

    /**
     * Initialize Chrome WebDriver with options
     * @param headless true for headless mode
     */
    private static void initializeChromeDriver(boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();

        // Performance and stability options
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-images");
        chromeOptions.addArguments("--disable-javascript");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--start-maximized");

        // Headless mode
        if (headless) {
            chromeOptions.addArguments("--headless");
        }

        // Additional options for CI/CD environments
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        driver.set(new ChromeDriver(chromeOptions));
    }

    /**
     * Initialize Firefox WebDriver with options
     * @param headless true for headless mode
     */
    private static void initializeFirefoxDriver(boolean headless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Window size options
        firefoxOptions.addArguments("--width=1920");
        firefoxOptions.addArguments("--height=1080");

        // Headless mode
        if (headless) {
            firefoxOptions.addArguments("--headless");
        }

        // Performance options
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        firefoxOptions.addPreference("media.volume_scale", "0.0");

        driver.set(new FirefoxDriver(firefoxOptions));
    }

    /**
     * Initialize Edge WebDriver with options
     * @param headless true for headless mode
     */
    private static void initializeEdgeDriver(boolean headless) {
        EdgeOptions edgeOptions = new EdgeOptions();

        // Performance and stability options
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--window-size=1920,1080");
        edgeOptions.addArguments("--start-maximized");

        // Headless mode
        if (headless) {
            edgeOptions.addArguments("--headless");
        }

        edgeOptions.addArguments("--remote-allow-origins=*");

        driver.set(new EdgeDriver(edgeOptions));
    }

    /**
     * Initialize Safari WebDriver
     * Note: Safari doesn't support headless mode
     */
    private static void initializeSafariDriver() {
        driver.set(new SafariDriver());
    }

    /**
     * Initialize RemoteWebDriver for Selenium Grid/Cloud execution
     * @param browserName Name of the browser
     * @param gridUrl Selenium Grid hub URL
     * @param headless true for headless mode
     */
    public static void initializeRemoteDriver(String browserName, String gridUrl, boolean headless) {
        if (gridUrl == null || gridUrl.trim().isEmpty()) {
            throw new RuntimeException("Grid URL cannot be null or empty");
        }

        setBrowserName(browserName.toLowerCase().trim());

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(getBrowserName());

            switch (getBrowserName()) {
                case Constants.CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    capabilities.merge(chromeOptions);
                    break;

                case Constants.FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    capabilities.merge(firefoxOptions);
                    break;

                case Constants.EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless");
                    }
                    capabilities.merge(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("Browser not supported for remote execution: " + browserName);
            }

            driver.set(new RemoteWebDriver(new URL(gridUrl), capabilities));
            configureDriver();

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RemoteWebDriver", e);
        }
    }

    /**
     * Configure WebDriver with common settings
     */
    private static void configureDriver() {
        WebDriver webDriver = getDriver();

        // Maximize window (except for headless mode)
        try {
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            // Ignore maximize errors in headless mode
            System.out.println("Window maximize not supported in current mode");
        }

        // Set timeouts
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.PAGE_LOAD_TIMEOUT));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        // Clear cookies
        webDriver.manage().deleteAllCookies();
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance for current thread
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException("WebDriver not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }

    /**
     * Get current browser name
     * @return Browser name for current thread
     */
    public static String getBrowserName() {
        return browserName.get();
    }

    /**
     * Set browser name for current thread
     * @param browser Browser name
     */
    private static void setBrowserName(String browser) {
        browserName.set(browser);
    }

    /**
     * Close current WebDriver instance and clean up thread local variables
     */
    public static void closeDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                System.err.println("Error closing WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
                browserName.remove();
            }
        }
    }

    /**
     * Check if WebDriver is initialized
     * @return true if WebDriver is initialized, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }

    /**
     * Get WebDriver session ID (useful for debugging)
     * @return Session ID as String
     */
    public static String getSessionId() {
        WebDriver webDriver = driver.get();
        if (webDriver != null && webDriver instanceof RemoteWebDriver) {
            return ((RemoteWebDriver) webDriver).getSessionId().toString();
        }
        return "N/A";
    }

    /**
     * Take screenshot and return as base64 string
     * @return Screenshot as base64 string
     */
    public static String getScreenshotAsBase64() {
        WebDriver webDriver = getDriver();
        if (webDriver instanceof org.openqa.selenium.TakesScreenshot) {
            return ((org.openqa.selenium.TakesScreenshot) webDriver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BASE64);
        }
        return null;
    }
}

