package CoverFoxtc;

import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import CoverFoxUsingexcel.CoverFoxAddressDetailsPage;
import CoverFoxUsingexcel.CoverFoxHelthPlanPage;
import CoverFoxUsingexcel.CoverFoxHomePage;
import CoverFoxUsingexcel.CoverFoxMemberDetailsPage;
import CoverFoxUsingexcel.CoverFoxResultsPage;



@Listeners(CoverFoxUtility.Listner.class)

public class ListnerTestClass {
	WebDriver driver;
	CoverFoxHomePage homePage;
	CoverFoxHelthPlanPage healthPage;
	CoverFoxAddressDetailsPage addressPage;
	CoverFoxMemberDetailsPage memberDetails;
	CoverFoxResultsPage resultPage;
 
	public static Logger logger;
	@BeforeTest
	public void launchBrowser() throws InterruptedException {
		
		logger=Logger.getLogger("CoverFoxInsurence");
		 PropertyConfigurator.configure("Log4j.properties");
	     logger.info("opening Browser");
		ChromeOptions co = new ChromeOptions();
		
		co.addArguments("--disable-notifications");
		driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
		driver.get("https://www.coverfox.com/");

	}

	@BeforeClass
	public void preconditions() throws EncryptedDocumentException, IOException, InterruptedException {
		// Reading data from excel
		// as we are taking this variables are local so we have to intialize them here
		// otherwise we can also declare them as global and then intialize in beforetest
		// or in this method
		// NOTE : We can also do it by using utility common methods just by getting data
		// directly into the arguments by calling utility method
		String filePath = System.getProperty("user.dir") + "\\CoverFox.xlsx";
		FileInputStream fi = new FileInputStream(filePath);
		Sheet mySheet = WorkbookFactory.create(fi).getSheet("Sheet3");
		String pinCode = mySheet.getRow(3).getCell(0).getStringCellValue();
		String mobNo = mySheet.getRow(1).getCell(1).getStringCellValue();
		String age = mySheet.getRow(1).getCell(2).getStringCellValue();

		// Home-Page
		Thread.sleep(1000);
		homePage = new CoverFoxHomePage(driver);
		homePage.clickOnGetStarted();
		logger.info("Click on gender button");

		// Health-Plan Page
		Thread.sleep(1000);
		healthPage = new CoverFoxHelthPlanPage(driver);
		healthPage.clickOnNextBtn();
		logger.info("Click on next button");

		// Member-details Page
		Thread.sleep(1000);
		memberDetails = new CoverFoxMemberDetailsPage(driver);
		memberDetails.selectAgeDropDown(age);
		logger.warn("Age shoud be btn 18 to 90");
		logger.info("Age is selected");
		memberDetails.clickOnNextBtn();
		logger.info("Click on next button");
		

		Thread.sleep(1000);

		// Address-Details Page
		addressPage = new CoverFoxAddressDetailsPage(driver);
		
		addressPage.enterPinCode(pinCode);
		logger.warn("enter valid pin code");
		logger.info("pincode is selected");
		addressPage.enterMobileNumber(mobNo);
		logger.warn("enter valid moblie no");
		logger.info("moblile number is selected");
		addressPage.clickOnContinueBtn();
		logger.info(" click on Continue button");
		Thread.sleep(2000);
	}

	@Test
	public void validateBanners()

	{
		// Result-Page
		resultPage = new CoverFoxResultsPage(driver);
		Assert.fail();
		Assert.assertEquals(resultPage.getTotalBanners(), resultPage.getTextOnHomePage(),
				"No of Banners and text is not  matching");
		logger.info("validating banners");
	}

	@Test
	public void validatePresenceOfSortDropdown() {
		
		resultPage = new CoverFoxResultsPage(driver);
		//Assert.fail();
		Assert.assertTrue(resultPage.sortPlanDropdownIsDisplayed(),
				"Sort Plan Dropdown is not displayed , Tc is failed");
		logger.info("validating presence of sort drop down");
		
	}

	@AfterClass
	public void closeBrowser() throws InterruptedException

	{
//		Thread.sleep(3000);
   driver.close();
   logger.info("closing browser");
	}
}


