package CoverFoxUsingexcel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CoverFoxHomePage {

	
	//variables
		@FindBy(xpath="//button[@title='Get Started']") private WebElement Get_Started_Btn;
		
		//constructor
		public CoverFoxHomePage(WebDriver driver)
		{
			PageFactory.initElements(driver, this);
		}
		
		//methods
		public void clickOnGetStarted()
		{
			Get_Started_Btn.click();
		}
}
