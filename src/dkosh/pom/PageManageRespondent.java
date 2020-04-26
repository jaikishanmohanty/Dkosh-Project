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

public class PageManageRespondent {
	
	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	
	public PageManageRespondent(WebDriver driver) {
		this.driver = driver;
		actions = new BrowserActions(driver);
		navigationBar = new NavigationBar(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}

	
	@FindBy(xpath = "//div[@class='page-header']/h2")
	private WebElement createPageHeader;
	
	@FindBy(xpath = "//input[contains(@name ,'SfFormRespondentSearch[int_employee_group_id')]")
	private WebElement txtRespondentGroupSearchField;
	
	@FindBy(xpath = "//tr[1]/td[@class='text-center']/input[@type='checkbox']")
	private WebElement chkRespondentGroup;
	
	@FindBy(xpath = "//*[@id='respondent-edit-3']")
	private WebElement selectEditResponse;
	
	@FindBy(xpath = "//*[@id='respondent-verify-3']")
	private WebElement selectVerifyResponse;
	
	@FindBy(xpath = "//*[@id='respondent-approve-3']")
	private WebElement selectApproveResponse;
	
	@FindBy(xpath = "//tr[1]/td[3]")
	private WebElement txtRespondentGroup;
	
	 @FindBy(xpath = "//div[@id='respondent-from-3-datetime']/input")
	 private  WebElement dateAvailableFrom;
	 
	 @FindBy(xpath = "//button[@type='submit']")
	 private  WebElement btnOk;
	
	 @FindBy(xpath = "//div[@class = 'alert-success alert fade in']")
		private WebElement successAlert;

	/**
	 * @return the createPageHeader
	 */
	public WebElement getCreatePageHeader() {
		return createPageHeader;
	}

	/**
	 * @return the txtRespondentGroupSearchField
	 */
	public WebElement getTxtRespondentGroupSearchField() {
		return txtRespondentGroupSearchField;
	}

	/**
	 * @return the chkRespondentGroup
	 */
	public WebElement getChkRespondentGroup() {
		return chkRespondentGroup;
	}

	/**
	 * @return the txtRespondentGroup
	 */
	public WebElement getTxtRespondentGroup() {
		return txtRespondentGroup;
	}

	/**
	 * @return the dateAvailableFrom
	 */
	public WebElement getDateAvailableFrom() {
		return dateAvailableFrom;
	}

	/**
	 * @return the btnOk
	 */
	public WebElement getBtnOk() {
		return btnOk;
	}

	/**
	 * @return the successAlert
	 */
	public WebElement getSuccessAlert() {
		return successAlert;
	}

	/**
	 * @return the selectEditResponse
	 */
	public WebElement getSelectEditResponse() {
		return selectEditResponse;
	}

	/**
	 * @return the selectVerifyResponse
	 */
	public WebElement getSelectVerifyResponse() {
		return selectVerifyResponse;
	}

	/**
	 * @return the selectApproveResponse
	 */
	public WebElement getSelectApproveResponse() {
		return selectApproveResponse;
	}
	
	public Boolean chkSelectRespondentGroup()
	{
		Boolean respondentGroupField = driver.findElement(By.xpath("//tr[1]/td[@class='text-center']/input[@type='checkbox']")).isSelected();
		return respondentGroupField;
	}
	
	
}
