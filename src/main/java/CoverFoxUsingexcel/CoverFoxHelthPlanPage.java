package CoverFoxUsingexcel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CoverFoxHelthPlanPage {

	//data members
	
	@FindBy(xpath="//div[text()=' Next ']") private WebElement nextBtn;
	
	//constructor
	public CoverFoxHelthPlanPage(WebDriver driver)
	{
		PageFactory.initElements(driver , this);
	}
	
	//methods
	
	public void clickOnNextBtn()
	{
		nextBtn.click();
	}
	
}
