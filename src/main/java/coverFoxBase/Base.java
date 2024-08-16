package coverFoxBase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
 import org.testng.Reporter;

public class Base {
	
	static protected WebDriver driver;
	public void openBrowser()
	{
		ChromeOptions opt =new ChromeOptions();
		opt.addArguments("--disable-notifications");
		driver=new ChromeDriver(opt);
		driver.manage().window().maximize();
		driver.get("https://www.coverfox.com/");
		Reporter.log("launching browser",true);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		Reporter.log("waiting...",false);
	}
	public void browserClose() throws InterruptedException
	{
		Reporter.log("closing browser",true);
		Thread.sleep(1000);
		driver.close();
	}

}
