package CoverFoxtc;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

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
import org.testng.annotations.Test;

import CoverFoxUsingexcel.CoverFoxAddressDetailsPage;
import CoverFoxUsingexcel.CoverFoxHelthPlanPage;
import CoverFoxUsingexcel.CoverFoxHomePage;
import CoverFoxUsingexcel.CoverFoxMemberDetailsPage;
import CoverFoxUsingexcel.CoverFoxResultsPage;
import coverFoxBase.Base;

public class CoverFoxTc extends Base {
	WebDriver driver;
	CoverFoxHomePage homePage;
	CoverFoxHelthPlanPage healthPage;
	CoverFoxAddressDetailsPage addressPage;
	CoverFoxMemberDetailsPage memberDetails;
	CoverFoxResultsPage resultPage;

	@BeforeTest
	public void launchBrowser() throws InterruptedException {
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
		Thread.sleep(10000);
		homePage = new CoverFoxHomePage(driver);
		homePage.clickOnGetStarted();

		// Health-Plan Page
		Thread.sleep(2000);
		healthPage = new CoverFoxHelthPlanPage(driver);
		healthPage.clickOnNextBtn();

		// Member-details Page
		Thread.sleep(2000);
		memberDetails = new CoverFoxMemberDetailsPage(driver);
		memberDetails.selectAgeDropDown(age);
		memberDetails.clickOnNextBtn();

		Thread.sleep(2000);

		// Address-Details Page
		addressPage = new CoverFoxAddressDetailsPage(driver);
		addressPage.enterPinCode(pinCode);
		addressPage.enterMobileNumber(mobNo);
		addressPage.clickOnContinueBtn();

		Thread.sleep(2000);
	}

	@Test
	public void validateBanners()

	{
		// Result-Page
		resultPage = new CoverFoxResultsPage(driver);
		Assert.assertEquals(resultPage.getTotalBanners(), resultPage.getTextOnHomePage(),
				"No of Banners and text is not  matching");

	}

	@Test
	public void validatePresenceOfSortDropdown() {
		resultPage = new CoverFoxResultsPage(driver);
		Assert.assertTrue(resultPage.sortPlanDropdownIsDisplayed(),
				"Sort Plan Dropdown is not displayed , Tc is failed");
	}

	@AfterClass
	public void closeBrowser() throws InterruptedException

	{
//		Thread.sleep(3000);
//		driver.close();
	}
}
