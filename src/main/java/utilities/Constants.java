package utilities;

/**
 * Constants class contains all application constants, test data, and configuration values
 * Used throughout the SauceDemo automation framework for maintainability and consistency
 */
public class Constants {

    // ========================
    // URL CONSTANTS
    // ========================

    /** Base URL for SauceDemo application */
    public static final String BASE_URL = "https://www.saucedemo.com/";

    /** Inventory/Products page URL */
    public static final String INVENTORY_URL = BASE_URL + "inventory.html";

    /** Shopping cart page URL */
    public static final String CART_URL = BASE_URL + "cart.html";

    /** Checkout step one page URL */
    public static final String CHECKOUT_STEP_ONE_URL = BASE_URL + "checkout-step-one.html";

    /** Checkout step two page URL */
    public static final String CHECKOUT_STEP_TWO_URL = BASE_URL + "checkout-step-two.html";

    /** Checkout complete page URL */
    public static final String CHECKOUT_COMPLETE_URL = BASE_URL + "checkout-complete.html";

    /** Product detail page URL pattern */
    public static final String PRODUCT_DETAIL_URL_PATTERN = BASE_URL + "inventory-item.html?id=";

    // ========================
    // TEST USER CREDENTIALS
    // ========================

    /** Standard user with normal behavior */
    public static final String STANDARD_USER = "standard_user";

    /** User that has been locked out of the application */
    public static final String LOCKED_OUT_USER = "locked_out_user";

    /** User that encounters problems with various elements */
    public static final String PROBLEM_USER = "problem_user";

    /** User with performance glitches and slow responses */
    public static final String PERFORMANCE_GLITCH_USER = "performance_glitch_user";

    /** User that encounters errors on various pages */
    public static final String ERROR_USER = "error_user";

    /** User with visual differences in the interface */
    public static final String VISUAL_USER = "visual_user";

    /** Common password for all test users */
    public static final String PASSWORD = "secret_sauce";

    // ========================
    // PRODUCT INFORMATION
    // ========================

    /** Sauce Labs Backpack product name */
    public static final String SAUCE_LABS_BACKPACK = "Sauce Labs Backpack";

    /** Sauce Labs Bike Light product name */
    public static final String SAUCE_LABS_BIKE_LIGHT = "Sauce Labs Bike Light";

    /** Sauce Labs Bolt T-Shirt product name */
    public static final String SAUCE_LABS_BOLT_TSHIRT = "Sauce Labs Bolt T-Shirt";

    /** Sauce Labs Fleece Jacket product name */
    public static final String SAUCE_LABS_FLEECE_JACKET = "Sauce Labs Fleece Jacket";

    /** Sauce Labs Onesie product name */
    public static final String SAUCE_LABS_ONESIE = "Sauce Labs Onesie";

    /** Test.allTheThings() T-Shirt (Red) product name */
    public static final String TEST_ALLTHETHINGS_TSHIRT = "Test.allTheThings() T-Shirt (Red)";

    // ========================
    // PRODUCT PRICES
    // ========================

    /** Sauce Labs Backpack price */
    public static final String BACKPACK_PRICE = "$29.99";

    /** Sauce Labs Bike Light price */
    public static final String BIKE_LIGHT_PRICE = "$9.99";

    /** Sauce Labs Bolt T-Shirt price */
    public static final String BOLT_TSHIRT_PRICE = "$15.99";

    /** Sauce Labs Fleece Jacket price */
    public static final String FLEECE_JACKET_PRICE = "$49.99";

    /** Sauce Labs Onesie price */
    public static final String ONESIE_PRICE = "$7.99";

    /** Test.allTheThings() T-Shirt price */
    public static final String TEST_TSHIRT_PRICE = "$15.99";

    // ========================
    // PRODUCT IDS
    // ========================

    /** Sauce Labs Backpack product ID */
    public static final String BACKPACK_ID = "4";

    /** Sauce Labs Bike Light product ID */
    public static final String BIKE_LIGHT_ID = "0";

    /** Sauce Labs Bolt T-Shirt product ID */
    public static final String BOLT_TSHIRT_ID = "1";

    /** Sauce Labs Fleece Jacket product ID */
    public static final String FLEECE_JACKET_ID = "5";

    /** Sauce Labs Onesie product ID */
    public static final String ONESIE_ID = "2";

    /** Test.allTheThings() T-Shirt product ID */
    public static final String TEST_TSHIRT_ID = "3";

    // ========================
    // SORTING OPTIONS
    // ========================

    /** Sort products by name A to Z */
    public static final String SORT_NAME_A_TO_Z = "Name (A to Z)";

    /** Sort products by name Z to A */
    public static final String SORT_NAME_Z_TO_A = "Name (Z to A)";

    /** Sort products by price low to high */
    public static final String SORT_PRICE_LOW_TO_HIGH = "Price (low to high)";

    /** Sort products by price high to low */
    public static final String SORT_PRICE_HIGH_TO_LOW = "Price (high to low)";

    // Sort option values for dropdown
    /** Sort value for name A to Z */
    public static final String SORT_VALUE_NAME_A_Z = "az";

    /** Sort value for name Z to A */
    public static final String SORT_VALUE_NAME_Z_A = "za";

    /** Sort value for price low to high */
    public static final String SORT_VALUE_PRICE_LOW_HIGH = "lohi";

    /** Sort value for price high to low */
    public static final String SORT_VALUE_PRICE_HIGH_LOW = "hilo";

    // ========================
    // ERROR MESSAGES
    // ========================

    /** Error message for locked out user */
    public static final String LOCKED_OUT_USER_ERROR = "Epic sadface: Sorry, this user has been locked out.";

    /** Error message for invalid credentials */
    public static final String INVALID_CREDENTIALS_ERROR = "Epic sadface: Username and password do not match any user in this service";

    /** Error message for missing username */
    public static final String MISSING_USERNAME_ERROR = "Epic sadface: Username is required";

    /** Error message for missing password */
    public static final String MISSING_PASSWORD_ERROR = "Epic sadface: Password is required";

    /** Error message for missing first name in checkout */
    public static final String MISSING_FIRST_NAME_ERROR = "Error: First Name is required";

    /** Error message for missing last name in checkout */
    public static final String MISSING_LAST_NAME_ERROR = "Error: Last Name is required";

    /** Error message for missing postal code in checkout */
    public static final String MISSING_POSTAL_CODE_ERROR = "Error: Postal Code is required";

    // ========================
    // SUCCESS MESSAGES
    // ========================

    /** Order completion success header */
    public static final String ORDER_SUCCESS_HEADER = "Thank you for your order!";

    /** Order completion success message */
    public static final String ORDER_SUCCESS_MESSAGE = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";

    // ========================
    // PAGE TITLES
    // ========================

    /** Login page title */
    public static final String LOGIN_PAGE_TITLE = "Swag Labs";

    /** Inventory page title */
    public static final String INVENTORY_PAGE_TITLE = "Products";

    /** Cart page title */
    public static final String CART_PAGE_TITLE = "Your Cart";

    /** Checkout step one page title */
    public static final String CHECKOUT_STEP_ONE_TITLE = "Checkout: Your Information";

    /** Checkout step two page title */
    public static final String CHECKOUT_STEP_TWO_TITLE = "Checkout: Overview";

    /** Checkout complete page title */
    public static final String CHECKOUT_COMPLETE_TITLE = "Checkout: Complete!";

    // ========================
    // TIMEOUT CONSTANTS
    // ========================

    /** Implicit wait timeout in seconds */
    public static final int IMPLICIT_WAIT = 10;

    /** Explicit wait timeout in seconds */
    public static final int EXPLICIT_WAIT = 10;

    /** Page load timeout in seconds */
    public static final int PAGE_LOAD_TIMEOUT = 30;

    /** Script timeout in seconds */
    public static final int SCRIPT_TIMEOUT = 30;

    /** Short wait timeout in seconds */
    public static final int SHORT_WAIT = 5;

    /** Long wait timeout in seconds */
    public static final int LONG_WAIT = 20;

    // ========================
    // BROWSER CONSTANTS
    // ========================

    /** Chrome browser identifier */
    public static final String CHROME = "chrome";

    /** Firefox browser identifier */
    public static final String FIREFOX = "firefox";

    /** Microsoft Edge browser identifier */
    public static final String EDGE = "edge";

    /** Safari browser identifier */
    public static final String SAFARI = "safari";

    // ========================
    // TEST DATA CONSTANTS
    // ========================

    /** Test first name for checkout */
    public static final String TEST_FIRST_NAME = "John";

    /** Test last name for checkout */
    public static final String TEST_LAST_NAME = "Doe";

    /** Test postal code for checkout */
    public static final String TEST_POSTAL_CODE = "12345";

    /** Test email address */
    public static final String TEST_EMAIL = "test@example.com";

    /** Test phone number */
    public static final String TEST_PHONE = "555-123-4567";

    /** Test address */
    public static final String TEST_ADDRESS = "123 Test Street";

    /** Test city */
    public static final String TEST_CITY = "Test City";

    /** Test state */
    public static final String TEST_STATE = "TS";

    // ========================
    // PAYMENT AND SHIPPING INFO
    // ========================

    /** Payment information displayed in checkout */
    public static final String PAYMENT_INFO = "SauceCard #31337";

    /** Shipping information displayed in checkout */
    public static final String SHIPPING_INFO = "Free Pony Express Delivery!";

    /** Tax rate for calculations (8% as decimal) */
    public static final double TAX_RATE = 0.08;

    // ========================
    // FILE PATHS
    // ========================

    /** Configuration file path */
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    /** Test data Excel file path */
    public static final String TEST_DATA_FILE_PATH = "src/test/resources/testdata.xlsx";

    /** Screenshots directory path */
    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    /** Reports directory path */
    public static final String REPORTS_PATH = "test-output/reports/";

    /** Logs directory path */
    public static final String LOGS_PATH = "test-output/logs/";

    // ========================
    // LOCATOR CONSTANTS
    // ========================

    /** Username field locator */
    public static final String USERNAME_FIELD_ID = "user-name";

    /** Password field locator */
    public static final String PASSWORD_FIELD_ID = "password";

    /** Login button locator */
    public static final String LOGIN_BUTTON_ID = "login-button";

    /** Error message locator */
    public static final String ERROR_MESSAGE_CSS = "[data-test='error']";

    /** Cart badge locator */
    public static final String CART_BADGE_CLASS = "shopping_cart_badge";

    /** Add to cart button prefix */
    public static final String ADD_TO_CART_BUTTON_PREFIX = "add-to-cart-";

    /** Remove button prefix */
    public static final String REMOVE_BUTTON_PREFIX = "remove-";

    // ========================
    // REGULAR EXPRESSIONS
    // ========================

    /** Regular expression for price format validation */
    public static final String PRICE_REGEX = "^\\$\\d+\\.\\d{2}$";

    /** Regular expression for postal code validation */
    public static final String POSTAL_CODE_REGEX = "^\\d{5}(-\\d{4})?$";

    /** Regular expression for email validation */
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

    // ========================
    // TEST ENVIRONMENT
    // ========================

    /** Development environment identifier */
    public static final String DEV_ENV = "dev";

    /** Testing environment identifier */
    public static final String TEST_ENV = "test";

    /** Staging environment identifier */
    public static final String STAGING_ENV = "staging";

    /** Production environment identifier */
    public static final String PROD_ENV = "prod";

    // ========================
    // API ENDPOINTS (Future Use)
    // ========================

    /** API base URL for future API testing integration */
    public static final String API_BASE_URL = "https://www.saucedemo.com/api/";

    /** Login API endpoint */
    public static final String LOGIN_API_ENDPOINT = API_BASE_URL + "auth/login";

    /** Products API endpoint */
    public static final String PRODUCTS_API_ENDPOINT = API_BASE_URL + "products";

    // ========================
    // UTILITY METHODS
    // ========================

    /**
     * Get all valid test users as array
     * @return Array of valid usernames
     */
    public static String[] getValidUsers() {
        return new String[]{
                STANDARD_USER,
                PROBLEM_USER,
                PERFORMANCE_GLITCH_USER,
                ERROR_USER,
                VISUAL_USER
        };
    }

    /**
     * Get all product names as array
     * @return Array of product names
     */
    public static String[] getAllProducts() {
        return new String[]{
                SAUCE_LABS_BACKPACK,
                SAUCE_LABS_BIKE_LIGHT,
                SAUCE_LABS_BOLT_TSHIRT,
                SAUCE_LABS_FLEECE_JACKET,
                SAUCE_LABS_ONESIE,
                TEST_ALLTHETHINGS_TSHIRT
        };
    }

    /**
     * Get all product prices as array
     * @return Array of product prices
     */
    public static String[] getAllPrices() {
        return new String[]{
                BACKPACK_PRICE,
                BIKE_LIGHT_PRICE,
                BOLT_TSHIRT_PRICE,
                FLEECE_JACKET_PRICE,
                ONESIE_PRICE,
                TEST_TSHIRT_PRICE
        };
    }

    /**
     * Get supported browsers as array
     * @return Array of supported browser names
     */
    public static String[] getSupportedBrowsers() {
        return new String[]{
                CHROME,
                FIREFOX,
                EDGE,
                SAFARI
        };
    }

    /**
     * Get all sort options as array
     * @return Array of sort option display texts
     */
    public static String[] getSortOptions() {
        return new String[]{
                SORT_NAME_A_TO_Z,
                SORT_NAME_Z_TO_A,
                SORT_PRICE_LOW_TO_HIGH,
                SORT_PRICE_HIGH_TO_LOW
        };
    }

    /**
     * Check if user is valid for login
     * @param username Username to validate
     * @return true if user is valid, false otherwise
     */
    public static boolean isValidUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        for (String validUser : getValidUsers()) {
            if (validUser.equals(username)) {
                return true;
            }
        }

        return username.equals(LOCKED_OUT_USER); // Locked out user is valid but restricted
    }

    /**
     * Check if browser is supported
     * @param browser Browser name to validate
     * @return true if browser is supported, false otherwise
     */
    public static boolean isSupportedBrowser(String browser) {
        if (browser == null || browser.trim().isEmpty()) {
            return false;
        }

        for (String supportedBrowser : getSupportedBrowsers()) {
            if (supportedBrowser.equalsIgnoreCase(browser.trim())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get product URL by product ID
     * @param productId Product ID
     * @return Complete product detail URL
     */
    public static String getProductUrl(String productId) {
        return PRODUCT_DETAIL_URL_PATTERN + productId;
    }

    /**
     * Private constructor to prevent instantiation
     * This class should only contain static constants and methods
     */
    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}
