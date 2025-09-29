package PageObjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Wait fluentWait;

    // Common header elements
    @FindBy(className = "app_logo")
    protected WebElement appLogo;

    @FindBy(id = "react-burger-menu-btn")
    protected WebElement hamburgerMenu;

    @FindBy(className = "shopping_cart_link")
    protected WebElement cartLink;

    @FindBy(className = "shopping_cart_badge")
    protected WebElement cartBadge;

    @FindBy(id = "logout_sidebar_link")
    protected WebElement logoutLink;

    @FindBy(id = "about_sidebar_link")
    protected WebElement aboutLink;

    @FindBy(id = "reset_sidebar_link")
    protected WebElement resetAppStateLink;

    @FindBy(id = "inventory_sidebar_link")
    protected WebElement allItemsLink;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.fluentWait = new FluentWait(driver).withTimeout(Duration.ofSeconds(60)).ignoring(NoSuchElementException.class).pollingEvery(Duration.ofMillis(500));
        PageFactory.initElements(driver, this);
    }

    public void clickHamburgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu)).click();
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public String getCartBadgeText() {
        return wait.until(ExpectedConditions.visibilityOf(cartBadge)).getText();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(getCartBadgeText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void logout() {
        clickHamburgerMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public String getAppTitle() {
        return wait.until(ExpectedConditions.visibilityOf(appLogo)).getText();
    }
}
