package dkosh.tc;

import browsers.BaseTest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
 * @author Jaikishan Mohanty
 * @since 30-Jan-2020
 */
public class TCQuestionPanel extends BaseTest {

	Logger log = LoggerHelper.getLogger(TCQuestionPanel.class);
	LoginPage login;
	NavigationBar navigationBar;
//	LoginAction login;
	LoginAction loginAction;
	WebDriverWait wait;
	PageManageRespondent objPageManageRespondent;
	PageManageResponses objPageManageResponses;
	PageAddResponses objPageAddResponses;
	PageModifyForm objPageModifyForms;
	PageFormSettings objPageManageFormSetting;
	PageManageForms objPageManageForms;
	DkoshCommanFunction objDkoshCommanFunction;
	SoftAssert softAssert;
	WebDriverWait explicitWait;
	BrowserActions actions;
	Actions action;
	GoogleSheetAPIUtils googleSheet;
	Object[][] createQuestionData;
	Object[][] arrCreateQuestionData;
	Object[][] createQuestionChoicesData;
	Object[][] arrCreateQuestionChoicesData;
	String dKOSHDataSpreadsheetId = "1Q4_V7oh3wSdl4HFnlk1UwHA-O4k3HE6KePS89EtlmW0";
	String createQuestionSheetName = "Question_Data!A2:AC";
	String createQuestionChoicesSheetName = "Create_Question_Choices!B2:Z";
	String objPageModifyFormsOption = "Modify Form";
	String statusNameNew = "New(Draft)";
	DkoshTitleData title = new DkoshTitleData();
	
	//Data From Sheet
	String form_idField;
	String sectionNumber;
	String txtSectionName;
	String txtSectionDescription;
	String question_idField;
	String selectQuestionType;
	String txtQuestionName;
	String txtDescription;
	String txtShortName;
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
	String applicableValidation_date_minValue;
	String applicableValidation_date_maxValue;
	String applicableValidation_time_minValue;
	String applicableValidation_time_maxValue;
	String applicableValidation_fileSource;
	String applicableValidation_fileType;
	String applicableValidation_fileExtension;
	String responseData;
	
	int sectionNo = 1;
	int questionNos =1;
	int firstSectionNo =1;
	
	//Choices Data
	String questionid_field;
	String questionType_field;
	String choiceType;
	String choice1;
	String choice2;
	
	
	// Create QUestion
	String expectedCreateQuestionMessage = "Add successfully.";

	//Create Section
	String expectedCreateSectionMessage = "Section has been created successfully.";
	String modifySection = " Modify Section";
	
	// Modify Question
	String txtDefaultType = "Select Prefill Value";

	// Prefill Value
	String updatePrefillValue = "Update Prefill Value";

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
		loginAction.login(username, password, mobilenumber);
		title.getTitle();
		message.getMessage();
	}



	//@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_008(String CF_Id) throws Exception
	{

		driver.navigate().to("http://10.1.10.110:89/Saarthi/Dkosh/index.php/question/modify-form?id=73");
		Thread.sleep(2000);
		softAssert = new SoftAssert();
        createQuestionData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, createQuestionSheetName);
        arrCreateQuestionData =actions.getFilterDataFromExcelSheet(createQuestionData, CF_Id);
        
        for(Object[] objCreateQuestionData : arrCreateQuestionData) {
          		
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
  			objDkoshCommanFunction.getQuestionListAction(selectQuestionType, sectionNumber, Integer.toString(questionNos));
              Thread.sleep(3000);
           	questionNos++; 
  			Thread.sleep(2000);
  			
        }     
         Reporter.log("TC_dK_008 : This Test Case add section, verify default question and add question in form."); 
	}
	
	/**
	 *  Verify modify question fields value according to input.
	 * @throws Exception
	 */

//	@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_029(String CF_Id) throws Exception
	{
		Thread.sleep(2000);
		softAssert = new SoftAssert();
        createQuestionData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, createQuestionSheetName);
        arrCreateQuestionData =actions.getFilterDataFromExcelSheet(createQuestionData, CF_Id);
        questionNos=1;
        firstSectionNo=1;
        sectionNo=1;
        
        for(Object[] objCreateQuestionData : arrCreateQuestionData) {
           
        	
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
              if(Integer.parseInt(sectionNumber)==firstSectionNo) 
    			{
            	  firstSectionNo--;
    				sectionNo++;
    			}
              if(Integer.parseInt(sectionNumber)==sectionNo)
              {
            	  questionNos=1;
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
        Reporter.log("TC_dK_008 : This Test Case modify questions.");
	}
	

//	@Test
	@Parameters({"CF_Id"})
	public void TC_dK_009(String CF_Id) throws Exception
	{	
		
	 for(Object[] objCreateQuestionData : arrCreateQuestionData) {
		
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
		
	}
	
	
	Object[][] moveToSectionData;
	Object[][] arrmoveToSectionData;
	
	String moveToSectionSheetName = "MoveToSection!A2:H";
	String fromSectionNumber;
	String fromSectionName;
	String questionNumber;
	String questionShortName;
	String toSectionNumber;
	String toSectionName;
	DkoshMessageData message = new DkoshMessageData();
	
	//@Test
	@Parameters({ "CF_Id"})
	public void TC_dK_018(String CF_Id) throws Exception
	{
		softAssert = new SoftAssert();
		driver.navigate().to("http://10.1.10.110:89/Saarthi/Dkosh/index.php/question/modify-form?id=52");
		Thread.sleep(2000);
		message = new DkoshMessageData();
		
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
      
        	moveToSection(Integer.parseInt(fromSectionNumber), Integer.parseInt(questionNumber), questionShortName, Integer.parseInt(toSectionNumber), toSectionName);
        	 
         }
         
        
	}
	
	Object[][] choicesData;
	Object[][] arrChoicesData;
	String choicesDataSheetName = "Choices_Data!B2:B";
	
	public void verifySourceType(Integer sectionNo, Integer questionNo) throws Exception
	{
		softAssert = new SoftAssert();
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
	}
	
	
//	/**
//	 * Function to Add Another Section
//	 * 
//	 * @param String sectionName, String sectionDescription
//	 * @author Jaikishan Mohanty
//	 * @since 1-Feb-2020
//	 * @throws InterruptedException
//	 */
//
//	public void addSectionAndVerify(String sectionName, String sectionDescription) throws InterruptedException {
//		actions.click(objPageModifyForms.addSection());
//		Thread.sleep(2000);
//		softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Add Section Pop not displayed");
//		softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_addSection(),"Title not macthed");
//
//		softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionName()),"Section Name field is not displayed");
//		softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSectionDescription()),"Section Desccription field is not displayed");
//
//		actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionName(), sectionName);
//		actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSectionDescription(), sectionDescription);
//		actions.click(objPageModifyForms.getBtnOK());
//		WebElement createSectionMessage = explicitWait.until(ExpectedConditions.visibilityOf(objPageModifyForms.getCreateSectionconfirmationMessage()));
//		softAssert.assertTrue(createSectionMessage.getText().contains(expectedCreateSectionMessage),"Create Section message not shown");
//		softAssert.assertAll();
//		log.info("Section has been created successfully");
//
//	}
//
//	/**
//	 * Function for verify Applicable Validation & Verify validation with all
//	 * question type.
//	 * @param Integer sectionNo, Integer questionNo, String questionType, String minimumNumber, String
//	 *                maximumNumber, String errorMessage, String dateMinValue,
//	 *                String dateMaxValue, String timeMinValue, String timeMaxValue,
//	 *                String fileSource, String fileType, String fileExtension
//	 * @author Jaikishan Mohanty
//	 * @since 30-Jan-2020
//	 * @throws InterruptedException
//	 */
//
//	public void addApplicableValidation(Integer sectionNo, Integer questionNo, String questionType, String minLength, String maxLength, String minValue, String maxValue, String rangeErrorMessage, String regularExpression, String REErrorMessage, String dateMinValue, String dateMaxValue, String timeMinValue,String timeMaxValue, String fileSource, String fileType, String fileExtension) throws InterruptedException
//	{
//
//		String applicableValidation = "Applicable Validation";
//		try {
//			actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
//		} catch (Exception e) {
//			actions.scrollTo(driver, objPageModifyForms.questionAction(sectionNo, questionNo));
//			actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
//		}
//
//		log.info("Question Action list opened successfully");
//		Thread.sleep(2000);
//		List<WebElement> optionList = objPageModifyForms.optionsInListAction(sectionNo, questionNo);
//		for (int i = 0; i < optionList.size(); i++) {
//			if (optionList.get(i).getText().equalsIgnoreCase(applicableValidation)) {
//				optionList.get(i).click();
//				break;
//			}
//			
//		}
//		Thread.sleep(2000);
//
//		softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Validation Pop not displayed");
//		softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), applicableValidation,"Title not macthed");
//
//		// For Question type : Paragraph, Short Text, Email,URl
//		if (questionType.equalsIgnoreCase("Paragraph") || questionType.equalsIgnoreCase("Short Text")
//				|| questionType.equalsIgnoreCase("Email") || questionType.equalsIgnoreCase("URL"))
//		{
//			log.info("Question Type : " + questionType);
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumLengthField()),"Minimum Length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumLengthField()),"Maximum Length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageLengthField()),"Error Message Length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getRegularExpressionField()),"Regular Expression field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageRegularExpressionField()),"Error Message for Regular Expression field is not displayed");
//
//	//		softAssert.assertAll();
//
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumLengthField(), minLength);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumLengthField(), maxLength);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageLengthField(), rangeErrorMessage);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getRegularExpressionField(), regularExpression);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageRegularExpressionField(), REErrorMessage);
//			Thread.sleep(1500);
//			actions.click(objPageModifyForms.getBtnOK());
//		}
//		// For Question Type : Date
//		else if (questionType.equalsIgnoreCase("Date")) {
//			log.info("Date question");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getDateMinimumValue()),
//					"Date minimum length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getDateMaximumValue()),
//					"Date maximum length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),
//					"Date Error message field is not displayed");
//
////			softAssert.assertAll();
//
//			actions.clearAndSendKeysToElement(objPageModifyForms.getDateMinimumValue(), dateMinValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getDateMaximumValue(), dateMaxValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
//			actions.click(objPageModifyForms.getBtnOK());
//		}
//		// For Question Type : Time
//		else if (questionType.equalsIgnoreCase("Time")) {
//			log.info("Time question");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTimeMinimumValue()),"Time minimum length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTimeMaximumValue()),"Time maximum length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Time Error message field is not displayed");
//
////			softAssert.assertAll();
//
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTimeMinimumValue(), timeMinValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTimeMaximumValue(), timeMaxValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
//			actions.click(objPageModifyForms.getBtnOK());
//		}
//		// For Question Type : Scale
//		else if (questionType.equalsIgnoreCase("Scale")) {
//			log.info("Scale question");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumValueField()),"Minimum value field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumValueField()),"Maximum value field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Error Message Value field is not displayed");
//
////			softAssert.assertAll();
//
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumValueField(), minValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumValueField(), maxValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
//			Thread.sleep(1500);
//			actions.click(objPageModifyForms.getBtnOK());
//		}
//		// For Question Type : Number
//		else if(questionType.equalsIgnoreCase("Number"))
//		{
//			log.info("Number question");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumValueField()),"Minimum value field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumValueField()),"Maximum value field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageForRange()),"Error Message Value field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getRegularExpressionField()),"Regular Expression field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageRegularExpressionField()),"Error Message for Regular Expression field is not displayed");
//			
////			softAssert.assertAll();
//			
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumValueField(), minValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumValueField(), maxValue);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageForRange(), rangeErrorMessage);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getRegularExpressionField(), regularExpression);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageRegularExpressionField(), REErrorMessage);
//			Thread.sleep(1500);
//			actions.click(objPageModifyForms.getBtnOK());
//			
//			
//		}
//		// For Question Type : File
//		else if (questionType.equalsIgnoreCase("File")) {
//			log.info("Question Type : " + questionType);
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileSource()),
//					"File Source Dropdown is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileType()),
//					"File Type Dropdown is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getFileExtension()),
//					"File Extension field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMinimumLengthField()),
//					"Minimum Length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getMaximumLengthField()),
//					"Maximum Length field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getErrorMessageLengthField()),
//					"Error Message Length field is not displayed");
//
//			softAssert.assertAll();
//			Thread.sleep(3000);
//			actions.selectDropDown(objPageModifyForms.getFileSource(), fileSource);
//			actions.selectDropDown(objPageModifyForms.getFileType(), fileType);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getFileExtension(), fileExtension);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMinimumLengthField(), minLength);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getMaximumLengthField(), maxLength);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getErrorMessageLengthField(), rangeErrorMessage);
//			actions.click(objPageModifyForms.getBtnOK());
//		} else {
//			log.error("Question type not matched with applicable validation");
//			actions.click(objPageModifyForms.questionAction(sectionNo, questionNo));
//			actions.scrollTo(driver, objPageModifyForms.questionAction(sectionNo, questionNo));
//		}
//
//	}
//
//
//	/**
//	 * Create Question From Question Panel & Verify Default Value of fields of Question
//	 * @param Integer sectionNo, String questionName, int numberOfQuestion
//	 * @author Jaikishan Mohanty
//	 * @since 30-Jan-2020
//	 * @throws InterruptedException
//	 */
//	public void createQuestionAndVerifyPanel(Integer sectionNo, String questionName, int questionNo) throws InterruptedException {
//		softAssert = new SoftAssert();
//		// Create Question with Question Name
//		objPageModifyForms.createQuestion(questionName).click();
//		log.info("Question has been created successfully");
//		  try {
//              softAssert.assertTrue(actions.getText(objPageModifyForms.getSuccessAlert()).contains(expectedCreateQuestionMessage),"Please Check Confirmation Meassage After Add Question.");                
//      }
//      catch (Exception e) {                        
//    	  log.error("Add message not showing.");
//      }
//		  log.info("Create Question message has been verified successfully");
//
//		Thread.sleep(3000);
//
//		boolean required = objPageModifyForms.requiredToggle(sectionNo,questionNo).getAttribute("class").contains("switch-on");
//		boolean enabled = objPageModifyForms.enabledToggle(sectionNo,questionNo).getAttribute("class").contains("switch-on");
//		boolean hidden = objPageModifyForms.hiddenToggle(sectionNo,questionNo).getAttribute("class").contains("switch-off");
//		Select selectedQuestionType = new Select(objPageModifyForms.txtQuestionType(sectionNo,questionNo));
//		softAssert.assertEquals(selectedQuestionType.getFirstSelectedOption().getText(), questionName,"Question Type not matched");
//		
//		
//		softAssert.assertTrue(objPageModifyForms.txtQuestionName(sectionNo, questionNo).getAttribute("value").contains("Question_"), "Question name not matched");		
//		softAssert.assertTrue(objPageModifyForms.txtInstruction(sectionNo, questionNo).getAttribute("value").contains(""), "Description not matched");
//		softAssert.assertTrue(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo).getAttribute("value").contains("short_name_"), "Question Short Name not matched");
//		softAssert.assertEquals(objPageModifyForms.txtPrefillType(sectionNo, questionNo).getAttribute("value"),txtDefaultType, "Default Prefill value not matched");
//		softAssert.assertEquals(required, true, "Required Default Value not matched");
//		softAssert.assertEquals(enabled, true, "Enabled Default Value not matched");
//		softAssert.assertEquals(hidden, true, "Hidden Default Value not matched");
//			
////		softAssert.assertAll();
//		log.info("Create Question and its panel verified successfully");
//	}
//
//	
//	/**
//	 * Modify Question field according to test data.
//	 * @param Integer sectionNo, Integer numberOfQuestion, String questionType, String
//	 *                questionName,String description, String shortName,String
//	 *                prefillType,String required,String enabled, String hidden
//	 * @author Jaikishan Mohanty
//	 * @since 30-Jan-2020
//	 * @throws InterruptedException
//	 */
//	public void ModifyQuestionFields(Integer sectionNo, Integer questionNo, String questionType, String questionName,
//			String description, String shortName, String prefillType, String prefillValue, String required, String enabled, String hidden)
//			throws InterruptedException {
//		
//		softAssert = new SoftAssert();
//		// Select Question Type
//		Select QuestionTypeSelect = new Select(objPageModifyForms.txtQuestionType(sectionNo, questionNo));
//		QuestionTypeSelect.selectByVisibleText(questionType);
//
//		// Enter Question Name
//		actions.clearAndSendKeysToElement(objPageModifyForms.txtQuestionName(sectionNo, questionNo), questionName);
//		
//		// Enter Description of Question
//		actions.clearAndSendKeysToElement(objPageModifyForms.txtInstruction(sectionNo, questionNo), description);
//		
//		// Enter ShortName
//		actions.sendAnyKeyToElement(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo),Keys.chord(Keys.CONTROL, "a"));
//		actions.sendAnyKeyToElement(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo), shortName);
//		
//		// Select Prefill type
//		Select selectPrefillType = new Select(objPageModifyForms.txtPrefillType(sectionNo, questionNo));
//
//		Thread.sleep(3000);
//		if (prefillType.equalsIgnoreCase("Fix value")) {
//			// Select prefill type as fix value and fill accordingly.
//			
//			selectPrefillType.selectByVisibleText(prefillType);
//			log.info("Fix value is selected");
//			Thread.sleep(3000);
//			action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();	
//		//	actions.clickOnElement(objPageModifyForms.prefillValueField(sectionNo, questionNo));
//			actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
//			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Prefill Value Pop not displayed");
//			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_updatePrefillValue(),"Title not macthed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtprefillValue()),"Prefill text field is not displayed");
//			Thread.sleep(2000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtprefillValue(), prefillValue);
//			actions.click(objPageModifyForms.getBtnOK());
//			log.info("Prefill Fix Value has been Saved successfully");
//
//		} else if (prefillType.equalsIgnoreCase("Query")) {
//			// Select prefill type as Query and fill accordingly.
//			selectPrefillType.selectByVisibleText(prefillType);
//			log.info("Query is selected");
//			Thread.sleep(3000);
//			action.moveToElement(objPageModifyForms.prefillValueField(sectionNo, questionNo)).perform();
//	//		actions.clickOnElement(objPageModifyForms.prefillValueField(sectionNo, questionNo));
//			actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
//			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Query Pop not displayed");
//			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_updatePrefillValue(),"Title not macthed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtMySql()),"My Sql field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSqlLite()),"SQL Lite field is not displayed");
//			
//			Thread.sleep(2000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtMySql(), prefillValue);
//			Thread.sleep(1000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSqlLite(), prefillValue);
//			actions.click(objPageModifyForms.getBtnOK());
//			log.info("Prefill Query has been Saved successfully");
//
//		}else if(prefillType.equalsIgnoreCase("Current value"))
//		{
//			selectPrefillType.selectByVisibleText(prefillType);
//			log.info("Current Value is selected");
//			Thread.sleep(3000);	
//			softAssert.assertEquals(actions.getValue(objPageModifyForms.prefillValueField(sectionNo, questionNo)), "now", "Prefill Value field for current value not matched");
//		}
//		else {
//			log.debug("Prefill Type is not matched");
//		}
//		
//		// Select Required Toggle
//		if (!objPageModifyForms.requiredToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + required)) {
//			actions.clickOnElement(objPageModifyForms.requiredToggle(sectionNo, questionNo));
//		}
//		// Select Enabled Toggle
//		if (!objPageModifyForms.enabledToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + enabled)) {
//			actions.clickOnElement(objPageModifyForms.enabledToggle(sectionNo, questionNo));
//		}
//		// Select Hidden Toggle
//		if (!objPageModifyForms.hiddenToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + hidden)) {
//			actions.clickOnElement(objPageModifyForms.hiddenToggle(sectionNo, questionNo));
//		}
//		/* Verify the entered value is correct or not */
//		Thread.sleep(3000);
//	//	softAssert.assertEquals(QuestionTypeSelect.getFirstSelectedOption().getText(), questionType,"Question Type not matched");
//		softAssert.assertEquals(objPageModifyForms.txtQuestionName(sectionNo, questionNo).getAttribute("value"),questionName, "Question name not matched");
//		softAssert.assertEquals(objPageModifyForms.txtInstruction(sectionNo, questionNo).getAttribute("value"), description,"Description not matched");	
//		softAssert.assertEquals(objPageModifyForms.txtQuestionShortName(sectionNo, questionNo).getAttribute("value"),shortName, "Question Short Name not matched");
////		softAssert.assertEquals(selectPrefillType.getFirstSelectedOption().getText(), prefillType,"Default Prefill value not matched");
//		softAssert.assertEquals(objPageModifyForms.requiredToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + required), true, "Required Default Value not matched");
//		softAssert.assertEquals(objPageModifyForms.enabledToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + enabled),true, "Enabled Default Value not matched");
//		softAssert.assertEquals(objPageModifyForms.hiddenToggle(sectionNo, questionNo).getAttribute("class").contains("switch-" + hidden),true, "Hidden Default Value not matched");
//		softAssert.assertAll();
//		log.info("Question has been modified successfully");
//	}
//
//	
//	/**
//	 *Add Source Type and Verify
//	 * 
//	 * @param Integer sectionNo, Integer questionNo, String questionType, String sourceType
//	 * @author Jaikishan Mohanty
//	 * @since 03-Feb-2020
//	 * @throws InterruptedException
//	 */
//	public void addSourceTypeAndVerify(Integer sectionNo, Integer questionNo, String questionType, String sourceType, String choice1, String choice2) throws InterruptedException
//	{
//
//		int rowNo =0;
//		Select selectQuestionType = new Select(objPageModifyForms.txtQuestionType(sectionNo, questionNo));
//		selectQuestionType.selectByVisibleText(questionType);
//		Thread.sleep(3000);
//		Select selectSourceType = new Select(objPageModifyForms.sourceType(sectionNo, questionNo));
//
//		Thread.sleep(2000);
//		if (sourceType.equalsIgnoreCase("User Defined List")) {
//			// Select source type as User Defined List and fill accordingly.
//			
//			selectSourceType.selectByVisibleText(sourceType);
//			log.info("User Defined List is selected");
//			Thread.sleep(2000);
//			action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
//			actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
//			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "User Defined List Pop not displayed");
//			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_userDefinedList(),"User Defined List Title not macthed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.userDefined_choicesSNo(rowNo)),"S.No field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.userDefined_choicesText(rowNo)),"Text choices field is not displayed");
//			Thread.sleep(2000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesSNo(rowNo), choice1);
//			actions.clearAndSendKeysToElement(objPageModifyForms.userDefined_choicesText(rowNo), choice2);
//			actions.click(objPageModifyForms.getBtnOK());
//			log.info("Choices has been saved successfully");
//		
//		}
//		
//		else if(sourceType.equalsIgnoreCase("Query"))
//		{
//			// Select Source type as Query and fill accordingly.
//			selectSourceType.selectByVisibleText(sourceType);
//			log.info("Query is selected");
//			Thread.sleep(2000);
//			action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
//			actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
//			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Manage Query Pop not displayed");
//			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_manageQuery(),"User Defined List Title not macthed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtMySql()),"MySql field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getTxtSqlLite()),"SQL Lite field is not displayed");
//			Thread.sleep(2000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtMySql(), choice1);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getTxtSqlLite(), choice2);
//			actions.click(objPageModifyForms.getBtnOK());
//			log.info("Query has been saved successfully");
//		}
//		else if(sourceType.equalsIgnoreCase("Year"))
//		{
//			// Select Source type as Year and fill accordingly.
//			selectSourceType.selectByVisibleText(sourceType);
//			log.info("Year is selected");
//			Thread.sleep(2000);
//			action.moveToElement(objPageModifyForms.addChoicesField(sectionNo, questionNo)).perform();
//			actions.click(objPageModifyForms.btnUpdatePencil(sectionNo, questionNo));
//			Thread.sleep(2000);
//			softAssert.assertTrue(objPageModifyForms.getPopUpBox().isDisplayed(), "Manage Query Pop not displayed");
//			softAssert.assertEquals(actions.getText(objPageModifyForms.getTitleOfPopUp()), title.getTitle_manageQuery(),"User Defined List Title not macthed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getYear_YearFromField()),"Year From field is not displayed");
//			softAssert.assertTrue(actions.isElementDisplayed(objPageModifyForms.getYear_YearToField()),"Year To field is not displayed");
//			Thread.sleep(2000);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getYear_YearFromField(), choice1);
//			actions.clearAndSendKeysToElement(objPageModifyForms.getYear_YearToField(), choice2);
//			actions.click(objPageModifyForms.getBtnOK());
//			log.info("Year Saved successfully.");
//				
//		}
//		else
//		{
//			selectSourceType.selectByVisibleText(sourceType);
//			log.info(sourceType+"Selected successfully");
//		}
//		log.error("Soruce type is wrong. Please enter correct source type");
//		
//	}
//	
	
	public void moveToSection(Integer fromSectionNo, Integer questionNo, String questionShortName, Integer toSectionNo, String toSectionName) throws Exception
	{

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
	
	
}
