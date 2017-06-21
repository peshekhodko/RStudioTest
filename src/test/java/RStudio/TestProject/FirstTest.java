package RStudio.TestProject;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;




public class FirstTest {
	
	private WebDriver driver;
	private String url;
	private ScreenshotHelper screenshotHelper;
	
	@Before
	public void openBrowser () {
		
		url = "https://www.rstudio.com/products/shiny-server-pro/evaluation";
		driver = new ChromeDriver();
		driver.get (url);
		screenshotHelper = new ScreenshotHelper();
	}
	
	@Test
	public void testTitle () {
		assertEquals (driver.getTitle(), "Free Evaluation – RStudio");
		
		WebElement firstName = driver.findElement(By.id("FirstName"));
		WebElement lastName = driver.findElement( By.id("LastName") );
		WebElement email = driver.findElement( By.id("Email") );
		WebElement company = driver.findElement( By.id("Company") );
		WebElement country = driver.findElement( By.id("Country") );
		WebElement downloadBtn = driver.findElement(By.className("mktoButton"));
		
		firstName.sendKeys("firstName");
		lastName.sendKeys("LastName");
		email.sendKeys("Email@email.com");
		company.sendKeys("Company");
		country.sendKeys("USA");
		downloadBtn.submit();
		
		//WebDriverWait _wait = new WebDriverWait(driver, new TimeSpan(0, 1, 0));

		//_wait.Until(d => d.FindElement(By.Id("Id_Your_UIElement"));
		
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("download-commercial"));
		
		//System.out.println("URL" + driver.getCurrentUrl());
		//System.out.println("TITLE" + driver.getTitle());
		assertTrue   (driver.getPageSource().contains("Download Shiny Server Pro") );
		assertTrue   (driver.getPageSource().contains("Thank you for your interest in Shiny Server Pro.") );
		assertEquals (driver.getTitle(), "Download Commercial Shiny – RStudio");
		assertEquals (driver.getCurrentUrl(), "https://www.rstudio.com/products/shiny/download-commercial/");
		
		

		
		
//		assertTrue ("The page title should start with the search string after the search.", (new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
//			        public Boolean apply(WebDriver d) {
//			          return d.getTitle().toLowerCase().startsWith("drupal!");
//			        }
//			      });
	}
	
	@After
	public void closeBrowser () {
		
		try {
			screenshotHelper.saveScreenshot("screenshot.png");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		driver.quit();
	}
	

	private class ScreenshotHelper {
		  public void saveScreenshot(String screenshotFileName) throws IOException {
	      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	      FileUtils.copyFile(screenshot, new File(screenshotFileName));
	    }
	  }
	
}
