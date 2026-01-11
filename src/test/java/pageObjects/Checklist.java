package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Checklist extends BasePage {

	public Checklist(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='typeId']")
	WebElement drpdwn_checklisttype;

	public void selChecklist(String checklisttype) {
		waitForElementandClick(drpdwn_checklisttype);
		clickRelativeOption(drpdwn_checklisttype, "li", checklisttype);

	}

	@FindBy(xpath = "//ng-multiselect-dropdown[@formcontrolname='checkPointType']")
	WebElement drpdwn_checkpointtype;

	public void selCheckPointType(String checkpointtype) {
		waitForElementandClick(drpdwn_checkpointtype);
		clickRelativeOption(drpdwn_checkpointtype, "li", checkpointtype);

	}

	@FindBy(xpath = ("//input[@formcontrolname='enterNumberData']"))
	WebElement txt_enternumber;

	public void enterNumber(String enternumber) {
		txt_enternumber.clear();
		waitAndSendKeys(txt_enternumber, enternumber);
	}

	@FindBy(xpath = ("//input[@formcontrolname='checkPointData']"))
	List<WebElement> txt_enterData;

	public void enterData(int index, String data) {
		enterValueInElementAtIndex(txt_enterData, index, data);

	}

	@FindBy(xpath = ("//input[@formcontrolname='description']"))
	List<WebElement> txt_description;

	public void enterDescription(String description) {
		enterValueInLatestElement(txt_description, description);

	}

	@FindBy(xpath = "//button[normalize-space()='Add']")
	WebElement btn_add;

	public void addButton() throws Exception {
		scrollAndClick(btn_add);
	}

	@FindBy(xpath = "//i[@class='fa fa-plus']")
	List<WebElement> btn_plus;

	public void plusButton() throws Exception {
		clickLatestElement(btn_plus);
	}

	@FindBy(xpath = "//input[@formcontrolname='isSub']")
	List<WebElement> sel_reqsub;

	public void selReqSub() {
		clickLatestElement(sel_reqsub);
	}

	public boolean isLastReqSubSelected() {
		if (sel_reqsub != null && !sel_reqsub.isEmpty()) {
			return sel_reqsub.get(sel_reqsub.size() - 1).isSelected();
		}
		return false;
	}

	@FindBy(xpath = ("//input[@formcontrolname='doesTheContainer']"))
	List<WebElement> txt_subdescription;

	public void enterSubDescription(String subdescription) {
		enterValueInLatestElement(txt_subdescription, subdescription);

	}

}
