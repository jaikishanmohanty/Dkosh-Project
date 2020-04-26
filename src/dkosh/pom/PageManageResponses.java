package dkosh.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import authentication.NavigationBar;
import browsers.BrowserActions;

public class PageManageResponses {
	
	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	
	public PageManageResponses(WebDriver driver) {
		this.driver = driver;
		actions = new BrowserActions(driver);
		navigationBar = new NavigationBar(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}
	
	By responseTableHeading = By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th");

	public String getColumnSequenceNo(String columnHeadingName)
	{
		String colSeqNo = driver.findElement(By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th[contains(.,'"+columnHeadingName+"')]")).getAttribute("data-col-seq");
		return colSeqNo;
	}
	
	public String getColumnName(int columnNumber)
	{
		String colName = driver.findElement(By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th["+columnNumber+"]")).getText();
		return colName;
	}

	@FindBy(xpath = "//a[@class='btn btn-primary btn-md pull-right']/i")
	private WebElement BtnCreate;
	/**
	 * @return the responseTableHeading
	 */
	public By getResponseTableHeading() {
		return responseTableHeading;
	}

	/**
	 * @return the btnCreate
	 */
	public WebElement getBtnCreate() {
		return BtnCreate;
	}
	

}
