package homeworktestNG;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddnewaccountonTechfiosSite {
	WebDriver driver;

	@BeforeMethod
	public void launchBrowser() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://techfios.com/test/billing/?ng=login/");
	}

	@Test
	public void addNewAccountOnTechFiosSite() throws InterruptedException {
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(By.xpath("//*[contains(@placeholder, 'ssword') and contains(@type, 'password')]"))
				.sendKeys("abc123");
		driver.findElement(By.xpath("//*[contains(text(), 'ign in') and @name = 'login']")).click();
		driver.findElement(By.xpath("//ul[@id='side-menu']/descendant::span[text() = 'Bank & Cash' ]")).click();

		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//ul[@class='nav nav-second-level collapse in']/descendant::a[text() = 'New Account' ]")));
		driver.findElement(
				By.xpath("//ul[@class='nav nav-second-level collapse in']/descendant::a[text() = 'New Account' ]"))
				.click();

		String expectedAccountTitle = "Session5 Homework" + new Random().nextInt(999);
		String Balance = "5,000" + new Random().nextInt(999);

		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='account']")).sendKeys(expectedAccountTitle);
		driver.findElement(By.xpath("//div[@class='ibox-content']/descendant::input[@id='description']"))
				.sendKeys("Machhapuchhre");
		driver.findElement(By.xpath("//div[@class='form-group']/descendant::input[@id='balance']")).sendKeys(Balance);
		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

		By ACCOUNT_CREATED_SUCCESSFULLY_LOCATOR = By
				.xpath("//div[@id='page-wrapper']/descendant::div[@class='alert alert-success fade in']/text()[2]");
		String ExpectedMessage = "Account Created Successfully";

		// Message Validation
		// new WebDriverWait (driver,
		// 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='page-wrapper']/descendant::div[@class='alert
		// alert-success fade in']/text()[2]")));
		// Assert.assertEquals(ACCOUNT_CREATED_SUCCESSFULLY_LOCATOR, ExpectedMessage,
		// "The message does not match");

		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,80000)");

		List<WebElement> descriptionOfElements = driver.findElements(By.xpath("//table/descendant::td"));
		Assert.assertTrue(isDescriptionMatch(expectedAccountTitle, descriptionOfElements),
				"Account Create unsucessful");

		WebElement DELETE_BUTTON_ELEMENT = driver
				.findElement(By.xpath("//td[text() = '" + expectedAccountTitle + "']/parent::tr/descendant::i[2]"));
		DELETE_BUTTON_ELEMENT.click();

		WebElement OK_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[text() = 'OK']"));
		OK_BUTTON_ELEMENT.click();

		Thread.sleep(5000);
		List<WebElement> descriptionOfElements1 = driver.findElements(By.xpath("//table/descendant::td"));
		Assert.assertFalse(isDescriptionMatch2(expectedAccountTitle, descriptionOfElements1), "Account Not Deleted");

	}

	private boolean isDescriptionMatch(String expectedAccountTitle, List<WebElement> descriptionOfElements) {
		for (int i = 0; i < descriptionOfElements.size(); i++) {
			if (expectedAccountTitle.equalsIgnoreCase(descriptionOfElements.get(i).getText()))
				;
			{
				return true;
			}
		}
		return false;
	}

	private boolean isDescriptionMatch2(String expectedAccountTitle, List<WebElement> descriptionOfElements1) {
		for (int i = 0; i < descriptionOfElements1.size(); i++) {
			if (expectedAccountTitle.equalsIgnoreCase(descriptionOfElements1.get(i).getText()))
				;
			{
				return false;
			}
		}
		return true;
	}

	@AfterMethod
	public void closingEverything() {
		driver.close();
		driver.quit();
//session 5 homework
	}
}
