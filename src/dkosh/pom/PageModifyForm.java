package dkosh.pom;

import java.util.List;

import org.apache.log4j.Logger;
import org.mortbay.servlet.WelcomeFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import authentication.NavigationBar;
import browsers.BrowserActions;
import dkosh.tc.TCForm;
import helpers.LoggerHelper;
import utilities.GoogleSheetAPIUtils;

/**
 * @author Jaikishan Mohanty
 *
 */
public class PageModifyForm {

	WebDriver driver;
	WebDriverWait wait;
	BrowserActions actions;
	NavigationBar navigationBar;
	SoftAssert softAssert;
	Actions action;
	
	public PageModifyForm(WebDriver driver) {
		this.driver = driver;
		actions = new BrowserActions(driver);
		action = new Actions(driver);
		navigationBar = new NavigationBar(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}
	Logger log = LoggerHelper.getLogger(PageModifyForm.class);

	@FindBy(xpath = "//div[@class='page-header']/h2")
	private WebElement createPageHeader;

	@FindBy(xpath = "//div[@class='dropdown pt-5']/a")
	private WebElement settingLink;


	@FindBy(xpath = "//div[@id='w10-success']")
	WebElement createQuestionconfirmationMessage;

	@FindBy(xpath = "//div[@id='w11-success']")
	WebElement createSectionconfirmationMessage;

	@FindBy(xpath = "//div[@class = 'alert-success alert fade in']")
	private WebElement successAlert;

	/**
	 * @return the successAlert
	 */
	public WebElement getSuccessAlert() {
		return successAlert;
	}

	/**
	 * @return the createQuestionconfirmationMessage
	 */
	public WebElement getCreateQuestionconfirmationMessage() {
		return createQuestionconfirmationMessage;
	}

	/**
	 * @return the createSectionconfirmationMessage
	 */
	public WebElement getCreateSectionconfirmationMessage() {
		return createSectionconfirmationMessage;
	}

	By optionsListInSection = By.xpath("//ul[contains(@class,'dropdown-menu dropdown-menu-right')]/li/a");



	/**
	 * @return the createPageHeader
	 */
	public WebElement getCreatePageHeader() {
		return createPageHeader;
	}

	/**
	 * @return the settingLink
	 */
	public WebElement getSettingLink() {
		return settingLink;
	}

	/**
	 * @return the optionsListInSection
	 */
	public List<WebElement> getOptionsListInSection() {
		return driver.findElements(optionsListInSection);
	}


	public WebElement selectQuestion(String questionType)
	{
		WebElement selectQuestion = (driver.findElement(By.xpath("//div[@id='myHeader']/div[2]/div/a/p[contains(.,'"+questionType+"')]")));
		return selectQuestion;
	}

	public WebElement addSection()
	{
		WebElement selectQuestion = (driver.findElement(By.xpath("//div[@id='myHeader']/div[1]/div/a[contains(.,' Add Another Section')]")));
		return selectQuestion;
	}



	//Create Question function with Question Name as a parameter i.e. Paragraph

	@FindBy(xpath = "//div[@class='col-lg-6 col-md-12 col-sm-3 col-xs-6']//p[contains(text(),'Paragraph')]")
	WebElement quesParagraph;

	@FindBy(xpath = "//div[@class='box box-primary']/div[2]/div")
	List<WebElement>  totalQuestion;

	public List<WebElement> getTotalQuestion() {
		return totalQuestion;
	}


	public WebElement createQuestion(String questionName)
	{
		WebElement question = driver.findElement(By.xpath("//a[@class='create-question btn btn-outline-primary btn-block question_block question-type']//p[contains(text(),'"+questionName+"')]"));
		return question;
	}
	public WebElement txtQuestionName(Integer sectionNo,Integer questionNo)
	{
		WebElement txtquestionName = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//input[contains(@id,'txt_text')]"));
		return txtquestionName;
	}

	public WebElement txtInstruction(Integer sectionNo,Integer questionNo)
	{
		WebElement txtdescription = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//input[contains(@id,'txt_instruction')]"));
		return txtdescription;
	}
	public WebElement txtQuestionShortName(Integer sectionNo, Integer questionNo)
	{
		WebElement txtshortName = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//input[contains(@id,'txt_question_short_name')]"));
		return txtshortName;
	}
	
	public List<WebElement> listOfShortName(Integer sectionNo)
	{
		List<WebElement>  listOfshortName = driver.findElements(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]//div[@class='col-sm-9 first-col']//input[contains(@id,'txt_question_short_name')]"));
		return listOfshortName;
	}
	
	public WebElement txtPrefillType(Integer sectionNo, Integer questionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//select[contains(@id,'txt_default_value')]"));
	}

	public WebElement txtDefaultPrefillType(Integer sectionNo, Integer questionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//select[contains(@id,'txt_default_value')]/option[1]"));
	}
	public WebElement txtQuestionType(Integer sectionNo, Integer questionNo)
	{    	
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-3 second-col']//select[contains(@id,'int_question_type')]"));
	}

	//Toggle parameter accept on and off

	public WebElement questionAction(Integer sectionNo, Integer questionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='row']//div[contains(@class,'dropdown pull-right')]/a"));
	}

	public List<WebElement> optionsInListAction(Integer sectionNo, Integer questionNo)
	{
		return driver.findElements(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='que-action']/div/ul/li/a"));
	}


	public WebElement requiredToggle(Integer sectionNo, Integer questionNo)
	{
		WebElement ysn_required = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-3 second-col']//div[contains(@class,'switch-id-ysn_required')] "));
		return ysn_required;
	}

	public WebElement enabledToggle(Integer sectionNo, Integer questionNo)
	{
		WebElement ysn_enabled = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-3 second-col']//div[contains(@class,'switch-id-ysn_enabled')] "));
		return ysn_enabled;
	}

	public WebElement hiddenToggle(Integer sectionNo, Integer questionNo)
	{
		WebElement ysn_hidden = driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-3 second-col']//div[contains(@class,'switch-id-ysn_hidden')] "));
		return ysn_hidden;
	}

	//Validation Pop up
	@FindBy(xpath = "//div[@id='modal']//div[@class='modal-content']")
	WebElement popUpBox;

	@FindBy(xpath = "//div[@id='modal']//div[@class='modal-header']/strong")
	WebElement titleOfPopUp;

	@FindBy(xpath = "//input[@id='sfquestion-int_min_length']")
	WebElement minimumLengthField;

	@FindBy(xpath = "//input[@id='sfquestion-int_max_length']")
	WebElement maximumLengthField;

	@FindBy(xpath = "//input[@id='sfquestion-txt_error_length_msg']")
	WebElement errorMessageLengthField;

	@FindBy(xpath = "//input[@id='sfquestion-txt_regular_expression']")
	WebElement regularExpressionField;

	@FindBy(xpath = "//input[@id='sfquestion-txt_error_regex_msg']")
	WebElement errorMessageRegularExpressionField;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnOK;

	@FindBy(xpath = "//div[@id='modal']//a[@class='close']")
	WebElement btnClose;

	@FindBy(xpath = "//span[@class='que-short-name']")
	WebElement shortNameApplicableValidation;

	@FindBy(xpath = "//span[@class='que-type']")
	WebElement questionTypeApplicableValidation;

	@FindBy(xpath = "//input[@id='sfquestion-dat_min_value']")
	WebElement dateMinimumValue;

	@FindBy(xpath = "//input[@id='sfquestion-dat_max_value']")
	WebElement dateMaximumValue;

	@FindBy(xpath = "//input[@id='sfquestion-txt_error_range_msg']")
	WebElement errorMessageForRange;

	@FindBy(xpath = "//input[@id='sfquestion-time_min_value']")
	WebElement timeMinimumValue;

	@FindBy(xpath = "//input[@id='sfquestion-time_max_value']")
	WebElement timeMaximumValue;

	@FindBy(xpath = "//input[@id='sfquestion-int_min_value']")
	WebElement minimumValueField;

	@FindBy(xpath = "//input[@id='sfquestion-int_max_value']")
	WebElement maximumValueField;

	@FindBy(xpath = "//*[@id='sfquestion-int_file_source_type']")
	WebElement fileSource;

	@FindBy(xpath = "//*[@id='sfquestion-int_file_type']")
	WebElement fileType;

	@FindBy(xpath = "//*[@id='sfquestion-txt_file_extensions']")
	WebElement fileExtension;


	/* -------------------------------------------------------------*/


	public WebElement getPopUpBox() {
		return popUpBox;
	}

	public WebElement getTitleOfPopUp() {
		return titleOfPopUp;
	}

	public WebElement getMinimumLengthField() {
		return minimumLengthField;
	}

	public WebElement getMaximumLengthField() {
		return maximumLengthField;
	}

	public WebElement getErrorMessageLengthField() {
		return errorMessageLengthField;
	}

	public WebElement getRegularExpressionField() {
		return regularExpressionField;
	}

	public WebElement getErrorMessageRegularExpressionField() {
		return errorMessageRegularExpressionField;
	}

	public WebElement getBtnOK() {
		return btnOK;
	}

	public WebElement getBtnClose() {
		return btnClose;
	}

	public WebElement getShortNameApplicableValidation() {
		return shortNameApplicableValidation;
	}

	public WebElement getQuestionTypeApplicableValidation() {
		return questionTypeApplicableValidation;
	}

	public WebElement getDateMinimumValue() {
		return dateMinimumValue;
	}

	public WebElement getDateMaximumValue() {
		return dateMaximumValue;
	}

	public WebElement getErrorMessageForRange() {
		return errorMessageForRange;
	}

	public WebElement getTimeMinimumValue() {
		return timeMinimumValue;
	}

	public WebElement getTimeMaximumValue() {
		return timeMaximumValue;
	}

	public WebElement getMinimumValueField() {
		return minimumValueField;
	}

	public WebElement getMaximumValueField() {
		return maximumValueField;
	}

	public WebElement getFileType() {
		return fileType;
	}

	public WebElement getFileExtension() {
		return fileExtension;
	}

	public WebElement getFileSource() {
		return fileSource;
	}

	/* --------------------------------------------------------------------------*/

	//Prefill xpath

	@FindBy(xpath = "//*[@id='sfquestion-txt_prefill_value']")
	WebElement txtprefillValue;

	@FindBy(xpath = "//*[@id='sfquestion-txt_mysql_query']")
	WebElement txtMySql;

	@FindBy(xpath = "//*[@id='sfquestion-txt_sqlite_query']")
	WebElement txtSqlLite;

	public WebElement getTxtprefillValue() {
		return txtprefillValue;
	}

	public WebElement getTxtMySql() {
		return txtMySql;
	}

	public WebElement getTxtSqlLite() {
		return txtSqlLite;
	}

	public WebElement prefillValueField(Integer sectionNo, Integer questionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//input[contains(@id,'txt_prefill_value')]"));
	}
	public WebElement btnUpdatePencil(Integer sectionNo, Integer questionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//div[@class='pencil']//a"));
	}

	/* --------------------------------------------------------------------------*/

	//Add Section
	@FindBy(xpath = "//a[@class='view add-section btn btn-outline-primary btn-block section_block']")
	WebElement btnAddSection;

	@FindBy(xpath = "//input[@id='sfformsection-txt_name']")
	WebElement txtSectionName;

	@FindBy(xpath = "//textarea[@id='sfformsection-txt_instruction']")
	WebElement txtSectionDescription;

	
	//Source Type

	public WebElement sourceType(Integer sectionNo, Integer questionNo)
	{    	
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-3 second-col']//select[contains(@id,'int_choice_lov_source_type')]"));
	}

	public WebElement addChoicesField(Integer sectionNo, Integer questionNo)
	{    	
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[2]/div["+questionNo+"]//div[@class='col-sm-9 first-col']//div[@class='col-sm-12 que-col']/div[@class='option-card']"));
	}

	@FindBy(xpath = "//label[contains(text(),'Choices of this question depends on other question')]")
	WebElement query_ChoiceLabel;

	@FindBy(xpath = "*//div[contains(@class,'switch-id-sfquestion-ysn_choice_depends')]")
	WebElement query_ChoiceDependToggle;

	//For single choice depend.
	@FindBy(xpath = "//select[@id='sfquestion-0-txt_section_data']")
	WebElement query_choicesDependOnSection;

	@FindBy(xpath = "//select[@id='sfquestion-0-txt_question']")
	WebElement query_choicesDependOnQuestion;

	@FindBy(xpath = "//div[@class='wrapper']//td[3]/i")
	WebElement query_choicesDependOnDeleteButton;

	@FindBy(xpath = "//input[@id='sfquestion-int_min_value']")
	WebElement year_YearFromField;

	@FindBy(xpath = "//input[@id='sfquestion-int_max_value']")
	WebElement year_YearToField;

	@FindBy(xpath = "//th[@class='td-remove']/i")
	WebElement addChoicesButton;

	@FindBy(xpath = "//div[@class='wrapper']//td[4]/i")
	WebElement userDefined_choiceDeleteButton;
	
	@FindBy(xpath = "//div[@id='modalContent']//div[@class='col-md-12']")
	WebElement movesectionLabel;
	
	@FindBy(xpath = "//select[@id='sfquestion-txt_move']")
	WebElement moveToSection;

	public WebElement userDefined_choicesDeleteButton(Integer rowNo)
	{
		return driver.findElement(By.xpath("//div[@class='wrapper']//tr[2]//td[4]/i"));
	}
	public WebElement userDefined_choicesSNo(Integer rowNo)
	{
		return driver.findElement(By.xpath("//input[@id='sfformquestionchoice-"+rowNo+"-int_sequence']"));
	}

	public WebElement userDefined_choicesText(Integer rowNo)
	{
		return driver.findElement(By.xpath("//input[@id='sfformquestionchoice-"+rowNo+"-txt_text']"));
	}

	public WebElement userDefined_choicesImage(Integer rowNo)
	{
		return driver.findElement(By.xpath("//input[@id='sfformquestionchoice-"+rowNo+"-mbl_image']"));
	}

	/**
	 * @return the query_ChoiceLabel
	 */
	public WebElement getQuery_ChoiceLabel() {
		return query_ChoiceLabel;
	}

	/**
	 * @return the query_ChoiceDependToggle
	 */
	public WebElement getQuery_ChoiceDependToggle() {
		return query_ChoiceDependToggle;
	}

	/**
	 * @return the query_choicesDependOnSection
	 */
	public WebElement getQuery_choicesDependOnSection() {
		return query_choicesDependOnSection;
	}

	/**
	 * @return the query_choicesDependOnQuestion
	 */
	public WebElement getQuery_choicesDependOnQuestion() {
		return query_choicesDependOnQuestion;
	}

	/**
	 * @return the query_choicesDependOnDeleteButton
	 */
	public WebElement getQuery_choicesDependOnDeleteButton() {
		return query_choicesDependOnDeleteButton;
	}

	/**
	 * @return the year_YearFromField
	 */
	public WebElement getYear_YearFromField() {
		return year_YearFromField;
	}

	/**
	 * @return the year_YearToField
	 */
	public WebElement getYear_YearToField() {
		return year_YearToField;
	}

	/**
	 * @return the addChoicesButton
	 */
	public WebElement getAddChoicesButton() {
		return addChoicesButton;
	}

	/**
	 * @return the userDefined_choiceDeleteButton
	 */
	public WebElement getUserDefined_choiceDeleteButton() {
		return userDefined_choiceDeleteButton;
	}
	
	
	
	
	public WebElement getMoveToSection() {
		return moveToSection;
	}
	
	/**
	 * @return the movesectionLabel
	 */
	public WebElement getMovesectionLabel() {
		return movesectionLabel;
	}

	/**
	 * @return the btnAddSection
	 */
	public WebElement getBtnAddSection() {
		return btnAddSection;
	}

	/**
	 * @return the txtSectionName
	 */
	public WebElement getTxtSectionName() {
		return txtSectionName;
	}

	/**
	 * @return the txtSectionDescription
	 */
	public WebElement getTxtSectionDescription() {
		return txtSectionDescription;
	}
	
	
	//Split Section
	
	@FindBy(xpath = "//div[@id='sfformsection-txt_question']/label")
	List<WebElement> listOfQuestions;
	
	@FindBy(xpath = "//input[@id='AllCheck']")
	WebElement selectAllCheckbox;
	
	@FindBy(xpath = "//span[@id='SelectedQuestion']")
	WebElement selectedQuestionNumber;
	
	@FindBy(xpath = "//div[@id='sfformsection-int_split_type']/label")
	WebElement splitSectionType;
	
	@FindBy(xpath = "//select[@id='sfformsection-txt_merge_section']")
	WebElement mergeToSection;
	
	/**
	 * @return the listOfQuestions
	 */
	public List<WebElement> getListOfQuestions() {
		return listOfQuestions;
	}

	/**
	 * @param listOfQuestions the listOfQuestions to set
	 */
	public void setListOfQuestions(List<WebElement> listOfQuestions) {
		this.listOfQuestions = listOfQuestions;
	}

	/**
	 * @return the selectAllCheckbox
	 */
	public WebElement getSelectAllCheckbox() {
		return selectAllCheckbox;
	}

	/**
	 * @return the selectedQuestionNumber
	 */
	public WebElement getSelectedQuestionNumber() {
		return selectedQuestionNumber;
	}

	/**
	 * @return the splitSectionType
	 */
	public WebElement getSplitSectionType() {
		return splitSectionType;
	}

	/**
	 * @return the mergeToSection
	 */
	public WebElement getMergeToSection() {
		return mergeToSection;
	}
	
	public WebElement getSectionSettingLink(Integer sectionNo)
	{
		return driver.findElement(By.xpath("//div[@class='box box-primary']["+sectionNo+"]/div[@class='dropdown pt-5']"));
		
	}
	
	
	

}
