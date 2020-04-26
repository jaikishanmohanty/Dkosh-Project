package dkosh.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageFormSettings {
	
    public WebDriver driver;
    public PageFormSettings(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
    }
    
  
    @FindBy(xpath = "//div[@class = 'alert-success alert fade in']")
	private WebElement successAlert;
    
	By successAlertForWait = By.xpath("//div[@class = 'alert-success alert fade in']");

    
    @FindBy(xpath = "*//input[@id='sfform-txt_name']")
    WebElement txtName;
    
    @FindBy(xpath = "*//input[@id='sfform-txt_short_name']")
    WebElement txtshortname;
    
    @FindBy(xpath = "//div[@id='sfform-dat_available_from-datetime']/input")
    WebElement dateAvailableFrom;
    
    @FindBy(xpath = "//div[@id='sfform-dat_available_upto-datetime']/input")
    WebElement dateAvailableUpto;
    
    @FindBy(xpath = "*//input[@id='sfform-int_est_hrs']") 
    WebElement estimated_hours;
    
    @FindBy(xpath = "*//input[@id='sfform-int_est_mins']")
    WebElement estimated_minutes;
    
    @FindBy(xpath = "*//select[@id='sfform-int_frequency']")
    WebElement response_frequency;
    
    @FindBy(xpath = "*//textarea[@id='sfform-txt_instruction']")
    WebElement textAreaInstruction;
    
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSave;
    
    @FindBy(xpath = "//div[@class='form-group response-tbl']/span")
    WebElement responseTableName;
    
    @FindBy(xpath = "//span[@id='txt_response_table_name']")
    WebElement txtResponseTableName;
    
 
    @FindBy(xpath = "//label[contains(.,'Short Name')]/../div[@class='help-block']")
    WebElement errorShortName;
  
    /**
	 * @return the txtName
	 */
	public WebElement getTxtName() {
		return txtName;
	}

	/**
	 * @return the txtshortname
	 */
	public WebElement getTxtshortname() {
		return txtshortname;
	}

	/**
	 * @return the dateAvailableFrom
	 */
	public WebElement getDateAvailableFrom() {
		return dateAvailableFrom;
	}

	/**
	 * @return the dateAvailableUpto
	 */
	public WebElement getDateAvailableUpto() {
		return dateAvailableUpto;
	}

	/**
	 * @return the textAreaInstruction
	 */
	public WebElement getTextAreaInstruction() {
		return textAreaInstruction;
	}

	/**
	 * @return the btnSave
	 */
	public WebElement getBtnSave() {
		return btnSave;
	}

	/**
	 * @return the listOfHours
	 */
	public By getListOfHours() {
		return listOfHours;
	}

	/**
	 * @return the listOfMinutes
	 */
	public By getListOfMinutes() {
		return listOfMinutes;
	}

	public WebElement getEstimated_hours() {
            return estimated_hours;
    }

    public WebElement getEstimated_minutes() {
            return estimated_minutes;
    }

    public WebElement getResponse_frequency() {
            return response_frequency;
    }



    // Handling Calendar
    By listOfHours = By.xpath("//div[@class='datetimepicker-hours']//tbody//span");
    By listOfMinutes = By.xpath("//div[@class='datetimepicker-minutes']//tbody//span");
    
    public void selectDate(WebElement calendar,String dateNumber, String dateFormat) throws InterruptedException
    {
    	String datepicker = "*//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']["+dateNumber+"]";
    	By midButtonLink = By.xpath(datepicker+"/div[@class='datetimepicker-days']//th[@class='switch']");
    	By nextButtonLink = By.xpath(datepicker+"/div[@class='datetimepicker-months']//th[@class='next']");
    	By previousButtonLink = By.xpath(datepicker+"/div[@class='datetimepicker-months']//th[@class='prev']");
    	By listOfMonth = By.xpath(datepicker+"//div[@class='datetimepicker-months']//tbody//span");
    	By allDatesOfSelectedMonth = By.xpath(datepicker+"/div[@class='datetimepicker-days']//td[@class='day' or @class ='day active']");

    	//Clicking on calendar to open calendar widget
    	calendar.click();
    	Thread.sleep(2000);
    	// Retrieving current year value
    	String month_and_year= driver.findElement(midButtonLink).getText();
    	String month_and_year_array[] = month_and_year.split(" ");
    	String currentMonth = month_and_year_array[0];
    	String currentYear = month_and_year_array[1];
    	//String dateeee = "31-Dec-2019";
    	String date_month_year[] = dateFormat.split("-");
    	String day = date_month_year[0];
    	String monthName = date_month_year[1];
    	String year = date_month_year[2];
    	//button to move next in calendar

    	WebElement nextLink = driver.findElement(nextButtonLink);

    	//button to click in center of calendar header

    	WebElement midLink = driver.findElement(midButtonLink);

    	//button to move previous month in calendar

    	WebElement previousLink = driver.findElement(previousButtonLink); 

    	//get the year difference between current year and year to set in calendar

    	int yearDiff = Integer.parseInt(year)- Integer.parseInt(currentYear);
    	midLink.click();
    	Thread.sleep(2000);
    	if(yearDiff!=0){

    		//if you have to move next year

    		if(yearDiff>0){

    			for(int i=0;i< yearDiff;i++){
    				nextLink.click();
    				Thread.sleep(1000);
    			}

    		}

    		//if you have to move previous year

    		else if(yearDiff<0){

    			for(int i=0;i< (yearDiff*(-1));i++){

    				previousLink.click();

    			}

    		}

    	}

    	//Select the Desired month of that year
    	List<WebElement> listOfMonthName = driver.findElements(listOfMonth);
    	for (WebElement month: listOfMonthName) {
    		if (monthName.contains(month.getText())) {
    			month.click();
    			Thread.sleep(1000);
    			break;
    		}
    	}

    	//Select the Desired Date of that month
    	List<WebElement> allDateOfDesiredMonth = driver.findElements(allDatesOfSelectedMonth);
    	for(WebElement date:allDateOfDesiredMonth )
    	{
    		if(date.getText().equals(day))
    		{
    			date.click();
    			Thread.sleep(3000);
    			break;
    		}
    	}                

    }                

    //Method to select Time
    public void selectTime(String hour, String minute) throws InterruptedException
   
    {
    	List<WebElement> listOfhrs = driver.findElements(listOfHours);
    	for (WebElement hrs: listOfhrs) {
    		if (hrs.getText().contains(hour)) {
    			hrs.click();
    			Thread.sleep(4000);
    			break;
    		}
    	}

    	Thread.sleep(3000);

    	List<WebElement> listOfminutes = driver.findElements(listOfMinutes);
    	for (WebElement min: listOfminutes) {
    		if (min.getText().equals(hour+":"+minute)) {
    			min.click();
    			Thread.sleep(4000);
    			break;
    		}
    	}

    }
    
    // Response Management
    
    @FindBy(xpath = "//label[contains(.,'Can responses be modified?')]/..//div[@class='bootstrap-switch-container']")
    WebElement switchModifiedResponse;
    
    @FindBy(xpath = "//label[contains(.,'Is response verification required?')]/..//div[@class='bootstrap-switch-container']")
    WebElement switchVerifiedResponse;
    
    @FindBy(xpath = "//label[contains(.,'Is response approval required?')]/..//div[@class='bootstrap-switch-container']")
    WebElement switchApprovedResponse;
    
    
    public Boolean switchModifiedResponse()
    {
            Boolean modifiedResponse = driver.findElement(By.xpath("//label[contains(.,'Can responses be modified?')]/..//div[contains(@class,'switch-off')]")).isDisplayed();
            return modifiedResponse;
    }
    
    public Boolean switchVerifiedResponse()
    {
            Boolean verifiedResponse = driver.findElement(By.xpath("//label[contains(.,'Is response verification required?')]/..//div[contains(@class,'switch-off')]")).isDisplayed();
            return verifiedResponse;
    }
    public Boolean switchApprovedResponse()
    {
            Boolean approvedResponse = driver.findElement(By.xpath("//label[contains(.,'Is response approval required?')]/..//div[contains(@class,'switch-off')]")).isDisplayed();
            return approvedResponse;
    }
    
    
    
    
    
	/**
	 * @return the switchModifiedResponse
	 */
	public WebElement getSwitchModifiedResponse() {
		return switchModifiedResponse;
	}

	/**
	 * @return the switchVerifiedResponse
	 */
	public WebElement getSwitchVerifiedResponse() {
		return switchVerifiedResponse;
	}

	/**
	 * @return the switchApprovedResponse
	 */
	public WebElement getSwitchApprovedResponse() {
		return switchApprovedResponse;
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
	 * @return the errorShortName
	 */
	public WebElement getErrorShortName() {
		return errorShortName;
	}

	/**
	 * @return the responseTableName
	 */
	public WebElement getResponseTableName() {
		return responseTableName;
	}

	/**
	 * @return the txtResponseTableName
	 */
	public WebElement getTxtResponseTableName() {
		return txtResponseTableName;
	}
    
    
	
	// //div[contains(@class,'ysn_response_allow_edit bootstrap-switch')]
	
}
