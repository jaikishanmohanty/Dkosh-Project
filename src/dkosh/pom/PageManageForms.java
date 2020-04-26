package dkosh.pom;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import authentication.NavigationBar;
import browsers.BrowserActions;


public class PageManageForms {
	
	
	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	String screenshotUrl = System.getProperty("user.dir") + "/../Screenshot/";
	
	public PageManageForms(WebDriver driver) {
		this.driver = driver;
		actions = new BrowserActions(driver);
		navigationBar = new NavigationBar(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}
	
	@FindBy(xpath = "//tr[1]/td/div[@class='empty']")
	private WebElement noResultFound;
	
	@FindBy(xpath = "//tr[1]/td[5]/a")
	private WebElement respondentGroupNameInFormList;
	
	@FindBy(xpath = "//div[@class='page-header']/h2")
	private WebElement createPageHeader;
	
	@FindBy(xpath = "//input[@id='sfformsearch-txt_form_name']")
	private WebElement txtFormName;
	
	@FindBy(xpath = "//input[@id='sfformsearch-txt_form_short_name']")
	private WebElement txtShortName;
	
	@FindBy(xpath = "//div[@id='w2-container']//table//td/a[@class = 'view btn btn-sm btn-flat btn-primary red-tooltip']/b")
	private WebElement btnStatus;
	
	@FindBy(id= "sfform-int_status_type")
	private WebElement statusDropdownInPopUpWindow;
	
	@FindBy(xpath = "//button[contains(.,'Ok')]")
	private WebElement okButtonInStatusPopUp;
	
	By StatusDropDown = By.xpath(".//*[@id='select2-sfformsearch-int_status_type-container']");
	By AllDataStatusDropDown = By.xpath(".//*[@id='select2-sfformsearch-int_status_type-results']/li");
	By StatusDropDownInput = By.xpath("/html/body/span/span/span[1]/input");
	
	@FindBy(xpath = "//a[@id='dropdownMenuLink']/i")
	private WebElement settingIcon;
	
	By optionsInSetting = By.xpath("//a[@id='dropdownMenuLink']/following-sibling::ul/li/a");
	
	@FindBy(xpath = "//a[@id='dropdownMenuLink']/i")
	private WebElement optionName;
	
	@FindBy(xpath = "//*[@id='modal']/div/div/div[1]/a")
	private WebElement closeButtonInStatusPopUp;
	
	@FindBy(xpath = "//div[@class = 'alert-success alert fade in']")
	private WebElement successAlert;
	
	By successAlertForWait = By.xpath("//div[@class = 'alert-success alert fade in']");
	@FindBy(xpath = "//div[@class='modal-header']/strong/h4[@id='myModalLabel']")
	private WebElement titleOfChangeStatusWindow;
	
	By titleOfChangeStatusWindowForWait = By.xpath("//div[@class='modal-header']/strong/h4[@id='myModalLabel']");
		
	
	public WebElement selectOption(String optionName)
	{
		WebElement deleteRowNo = (driver.findElement(By.xpath("//a[@id='dropdownMenuLink']/following-sibling::ul/li/a[contains(.,'"+optionName+"')]")));
		return deleteRowNo;
	}
	
	
	public Boolean switchResponse()
	{
		Boolean switchResponse = actions.isElementDisplayed(driver.findElement(By.xpath("//div[@class='form-group']//div[contains(@class,'bootstrap-switch-on')]")));
		return switchResponse;
	}
	
	@FindBy(xpath = "//div[@class='form-group']//div[contains(@class,'bootstrap-switch-off')]")
	private WebElement switchResponseOff;
	
	/**
	 * @return the statusDropDown
	 */
	public By getStatusDropDown() {
		return StatusDropDown;
	}

	/**
	 * @return the allDataStatusDropDown
	 */
	public By getAllDataStatusDropDown() {
		return AllDataStatusDropDown;
	}

	/**
	 * @return the statusDropDownInput
	 */
	public By getStatusDropDownInput() {
		return StatusDropDownInput;
	}

	/**
	 * @return the txtFormName
	 */
	public WebElement getTxtFormName() {
		return txtFormName;
	}

	/**
	 * @return the txtShortName
	 */
	public WebElement getTxtShortName() {
		return txtShortName;
	}

	/**
	 * @return the btnStatus
	 */
	public WebElement getBtnStatus() {
		return btnStatus;
	}

	/**
	 * @return the statusDropdownInPopUpWindow
	 */
	public WebElement getStatusDropdownInPopUpWindow() {
		return statusDropdownInPopUpWindow;
	}

	/**
	 * @return the okButtonInStatusPopUp
	 */
	public WebElement getOkButtonInStatusPopUp() {
		return okButtonInStatusPopUp;
	}
	
	/**
	 * @return the settingIcon
	 */
	public WebElement getSettingIcon() {
		return settingIcon;
	}
	/**
	 * @return the respondentGroupNameInFormList
	 */
	public WebElement getRespondentGroupNameInFormList() {
		return respondentGroupNameInFormList;
	}

	/**
	 * @return the noResultFound
	 */
	public WebElement getNoResultFound() {
		return noResultFound;
	}

	/**
	 * @return the optionName
	 */
	public WebElement getOptionName() {
		return optionName;
	}

	/**
	 * @return the closeButtonInStatusPopUp
	 */
	public WebElement getCloseButtonInStatusPopUp() {
		return closeButtonInStatusPopUp;
	}

	/**
	 * @return the successAlert
	 */
	public WebElement getSuccessAlert() {
		return successAlert;
	}

	/**
	 * @return the successAlertForWait
	 */
	public By getSuccessAlertForWait() {
		return successAlertForWait;
	}

	/**
	 * @return the titleOfChangeStatusWindow
	 */
	public WebElement getTitleOfChangeStatusWindow() {
		return titleOfChangeStatusWindow;
	}

	/**
	 * @return the titleOfChangeStatusWindowForWait
	 */
	public By getTitleOfChangeStatusWindowForWait() {
		return titleOfChangeStatusWindowForWait;
	}

	/**
	 * @return the optionsInSetting
	 */
	public By getOptionsInSetting() {
		return optionsInSetting;
	}

	/**
	 * @return the switchResponseOff
	 */
	public WebElement getSwitchResponseOff() {
		return switchResponseOff;
	}


	/**
	 * @return the createPageHeader
	 */
	public WebElement getCreatePageHeader() {
		return createPageHeader;
	}
	
	@FindBy(xpath = "//ul[@id='navigationMenuItems']/li/a[@id='m_1_forms']")
	private WebElement formTab;
	
	
	public WebElement selectForm(String formName)
	{
		WebElement selectForm = (driver.findElement(By.xpath(".//a[@id='m_1_forms']/following-sibling::ul/li/a[contains(text(),'"+formName+"')]")));
		return selectForm;
	}
	
	/**
	 * @return the formTab
	 */
	public WebElement getFormTab() {
		return formTab;
	}


	/**Verify, Manage Form page - title & url.
	 * @author priyankag
	 * @throws InterruptedException
	 */
	
	public void TCA_GoToManageFormPage() throws InterruptedException {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {
		String titleManageFormPage= "Manage Forms";
		 String urlManageFormPage = "http://10.1.10.110:89/Saarthi/Dkosh/index.php/form/index";
		softAssert = new SoftAssert();
		actions.clickOnElement(navigationBar.getAppIcon());
		actions.clickOnElement(navigationBar.getDkosh_app());
		actions.clickOnElement(navigationBar.getDkosh_dropdown());
		actions.clickOnElement(navigationBar.getManageFormLink());
		Thread.sleep(3000);
		softAssert.assertTrue(actions.getText(getCreatePageHeader()).contains(titleManageFormPage),"Verify manage form page title.");
		softAssert.assertEquals(driver.getCurrentUrl(),urlManageFormPage,"Verify manage form page url.");	
		softAssert.assertAll();
		}
		catch (Exception | AssertionError e) {
			 actions.captureScreenShot(driver, screenshotUrl, methodName);
			 softAssert.assertAll();
		}
		}
	

	/**Verify, form setting page is opening.
	 * @author priyankag
	 * @throws InterruptedException
	 */
	
	public void TCA_GoToFormSettingPage() throws InterruptedException {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		try {
		String titleFormSettingsPage= "Form Settings";
		 String urlFormSettingPage = "http://10.1.10.110:89/Saarthi/Dkosh/index.php/form/create";
		softAssert = new SoftAssert();
		actions.clickOnElement(navigationBar.getAppIcon());
		actions.clickOnElement(navigationBar.getDkosh_app());
		actions.clickOnElement(navigationBar.getDkosh_dropdown());
		actions.clickOnElement(navigationBar.getManageFormLink());
		actions.clickOnElement(navigationBar.getCreateIcon());
		Thread.sleep(3000);
		Assert.assertFalse(actions.isElementPresent(navigationBar.getDatabaseException()), "DataBase Exception is showing.");
		softAssert.assertTrue(actions.getText(getCreatePageHeader()).contains(titleFormSettingsPage),"Verify form settings page title.");
		softAssert.assertEquals(driver.getCurrentUrl(),urlFormSettingPage,"Verify form settings page url.");
		softAssert.assertAll();
		}
		catch (Exception | AssertionError e) {
		 actions.captureScreenShot(driver, screenshotUrl, methodName);
		 softAssert.assertAll();
	}
		}

}
