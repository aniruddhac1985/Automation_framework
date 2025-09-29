package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import utilities.Constants;
import java.util.regex.Pattern;

/**
 * CheckoutStepOnePage class represents the first step of checkout process in SauceDemo application
 * Contains methods to interact with user information form and validation
 * URL: https://www.saucedemo.com/checkout-step-one.html
 */
public class CheckoutStepOnePage extends BasePage {

    // ========================
    // PAGE HEADER ELEMENTS
    // ========================

    /** Page title element */
    @FindBy(className = "title")
    private WebElement pageTitle;

    /** Secondary header container */
    @FindBy(className = "header_secondary_container")
    private WebElement secondaryHeader;

    // ========================
    // FORM INPUT ELEMENTS
    // ========================

    /** First name input field */
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    /** Last name input field */
    @FindBy(id = "last-name")
    private WebElement lastNameField;

    /** Postal code input field */
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    // ========================
    // FORM ACTION BUTTONS
    // ========================

    /** Continue button to proceed to next checkout step */
    @FindBy(id = "continue")
    private WebElement continueButton;

    /** Cancel button to return to cart */
    @FindBy(id = "cancel")
    private WebElement cancelButton;

    // ========================
    // ERROR MESSAGE ELEMENTS
    // ========================

    /** Error message container */
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    /** Error button (X to close error message) */
    @FindBy(className = "error-button")
    private WebElement errorCloseButton;

    // ========================
    // FORM CONTAINER ELEMENTS
    // ========================

    /** Checkout information container */
    @FindBy(className = "checkout_info")
    private WebElement checkoutInfoContainer;

    /** Form wrapper */
    @FindBy(className = "checkout_info_wrapper")
    private WebElement checkoutInfoWrapper;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize CheckoutStepOnePage
     * @param driver WebDriver instance
     */
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    // ========================
    // PAGE INFORMATION METHODS
    // ========================

    /**
     * Get page title text
     * @return Page title as String
     */
    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOf(pageTitle)).getText();
    }

    /**
     * Check if checkout step one page is loaded
     * @return true if page is loaded correctly, false otherwise
     */
    public boolean isCheckoutStepOnePageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageTitle))
                    .getText().equals(Constants.CHECKOUT_STEP_ONE_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // FORM INPUT METHODS
    // ========================

    /**
     * Enter first name in the form field
     * @param firstName First name to enter
     */
    public void enterFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(firstNameField));
        field.clear();
        if (firstName != null) {
            field.sendKeys(firstName);
        }
    }

    /**
     * Enter last name in the form field
     * @param lastName Last name to enter
     */
    public void enterLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(lastNameField));
        field.clear();
        if (lastName != null) {
            field.sendKeys(lastName);
        }
    }

    /**
     * Enter postal code in the form field
     * @param postalCode Postal code to enter
     */
    public void enterPostalCode(String postalCode) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(postalCodeField));
        field.clear();
        if (postalCode != null) {
            field.sendKeys(postalCode);
        }
    }

    /**
     * Fill entire checkout form with user information
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     */
    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    /**
     * Fill checkout form using test data from Constants
     */
    public void fillCheckoutFormWithTestData() {
        fillCheckoutForm(
                Constants.TEST_FIRST_NAME,
                Constants.TEST_LAST_NAME,
                Constants.TEST_POSTAL_CODE
        );
    }

    // ========================
    // FORM VALUE RETRIEVAL METHODS
    // ========================

    /**
     * Get current value of first name field
     * @return First name field value
     */
    public String getFirstNameValue() {
        return firstNameField.getAttribute("value");
    }

    /**
     * Get current value of last name field
     * @return Last name field value
     */
    public String getLastNameValue() {
        return lastNameField.getAttribute("value");
    }

    /**
     * Get current value of postal code field
     * @return Postal code field value
     */
    public String getPostalCodeValue() {
        return postalCodeField.getAttribute("value");
    }

    /**
     * Get placeholder text for first name field
     * @return Placeholder text
     */
    public String getFirstNamePlaceholder() {
        return firstNameField.getAttribute("placeholder");
    }

    /**
     * Get placeholder text for last name field
     * @return Placeholder text
     */
    public String getLastNamePlaceholder() {
        return lastNameField.getAttribute("placeholder");
    }

    /**
     * Get placeholder text for postal code field
     * @return Placeholder text
     */
    public String getPostalCodePlaceholder() {
        return postalCodeField.getAttribute("placeholder");
    }

    // ========================
    // FORM VALIDATION METHODS
    // ========================

    /**
     * Validate first name field value
     * @param firstName Expected first name
     * @return true if values match, false otherwise
     */
    public boolean validateFirstName(String firstName) {
        return getFirstNameValue().equals(firstName);
    }

    /**
     * Validate last name field value
     * @param lastName Expected last name
     * @return true if values match, false otherwise
     */
    public boolean validateLastName(String lastName) {
        return getLastNameValue().equals(lastName);
    }

    /**
     * Validate postal code field value
     * @param postalCode Expected postal code
     * @return true if values match, false otherwise
     */
    public boolean validatePostalCode(String postalCode) {
        return getPostalCodeValue().equals(postalCode);
    }

    /**
     * Validate postal code format using regex
     * @return true if postal code format is valid, false otherwise
     */
    public boolean isPostalCodeFormatValid() {
        String postalCode = getPostalCodeValue();
        return Pattern.matches(Constants.POSTAL_CODE_REGEX, postalCode);
    }

    /**
     * Check if all required fields are filled
     * @return true if all fields have values, false otherwise
     */
    public boolean areAllFieldsFilled() {
        return !getFirstNameValue().trim().isEmpty() &&
                !getLastNameValue().trim().isEmpty() &&
                !getPostalCodeValue().trim().isEmpty();
    }

    /**
     * Validate all form fields with expected values
     * @param expectedFirstName Expected first name
     * @param expectedLastName Expected last name
     * @param expectedPostalCode Expected postal code
     * @return true if all fields match expected values, false otherwise
     */
    public boolean validateAllFormFields(String expectedFirstName,
                                         String expectedLastName,
                                         String expectedPostalCode) {
        return validateFirstName(expectedFirstName) &&
                validateLastName(expectedLastName) &&
                validatePostalCode(expectedPostalCode);
    }

    // ========================
    // FIELD STATE METHODS
    // ========================

    /**
     * Check if first name field is enabled and editable
     * @return true if field is enabled, false otherwise
     */
    public boolean isFirstNameFieldEnabled() {
        return firstNameField.isEnabled();
    }

    /**
     * Check if last name field is enabled and editable
     * @return true if field is enabled, false otherwise
     */
    public boolean isLastNameFieldEnabled() {
        return lastNameField.isEnabled();
    }

    /**
     * Check if postal code field is enabled and editable
     * @return true if field is enabled, false otherwise
     */
    public boolean isPostalCodeFieldEnabled() {
        return postalCodeField.isEnabled();
    }

    /**
     * Check if all form fields are enabled
     * @return true if all fields are enabled, false otherwise
     */
    public boolean areAllFieldsEnabled() {
        return isFirstNameFieldEnabled() &&
                isLastNameFieldEnabled() &&
                isPostalCodeFieldEnabled();
    }

    // ========================
    // FORM INTERACTION METHODS
    // ========================

    /**
     * Clear all form fields
     */
    public void clearAllFields() {
        firstNameField.clear();
        lastNameField.clear();
        postalCodeField.clear();
    }

    /**
     * Focus on first name field
     */
    public void focusOnFirstName() {
        firstNameField.click();
    }

    /**
     * Focus on last name field
     */
    public void focusOnLastName() {
        lastNameField.click();
    }

    /**
     * Focus on postal code field
     */
    public void focusOnPostalCode() {
        postalCodeField.click();
    }

    /**
     * Use Tab key to navigate through form fields
     */
    public void tabThroughFields() {
        firstNameField.click();
        firstNameField.sendKeys(Keys.TAB);
        lastNameField.sendKeys(Keys.TAB);
        postalCodeField.sendKeys(Keys.TAB);
    }

    /**
     * Use Enter key to submit form from current field
     */
    public void submitFormWithEnter() {
        postalCodeField.sendKeys(Keys.ENTER);
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Click continue button to proceed to next checkout step
     * @return CheckoutStepTwoPage object
     */
    public CheckoutStepTwoPage continueToNextStep() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return new CheckoutStepTwoPage(driver);
    }

    /**
     * Fill form and continue to next step in one operation
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     * @return CheckoutStepTwoPage object
     */
    public CheckoutStepTwoPage completeUserInformation(String firstName,
                                                       String lastName,
                                                       String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        return continueToNextStep();
    }

    /**
     * Fill form with test data and continue to next step
     * @return CheckoutStepTwoPage object
     */
    public CheckoutStepTwoPage completeUserInformationWithTestData() {
        return completeUserInformation(
                Constants.TEST_FIRST_NAME,
                Constants.TEST_LAST_NAME,
                Constants.TEST_POSTAL_CODE
        );
    }

    /**
     * Cancel checkout and return to cart
     * @return CartPage object
     */
    public CartPage cancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        return new CartPage(driver);
    }

    // ========================
    // BUTTON STATE METHODS
    // ========================

    /**
     * Check if continue button is enabled and clickable
     * @return true if button is clickable, false otherwise
     */
    public boolean isContinueButtonEnabled() {
        try {
            return continueButton.isDisplayed() && continueButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if cancel button is enabled and clickable
     * @return true if button is clickable, false otherwise
     */
    public boolean isCancelButtonEnabled() {
        try {
            return cancelButton.isDisplayed() && cancelButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get continue button text
     * @return Button text as String
     */
    public String getContinueButtonText() {
        return continueButton.getText();
    }

    /**
     * Get cancel button text
     * @return Button text as String
     */
    public String getCancelButtonText() {
        return cancelButton.getText();
    }

    // ========================
    // ERROR HANDLING METHODS
    // ========================

    /**
     * Get error message text
     * @return Error message as String
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    /**
     * Check if error message is displayed
     * @return true if error message is visible, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Close error message by clicking X button
     */
    public void closeErrorMessage() {
        if (isErrorMessageDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(errorCloseButton)).click();
        }
    }

    /**
     * Verify specific error message is displayed
     * @param expectedErrorMessage Expected error message text
     * @return true if expected error is displayed, false otherwise
     */
    public boolean verifyErrorMessage(String expectedErrorMessage) {
        try {
            return isErrorMessageDisplayed() && getErrorMessage().equals(expectedErrorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if first name error is displayed
     * @return true if first name error is shown, false otherwise
     */
    public boolean isFirstNameErrorDisplayed() {
        return verifyErrorMessage(Constants.MISSING_FIRST_NAME_ERROR);
    }

    /**
     * Check if last name error is displayed
     * @return true if last name error is shown, false otherwise
     */
    public boolean isLastNameErrorDisplayed() {
        return verifyErrorMessage(Constants.MISSING_LAST_NAME_ERROR);
    }

    /**
     * Check if postal code error is displayed
     * @return true if postal code error is shown, false otherwise
     */
    public boolean isPostalCodeErrorDisplayed() {
        return verifyErrorMessage(Constants.MISSING_POSTAL_CODE_ERROR);
    }

    // ========================
    // FORM SUBMISSION WITH VALIDATION
    // ========================

    /**
     * Attempt to continue with empty first name
     * @return true if error is displayed, false otherwise
     */
    public boolean testMissingFirstNameValidation() {
        fillCheckoutForm("", Constants.TEST_LAST_NAME, Constants.TEST_POSTAL_CODE);
        continueToNextStep();
        return isFirstNameErrorDisplayed();
    }

    /**
     * Attempt to continue with empty last name
     * @return true if error is displayed, false otherwise
     */
    public boolean testMissingLastNameValidation() {
        fillCheckoutForm(Constants.TEST_FIRST_NAME, "", Constants.TEST_POSTAL_CODE);
        continueToNextStep();
        return isLastNameErrorDisplayed();
    }

    /**
     * Attempt to continue with empty postal code
     * @return true if error is displayed, false otherwise
     */
    public boolean testMissingPostalCodeValidation() {
        fillCheckoutForm(Constants.TEST_FIRST_NAME, Constants.TEST_LAST_NAME, "");
        continueToNextStep();
        return isPostalCodeErrorDisplayed();
    }

    // ========================
    // FORM INFORMATION METHODS
    // ========================

    /**
     * Get all form information as formatted string
     * @return Form information as String
     */
    public String getFormInformation() {
        StringBuilder formInfo = new StringBuilder();

        try {
            formInfo.append("Checkout Step One Form Information:\n");
            formInfo.append("=====================================\n");
            formInfo.append("Page Title: ").append(getPageTitle()).append("\n");
            formInfo.append("First Name: '").append(getFirstNameValue()).append("'\n");
            formInfo.append("Last Name: '").append(getLastNameValue()).append("'\n");
            formInfo.append("Postal Code: '").append(getPostalCodeValue()).append("'\n");
            formInfo.append("All Fields Filled: ").append(areAllFieldsFilled()).append("\n");
            formInfo.append("Continue Button Enabled: ").append(isContinueButtonEnabled()).append("\n");
            formInfo.append("Cancel Button Enabled: ").append(isCancelButtonEnabled()).append("\n");
            formInfo.append("Error Message Displayed: ").append(isErrorMessageDisplayed()).append("\n");

            if (isErrorMessageDisplayed()) {
                formInfo.append("Error Message: '").append(getErrorMessage()).append("'\n");
            }

        } catch (Exception e) {
            formInfo.append("Error retrieving form information: ").append(e.getMessage());
        }

        return formInfo.toString();
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for checkout step one page to fully load
     * @param timeoutSeconds Timeout in seconds
     * @return true if page loaded successfully, false otherwise
     */
    public boolean waitForPageLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            wait.until(ExpectedConditions.visibilityOf(firstNameField));
            wait.until(ExpectedConditions.visibilityOf(lastNameField));
            wait.until(ExpectedConditions.visibilityOf(postalCodeField));
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for error message to appear
     * @param timeoutSeconds Timeout in seconds
     * @return true if error message appeared, false otherwise
     */
    public boolean waitForErrorMessage(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for error message to disappear
     * @param timeoutSeconds Timeout in seconds
     * @return true if error message disappeared, false otherwise
     */
    public boolean waitForErrorMessageToDisappear(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.invisibilityOf(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for form to be filled with expected values
     * @param expectedFirstName Expected first name
     * @param expectedLastName Expected last name
     * @param expectedPostalCode Expected postal code
     * @param timeoutSeconds Timeout in seconds
     * @return true if form contains expected values, false otherwise
     */
    public boolean waitForFormFill(String expectedFirstName,
                                   String expectedLastName,
                                   String expectedPostalCode,
                                   int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> validateAllFormFields(expectedFirstName, expectedLastName, expectedPostalCode));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

