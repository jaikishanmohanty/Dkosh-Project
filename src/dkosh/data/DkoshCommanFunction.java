package dkosh.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import authentication.NavigationBar;
import browsers.BrowserActions;
import dkosh.pom.PageModifyForm;
import helpers.LoggerHelper;
import utilities.GoogleSheetAPIUtils;

public class DkoshCommanFunction {
	
	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	Actions action;
	GoogleSheetAPIUtils googleSheet;
	PageModifyForm objPageModifyForms;
	DkoshTitleData title = new DkoshTitleData();
	DkoshMessageData message = new DkoshMessageData();
	
	// Sheet Data
	String questionListActionHeadingDataSheetName = "QuestionList_Action!A1:Z";
	String dKOSHDataSpreadsheetId = "1Q4_V7oh3wSdl4HFnlk1UwHA-O4k3HE6KePS89EtlmW0";
	
	// Prefill Default Type
	String txtDefaultType = "Select Prefill Value";
	String screenshotUrl = System.getProperty("user.dir") + "/../Screenshot/";


	
	public DkoshCommanFunction(WebDriver driver) throws Exception {
		this.driver = driver;
		actions = new BrowserActions(driver);
		action = new Actions(driver);
		navigationBar = new NavigationBar(driver);
		googleSheet = new GoogleSheetAPIUtils();
		objPageModifyForms =new PageModifyForm(driver);
		PageFactory.initElements(driver, this);
		softAssert = new SoftAssert();
		title.getTitle();
		message.getMessage();
		wait = new WebDriverWait(driver, 40);
	}
	Logger log = LoggerHelper.getLogger(DkoshCommanFunction.class);

	
		/**
		 * Function to Add Another Section
		 * 
		 * @param String sectionName, String sectionDescription
		 * @author Jaikishan Mohanty
		 * @since 1-Feb-2020
		 * @throws InterruptedException
		 */
		public void addSectionAndVerify(String sectionName, String sectionDescription) throws InterruptedException {
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			actions.click(objPageModifyForms.addSection());
			Thread.sleep(2000);
			actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Add Section Pop not displayed");
			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_addSection(),"Title not macthed");
			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionName()),"Section Name field is not displayed");
			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionDescription()),"Section Desccription field is not displayed");
			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionName(), sectionName);
			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionDescription(), sectionDescription);
			actions.click(objPageModifyForms.getBtnOK());
			try {
				Assert.assertTrue(actions.getText(objPageModifyForms.getSuccessAlert()).contains(message.getMsg_createSection()));
			} catch (Exception e) {
				log.error("Create Section message is not displayed.");
			}
			softAssert.assertAll();
			log.info("Section has been created successfully.");
		}
		
		/**
		 * Create Question From Question Panel & Verify Default Value of fields of Question
		 * @param Integer sectionNo, String questionName, int numberOfQuestion
		 * @author Jaikishan Mohanty
		 * @since 30-Jan-2020
		 * @throws InterruptedException
		 */
		public void createQuestionAndVerifyPanel(Integer sectionNo, String questionName, int questionNo) throws InterruptedException {
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
			// Create Question with Question Name
			objPageModifyForms.createQuestion(questionName).click();
			Thread.sleep(1500);
			try {
				Assert.assertTrue(actions.getText(objPageModifyForms.getSuccessAlert()).contains(message.getMsg_createQuestion()));                	
			}
			catch (Exception e) {                        
				log.error("Please Check Confirmation Meassage After Add Question.");
			}
			
			Thread.sleep(3000);

			boolean required = objPageModifyForms.requiredToggle(sectionNo,questionNo).getAttribute("class").contains("switch-on");
			boolean enabled = objPageModifyForms.enabledToggle(sectionNo,questionNo).getAttribute("class").contains("switch-on");
			boolean hidden = objPageModifyForms.hiddenToggle(sectionNo,questionNo).getAttribute("class").contains("switch-off");
			Select selectedQuestionType = new Select(objPageModifyForms.txtQuestionType(sectionNo,questionNo));
			softAssert.assertEquals(selectedQuestionType.getFirstSelectedOption().getText(), questionName,"Question Type not matched");
			softAssert.assertTrue(objPageModifyForms.txtQuestionName(sectionNo, questionNo).getAttribute("value").contains("Question_"), "Question name not matched");		
			softAssert.assertTrue(objPageModifyForms.txtInstruction(sectionNo, questionNo).getAttribute("value").contains(""), "Description not matched");
			softAssert.assertTrue(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo).getAttribute("value").contains("short_name_"), "Question Short Name not matched");
			if(!questionName.equalsIgnoreCase("file"))
			{
			softAssert.assertEquals(objPageModifyForms.txtDefaultPrefillType(sectionNo, questionNo).getText(),txtDefaultType, "Default Prefill value not matched");
			}
			softAssert.assertEquals(required, true, "Required Default Value not matched");
			softAssert.assertEquals(enabled, true, "Enabled Default Value not matched");
			softAssert.assertEquals(hidden, true, "Hidden Default Value not matched");
			softAssert.assertAll();
			log.info("Create Question and its panel verified successfully");
		}
		
		/**
		 * Modify Question field according to test data.
		 * @param Integer sectionNo, Integer numberOfQuestion, String questionType, String
		 *                questionName,String description, String shortName,String
		 *                prefillType,String required,String enabled, String hidden
		 * @author Jaikishan Mohanty
		 * @since 30-Jan-2020
		 * @throws InterruptedException
		 */
		public void ModifyQuestionFields(Integer sectionNo, Integer questionNo, String questionType, String questionName,
				String description, String shortName, String prefillType, String prefillValue, String required, String enabled, String hidden)
						throws InterruptedException {
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
			// Select Question Type
			Select QuestionTypeSelect = new Select(objPageModifyForms.txtQuestionType(sectionNo, questionNo));
			QuestionTypeSelect.selectByVisibleText(questionType);

			// Enter Question Name
			actions.clearAndSendKeysToElement(objPageModifyForms.txtQuestionName(sectionNo, questionNo), questionName);

			// Enter Description of Question
			actions.clearAndSendKeysToElement(objPageModifyForms.txtInstruction(sectionNo, questionNo), description);

			// Enter ShortName
			actions.sendAnyKeyToElement(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo),Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(2000);
			actions.sendAnyKeyToElement(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo), shortName);
			Thread.sleep(3000);
			actions.SendTabToElement(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo));
			
			// Select Prefill type
			Select selectPrefillType = null;
			if(!questionType.equalsIgnoreCase("file"))
			{
			selectPrefillType = new Select(objPageModifyForms.txtPrefillType(sectionNo, questionNo));
			}
			Thread.sleep(3000);
			if (prefillType.equalsIgnoreCase("Fix value")) {
				// Select prefill type as fix value and fill accordingly.

				selectPrefillType.selectByVisibleText(prefillType);
				log.info("Fix value is selected");
				Thread.sleep(4000);
				action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				Thread.sleep(1000);
				try {
					actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				} catch (Exception e) {
					action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				}
				actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
				Thread.sleep(3000);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Prefill Value Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_updatePrefillValue(),"Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtprefillValue()),"Prefill text field is not displayed");
				Thread.sleep(2000);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtprefillValue(), prefillValue);
				actions.click(objPageModifyForms.getBtnOK());
				log.info("Prefill Fix Value has been Saved successfully");

			} else if (prefillType.equalsIgnoreCase("Query")) {
				// Select prefill type as Query and fill accordingly.
				selectPrefillType.selectByVisibleText(prefillType);
				log.info("Query is selected");
				Thread.sleep(5000);
				action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				Thread.sleep(1000);
				try {
					actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				} catch (Exception e) {
					action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				}
				actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
				Thread.sleep(3000);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Query Pop not displayed");	
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_updatePrefillValue(),"Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtMySql()),"My Sql field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSqlLite()),"SQL Lite field is not displayed");

				Thread.sleep(2000);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtMySql(), prefillValue);
				Thread.sleep(1000);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSqlLite(), prefillValue);
				actions.click(objPageModifyForms.getBtnOK());
				log.info("Prefill Query has been Saved successfully");

			}else if(prefillType.equalsIgnoreCase("Current value"))
				{
				selectPrefillType.selectByVisibleText(prefillType);
				log.info("Current Value is selected");
				Thread.sleep(4000);	
				log.info("Current Value : "+actions.getValue(objPageModifyForms.prefillValueField(sectionNo, questionNo)));
				softAssert.assertEquals(actions.getValue(objPageModifyForms.prefillValueField(sectionNo, questionNo)), "now", "Prefill Value field for current value not matched");
			}
			else {
				log.debug("Prefill Type is not matched");
			}
			Thread.sleep(3000);
			// Select Required Toggle
			if (!objPageModifyForms.requiredToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + required)) {
				actions.clickOnElement(objPageModifyForms.requiredToggle(sectionNo, questionNo));
			}
			// Select Enabled Toggle
			if (!objPageModifyForms.enabledToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + enabled)) {
				actions.clickOnElement(objPageModifyForms.enabledToggle(sectionNo, questionNo));
			}
			// Select Hidden Toggle
			if (!objPageModifyForms.hiddenToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + hidden)) {
				actions.clickOnElement(objPageModifyForms.hiddenToggle(sectionNo, questionNo));
			}
			/* Verify the entered value is correct or not */
			Thread.sleep(3000);
		
			softAssert.assertEquals(objPageModifyForms.txtQuestionName(sectionNo, questionNo).getAttribute("value"),questionName, "Question name not matched");
			softAssert.assertEquals(objPageModifyForms.txtInstruction(sectionNo, questionNo).getAttribute("value"), description,"Description not matched");	
			softAssert.assertEquals(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo).getAttribute("value"),shortName, "Question Short Name not matched");
			softAssert.assertEquals(objPageModifyForms.requiredToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + required), true, "Required Default Value not matched");
			softAssert.assertEquals(objPageModifyForms.enabledToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + enabled),true, "Enabled Default Value not matched");
			softAssert.assertEquals(objPageModifyForms.hiddenToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + hidden),true, "Hidden Default Value not matched");
			softAssert.assertAll();
			log.info("Question has been modified successfully");
		}
		
		/**
		 *Add Source Type and Verify
		 * 
		 * @param Integer sectionNo, Integer questionNo, String questionType, String sourceType
		 * @author Jaikishan Mohanty
		 * @since 03-Feb-2020
		 * @throws InterruptedException
		 */
		
		public void addSourceTypeAndVerify(Integer sectionNo, Integer questionNo, String questionType, String sourceType, String choice1, String choice2) throws InterruptedException
		{
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
			//try {
			Integer rowNo =0;
			Select selectQuestionType = new Select(objPageModifyForms.txtQuestionType(sectionNo, questionNo));
			selectQuestionType.selectByVisibleText(questionType);
			Thread.sleep(3000);
			Select selectSourceType = new Select(objPageModifyForms.sourceType(sectionNo, questionNo));

			Thread.sleep(2000);
			if (sourceType.equalsIgnoreCase("User Defined List")) {
				// Select source type as User Defined List and fill accordingly.

				selectSourceType.selectByVisibleText(sourceType);
				log.info("User Defined List is selected");
				Thread.sleep(4000);
				action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
				Thread.sleep(1000);
				try {
					actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				} catch (Exception e) {
					action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				}
				actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
				Thread.sleep(2000);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "User Defined List Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_userDefinedList(),"User Defined List Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.userDefined_choicesSNo(rowNo)),"S.No field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.userDefined_choicesText(rowNo)),"Text choices field is not displayed");
				Thread.sleep(2000);
				actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesSNo(rowNo), choice1);
				actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesText(rowNo), choice2);
				actions.click(objPageModifyForms.getBtnOK());
				log.info("Choices has been saved successfully");
			}

			else if(sourceType.equalsIgnoreCase("Query"))
			{
				// Select Source type as Query and fill accordingly.
				selectSourceType.selectByVisibleText(sourceType);
				
				log.info("Query is selected");
				Thread.sleep(4000);
				action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
				Thread.sleep(1000);
				try {
					actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				} catch (Exception e) {
					action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
				}
				actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
				Thread.sleep(2000);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Manage Query Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_manageQuery(),"Manage Query Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtMySql()),"MySql field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSqlLite()),"SQL Lite field is not displayed");
				Thread.sleep(2000);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtMySql(), choice1);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSqlLite(), choice2);
				actions.click(objPageModifyForms.getBtnOK());
				log.info("Query has been saved successfully");
			}
			else if(sourceType.equalsIgnoreCase("Year"))
			{
				// Select Source type as Year and fill accordingly.
				selectSourceType.selectByVisibleText(sourceType);
				log.info("Year is selected");				
				Thread.sleep(4000);
				action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
				Thread.sleep(1000);
				try {
					actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				} catch (Exception e) {
					action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
				}
				actions.waitForVisibility(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo),10);
				actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
				Thread.sleep(3000);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Year Range Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_yearRange(),"Year Range Title not macthed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getYear_YearFromField()),"Year From field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getYear_YearToField()),"Year To field is not displayed");
				Thread.sleep(2000);
				actions.clearAndSendKeysToElement(objPageModifyForms.getYear_YearFromField(), choice1);
				actions.clearAndSendKeysToElement(objPageModifyForms.getYear_YearToField(), choice2);
				actions.click(objPageModifyForms.getBtnOK());
				log.info("Year Saved successfully.");
			}
			else
			{
				selectSourceType.selectByVisibleText(sourceType);
				log.info(sourceType+" is Selected successfully");
				Thread.sleep(2000);
			}
			softAssert.assertAll();
		/*	}
			catch (Exception | AssertionError e) {
				 actions.captureScreenShot(driver, screenshotUrl, methodName);
				 softAssert.assertAll();
			}*/
		}
		/**
		 * Function for verify Applicable Validation & Verify validation with all
		 * question type.
		 * @param Integer sectionNo, Integer questionNo, String questionType, String minimumNumber, String
		 *                maximumNumber, String errorMessage, String dateMinValue,
		 *                String dateMaxValue, String timeMinValue, String timeMaxValue,
		 *                String fileSource, String fileType, String fileExtension
		 * @author Jaikishan Mohanty
		 * @since 30-Jan-2020
		 * @throws InterruptedException
		 */

		public void addApplicableValidation(Integer sectionNo, Integer questionNo, String questionType, String minLength, String maxLength, String minValue, String maxValue, String rangeErrorMessage, String regularExpression, String REErrorMessage, String dateMinValue, String dateMaxValue, String timeMinValue,String timeMaxValue, String fileSource, String fileType, String fileExtension) throws InterruptedException
		{
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
		//	try {
			String applicableValidation = title.getTitle_applicableValidation();
			try {
				actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
			} catch (Exception e) {
				actions.scrollTo(driver, objPageModifyForms.questionAction(sectionNo, questionNo));
				actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
			}

			log.info("Question Action list opened successfully");
			Thread.sleep(2000);
			List<WebElement> optionList = objPageModifyForms.optionsInListAction(sectionNo, questionNo);
			for (int i = 0; i < optionList.size(); i++) {
				if (optionList.get(i).getText().equalsIgnoreCase(applicableValidation)) {
					optionList.get(i).click();
					Thread.sleep(2000);
					break;
				}
			}
			Thread.sleep(3000);
			// For Question type : Paragraph, Short Text, Email,URl
			if (questionType.equalsIgnoreCase("Paragraph") || questionType.equalsIgnoreCase("Short Text")
					|| questionType.equalsIgnoreCase("Email") || questionType.equalsIgnoreCase("URL") || questionType.equalsIgnoreCase("Autocomplete"))
			{
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumLengthField()),"Minimum Length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumLengthField()),"Maximum Length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageLengthField()),"Error Message Length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getRegularExpressionField()),"Regular Expression field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageRegularExpressionField()),"Error Message for Regular Expression field is not displayed");
				actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumLengthField(), minLength);
				actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumLengthField(), maxLength);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageLengthField(), rangeErrorMessage);
				actions.clearAndSendKeysToElement(objPageModifyForms.getRegularExpressionField(), regularExpression);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageRegularExpressionField(), REErrorMessage);
				Thread.sleep(1500);
				actions.click(objPageModifyForms.getBtnOK());
			}
			// For Question Type : Date
			else if (questionType.equalsIgnoreCase("Date")) {
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getDateMinimumValue()),
						"Date minimum length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getDateMaximumValue()),
						"Date maximum length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),
						"Date Error message field is not displayed");
				actions.clearAndSendKeysToElement(objPageModifyForms.getDateMinimumValue(), dateMinValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getDateMaximumValue(), dateMaxValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
				actions.click(objPageModifyForms.getBtnOK());
			}
			// For Question Type : Time
			else if (questionType.equalsIgnoreCase("Time")) {
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTimeMinimumValue()),"Time minimum length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTimeMaximumValue()),"Time maximum length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Time Error message field is not displayed");
				actions.clearAndSendKeysToElement(objPageModifyForms.getTimeMinimumValue(), timeMinValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getTimeMaximumValue(), timeMaxValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
				actions.click(objPageModifyForms.getBtnOK());
			}
			// For Question Type : Scale
			else if (questionType.equalsIgnoreCase("Scale")) {
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");

				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumValueField()),"Minimum value field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumValueField()),"Maximum value field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Error Message Value field is not displayed");

				actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumValueField(), minValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumValueField(), maxValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
				Thread.sleep(1500);
				actions.click(objPageModifyForms.getBtnOK());
			}
			// For Question Type : Number
			else if(questionType.equalsIgnoreCase("Number"))
			{
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");

				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumValueField()),"Minimum value field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumValueField()),"Maximum value field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Error Message Value field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getRegularExpressionField()),"Regular Expression field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageRegularExpressionField()),"Error Message for Regular Expression field is not displayed");

				actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumValueField(), minValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumValueField(), maxValue);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
				actions.clearAndSendKeysToElement(objPageModifyForms.getRegularExpressionField(), regularExpression);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageRegularExpressionField(), REErrorMessage);
				Thread.sleep(1500);
				actions.click(objPageModifyForms.getBtnOK());
			}
			// For Question Type : File
			else if (questionType.equalsIgnoreCase("File")) {
				log.info("Question Type for Applicable Validation : " + questionType);
				actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
				softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
				softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not matched");

				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileSource()),
						"File Source Dropdown is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileType()),
						"File Type Dropdown is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileExtension()),
						"File Extension field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumLengthField()),
						"Minimum Length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumLengthField()),
						"Maximum Length field is not displayed");
				softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageLengthField()),
						"Error Message Length field is not displayed");

				Thread.sleep(3000);
				actions.selectDropDown(objPageModifyForms.getFileSource(), fileSource);
				actions.selectDropDown(objPageModifyForms.getFileType(), fileType);
				actions.clearAndSendKeysToElement(objPageModifyForms.getFileExtension(), fileExtension);
				actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumLengthField(), minLength);
				actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumLengthField(), maxLength);
				actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageLengthField(), rangeErrorMessage);
				actions.click(objPageModifyForms.getBtnOK());
			} else {
				log.error("Question type not matched with applicable validation");
				actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
				actions.scrollTo(driver, objPageModifyForms.questionAction(sectionNo, questionNo));
			}
			softAssert.assertAll();
			/*}
			catch (Exception | AssertionError e) {
				 actions.captureScreenShot(driver, screenshotUrl, methodName);
				 softAssert.assertAll();
			}*/
		}

		/**
		 * Function for move question from one section to another
		 * @param Integer fromSectionNo, Integer questionNo, String questionShortName,  Integer toSectionNo, String toSectionName
		 * @author Jaikishan Mohanty
		 * @since 14-Feb-2020
		 * @throws Exception
		 */
		
		public void moveToSection(Integer fromSectionNo, Integer questionNo, String questionShortName, Integer toSectionNo, String toSectionName) throws Exception
		{
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
			try {
			String moveToSectionList = "Move to section";
			message.getMessage();
			title.getTitle();
			
			try {
				Thread.sleep(2000);
				actions.click(objPageModifyForms.questionAction(fromSectionNo, questionNo));
			} catch (Exception e) {
				actions.scrollTo(driver, objPageModifyForms.questionAction(fromSectionNo, questionNo));
				Thread.sleep(1000);
				actions.scrollUp(driver);
				actions.click(objPageModifyForms.questionAction(fromSectionNo, questionNo));
			
			}
			
			log.info("Question Action list opened successfully");
			Thread.sleep(2000);
			List<WebElement> optionList = objPageModifyForms.optionsInListAction(fromSectionNo, questionNo);
			for (int i = 0; i < optionList.size(); i++) {
				if (optionList.get(i).getText().equalsIgnoreCase(moveToSectionList)) {
					optionList.get(i).click();
					break;
				}	
			}
			
			Thread.sleep(2000);
			actions.waitForVisibility(objPageModifyForms.getPopUpBox(),5);
			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Move to section Pop not displayed");
			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_moveToSection(),"Title of Move to Section not macthed");
			softAssert.assertTrue(actions.getText(objPageModifyForms.getMovesectionLabel()).contains("Move "+questionShortName+" to section:"),"Move to Section label not matched");
			
			Select moveToSection = new Select(objPageModifyForms.getMoveToSection());
			moveToSection.selectByVisibleText(toSectionName);
			
			actions.click(objPageModifyForms.getBtnOK());	
			Thread.sleep(2000);
			Assert.assertTrue(actions.getText(objPageModifyForms.getSuccessAlert()).contains(message.getMsg_moveToSection()));
			log.info("Question has been moved successfully.");
			Thread.sleep(2000);
		
			List<WebElement> listOfQuestionShortName = objPageModifyForms.listOfShortName(toSectionNo);
			String searchedShortName = null;
			for(int noOfQuestion=0 ; noOfQuestion<listOfQuestionShortName.size() ; noOfQuestion++)
			{
				if(listOfQuestionShortName.get(noOfQuestion).getAttribute("value").equals(questionShortName))
				{
					searchedShortName = actions.getValue(listOfQuestionShortName.get(noOfQuestion));
				}
				
			}
			softAssert.assertTrue(searchedShortName.contains(questionShortName), "Question does not find in moved section");
			softAssert.assertAll();
			}
			catch (Exception | AssertionError e) {
				 actions.captureScreenShot(driver, screenshotUrl, methodName);
				 softAssert.assertAll();
			}
		}
		
		public WebElement ListActionIcon(String sectionNo, String questionNo)
		{
			return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='que-action']"));
		}
		
		
		
		/**
		 * @author priyankag
		 * Get Question List Action
		 * @throws Exception
		 */
		
		public void getQuestionListAction(String questionType,String sectionNo, String questionNo) throws Exception {

			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
			//try {
			Object[][] questionListActionHeadingData=googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, questionListActionHeadingDataSheetName);
			Object[][] arrQuestionListActionHeadingData =actions.getFilterDataFromExcelSheet(questionListActionHeadingData, "QuestionList_Action");
			actions.clickOnElement(ListActionIcon(sectionNo, questionNo));
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
				actions.clickOnElement(ListActionIcon(sectionNo, questionNo));
			}
			softAssert.assertAll();
		/*	}
			catch (Exception | AssertionError e) {
				 actions.captureScreenShot(driver, screenshotUrl, methodName);
				 softAssert.assertAll();
			}*/
		}
		
	
		Object[][] choicesData;
		Object[][] arrChoicesData;
		String choicesDataSheetName = "Choices_Data!B2:B";
		
		/**
		 * @author jaikishanm
		 * Verify source type with Choices_data sheet.
		 * @throws Exception
		 */
		
		public void verifySourceType(Integer sectionNo, Integer questionNo) throws Exception
		{
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			softAssert = new SoftAssert();
		//	try {
			choicesData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, choicesDataSheetName);
			
			 List<Object> choicesDataList = new ArrayList<Object>();
			    for (Object[] arrChoicesData : choicesData) {
			    	choicesDataList.addAll(Arrays.asList(arrChoicesData));
			    }
			    
			  Select selectSourceType = new Select(objPageModifyForms.sourceType(sectionNo, questionNo));
			  actions.click(objPageModifyForms.sourceType(sectionNo, questionNo));
			  List<WebElement> listOfSourceType = selectSourceType.getOptions();
			  
			  softAssert.assertEquals(listOfSourceType.size(),choicesDataList.size(), "Size ofSource Type list is not matched.");
			  
			  for(int choicesNo=0 ; choicesNo<choicesDataList.size() ; choicesNo++)
			  {
				softAssert.assertEquals(listOfSourceType.get(choicesNo).getText(), choicesDataList.get(choicesNo),"Choice "+listOfSourceType.get(choicesNo).getText()+" not matched with "+choicesDataList.get(choicesNo));   
			  }
			  
			  softAssert.assertAll();		  
			  actions.click(objPageModifyForms.sourceType(sectionNo, questionNo));	    
		/*}
		catch (Exception | AssertionError e) {
			 actions.captureScreenShot(driver, screenshotUrl, methodName);
			 softAssert.assertAll();
		}*/
		}
		
}
