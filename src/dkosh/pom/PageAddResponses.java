package dkosh.pom;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.mortbay.log.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import authentication.NavigationBar;
import browsers.BrowserActions;

public class PageAddResponses {
	
	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	String screenshotUrl = System.getProperty("user.dir") + "/../Screenshot/";
	
	public PageAddResponses(WebDriver driver) {
		this.driver = driver;
		actions = new BrowserActions(driver);
		navigationBar = new NavigationBar(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}
	
	@FindBy(xpath = "//div[@class = 'alert-success alert fade in']")
	private WebElement successAlert;
	
	// No Of Sections
	
	By noOfSections = By.xpath("//div[@class='dynamicForm']/div[@class='row']");

	// Get Column Sequense No
	 
	public By getColumnSequenceNo(String sectionNo, String questionNo)
	{
		By colSeqNo = By.xpath("//div[@class='dynamicForm']/div[@class='row']["+sectionNo+"]//div[@class='box-body']/div/div["+questionNo+"]");
		return colSeqNo;
	}
	
	// Save Button In Response Page
	
	@FindBy(xpath = ".//button[contains(.,'Save')]")
	private WebElement saveButtonInResponsePage;
	
	public void selectDropdown(String shortName,String response) throws InterruptedException {
	
	By DropDown = By.xpath(".//*[@id='select2-dynamicform-"+shortName+"-container']");
	By DropDownInput = By.xpath("html/body/span/span/span[1]/input");
	By AllDataDropDown = (By.xpath(".//*[@id='select2-dynamicform-"+shortName+"-results']/li"));
	
	actions.selectCustomizeDropDownValue(response, DropDown, AllDataDropDown);
	/*if(actions.isElementPresent(driver.findElements(By.xpath(".//*[@id='select2-dynamicform-"+shortName+"-container']"))))
	{
	actions.selectCustomizeDropDownValue(response, DropDown, AllDataDropDown);
	Thread.sleep(2000);
	}
	else {
		Reporter.log("Dropdownlist of - "+shortName +" - question is not visible in add response form.");
		softAssert.assertEquals(actions.isElementPresent(driver.findElements(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+response+"')]"))),true,"Options of - "+shortName +" - question is not visible in add response form.");
		softAssert.assertAll();
	}*/
	}
	
	/**
	 * @return the noOfSections
	 */
	public By getNoOfSections() {
		return noOfSections;
	}

	/**
	 * @return the saveButtonInResponsePage
	 */
	public WebElement getSaveButtonInResponsePage() {
		return saveButtonInResponsePage;
	}

	/**
	 * @return the successAlert
	 */
	public WebElement getSuccessAlert() {
		return successAlert;
	}
	
	/*// Function For Add Response
	public void addResponse(String questionType,String shortName,String responseValue) throws InterruptedException {

		System.out.println("Question Type:"+questionType);
		if(questionType.equals("Check Box") || questionType.equals("Radio")) {

			Thread.sleep(3000);
			try {
				if(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]/input")).getAttribute("checked").equals("false"))
				{
				Thread.sleep(2000);
				actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]")));
				Thread.sleep(2000);
				}
			}
			catch (Exception e) {
				
				actions.scrollTo(driver, driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]/input")));		
				//if(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]/input")).getAttribute("checked").equals("false"))
			//	{
				Thread.sleep(2000);
				actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]")));
				Thread.sleep(2000);
				//}
			}
			}
		if(questionType.equals("Paragraph")||questionType.equals("Short Text") ||questionType.equals("Number")|| questionType.equals("Time")|| questionType.equals("Date")|| questionType.equals("Autocomplete")||questionType.equals("Email")|| questionType.equals("URL")|| questionType.equals("Scale")) {
		try {
			
			if(!questionType.equals("Autocomplete")) {
				Thread.sleep(2000);
			actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
			}
			else {
				actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
				actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']/following-sibling::div//strong")));
			}
		}
		catch (Exception e) {
			actions.scrollTo(driver, driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")));
			if(!questionType.equals("Autocomplete")) {
				actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
			}
				else {
					actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
					actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']/following-sibling::div//strong")));
				}		
			}
		}
		
		if(questionType.equals("Location")) {
			Thread.sleep(2000);
			   try {
				actions.clickOnElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
				Thread.sleep(2000);
			}
			catch (Exception e) {
				actions.scrollTo(driver, driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
				actions.clickOnElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
			}
		}
		if(questionType.equals("Drop Down")) {		
			try {
				//actions.scrollTo(driver, driver.findElement(By.xpath(".//*[@id='select2-dynamicform-"+shortName+"-container']")));
				selectDropdown(shortName, responseValue);
				
			} catch (Exception e) {
				selectDropdown(shortName, responseValue);
			}
		}
		if(questionType.equals("file")) {
			Thread.sleep(3000);
			try {
				actions.scrollTo(driver, driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")));
				actions.sendAnyKeyToElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")), responseValue);
							}
			catch (Exception e) {
				actions.sendAnyKeyToElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")), responseValue);

			}
		
		}
	}*/
	
	// Function For Add Response
		public void addResponse(String questionType,String shortName,String responseValue) throws InterruptedException, NoSuchElementException {

			softAssert = new SoftAssert(); 
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		//	try {
			if(questionType.equals("Check Box") || questionType.equals("Radio")) {
				Thread.sleep(3000);
				try {
					if(actions.isElementPresent(driver.findElements(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]"))))
					{
					actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]")));
					Thread.sleep(2000);
					}
					else {
						Reporter.log("Options of - "+shortName +" - question is not visible in add response form.");
						softAssert.assertEquals(actions.isElementPresent(driver.findElements(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]"))),true,"Options of - "+shortName +" - question is not visible in add response form.");
						softAssert.assertAll();
					}
				}
				catch (Exception e) {
					actions.scrollTo(driver, driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]/input")));		
					Thread.sleep(2000);
					actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']//label[contains(.,'"+responseValue+"')]")));
					Thread.sleep(2000);
				}
				}
			
			if(questionType.equals("Paragraph")||questionType.equals("Short Text") ||questionType.equals("Number")|| questionType.equals("Time")|| questionType.equals("Date")|| questionType.equals("Autocomplete")||questionType.equals("Email")|| questionType.equals("URL")|| questionType.equals("Scale")) {
			try {
				
				if(!questionType.equals("Autocomplete")) {
					Thread.sleep(2000);
				actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
				}
				else {
					actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
					actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']/following-sibling::div//strong")));
				}
			}
			catch (Exception e) {
				actions.scrollTo(driver, driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")));
				if(!questionType.equals("Autocomplete")) {
					actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
				}
					else {
						actions.clearAndSendKeysToElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']")), responseValue);
						actions.clickOnElement(driver.findElement(By.xpath("//*[@id='dynamicform-"+shortName+"']/following-sibling::div//strong")));
					}	
				}
			}
			
			if(questionType.equals("Location")) {
				Thread.sleep(2000);
				   try {
					actions.clickOnElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
					Thread.sleep(2000);
				}
				catch (Exception e) {
					actions.scrollTo(driver, driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
					actions.clickOnElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']/../../following-sibling::div/button")));
				}
			}
			if(questionType.equals("Drop Down")) {		
				try {
					selectDropdown(shortName, responseValue);
					
				} catch (Exception e) {
					selectDropdown(shortName, responseValue);
				}
			}
			if(questionType.equals("file")) {
				Thread.sleep(3000);
				try {
					actions.scrollTo(driver, driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")));
					actions.sendAnyKeyToElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")), responseValue);
								}
				catch (Exception e) {
					actions.sendAnyKeyToElement(driver.findElement(By.xpath("//input[@id='dynamicform-"+shortName+"']")), responseValue);

				}
			}
			softAssert.assertAll();
		/*}
			catch (Exception | AssertionError e) {
				actions.captureScreenShot(driver, screenshotUrl, methodName);
			}*/
		}
}
