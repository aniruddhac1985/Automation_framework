package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AppPage extends BasePage{
	
	
	@FindBy(xpath="//*[contains(text(),'Cliq')]")
	public WebElement cliq;
	

	@FindBy(xpath="//*[contains(text(),'Creator')]")
	public WebElement creator;
	
	
	@FindBy(xpath="//*[contains(text(),'CRM')]")
	public WebElement crm;
	
	
	@FindBy(xpath="//*[contains(text(),'SalesIQ')]")
	public WebElement salesIQ;
	
	
	@FindBy(xpath="//*[contains(text(),'Subscriptions')]")
	public WebElement subscriptions;
	
	
	
	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(crm);
	}

	

	
	
	
}
