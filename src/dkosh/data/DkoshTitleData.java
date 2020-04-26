package dkosh.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import utilities.GoogleSheetAPIUtils;

/**
 * @author Jaikishan Mohanty
 * @since 12-Feb-2020
 */

public class DkoshTitleData {

	GoogleSheetAPIUtils googleSheet;
	String dKOSHDataSpreadsheetId = "1Q4_V7oh3wSdl4HFnlk1UwHA-O4k3HE6KePS89EtlmW0";
	String titleSheetName = "Title!A2:A";
	Object[][] titleData;
	
	
	String title_formSettings;
	String title_manageForms;
	String title_manageRespondent;
	String title_createEmployeeGroup;
	String title_addSection;
	String title_modifySection;
	String title_mergeSection;
	String title_splitSection;
	String title_exportQuestions;
	String title_importQuestions;
	String title_sectionDependsOn;
	String title_setQuestionSequence;
	String title_deleteSection;
	String title_questionDependsOn;
	String title_moveToSection;
	String title_updatePrefillValue;
	String title_userDefinedList;
	String title_manageQuery;
	String title_modifyForm;
	String title_yearRange;
	String title_applicableValidation;

	public void getTitle() throws Exception
	{
		googleSheet  = new GoogleSheetAPIUtils();
		titleData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, titleSheetName);
		
		
		 List<Object> titleList = new ArrayList<Object>();
		    for (Object[] arrTitleData : titleData) {
		    	titleList.addAll(Arrays.asList(arrTitleData));
		    }
		    title_formSettings = (String) titleList.get(0);
		    title_manageForms = (String) titleList.get(1);
		    title_manageRespondent = (String) titleList.get(2);
		    title_createEmployeeGroup = (String) titleList.get(3);
		    title_addSection = (String) titleList.get(4);
		    title_modifySection = (String) titleList.get(5);
			title_mergeSection = (String) titleList.get(6);
			title_splitSection = (String) titleList.get(7);
		   	title_exportQuestions = (String) titleList.get(8);
		  	title_importQuestions = (String) titleList.get(9);
		   	title_sectionDependsOn = (String) titleList.get(10);
		   	title_setQuestionSequence = (String) titleList.get(11);
		   	title_deleteSection = (String) titleList.get(12);
		  	title_questionDependsOn = (String) titleList.get(13);
			title_moveToSection = (String) titleList.get(14);
		  	title_updatePrefillValue = (String) titleList.get(15);
		  	title_userDefinedList = (String) titleList.get(16);
		  	title_manageQuery =  (String) titleList.get(17);
		  	title_modifyForm =  (String) titleList.get(18);
		  	title_yearRange  =  (String) titleList.get(19);
		  	title_applicableValidation  =  (String) titleList.get(20);
	}

/**
	 * @return the title_applicableValidation
	 */
	public String getTitle_applicableValidation() {
		return title_applicableValidation;
	}

/**
	 * @return the title_yearRange
	 */
	public String getTitle_yearRange() {
		return title_yearRange;
	}

/**
	 * @return the title_formSettings
	 */
	public String getTitle_formSettings() {
		return title_formSettings;
	}

	/**
	 * @return the title_manageForms
	 */
	public String getTitle_manageForms() {
		return title_manageForms;
	}

	/**
	 * @return the title_manageRespondent
	 */
	public String getTitle_manageRespondent() {
		return title_manageRespondent;
	}

	/**
	 * @return the title_modifySection
	 */
	public String getTitle_modifySection() {
		return title_modifySection;
	}

	/**
	 * @return the title_mergeSection
	 */
	public String getTitle_mergeSection() {
		return title_mergeSection;
	}

	/**
	 * @return the title_splitSection
	 */
	public String getTitle_splitSection() {
		return title_splitSection;
	}

	/**
	 * @return the title_exportQuestions
	 */
	public String getTitle_exportQuestions() {
		return title_exportQuestions;
	}

	/**
	 * @return the title_importQuestions
	 */
	public String getTitle_importQuestions() {
		return title_importQuestions;
	}

	/**
	 * @return the title_sectionDependsOn
	 */
	public String getTitle_sectionDependsOn() {
		return title_sectionDependsOn;
	}

	/**
	 * @return the title_setQuestionSequence
	 */
	public String getTitle_setQuestionSequence() {
		return title_setQuestionSequence;
	}

	/**
	 * @return the title_questionDependsOn
	 */
	public String getTitle_questionDependsOn() {
		return title_questionDependsOn;
	}

	/**
	 * @return the title_moveToSection
	 */
	public String getTitle_moveToSection() {
		return title_moveToSection;
	}

	/**
	 * @return the title_updatePrefillValue
	 */
	public String getTitle_updatePrefillValue() {
		return title_updatePrefillValue;
	}

/**
	 * @return the title_createEmployeeGroup
	 */
	public String getTitle_createEmployeeGroup() {
		return title_createEmployeeGroup;
	}

	/**
	 * @return the title_addSection
	 */
	public String getTitle_addSection() {
		return title_addSection;
	}

	/**
	 * @return the title_deleteSection
	 */
	public String getTitle_deleteSection() {
		return title_deleteSection;
	}
	/**
	 * @return the title_userDefinedList
	 */
	public String getTitle_userDefinedList() {
		return title_userDefinedList;
	}

	/**
	 * @return the title_manageQuery
	 */
	public String getTitle_manageQuery() {
		return title_manageQuery;
	}

	/**
	 * @return the title_modifyForm
	 */
	public String getTitle_modifyForm() {
		return title_modifyForm;
	}
	
}
