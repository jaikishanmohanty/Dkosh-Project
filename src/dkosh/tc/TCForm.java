package dkosh.tc;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import authentication.LoginAction;
import authentication.LoginPage;
import authentication.NavigationBar;
import browsers.BaseTest;
import browsers.BrowserActions;
import dkosh.data.DkoshCommanFunction;
import dkosh.data.DkoshMessageData;
import dkosh.data.DkoshTitleData;
import dkosh.pom.PageAddResponses;
import dkosh.pom.PageFormSettings;
import dkosh.pom.PageManageForms;
import dkosh.pom.PageManageRespondent;
import dkosh.pom.PageManageResponses;
import dkosh.pom.PageModifyForm;
import helpers.LoggerHelper;
import utilities.DataProviders;
import utilities.GoogleSheetAPIUtils;
/**
 * @author Priyanka Garg
 * 
 */
public class TCForm extends BaseTest {


	SoftAssert softAssert;
	NavigationBar navigationBar;
	BrowserActions actions;
	Actions action;
	LoginPage login;
	LoginAction loginAction;
	WebDriverWait wait;
	GoogleSheetAPIUtils googleSheet;
	PageFormSettings objPageManageFormSetting;
	PageManageForms objPageManageForms;
	PageManageRespondent objPageManageRespondent;
	PageModifyForm objPageModifyForms;
	PageManageResponses objPageManageResponses;
	PageAddResponses objPageAddResponses;
	DkoshCommanFunction objDkoshCommanFunction;
	
	// Form Settings
	
	String form_idField;
	String txtName;
	String txtShortName;
	String availableFromDateField;
	String availableUpToDateField;
	String txtAreaInstruction;
	String modifyResponse;
	String verifyResponse;
	String approveResponse;
	String numberAvailableFromDateField;
	String hourAvailableFrom;
	String minuteAvailableFrom;

	// Question Data

	String sectionNumber;
	String txtSectionName;
	String txtSectionDescription;
	String question_idField;
	String selectQuestionType;
	String txtQuestionName;
	String selectPrefillType;
	String txtprefillValue;
	String ysn_required;
	String ysn_enabled;
	String ysn_hidden;
	String applicableValidation_minLength;
	String applicableValidation_maxLength;
	String applicableValidation_minValue;
	String applicableValidation_maxValue;
	String applicableValidation_rangeErrorMessage;
	String applicableValidation_regularExpression;
	String applicableValidation_RE_ErrorMessage;
	String date_minValue;
	String date_maxValue;
	String time_minValue;
	String time_maxValue;
	String fileSource;
	String fileType;
	String fileExtension;
	String choiceType;
	String choice1;
	String choice2;
	String response;
	String txtDescription;
	String dateFormatSeq;
	String numberAvailableUpToDateField;
	String applicableValidation_date_minValue;
	String applicableValidation_date_maxValue;
	String applicableValidation_time_minValue;
	String applicableValidation_time_maxValue;
	String applicableValidation_fileSource;
	String applicableValidation_fileType;
	String applicableValidation_fileExtension;
	
	//Choices Data
	String questionid_field;
	String questionType_field;


	String msgResponseTable = "Response Table Name";
	String txtResponseTableName = "tbl_resp_";
	String urlManageRespondentPage = "http://10.1.10.110:89/Saarthi/Dkosh/index.php/respondent/index?intFormId=";

	// Meassage
	String errorShortField = "Short Name already exists.";
	String errorShortNameFieldLength = "Short Name should contain at most 25 characters.";
	String errorShortNameText = "Your short name contains only small letters, underscores and numbers.";

	//Status Name
	String statusNameNew = "New(Draft)";
	String statusNameActive = "Active";
	
	// Prefill Value
	String updatePrefillValue = "Update Prefill Value";

	// Modify Question
	String txtDefaultType = "Select Prefill Value";

	// SheetId & Sheet Name

	Object[][] arrCreateFormData;
	Object[][] arrAddRespondentData;
	Object[][] arrQuestionData;
	Object[][] arrFormStatusData;
	Object[][] arrActionsOnStatusData;
	Object[][] createQuestionChoicesData;
	Object[][] arrCreateQuestionChoicesData;
	Object[][] moveToSectionData;
	Object[][] arrmoveToSectionData;
	
	String dKOSHDataSpreadsheetId = "1Q4_V7oh3wSdl4HFnlk1UwHA-O4k3HE6KePS89EtlmW0";
	String questionSheetName ="Question_Data!A2:AF";
	String createFormSheetName ="Create_Form!A2:Z";
	String formStatusDataSheetName = "Manage_Form_Status!A2:Z";
	String sectionActionDataSheetName = "Sections _Action!A2:Z";
	String manageRespondentDataSheetName = "Manage_Respondent!A2:Z";
	String actionsOnStatusDataSheetName = "Actions_On_Status!A2:Z";
	String actionsOnStatusHeadingDataSheetName = "Actions_On_Status!A1:Z";
	String questionListActionHeadingDataSheetName = "QuestionList_Action!A1:Z";
	String createQuestionChoicesSheetName = "Create_Question_Choices!B2:Z";
	String moveToSectionSheetName = "MoveToSection!A2:H";

	int sectionNo = 1;
	int questionNos =1;
	int firstSectionNo =1;
	
	DkoshTitleData title = new DkoshTitleData();
	DkoshMessageData objDkoshMessages = new DkoshMessageData();
	Logger log = LoggerHelper.getLogger(TCForm.class);	



	// Section 
	
	String fromSectionNumber;
	String fromSectionName;
	String questionNumber;
	String questionShortName;
	String toSectionNumber;
	String toSectionName;
	
	// Screenshot
	
	String screenshotUrl = System.getProperty("user.dir") + "/../Screenshot/";
	// String  methodName;

	@Test(dataProvider = "loginDetails", dataProviderClass = DataProviders.class)
	public void TC_Login(String username, String password, String mobilenumber) throws Exception {

		actions = new BrowserActions(driver);
		action = new Actions(driver);
		login = new LoginPage(driver);
		navigationBar = new NavigationBar(driver);
		googleSheet = new GoogleSheetAPIUtils();
		wait = new WebDriverWait(driver, 40);
		loginAction = new LoginAction(driver);
		objPageManageFormSetting = new PageFormSettings(driver);
		objPageManageForms = new PageManageForms(driver);
		objPageManageRespondent = new PageManageRespondent(driver);
		objPageModifyForms =new PageModifyForm(driver);
		objPageManageResponses = new PageManageResponses(driver);
		objPageAddResponses = new PageAddResponses(driver);
		objDkoshCommanFunction = new DkoshCommanFunction(driver);
		title.getTitle();
		objDkoshMessages.getMessage();
		loginAction.login(username, password, mobilenumber);
	}

	/**
	 *  Verify manage form page - title & url .
	 * @throws Exception
	 */
	@Test
	public void TC_dK_001() throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		objPageManageForms.TCA_GoToManageFormPage();
		Reporter.log("TC_dK_001 : This Test Case for verify title & url of manage form page.");
	}

	/**
	 *  Verify Form setting page - title & url .
	 * @throws Exception
	 */
	@Test
	public void TC_dK_002() throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		objPageManageForms.TCA_GoToFormSettingPage();
		Reporter.log("TC_dK_002 : This Test Case for verify title of form setting page.");
	}

	/**
	 *  Verify Form Create successfully with mandatory field.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_003(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		objPageManageForms.TCA_GoToFormSettingPage();
		initializeCreateFormData(CF_Id);
		int numberOfQuestion = 1;
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			availableFromDateField = (String)objCreateFormData[3];
			numberAvailableFromDateField ="1";
			dateFormatSeq =availableFromDateField.substring(0, availableFromDateField.indexOf(" "));
			//hourAvailableFrom =availableFromDateField.substring(availableFromDateField.indexOf(" ")+1,availableFromDateField.indexOf(":"));
			//minuteAvailableFrom =availableFromDateField.substring(availableFromDateField.indexOf(":")+1,availableFromDateField.length());
			hourAvailableFrom ="10";
			minuteAvailableFrom = "15";
			availableUpToDateField = (String)objCreateFormData[4];
			txtAreaInstruction = (String)objCreateFormData[5];
			modifyResponse = (String)objCreateFormData[6];
			verifyResponse = (String)objCreateFormData[7];
			approveResponse = (String)objCreateFormData[8];

			if(form_idField.equals(CF_Id)) {		

				actions.clearAndSendKeysToElement(objPageManageFormSetting.getTxtName(), txtName);
				
				Assert.assertEquals(objPageManageFormSetting.getTxtName().getAttribute("aria-required"), "true","Form Name field should be mandatory.");
				log.info("Verify Name Field is mandatory.");
				
				if(txtShortName.length()>25) {
					actions.clearAndSendKeysToElement(objPageManageFormSetting.getTxtshortname(), txtShortName);
					actions.SendKeysToElement(objPageManageFormSetting.getTxtshortname());
					Thread.sleep(2000);
					Assert.assertEquals(actions.getText(objPageManageFormSetting.getErrorShortName()), errorShortNameFieldLength,"When Short Name length  greater than 25 error message is not showing.");
				}	
				else {
					actions.clearAndSendKeysToElement(objPageManageFormSetting.getTxtshortname(), txtShortName);
				}
				Assert.assertEquals(objPageManageFormSetting.getTxtshortname().getAttribute("aria-required"), "true","Short Name field should be mandatory.");
				log.info("Verify Short Name Field is mandatory.");
				//softAssert.assertEquals(actions.getText(objPageManageFormSetting.getResponseTableName()), msgResponseTable,"Please Check Response Table Name Message is not showing.");
				softAssert.assertEquals(actions.getText(objPageManageFormSetting.getTxtResponseTableName()), txtResponseTableName+txtShortName,"Please Check Response Table Name.");
				Thread.sleep(3000);
				objPageManageFormSetting.selectDate(objPageManageFormSetting.getDateAvailableFrom(), numberAvailableFromDateField, dateFormatSeq);
				objPageManageFormSetting.selectTime(hourAvailableFrom, minuteAvailableFrom);
				Thread.sleep(3000);	
				Assert.assertEquals(objPageManageFormSetting.getDateAvailableFrom().getAttribute("aria-required"), "true","Date Available From field should be mandatory.");
				log.info("Verify Date Available From Date Field is mandatory.");
				if(modifyResponse.equals("1")) {
					Assert.assertTrue(objPageManageFormSetting.switchModifiedResponse());		
					actions.clickOnElement(objPageManageFormSetting.getSwitchModifiedResponse());
				}
				if(verifyResponse.equals("1")) {
					Assert.assertTrue(objPageManageFormSetting.switchVerifiedResponse());
					actions.clickOnElement(objPageManageFormSetting.getSwitchVerifiedResponse());
				}
				if(approveResponse.equals("1")) {
					Assert.assertTrue(objPageManageFormSetting.switchApprovedResponse());
					actions.clickOnElement(objPageManageFormSetting.getSwitchApprovedResponse());
				}
				Thread.sleep(2000);
				actions.clickOnElement(objPageManageFormSetting.getBtnSave());
				try {
					Assert.assertTrue(actions.getText(objPageManageFormSetting.getSuccessAlert()).contains(objDkoshMessages.getMsg_createForm()),"Please Check Confirmation Meassage After Create Form.");		
				}
				catch (Exception e) {			
					softAssert.assertEquals(actions.getText(objPageManageFormSetting.getErrorShortName()), errorShortField,"Please Check form is not create successfully & error in short field also not showing.");		
				}
			}
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_003 : This Test Case for verify title of form setting page.");

	}

	/**
	 *  Verify Default status of form
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_004(String CF_Id) throws Exception {

		softAssert = new SoftAssert();
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			if(form_idField.equals(CF_Id)) {	
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				actions.selectCustomizeDropDownValue(statusNameNew, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
				Thread.sleep(2000);
				softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), statusNameNew,"Status Of Form should be New.");		
				Reporter.log("TC_dK_004 : This Test Case For Verify Default status of form.");
			}
		}
		softAssert.assertAll();
	}

	/**
	 * Title & Url Of Manage Respondent Page.
	 * Add Respondent Group In Form
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_005(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		objPageManageForms.TCA_GoToManageFormPage();
		initializeManageRespondentData(CF_Id);
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) {
			
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			availableFromDateField = (String)objCreateFormData[3];
			numberAvailableFromDateField ="1";
			dateFormatSeq =availableFromDateField.substring(0, availableFromDateField.indexOf(" "));
			//hourAvailableFrom =availableFromDateField.substring(availableFromDateField.indexOf(" ")+1,availableFromDateField.indexOf(":"));
			//minuteAvailableFrom =availableFromDateField.substring(availableFromDateField.indexOf(":")+1,availableFromDateField.length());
			hourAvailableFrom ="10";
			minuteAvailableFrom = "15";
			availableUpToDateField = (String)objCreateFormData[4];
			txtAreaInstruction = (String)objCreateFormData[5];
			modifyResponse = (String)objCreateFormData[6];
			verifyResponse = (String)objCreateFormData[7];
			approveResponse = (String)objCreateFormData[8];
			
			// Manage Respondent Data
			
			String formName = (String)objAddRespondentData[2];
			String respondentGroup = (String)objAddRespondentData[3];
			String availableFromDateFieldInRespondent = (String)objAddRespondentData[4];
			String availableUpToDateFieldInRespondent = (String)objAddRespondentData[5];
			String editResponsesType = (String)objAddRespondentData[6];
			String verifyResponsesType = (String)objAddRespondentData[7];
			String approveResponsesType = (String)objAddRespondentData[8];
			
			if(form_idField.equals(CF_Id)) {
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_manageRespondent()));
				Thread.sleep(2000);
				actions.switchToParentWindow();
				Thread.sleep(5000);
				softAssert.assertEquals(actions.getText(objPageManageForms.getCreatePageHeader()), title.getTitle_manageRespondent()+" - "+txtName,"Please Check title of manage respondent page.");
				softAssert.assertTrue(driver.getCurrentUrl().contains(urlManageRespondentPage),"Please Check url of manage respondent page.");
				actions.click(objPageManageRespondent.getTxtRespondentGroupSearchField());
				Thread.sleep(1000);
				actions.sendAnyKeyToElement(objPageManageRespondent.getTxtRespondentGroupSearchField(), respondentGroup);
				actions.SendKeysToElement(objPageManageRespondent.getTxtRespondentGroupSearchField());
				Thread.sleep(2000);
				if(objPageManageRespondent.chkSelectRespondentGroup().equals(true)) {
					log.info("RespondentGroup Already Added.");
				}
				else {
					actions.Select_The_Checkbox(objPageManageRespondent.getChkRespondentGroup());
				}
				softAssert.assertEquals(actions.getText(objPageManageRespondent.getTxtRespondentGroup()), respondentGroup,"Verify Respondent Group Name.");
				Thread.sleep(3000);
				objPageManageFormSetting.selectDate(objPageManageRespondent.getDateAvailableFrom(), "1",dateFormatSeq);
				objPageManageFormSetting.selectTime(hourAvailableFrom, minuteAvailableFrom);
				Thread.sleep(3000);		

				if(modifyResponse.equals("1")) {	
					actions.selectDropDown(objPageManageRespondent.getSelectEditResponse(), editResponsesType);
					Select selectResponseType = new Select(objPageManageRespondent.getSelectEditResponse());
					softAssert.assertEquals(selectResponseType.getFirstSelectedOption().getText(), editResponsesType);
				}
				else {
					softAssert.assertEquals(objPageManageRespondent.getSelectEditResponse().getAttribute("disabled").toString(), "true");
				}

				if(verifyResponse.equals("1")) {
					actions.selectDropDown(objPageManageRespondent.getSelectVerifyResponse(), verifyResponsesType);
					Select selectResponseType = new Select(objPageManageRespondent.getSelectVerifyResponse());
					softAssert.assertEquals(selectResponseType.getFirstSelectedOption().getText(), verifyResponsesType);
				}
				else {
					softAssert.assertEquals(objPageManageRespondent.getSelectVerifyResponse().getAttribute("disabled").toString(), "true");
				}

				if(approveResponse.equals("1")) {
					actions.selectDropDown(objPageManageRespondent.getSelectApproveResponse(), approveResponsesType);
					Select selectResponseType = new Select(objPageManageRespondent.getSelectApproveResponse());
					softAssert.assertEquals(selectResponseType.getFirstSelectedOption().getText(), approveResponsesType);
				}
				else {
					softAssert.assertEquals(objPageManageRespondent.getSelectApproveResponse().getAttribute("disabled").toString(), "true");
				}
				actions.clickOnElement(objPageManageRespondent.getBtnOk());
				Assert.assertTrue(actions.getText(objPageManageRespondent.getSuccessAlert()).contains(objDkoshMessages.getMsg_addRespondentGroup()));			
			}
		}
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_005 : This Test Case For Verify Confirmation Message After Add Respondent Group In Form & Verify Title & Url Of Manage Respondent Page.");
	}


	/**
	 *  Verify Title of Modify Form Page.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_006(String CF_Id) throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
		}
		objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		actions.selectCustomizeDropDownValue(statusNameNew, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), statusNameNew);
		actions.clickOnElement(objPageManageForms.getSettingIcon());
		actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_modifyForm()));
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Thread.sleep(4000);
		softAssert.assertEquals(actions.getText(objPageModifyForms.getCreatePageHeader()), title.getTitle_modifyForm()+" - "+txtName);
		softAssert.assertAll();
		Reporter.log("TC_dK_006 : This Test Case For Verify Title of Modify Form Page.");
		
	}

	/**
	 *  Verify Options in section.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_007(String CF_Id) throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			txtShortName = (String)objCreateFormData[2];
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			Thread.sleep(3000);
		}
		actions.clickOnElement(objPageManageForms.getSettingIcon());
		actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_modifyForm()));
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Thread.sleep(4000);
		actions.clickOnElement(objPageModifyForms.getSettingLink());
		List<WebElement> actualOptionsListInSection = objPageModifyForms.getOptionsListInSection();
		Object[][] sectionActionsData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, sectionActionDataSheetName);
		int optionNo=0;
		for(Object[] objSectionActionData : sectionActionsData) {
			String actualOptionNameInSection = actions.getText(actualOptionsListInSection.get(optionNo));
			softAssert.assertEquals(actualOptionNameInSection,(String)objSectionActionData[0],"Verify Option Name Of Section.");
			optionNo++;
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_007 : This Test Case For Verify Options List Available on Section.");
	}

	/**
	 *  Verify default fields after create new question & Verify Option List.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_008(String CF_Id) throws Exception
	
	{
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2]; 
			Thread.sleep(2000);
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			actions.clickOnElement(objPageManageForms.getSettingIcon());
			actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_modifyForm()));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			Thread.sleep(4000);
		}
		
		initializeCreateQuestionData(CF_Id);

		for(Object[] objCreateQuestionData : arrQuestionData) {

			form_idField = (String)objCreateQuestionData[0];
			sectionNumber = (String)objCreateQuestionData[1];
			txtSectionName = (String)objCreateQuestionData[2];
			txtSectionDescription = (String)objCreateQuestionData[3];
			question_idField = (String)objCreateQuestionData[4];
			selectQuestionType = (String)objCreateQuestionData[5];
			txtQuestionName = (String)objCreateQuestionData[6];
			txtDescription = (String)objCreateQuestionData[7];
			txtShortName = (String)objCreateQuestionData[8];
			selectPrefillType = (String)objCreateQuestionData[9];
			txtprefillValue = (String)objCreateQuestionData[10];
			ysn_required = (String)objCreateQuestionData[11];
			ysn_enabled = (String)objCreateQuestionData[12];
			ysn_hidden = (String)objCreateQuestionData[13];
			
			

			if(Integer.parseInt(sectionNumber)==firstSectionNo) 
			{
				//Modify Section
				actions.click(objPageModifyForms.getSettingLink());
				
				List<WebElement> optionListOfSection = objPageModifyForms.getOptionsListInSection();
				for (int i = 0; i < optionListOfSection.size(); i++) {
					System.out.println("Options are : "+optionListOfSection.get(i).getText());
				
					if (optionListOfSection.get(i).getText().contains(title.getTitle_modifySection())) {
						Thread.sleep(2000);
						optionListOfSection.get(i).click();
						break;
					}
				}
				Thread.sleep(2000);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Modify Section Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_modifySection(),"Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionName()),"Section Name field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionDescription()),"Section Desccription field is not displayed");
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionName(), txtSectionName);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionDescription(), txtSectionDescription);
				actions.click(objPageModifyForms.getBtnOK());
				Thread.sleep(2000);
				log.info("Section has been modified sucessfully.");
				firstSectionNo--;
				sectionNo++;
			}

			else if(Integer.parseInt(sectionNumber)==sectionNo)
			{
				objDkoshCommanFunction.addSectionAndVerify(txtSectionName, txtSectionDescription);
				questionNos=1;
				sectionNo++;
				Thread.sleep(3000);
			}
			else
			{ log.info("Question is added in Same Section."); }

			objDkoshCommanFunction.createQuestionAndVerifyPanel(Integer.parseInt(sectionNumber),selectQuestionType, questionNos);
			Thread.sleep(3000);
			if(selectQuestionType.equals("Radio") || selectQuestionType.equals("Drop Down") ||  selectQuestionType.equals("Check Box") || selectQuestionType.equals("Autocomplete") )
			{
				objDkoshCommanFunction.verifySourceType(Integer.parseInt(sectionNumber), questionNos);
			}
			Thread.sleep(2000);
			objDkoshCommanFunction.getQuestionListAction(selectQuestionType, sectionNumber, Integer.toString(questionNos));
            Thread.sleep(3000);
            questionNos++; 
		}     
		softAssert.assertAll();     
		Reporter.log("TC_dK_008 : This Test Case add section, verify default question and add question in form."); 

	}
	
	

	/**
	 *  Verify modify question fields value according to input.
	 * @throws Exception
	 */

	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_029(String CF_Id) throws Exception
	{
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		  questionNos=1;
	        firstSectionNo=1;
	        sectionNo=1;
			initializeCreateFormData(CF_Id);
			for(Object[] objCreateFormData : arrCreateFormData) {
				form_idField = (String)objCreateFormData[0];
				txtName = (String)objCreateFormData[1];
				txtShortName = (String)objCreateFormData[2]; 
				Thread.sleep(2000);
				objPageManageForms.TCA_GoToManageFormPage();
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(2000);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_modifyForm()));
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				Thread.sleep(4000);
			}
			
	        initializeCreateQuestionData(CF_Id);    
        for(Object[] objCreateQuestionData : arrQuestionData) {
                form_idField = (String)objCreateQuestionData[0];
                sectionNumber = (String)objCreateQuestionData[1];
                txtSectionName = (String)objCreateQuestionData[2];
                txtSectionDescription = (String)objCreateQuestionData[3];
                question_idField = (String)objCreateQuestionData[4];
                selectQuestionType = (String)objCreateQuestionData[5];
                txtQuestionName = (String)objCreateQuestionData[6];
                txtDescription = (String)objCreateQuestionData[7];
                txtShortName = (String)objCreateQuestionData[8];
                selectPrefillType = (String)objCreateQuestionData[9];
                txtprefillValue = (String)objCreateQuestionData[10];
                ysn_required = (String)objCreateQuestionData[11];
                ysn_enabled = (String)objCreateQuestionData[12];
                ysn_hidden = (String)objCreateQuestionData[13];

              Thread.sleep(2000);

      		if(Integer.parseInt(sectionNumber)>sectionNo)
      		{
      			questionNos = 1;
      			sectionNo++;
      		}

              objDkoshCommanFunction.ModifyQuestionFields(Integer.parseInt(sectionNumber), questionNos ,selectQuestionType, txtQuestionName, txtDescription, txtShortName, selectPrefillType,txtprefillValue, ysn_required, ysn_enabled,ysn_hidden);
               
              if(selectQuestionType.equals("Radio") || selectQuestionType.equals("Drop Down") ||  selectQuestionType.equals("Check Box") || selectQuestionType.equals("Autocomplete") )
    			{
               	 createQuestionChoicesData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, createQuestionChoicesSheetName);
                 arrCreateQuestionChoicesData =actions.getFilterDataFromExcelSheet(createQuestionChoicesData, question_idField);
                 int choicesNo = 0;
                 for(Object[] objCreateQuestionChoicesData : arrCreateQuestionChoicesData)
                 {
                	 questionid_field = (String)objCreateQuestionChoicesData[0];
                	 questionType_field = (String)objCreateQuestionChoicesData[1];
                	 choiceType = (String)objCreateQuestionChoicesData[2];
                	 choice1 = (String)objCreateQuestionChoicesData[3];
                	 choice2 = (String)objCreateQuestionChoicesData[4];
                	 if(!choiceType.equalsIgnoreCase("User Defined List"))
                	 {
                		 objDkoshCommanFunction.addSourceTypeAndVerify(Integer.parseInt(sectionNumber), questionNos, questionType_field, choiceType, choice1, choice2);
                	 }
                	 if(choiceType.equalsIgnoreCase("User Defined List") && choicesNo>=0)
                	 {
             			Thread.sleep(2000);
             			if(choicesNo==0)
             			{
             				 objDkoshCommanFunction.addSourceTypeAndVerify(Integer.parseInt(sectionNumber), questionNos, questionType_field, choiceType, choice1, choice2);
             				   choicesNo++;
             			}
             			else
             			{
             				if(choicesNo==1)
             				{
             					action.moveToElement(objPageModifyForms.addChoicesField(Integer.parseInt(sectionNumber), questionNos)).perform();
             					actions.click(objPageModifyForms.btnUpdatePencil(Integer.parseInt(sectionNumber), questionNos));
             					Thread.sleep(2000);
             				}
             			actions.click(objPageModifyForms.getAddChoicesButton());
             			Thread.sleep(2000);
             			actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesSNo(choicesNo), choice1);
             			actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesText(choicesNo), choice2);
             		   choicesNo++;
             			}
             			log.info("Choices has been saved successfully");
                	 }	
                	  Thread.sleep(2000);
                	  if(arrCreateQuestionChoicesData.length == choicesNo)
                	  {
                		  actions.click(objPageModifyForms.getBtnOK());
                	  }
              
                 }
                 }
    			questionNos++; 
    			Thread.sleep(2000);
        }
		softAssert.assertAll();
        Reporter.log("TC_dK_029 : This Test Case modify questions.");
	}


	
	/**
	 *  Verify default fields of applicable validation popup with all question type & add value in applicable validation field.
	 * @throws Exception
	 */
	
	@Test
	@Parameters({"CF_Id"})
	public void TC_dK_009(String CF_Id) throws Exception
	{			
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		 questionNos=1;
	        firstSectionNo=1;
	        sectionNo=1;
		initializeCreateQuestionData(CF_Id);
		for(Object[] objCreateQuestionData : arrQuestionData) {
		
		 form_idField = (String)objCreateQuestionData[0];
         sectionNumber = (String)objCreateQuestionData[1];
         selectQuestionType = (String)objCreateQuestionData[5];
         applicableValidation_minLength = (String)objCreateQuestionData[14];
         applicableValidation_maxLength = (String)objCreateQuestionData[15];
         applicableValidation_minValue = (String)objCreateQuestionData[16];
         applicableValidation_maxValue = (String)objCreateQuestionData[17];
         applicableValidation_rangeErrorMessage = (String)objCreateQuestionData[18];
         applicableValidation_regularExpression = (String)objCreateQuestionData[19];
         applicableValidation_RE_ErrorMessage = (String)objCreateQuestionData[20];
         applicableValidation_date_minValue = (String)objCreateQuestionData[21];
         applicableValidation_date_maxValue = (String)objCreateQuestionData[22];
         applicableValidation_time_minValue = (String)objCreateQuestionData[23];
         applicableValidation_time_maxValue = (String)objCreateQuestionData[24];
         applicableValidation_fileSource = (String)objCreateQuestionData[25];
         applicableValidation_fileType = (String)objCreateQuestionData[26];
         applicableValidation_fileExtension =  (String)objCreateQuestionData[27];
		Thread.sleep(2000);

		if(Integer.parseInt(sectionNumber)>sectionNo)
		{
			questionNos = 1;
			sectionNo++;
		}
		objDkoshCommanFunction.addApplicableValidation(Integer.parseInt(sectionNumber), questionNos, selectQuestionType, applicableValidation_minLength, applicableValidation_maxLength, applicableValidation_minValue, applicableValidation_maxValue, applicableValidation_rangeErrorMessage, applicableValidation_regularExpression, applicableValidation_RE_ErrorMessage, applicableValidation_date_minValue, applicableValidation_date_maxValue, applicableValidation_time_minValue, applicableValidation_time_maxValue, applicableValidation_fileSource, applicableValidation_fileType, applicableValidation_fileExtension);
		questionNos++;
		Thread.sleep(2000);
	 }
        Reporter.log("TC_dK_009 : This Test Case for Verify default fields of applicable validation popup with all question type & add value in applicable validation field."); 

	}
	
	
	/**
	 *  Verify Respondent Group name in manage form list.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_010(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeManageRespondentData(CF_Id);
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			String respondentGroup = (String)objAddRespondentData[3];
			if(form_idField.equals(CF_Id)) {	
				objPageManageForms.TCA_GoToManageFormPage();
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				softAssert.assertEquals(actions.getText(objPageManageForms.getRespondentGroupNameInFormList()), respondentGroup,"Verify Respondent group name in form list.");
			}
		}
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_010 : This Test Case For Verify Respondent Group name in manage form list.");
	}

	/**
	 * Verify default response toggle button after change status New to Active.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_012(String document_id) throws Exception {

		softAssert = new SoftAssert();
		initializeFormStatusData(document_id);
		objPageManageForms.TCA_GoToManageFormPage();
		Thread.sleep(3000);
		for(Object[] objstatusData : arrFormStatusData) {
			String formIdInSalesStatus = (String)objstatusData[0];
			String formShortName = (String)objstatusData[1];
			String currentStatusName = (String)objstatusData[2];
			String nextStatusName = (String)objstatusData[3];
			String statusMessage = (String)objstatusData[4];
			String ysn_Remark = (String)objstatusData[5];
			if (formIdInSalesStatus .equals(document_id)){
				changeStatusOfForm(formShortName,currentStatusName,nextStatusName,statusMessage, ysn_Remark);
			}
		}
		if(objPageManageForms.switchResponse().equals(true)) {
			actions.clickOnElement(objPageManageForms.getFormTab());
		}
		else
			softAssert.assertEquals(objPageManageForms.switchResponse(), true,"Toggle Button Should be On.");
		Reporter.log("TC_dK_012 : This Test Case For Verify default response toggle button after change status New to Active.");
		softAssert.assertAll();
	}

	/**
	 * changeStatusOfForm()
	 * @throws Exception
	 */
	public void changeStatusOfForm(String formShortName,String currentStatusName,String nextStatusName,String statusMessage,String ysnRemark) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		try {
		objPageManageForms.TCA_GoToManageFormPage();
		actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), formShortName);
		objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		actions.selectCustomizeDropDownValue(currentStatusName, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), currentStatusName);
		actions.clickOnElement(objPageManageForms.getBtnStatus());
		Thread.sleep(2000);
		actions.selectDropDown(objPageManageForms.getStatusDropdownInPopUpWindow(), nextStatusName);
		actions.clickOnElement(objPageManageForms.getOkButtonInStatusPopUp());
		Thread.sleep(3000);
		softAssert.assertEquals(actions.getText(objPageManageForms.getSuccessAlert()).substring(2), objDkoshMessages.getMsg_updateStatus());
		}
		catch (Exception | AssertionError e) {
			 actions.captureScreenShot(driver, screenshotUrl, methodName);
			 softAssert.assertAll();
		}
	}

	/**
	 *  Verify form status & Response Form list after accept response toggle button.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_013(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeManageRespondentData(CF_Id);
		initializeCreateFormData(CF_Id);
		int numberOfQuestion = 1;
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			String respondentGroup = (String)objAddRespondentData[3];
			if(form_idField.equals(CF_Id)) {
				objPageManageForms.TCA_GoToManageFormPage();
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				actions.selectCustomizeDropDownValue(statusNameActive, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
				Thread.sleep(2000);
				softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), statusNameActive);
				if(objPageManageForms.switchResponse().equals(true)) {
					if(actions.getText(objPageManageForms.getRespondentGroupNameInFormList()).equals("--")){
						actions.clickOnElement(objPageManageForms.getSettingIcon());
						actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_manageRespondent()));
						actions.switchToParentWindow();
						softAssert.assertEquals(actions.getText(objPageManageForms.getCreatePageHeader()), title.getTitle_manageRespondent()+" - "+txtName,"Please Check title of manage respondent page.");
						softAssert.assertTrue(driver.getCurrentUrl().contains(urlManageRespondentPage),"Please Check url of manage respondent page.");
						Thread.sleep(2000);
						actions.sendAnyKeyToElement(objPageManageRespondent.getTxtRespondentGroupSearchField(), respondentGroup);
						actions.SendKeysToElement(objPageManageRespondent.getTxtRespondentGroupSearchField());
						Thread.sleep(2000);
						if(objPageManageRespondent.chkSelectRespondentGroup().equals(true)) {
							log.info("RespondentGroup Already Added.");
						}
						else {
							actions.Select_The_Checkbox(objPageManageRespondent.getChkRespondentGroup());
						}
						softAssert.assertEquals(actions.getText(objPageManageRespondent.getTxtRespondentGroup()), respondentGroup,"Verify Respondent Group Name.");						
					}
					else {

						actions.clickOnElement(objPageManageForms.getFormTab());
						actions.clickOnElement(objPageManageForms.selectForm(txtName));
					}
				}
			}
		}
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_013 : This Test Case For Verify form status & Response Form list after accept response toggle button.");

	}

	/**
	 * Verify title ,Table header of manage responses page.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_014(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeManageRespondentData(CF_Id);
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			modifyResponse = (String)objCreateFormData[6];
			verifyResponse = (String)objCreateFormData[7];
			approveResponse = (String)objCreateFormData[8];
			
			// Manage Respondent
			String respondentGroup = (String)objAddRespondentData[3];
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			actions.selectCustomizeDropDownValue(statusNameActive, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), statusNameActive,"Status is not active for Add response.");
			if(objPageManageForms.switchResponse().equals(true)) {
				if(actions.getText(objPageManageForms.getRespondentGroupNameInFormList()).equals("--")){
					actions.clickOnElement(objPageManageForms.getSettingIcon());
					actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_manageRespondent()));
					actions.switchToParentWindow();
					softAssert.assertEquals(actions.getText(objPageManageForms.getCreatePageHeader()), title.getTitle_manageRespondent()+" - "+txtName,"Please Check title of manage respondent page.");
					softAssert.assertTrue(driver.getCurrentUrl().contains(urlManageRespondentPage),"Please Check url of manage respondent page.");
					Thread.sleep(2000);
					actions.sendAnyKeyToElement(objPageManageRespondent.getTxtRespondentGroupSearchField(), respondentGroup);
					actions.SendKeysToElement(objPageManageRespondent.getTxtRespondentGroupSearchField());
					Thread.sleep(2000);
					if(objPageManageRespondent.chkSelectRespondentGroup().equals(true)) {
						log.info("RespondentGroup Already Added.");
					}
					else {
						actions.Select_The_Checkbox(objPageManageRespondent.getChkRespondentGroup());
					}
					softAssert.assertEquals(actions.getText(objPageManageRespondent.getTxtRespondentGroup()), respondentGroup,"Verify Respondent Group Name.");						
				}
				else {
					actions.clickOnElement(objPageManageForms.getFormTab());
					actions.clickOnElement(objPageManageForms.selectForm(txtName));
				}
			}
			Thread.sleep(4000);
		}
		initializeCreateQuestionData(CF_Id);
		
		int columnNo = 2;
		int manageResponseQuestionNumber = 2;
		for(Object[] objQuestionData : arrQuestionData) {
			String form_idField = (String)objQuestionData[0];
			if(form_idField.equals(CF_Id)) { 

				/*log.info("Number Of Question in Form:"+arrQuestionData.length);
				log.info("UserValue:"+columnNo);
				log.info("Index Of Submission Date:"+driver.findElement(By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th[contains(.,'Submission Date')]")).getAttribute("data-col-seq"));
				log.info("Total No Of Columns:"+driver.findElements(By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th")).size());
				log.info("Total No Of Questions:"+(driver.findElements(By.xpath("//tr[@class='kv-page-summary bg-info text-right text-white']/th")).size()-2-4));
*/
				if(objPageManageResponses.getColumnName(columnNo).equals("User")) {
					softAssert.assertEquals(verifyResponse, "0");
					softAssert.assertEquals(approveResponse, "0");
				}
				else 
					if(objPageManageResponses.getColumnName((columnNo+1)).equals("User"))
					{
						if(verifyResponse.equals("1")) {
							softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Verified");
						}
						else
							if(approveResponse.equals("1")) {
								softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Approved");
							}
						manageResponseQuestionNumber = manageResponseQuestionNumber+1;
					}
					else 
						if(objPageManageResponses.getColumnName((columnNo+2)).equals("User")) {
							if((verifyResponse.equals("1")) && (approveResponse.equals("1"))) {
								softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Verified");
								softAssert.assertEquals(objPageManageResponses.getColumnName((columnNo+1)),"Approved");
							}
							manageResponseQuestionNumber = manageResponseQuestionNumber+1;
						}
			}
					}
		manageResponseQuestionNumber = manageResponseQuestionNumber-2;
		softAssert.assertEquals(arrQuestionData.length, manageResponseQuestionNumber,"Verify number of questions in form.");
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_014 : This Test Case For Verify title ,Table header of manage responses page.");
	}

	/**
	 * Verify confirmation message after add responses in form.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_015(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeManageRespondentData(CF_Id);
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			txtAreaInstruction = (String)objCreateFormData[5];
			modifyResponse = (String)objCreateFormData[6];
			verifyResponse = (String)objCreateFormData[7];
			approveResponse = (String)objCreateFormData[8];
			
			// Manage Respondent

			String respondentGroup = (String)objAddRespondentData[3];
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			actions.selectCustomizeDropDownValue(statusNameActive, objPageManageForms.getStatusDropDown(), objPageManageForms.getAllDataStatusDropDown());	
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), statusNameActive);
			if(objPageManageForms.switchResponse().equals(true)) {
				if(actions.getText(objPageManageForms.getRespondentGroupNameInFormList()).equals("--")){
					actions.clickOnElement(objPageManageForms.getSettingIcon());
					actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_manageRespondent()));
					actions.switchToParentWindow();
					softAssert.assertEquals(actions.getText(objPageManageForms.getCreatePageHeader()), title.getTitle_manageRespondent()+" - "+txtName,"Please Check title of manage respondent page.");
					softAssert.assertTrue(driver.getCurrentUrl().contains(urlManageRespondentPage),"Please Check url of manage respondent page.");
					Thread.sleep(2000);
					actions.sendAnyKeyToElement(objPageManageRespondent.getTxtRespondentGroupSearchField(), respondentGroup);
					actions.SendKeysToElement(objPageManageRespondent.getTxtRespondentGroupSearchField());
					Thread.sleep(2000);
					if(objPageManageRespondent.chkSelectRespondentGroup().equals(true)) {
						log.info("RespondentGroup Already Added.");
					}
					else {
						actions.Select_The_Checkbox(objPageManageRespondent.getChkRespondentGroup());
					}
					softAssert.assertEquals(actions.getText(objPageManageRespondent.getTxtRespondentGroup()), respondentGroup,"Verify Respondent Group Name.");						
				}
				else {
					actions.clickOnElement(objPageManageForms.getFormTab());
					actions.clickOnElement(objPageManageForms.selectForm(txtName));
					actions.clickOnElement(objPageManageResponses.getBtnCreate());
				}
			}
			else
				// iF Switch Response is not on
			{
				actions.clickOnElement(objPageManageForms.getSwitchResponseOff());
			}
			Thread.sleep(4000);
		}
		}
		initializeCreateQuestionData(CF_Id);
		int quesNo = 1;
		for(Object[] objQuestionData : arrQuestionData) {
			form_idField = (String)objQuestionData[0];
			sectionNumber = (String)objQuestionData[1];
			txtSectionName = (String)objQuestionData[2];
			txtSectionDescription = (String)objQuestionData[3];
			question_idField = (String)objQuestionData[4];
			selectQuestionType = (String)objQuestionData[5];
			String txtShortName = (String)objQuestionData[8];
			String response = (String)objQuestionData[31];
			if(form_idField.equals(CF_Id)) {				
			objPageAddResponses.addResponse(selectQuestionType, txtShortName, response);
			}
		}

		actions.scrollPageVertically();
		actions.scrollTo(driver, objPageAddResponses.getSaveButtonInResponsePage());
		actions.clickOnElement(objPageAddResponses.getSaveButtonInResponsePage());
		Assert.assertTrue(actions.getText(objPageAddResponses.getSuccessAlert()).contains(objDkoshMessages.getMsg_addResponse()),"Please Check Confirmation Meassage After add response.");		
		softAssert.assertAll();
		Reporter.log("TC_dK_015 : This Test Case For Verify confirmation message after add responses in form.");
	}
	
	
	/**
	 *  Verify Options with different Status of Form.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_017(String CF_Id) throws Exception {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		Object[][] actionsOnStatusHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, actionsOnStatusHeadingDataSheetName);
		Object[][] arrActionsOnStatusHeadingData =actions.getFilterDataFromExcelSheet(actionsOnStatusHeadingData, "Status");
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			if(form_idField.equals(CF_Id)) {	
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				String getStatusName = actions.getText(objPageManageForms.getBtnStatus());
				initializeActionsOnStatusData(getStatusName);
				for(Object[] objActionsOnStatusData : arrActionsOnStatusData) {
				String statusName = (String)objActionsOnStatusData[0];
				String modifyForm = (String)objActionsOnStatusData[1];
				String previewForm = (String)objActionsOnStatusData[2];
				String shareForm = (String)objActionsOnStatusData[3];
				String manageRespondents = (String)objActionsOnStatusData[4];
				String analyseResponses = (String)objActionsOnStatusData[5];
				String deleteTestResponses = (String)objActionsOnStatusData[6];
				String settings = (String)objActionsOnStatusData[7];
				String exportQuestion = (String)objActionsOnStatusData[8];
				String delete = (String)objActionsOnStatusData[9];
				String generateCustomisedReport = (String)objActionsOnStatusData[10];
				String shareReport = (String)objActionsOnStatusData[11];
				String deleteReport = (String)objActionsOnStatusData[12];
				
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				List<WebElement> actualOptionsList = driver.findElements(objPageManageForms.getOptionsInSetting());
				for(Object[] objActionsOnStatusHeadingData : arrActionsOnStatusHeadingData) {
					String modifyForm_label = (String)objActionsOnStatusHeadingData[1];
					String previewForm_label = (String)objActionsOnStatusHeadingData[2];
					String shareForm_label = (String)objActionsOnStatusHeadingData[3];
					String manageRespondent_label = (String)objActionsOnStatusHeadingData[4];
					String analyseResponse_label = (String)objActionsOnStatusHeadingData[5];
					String deleteTestResponse_label = (String)objActionsOnStatusHeadingData[6];
					String settings_label = (String)objActionsOnStatusHeadingData[7];
					String exportQuestion_label = (String)objActionsOnStatusHeadingData[8];
					String delete_label = (String)objActionsOnStatusHeadingData[9];
					String generateCustomisedReport_label = (String)objActionsOnStatusHeadingData[10];
					String shareReport_label = (String)objActionsOnStatusHeadingData[11];
					String deleteReport_label = (String)objActionsOnStatusHeadingData[12];

					if(getStatusName.equals(statusName)) {
						
						if(modifyForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(modifyForm_label, actualOptionsList).contains(modifyForm_label),"Verify this status contains "+modifyForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(modifyForm_label, actualOptionsList).contains(modifyForm_label),"Option List should not contain "+modifyForm_label+ " with "+statusName+" Status.");		
						}
						if(previewForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Verify this status contains "+previewForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Option List should not contain "+previewForm_label+ " with "+statusName+" Status.");
						}
						if(shareForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(shareForm_label, actualOptionsList).contains(shareForm_label),"Verify this status contains "+shareForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(shareForm_label, actualOptionsList).contains(shareForm_label),"Option List should not contain "+shareForm_label+ " with "+statusName+" Status.");
						}
						if(manageRespondents.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(manageRespondent_label, actualOptionsList).contains(manageRespondent_label),"Verify this status contains "+manageRespondent_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(manageRespondent_label, actualOptionsList).contains(manageRespondent_label),"Option List should not contain "+manageRespondent_label+ " with "+statusName+" Status.");
						}
						if(analyseResponses.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(analyseResponse_label, actualOptionsList).contains(analyseResponse_label),"Verify this status contains "+analyseResponse_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(analyseResponse_label, actualOptionsList).contains(analyseResponse_label),"Option List should not contain "+analyseResponse_label+ " with "+statusName+" Status.");
						}
						
						if(deleteTestResponses.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(deleteTestResponse_label, actualOptionsList).contains(deleteTestResponse_label),"Verify this status contains "+deleteTestResponse_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(deleteTestResponse_label, actualOptionsList).contains(deleteTestResponse_label),"Option List should not contain "+deleteTestResponse_label+ " with "+statusName+" Status.");
						}
						if(settings.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(settings_label, actualOptionsList).contains(settings_label),"Verify this status contains "+settings_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(settings_label, actualOptionsList).contains(settings_label),"Option List should not contain "+settings_label+ " with "+statusName+" Status.");
						}
						if(exportQuestion.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(exportQuestion_label, actualOptionsList).contains(exportQuestion_label),"Verify this status contains "+exportQuestion_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(exportQuestion_label, actualOptionsList).contains(exportQuestion_label),"Option List should not contain "+exportQuestion_label+ " with "+statusName+" Status.");
						}
						
						if(delete.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(delete_label, actualOptionsList).contains(delete_label),"Verify this status contains "+delete_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(delete_label, actualOptionsList).contains(delete_label),"Option List should not contain "+delete_label+ " with "+statusName+" Status.");
						}
						
						if(generateCustomisedReport.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(generateCustomisedReport_label, actualOptionsList).contains(generateCustomisedReport_label),"Verify this status contains "+generateCustomisedReport_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(generateCustomisedReport_label, actualOptionsList).contains(generateCustomisedReport_label),"Option List should not contain "+generateCustomisedReport_label+ " with "+statusName+" Status.");
						}
						
						if(shareReport.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(shareReport_label, actualOptionsList).contains(shareReport_label),"Verify this status contains "+shareReport_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(shareReport_label, actualOptionsList).contains(shareReport_label),"Option List should not contain "+shareReport_label+ " with "+statusName+" Status.");
						}
						
						if(deleteReport.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(deleteReport_label, actualOptionsList).contains(deleteReport_label),"Verify this status contains "+deleteReport_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(deleteReport_label, actualOptionsList).contains(deleteReport_label),"Option List should not contain "+deleteReport_label+ " with "+statusName+" Status.");
						}
					}
				
				}	
				}
			}
		}
		softAssert.assertAll();
		Reporter.log("TC_dK_017 : This Test Case For Verify Options with different Status of Form.");
		
	}
	
	
	
	/**
	 *  Verify, Question move from one section to another and verify that question in moved section.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_018(String CF_Id) throws Exception
	{
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2]; 
			Thread.sleep(2000);
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			actions.clickOnElement(objPageManageForms.getSettingIcon());
			actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_modifyForm()));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			Thread.sleep(4000);
		}
		Thread.sleep(2000);
	
		 moveToSectionData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, moveToSectionSheetName);
		 arrmoveToSectionData =actions.getFilterDataFromExcelSheet(moveToSectionData, CF_Id);
 
         for(Object[] moveQuestionData : arrmoveToSectionData)
         {
        	 fromSectionNumber = (String) moveQuestionData[2];
        	 fromSectionName = (String) moveQuestionData[3];
        	 questionNumber = (String) moveQuestionData[4];
        	 questionShortName = (String) moveQuestionData[5];
        	 toSectionNumber = (String) moveQuestionData[6];
        	 toSectionName = (String) moveQuestionData[7];
        	 objDkoshCommanFunction.moveToSection(Integer.parseInt(fromSectionNumber), Integer.parseInt(questionNumber), questionShortName, Integer.parseInt(toSectionNumber), toSectionName);
         }
     	softAssert.assertAll();
		Reporter.log("TC_dK_018 : This Test Case For  Verify, Question move from one section to another and verify that question in moved section.");

	}
	/**
	 * @author priyankag
	 * Function for Initialize Create Form Data
	 * @throws Exception
	 */
	
	private void initializeCreateFormData(String CF_Id) {
		try {
			Object[][] createFormData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, createFormSheetName);
			if(arrCreateFormData == null)
			arrCreateFormData =actions.getFilterDataFromExcelSheet(createFormData, CF_Id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @author priyankag
	 * Function for Initialize Manage Respondent Data
	 * @throws Exception
	 */
	private void initializeManageRespondentData(String CF_Id) {
		try {
			Object[][] addRespondentData =googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, manageRespondentDataSheetName);
			if(arrAddRespondentData == null)
				arrAddRespondentData =actions.getFilterDataFromExcelSheet(addRespondentData, CF_Id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @author priyankag
	 * Function for Initialize Create Question Data
	 * @throws Exception
	 */
	private void initializeCreateQuestionData(String CF_Id) {
		try {
			Object[][] createQuestionData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, questionSheetName);
			if(arrQuestionData == null)
				arrQuestionData =actions.getFilterDataFromExcelSheet(createQuestionData, CF_Id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * @author priyankag
	 * Function for Initialize Manage Status Data
	 * @throws Exception
	 */
	private void initializeFormStatusData(String document_id) {
		try {
			Object[][] formStatusData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, formStatusDataSheetName);
			if(arrFormStatusData == null)
				arrFormStatusData =actions.getFilterDataFromExcelSheet(formStatusData, document_id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @author priyankag
	 * Function for Initialize Actions On Status Data
	 * @throws Exception
	 */
	private void initializeActionsOnStatusData(String statusName) {
		try {
			Object[][] actionsOnStatusData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, actionsOnStatusDataSheetName);
			//if(arrActionsOnStatusData == null)
				arrActionsOnStatusData =actions.getFilterDataFromExcelSheet(actionsOnStatusData, statusName);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * @author priyankag
	 * Get Question List Action
	 * @throws Exception
	 */
	
	public void TC_dK_019(String questionType,String sectionNo, String questionNo) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		Object[][] questionListActionHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, questionListActionHeadingDataSheetName);
		Object[][] arrQuestionListActionHeadingData =actions.getFilterDataFromExcelSheet(questionListActionHeadingData, "QuestionList_Action");
		actions.clickOnElement(objDkoshCommanFunction.ListActionIcon(sectionNo, questionNo));
		Thread.sleep(2000);
		List<WebElement> actualQuestionOptionsList = objPageModifyForms.optionsInListAction(Integer.parseInt(sectionNo), Integer.parseInt(questionNo));
		String questionListActionDataSheetName = "QuestionList_Action!A2:Z";
		Object[][] questionListActionData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, questionListActionDataSheetName);
		Object[][] arrQuestionListActionData;
		arrQuestionListActionData =actions.getFilterDataFromExcelSheet(questionListActionData, questionType);
		for(Object[] objQuestionListActionData : arrQuestionListActionData) {
			String questionTypeName = (String)objQuestionListActionData[0];
			String insertImages = (String)objQuestionListActionData[1];
			String insertAudio = (String)objQuestionListActionData[2];
			String insertVideo = (String)objQuestionListActionData[3];
			String reorderQuestion = (String)objQuestionListActionData[4];
			String moveToSection = (String)objQuestionListActionData[5];
			String exportQuestion = (String)objQuestionListActionData[6];
			String applicableValidation = (String)objQuestionListActionData[7];
			String manageDependency = (String)objQuestionListActionData[8];
		
			for(Object[] objQuestionListActionHeadingData : arrQuestionListActionHeadingData) {
				String insertImages_label = (String)objQuestionListActionHeadingData[1];
				String insertAudio_label = (String)objQuestionListActionHeadingData[2];
				String insertVideo_label = (String)objQuestionListActionHeadingData[3];
				String reorderQuestion_label = (String)objQuestionListActionHeadingData[4];
				String moveToSection_label = (String)objQuestionListActionHeadingData[5];
				String exportQuestion_label = (String)objQuestionListActionHeadingData[6];
				String applicableValidation_label = (String)objQuestionListActionHeadingData[7];
				String manageDependency_label = (String)objQuestionListActionHeadingData[8];

				if(questionType.equals(questionTypeName)) {

					if(insertImages.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(insertImages_label, actualQuestionOptionsList).contains(insertImages_label),"Verify this Question Type contains "+insertImages_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(insertImages_label, actualQuestionOptionsList).contains(insertImages_label),"Option List should not contain "+insertImages_label+ " with "+questionType+" - Type Question.");		
					}

					if(insertAudio.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(insertAudio_label, actualQuestionOptionsList).contains(insertAudio_label),"Verify this Question type contains "+insertAudio_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(insertAudio_label, actualQuestionOptionsList).contains(insertAudio_label),"Option List should not contain "+insertAudio_label+ " with "+questionType+" - Type Question.");		
					}

					if(insertVideo.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(insertVideo_label, actualQuestionOptionsList).contains(insertVideo_label),"Verify this Question type contains "+insertVideo_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(insertVideo_label, actualQuestionOptionsList).contains(insertVideo_label),"Option List should not contain "+insertVideo_label+ " with "+questionType+" - Type Question.");		
					}

					if(reorderQuestion.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(reorderQuestion_label, actualQuestionOptionsList).contains(reorderQuestion_label),"Verify this Question Type contains "+reorderQuestion_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(reorderQuestion_label, actualQuestionOptionsList).contains(reorderQuestion_label),"Option List should not contain "+reorderQuestion_label+ " with "+questionType+" - Type Question.");		
					}

					if(moveToSection.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(moveToSection_label, actualQuestionOptionsList).contains(moveToSection_label),"Verify this Question type contains "+moveToSection_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(moveToSection_label, actualQuestionOptionsList).contains(moveToSection_label),"Option List should not contain "+moveToSection_label+ " with "+questionType+" - Type Question.");		
					}

					if(exportQuestion.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(exportQuestion_label, actualQuestionOptionsList).contains(exportQuestion_label),"Verify this Question type contains "+exportQuestion_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(exportQuestion_label, actualQuestionOptionsList).contains(exportQuestion_label),"Option List should not contain "+exportQuestion_label+ " with "+questionType+" - Type Question.");		
					}
					if(applicableValidation.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(applicableValidation_label, actualQuestionOptionsList).contains(applicableValidation_label),"Verify this Question type contains "+applicableValidation_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(applicableValidation_label, actualQuestionOptionsList).contains(applicableValidation_label),"Option List should not contain "+applicableValidation_label+ " with "+questionType+" - Type Question.");		
					}
					if(manageDependency.equalsIgnoreCase("1")) {
						softAssert.assertTrue(actions.getOptionsList(manageDependency_label, actualQuestionOptionsList).contains(manageDependency_label),"Verify this Question type contains "+manageDependency_label+" Option.");
					}
					else {
						softAssert.assertFalse(actions.getOptionsList(manageDependency_label, actualQuestionOptionsList).contains(manageDependency_label),"Option List should not contain "+manageDependency_label+ " with "+questionType+" - Type Question.");		
					}
				}
			}
			actions.clickOnElement(objDkoshCommanFunction.ListActionIcon(sectionNo, questionNo));
		}
		softAssert.assertAll();
	}
	
	/**
	 * Verify, Title and pop up of Preview form is opened.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_021(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		Object[][] actionsOnStatusHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, actionsOnStatusHeadingDataSheetName);
		Object[][] arrActionsOnStatusHeadingData =actions.getFilterDataFromExcelSheet(actionsOnStatusHeadingData, "Status");
		String previewForm_label = null;
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			if(form_idField.equals(CF_Id)) {	
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				String getStatusName = actions.getText(objPageManageForms.getBtnStatus());
				initializeActionsOnStatusData(getStatusName);
				for(Object[] objActionsOnStatusData : arrActionsOnStatusData) {
				String statusName = (String)objActionsOnStatusData[0];
				String previewForm = (String)objActionsOnStatusData[2];
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				List<WebElement> actualOptionsList = driver.findElements(objPageManageForms.getOptionsInSetting());
				for(Object[] objActionsOnStatusHeadingData : arrActionsOnStatusHeadingData) {
					previewForm_label = (String)objActionsOnStatusHeadingData[2];
					if(getStatusName.equals(statusName)) {
						
						if(previewForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Verify this status contains "+previewForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Option List should not contain "+previewForm_label+ " with "+statusName+" Status.");
						}
					}
				}	
				actions.clickOnElement(objPageManageForms.selectOption(previewForm_label));
				Thread.sleep(2000);
				softAssert.assertTrue(actions.isElementDisplayed(driver.findElement(By.xpath("//div[@class='modal-header']/strong[contains(.,'"+txtName+"')]"))),"Verify Preview form popup title.");
				actions.clickOnElement(driver.findElement(By.xpath("//div[@class='modal-header']/a[@class = 'close']")));
				}
			}
		}
		actions.scrollPageVertically();
		softAssert.assertAll();
		Reporter.log("TC_dK_021 : This Test Case For Verify, Title and pop up of Preview form is opened.");
	}
	
	
	/**
	 * Verify, Save button on preview form with different status.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_022(String CF_Id) throws Exception {

		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		Object[][] actionsOnStatusHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, actionsOnStatusHeadingDataSheetName);
		Object[][] arrActionsOnStatusHeadingData =actions.getFilterDataFromExcelSheet(actionsOnStatusHeadingData, "Status");
		String previewForm_label = null;
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			if(form_idField.equals(CF_Id)) {	
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				String getStatusName = actions.getText(objPageManageForms.getBtnStatus());
				initializeActionsOnStatusData(getStatusName);
				for(Object[] objActionsOnStatusData : arrActionsOnStatusData) {
				String statusName = (String)objActionsOnStatusData[0];
				String previewForm = (String)objActionsOnStatusData[2];
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				List<WebElement> actualOptionsList = driver.findElements(objPageManageForms.getOptionsInSetting());
				for(Object[] objActionsOnStatusHeadingData : arrActionsOnStatusHeadingData) {
					previewForm_label = (String)objActionsOnStatusHeadingData[2];
					if(getStatusName.equals(statusName)) {
						if(previewForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Verify this status contains "+previewForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Option List should not contain "+previewForm_label+ " with "+statusName+" Status.");
						}
					}
				}	
				actions.clickOnElement(objPageManageForms.selectOption(previewForm_label));
				Thread.sleep(2000);
				if(!getStatusName.equals("New(Draft)")) {
				log.info("Save Button :"+actions.isElementDisplayed(driver.findElement(By.xpath("//button[contains(.,'Save')]"))));
				softAssert.assertTrue(actions.isElementDisplayed(driver.findElement(By.xpath("//button[contains(.,'Save')]"))),"Verify presense of save button in preview form with -"+getStatusName+ "- Status of form.");
				}
				actions.clickOnElement(driver.findElement(By.xpath("//div[@class='modal-header']/a[@class = 'close']")));
				}
				}
		}
		actions.scrollPageVertically();
		softAssert.assertAll();
		Reporter.log("TC_dK_022 : This Test Case For Verify, Save button on preview form with different status.");
	}
	
	
	
	/**
	 * Verify response list action after add responses & confirmation message after verify & approve response.(Manage responses)
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_020(String CF_Id) throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		initializeCreateFormData(CF_Id);
		initializeManageRespondentData(CF_Id);
		if(arrCreateFormData !=null) {
		for(Object[] objCreateFormData : arrCreateFormData) {
			for(Object[] objAddRespondentData : arrAddRespondentData) { 
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			modifyResponse = (String)objCreateFormData[6];
			verifyResponse = (String)objCreateFormData[7];
			approveResponse = (String)objCreateFormData[8];
			// Manage Respondent
			String respondentGroup = (String)objAddRespondentData[3]; 
			String editResponsesType = (String)objAddRespondentData[6];
			String verifyResponsesType = (String)objAddRespondentData[7];
			String approveResponsesType = (String)objAddRespondentData[8];
			objPageManageForms.TCA_GoToManageFormPage();
			actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
			objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if(objPageManageForms.switchResponse().equals(true)) {
				if(actions.getText(objPageManageForms.getRespondentGroupNameInFormList()).equals("--")){
					actions.clickOnElement(objPageManageForms.getSettingIcon());
					actions.clickOnElement(objPageManageForms.selectOption(title.getTitle_manageRespondent()));
					actions.switchToParentWindow();
					softAssert.assertEquals(actions.getText(objPageManageForms.getCreatePageHeader()), title.getTitle_manageRespondent()+" - "+txtName,"Please Check title of manage respondent page.");
					softAssert.assertTrue(driver.getCurrentUrl().contains(urlManageRespondentPage),"Please Check url of manage respondent page.");
					Thread.sleep(2000);
					actions.sendAnyKeyToElement(objPageManageRespondent.getTxtRespondentGroupSearchField(), respondentGroup);
					actions.SendKeysToElement(objPageManageRespondent.getTxtRespondentGroupSearchField());
					Thread.sleep(2000);
					if(objPageManageRespondent.chkSelectRespondentGroup().equals(true)) {
						log.info("RespondentGroup Already Added.");
					}
					else {
						actions.Select_The_Checkbox(objPageManageRespondent.getChkRespondentGroup());
					}
					softAssert.assertEquals(actions.getText(objPageManageRespondent.getTxtRespondentGroup()), respondentGroup,"Verify Respondent Group Name.");						
				}
				else {
					actions.clickOnElement(objPageManageForms.getFormTab());
					actions.clickOnElement(objPageManageForms.selectForm(txtName));
				}
			}else {
				
				softAssert.assertEquals(actions.getText(objPageManageForms.getBtnStatus()), "Active","For Manage Response Page Form Status Should be Active");
			}
			
			
			initializeCreateQuestionData(CF_Id);
			
			int columnNo = 2;
			int questionCount = 0;
			int totalResponses = 0;
			//actions.scrollRight(driver);
			Thread.sleep(2000);
			int actionColumnNo = driver.findElements(objPageManageResponses.getResponseTableHeading()).size();
			Thread.sleep(2000);
			List<WebElement> noOfResponses = driver.findElements(By.xpath("//div[@id='w2-container']/table//tbody/tr"));
			
			totalResponses = noOfResponses.size();

			for(Object[] objQuestionData : arrQuestionData) {
				form_idField = (String)objQuestionData[0];
				sectionNumber = (String)objQuestionData[1];
				txtSectionName = (String)objQuestionData[2];
				txtSectionDescription = (String)objQuestionData[3];
				question_idField = (String)objQuestionData[4];
				selectQuestionType = (String)objQuestionData[5];
				String txtShortName = (String)objQuestionData[8];
				String response = (String)objQuestionData[31];
				if(form_idField.equals(CF_Id)) { 

					if(objPageManageResponses.getColumnName(columnNo).equals("User")) {
						softAssert.assertEquals(verifyResponse, "0");
						softAssert.assertEquals(approveResponse, "0");
						questionCount = 0;
					}
					else 
						if(objPageManageResponses.getColumnName((columnNo+1)).equals("User"))
						{
							if(verifyResponse.equals("1")) {
								softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Verified");
							}
							else
								if(approveResponse.equals("1")) {
									softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Approved");
								}
							questionCount = 1;
						}
						else 
							if(objPageManageResponses.getColumnName((columnNo+2)).equals("User")) {
								if((verifyResponse.equals("1")) && (approveResponse.equals("1"))) {
									softAssert.assertEquals(objPageManageResponses.getColumnName(columnNo),"Verified");
									softAssert.assertEquals(objPageManageResponses.getColumnName((columnNo+1)),"Approved");
								}
								questionCount = 2;
							}
					
				// Add Response
				/*	actions.clickOnElement(objPageManageResponses.getBtnCreate())
					objPageAddResponses.addResponse(selectQuestionType, txtShortName, response);
					actions.scrollPageVertically();
					actions.scrollTo(driver, objPageAddResponses.getSaveButtonInResponsePage());
					actions.clickOnElement(objPageAddResponses.getSaveButtonInResponsePage());
					Assert.assertTrue(actions.getText(objPageAddResponses.getSuccessAlert()).contains(objDkoshMessages.getMsg_addResponse()),"Please Check Confirmation Meassage After add response.");		
*/				}
			}
			softAssert.assertEquals(arrQuestionData.length, (actionColumnNo - questionCount -4),"Verify number of questions in form.");
			//totalResponses = (noOfResponses.size()+1);
			//softAssert.assertEquals(totalResponses, (noOfResponses.size()+1),"Verify Number Of Rows After add response.");
			//js.executeScript("window.scrollBy(1000,0)");
			Thread.sleep(2000);
			actions.scrollRight(driver);
			WebElement element = driver.findElement(By.xpath("//tr["+totalResponses+"]/td["+actionColumnNo+"]/div/a/i"));		
			actions.clickOnElement(element);
			List<WebElement> actionsList = driver.findElements(By.xpath("//tr["+totalResponses+"]/td["+actionColumnNo+"]/div/a/following-sibling::ul/li/a"));
			log.info("Actions List Size :"+actionsList.size());
			if(modifyResponse.equals("1") && verifyResponse.equals("1") && approveResponse.equals("1")) {
				
				System.out.println("*****&:"+actionsList.contains("Modify Responses"));
				
				softAssert.assertEquals(actionsList.get(0).getText(),"Modify Responses","Modify Responses option should be available with this form at 0 index.");
				softAssert.assertEquals(actionsList.get(1).getText(),"Verify Responses","Verify Responses option should be available with this form at 1 index.");
				softAssert.assertEquals(actionsList.get(2).getText(),"Approve Responses","Approve Responses option should be available with this form at 2 index.");

				log.info("Modify Response Option :"+actionsList.get(0).getText());
			}
			if(verifyResponse.equals("1")) {
				System.out.println("======:"+actionsList.contains("Verify Responses"));

				softAssert.assertEquals(actionsList.get(1).getText(),"Verify Responses","Verify Responses option should be available with this form at 1 index.");
				log.info("Verify Response Option :"+actionsList.get(1).getText());
			}
			if(approveResponse.equals("1")) {
				System.out.println("*&&^^^^^:"+actionsList.contains("Approve Responses"));

				softAssert.assertEquals(actionsList.get(2).getText(),"Approve Responses","Approve Responses option should be available with this form at 2 index.");
				log.info("Approve Response Option :"+actionsList.get(2).getText());
			}
			}
		}
	}
		softAssert.assertAll();
		Reporter.log("TC_dK_020 : This Test Case For Verify response list action after add responses & confirmation message after verify & approve response.(Manage responses).");
	}
	
	

	/**
	 * Verify, Test response is save from preview form and verify in Manage response page.
	 * @throws Exception
	 */
	@Test
	@Parameters({ "CF_Id"})

	public void TC_dK_023(String CF_Id) throws Exception {
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		softAssert = new SoftAssert();
		Object[][] actionsOnStatusHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, actionsOnStatusHeadingDataSheetName);
		Object[][] arrActionsOnStatusHeadingData =actions.getFilterDataFromExcelSheet(actionsOnStatusHeadingData, "Status");
		String previewForm_label = null;
		objPageManageForms.TCA_GoToManageFormPage();
		initializeCreateFormData(CF_Id);
		for(Object[] objCreateFormData : arrCreateFormData) {
			form_idField = (String)objCreateFormData[0];
			txtName = (String)objCreateFormData[1];
			txtShortName = (String)objCreateFormData[2];
			if(form_idField.equals(CF_Id)) {	
				actions.clickOnElement(objPageManageForms.getTxtFormName());
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtFormName(), txtName);
				objPageManageForms.getTxtFormName().sendKeys(Keys.ENTER);
				actions.clearAndSendKeysToElement(objPageManageForms.getTxtShortName(), txtShortName);
				objPageManageForms.getTxtShortName().sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				String getStatusName = actions.getText(objPageManageForms.getBtnStatus());
				initializeActionsOnStatusData(getStatusName);
				for(Object[] objActionsOnStatusData : arrActionsOnStatusData) {
				String statusName = (String)objActionsOnStatusData[0];
				String previewForm = (String)objActionsOnStatusData[2];
				actions.clickOnElement(objPageManageForms.getSettingIcon());
				List<WebElement> actualOptionsList = driver.findElements(objPageManageForms.getOptionsInSetting());
				for(Object[] objActionsOnStatusHeadingData : arrActionsOnStatusHeadingData) {
					previewForm_label = (String)objActionsOnStatusHeadingData[2];
					if(getStatusName.equals(statusName)) {
						
						if(previewForm.equalsIgnoreCase("1")) {
							softAssert.assertTrue(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Verify this status contains "+previewForm_label+" Option.");
						}
						else {
							softAssert.assertFalse(actions.getOptionsList(previewForm_label, actualOptionsList).contains(previewForm_label),"Option List should not contain "+previewForm_label+ " with "+statusName+" Status.");
						}
					}
				}	
				actions.clickOnElement(objPageManageForms.selectOption(previewForm_label));
				Thread.sleep(2000);
				
				initializeCreateQuestionData(CF_Id);
				int quesNo = 1;
				for(Object[] objQuestionData : arrQuestionData) {
					form_idField = (String)objQuestionData[0];
					sectionNumber = (String)objQuestionData[1];
					txtSectionName = (String)objQuestionData[2];
					txtSectionDescription = (String)objQuestionData[3];
					question_idField = (String)objQuestionData[4];
					selectQuestionType = (String)objQuestionData[5];
					String txtShortName = (String)objQuestionData[8];
					String response = (String)objQuestionData[31];
					log.info("No Of Sections:"+driver.findElements(By.xpath("//div[contains(@id,'section_')]")).size());

					if(form_idField.equals(CF_Id)) {

						//log.info("No Of Question In 1st Section :"+driver.findElements(By.xpath("//div[@class='dynamicForm']/div[@class='row'][1]//div[@class='box-body']/div/div")).size());
						//log.info("No Of Question In 2nd Section :"+driver.findElements(By.xpath("//div[@class='dynamicForm']/div[@class='row'][2]//div[@class='box-body']/div/div")).size());
						objPageAddResponses.addResponse(selectQuestionType, txtShortName, response);
					}
				}
				}
			}
		}
		actions.scrollPageVertically();
		//actions.scrollTo(driver, objPageAddResponses.getSaveButtonInResponsePage());
		//	actions.clickOnElement(objPageAddResponses.getSaveButtonInResponsePage());
		//	Assert.assertTrue(actions.getText(objPageAddResponses.getSuccessAlert()).contains(objDkoshMessages.getMsg_addResponse()),"Please Check Confirmation Meassage After add response.");		
		softAssert.assertAll();
		Reporter.log("TC_dK_023 : This Test Case For Verify, Test response is save from preview form and verify in Manage response page.");
	}
	
	
	
	// Preview Form
	
	// No of Section 
	
	//div[contains(@id,'section_')]
	
	// First Section
	
	//div[contains(@id,'section_')][1]
	
	
	//div[contains(@id,'section_')][1]//div[contains(@id,'question_')]
	
	
}
