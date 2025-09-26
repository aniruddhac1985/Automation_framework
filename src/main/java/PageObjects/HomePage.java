package PageObjects;

import utilities.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
		
	@FindBy(xpath="/html/body/div[1]/div/a[3]")
	public WebElement logIn;
	
	public HomePage open(String url) {
	
		System.out.println("Page Opened");
		DriverManager.getDriver().navigate().to(url);
		return (HomePage) openPage(HomePage.class);
	}
	
	public LoginPage gotoLogin(){
		System.out.println("inside go to login");
		click(logIn, "Login Link");
		return (LoginPage) openPage(LoginPage.class);
			
	}


	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(logIn);
	}
	
	
	

}
