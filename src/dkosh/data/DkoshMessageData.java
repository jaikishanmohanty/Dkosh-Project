package dkosh.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilities.GoogleSheetAPIUtils;

public class DkoshMessageData {
	
	GoogleSheetAPIUtils googleSheet;
	String dKOSHDataSpreadsheetId = "1Q4_V7oh3wSdl4HFnlk1UwHA-O4k3HE6KePS89EtlmW0";
	String titleSheetName = "Dkosh_Messages!A2:Z";
	Object[][] dkoshMessageData;

	String msg_createForm;
	String msg_addRespondentGroup;
	String msg_updateStatus;
	String msg_createSection;
	String msg_createQuestion;
	String msg_addResponse;
	String msg_moveToSection;
	String msg_updateSection;
	
	

	public void getMessage() throws Exception
	{
		googleSheet  = new GoogleSheetAPIUtils();
		dkoshMessageData = googleSheet.readDataFromGoogleSheet(dKOSHDataSpreadsheetId, titleSheetName);
		
		
		 List<Object> messageList = new ArrayList<Object>();
		    for (Object[] arrMessageData : dkoshMessageData) {
		    	messageList.addAll(Arrays.asList(arrMessageData));
		    }
   
		    msg_createForm = (String) messageList.get(0);
		    msg_addRespondentGroup = (String) messageList.get(1);
		    msg_updateStatus = (String) messageList.get(2);
		    msg_createSection = (String) messageList.get(3);
		    msg_createQuestion = (String) messageList.get(4);
		    msg_addResponse = (String) messageList.get(5);
		    msg_moveToSection = (String) messageList.get(6);
		    msg_updateSection = (String) messageList.get(7);
	}


	/**
	 * @return the msg_createForm
	 */
	public String getMsg_createForm() {
		return msg_createForm;
	}


	/**
	 * @return the msg_moveToSection
	 */
	public String getMsg_moveToSection() {
		return msg_moveToSection;
	}


	/**
	 * @return the msg_addRespondentGroup
	 */
	public String getMsg_addRespondentGroup() {
		return msg_addRespondentGroup;
	}


	/**
	 * @return the msg_updateStatus
	 */
	public String getMsg_updateStatus() {
		return msg_updateStatus;
	}


	/**
	 * @return the msg_createSection
	 */
	public String getMsg_createSection() {
		return msg_createSection;
	}


	/**
	 * @return the msg_createQuestion
	 */
	public String getMsg_createQuestion() {
		return msg_createQuestion;
	}


	/**
	 * @return the msg_addResponse
	 */
	public String getMsg_addResponse() {
		return msg_addResponse;
	}

	/**
	 * @return the msg_updateSection
	 */
	public String getMsg_updateSection() {
		return msg_updateSection;
	}

	
	
}
