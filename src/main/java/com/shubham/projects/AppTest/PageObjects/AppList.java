package com.shubham.projects.AppTest.PageObjects;
import java.util.ArrayList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.shubham.projects.AppTest.utility.AndroidAction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AppList extends AndroidAction {
	
	AndroidDriver driver;
	
	
	@AndroidFindBy(accessibility ="ADD NEW LIST")
	private WebElement ADD_NEW_LIST_BTN;
	
	@AndroidFindBy(className = "android.widget.EditText")
	private WebElement InputTextField;
	 
	@AndroidFindBy(accessibility = "SAVE")
	private WebElement SAVE_BTN;
	
	@AndroidFindBy(className = "android.widget.ImageView")
	private WebElement Addeditem;
	
	@AndroidFindBy(accessibility = "Edit")
	private WebElement EDIT;
	
	@AndroidFindBy(accessibility = "Delete")
	private WebElement DELETE;
	
	@AndroidFindBy(accessibility = "YES")
	private WebElement YES;
	
	
	
	public AppList(AndroidDriver driver){
		super(driver);
	    this.driver = driver;
	    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void AddList(String listdetails) {
		ADD_NEW_LIST_BTN.click();
		InputTextField.click();
		driver.hideKeyboard();
		InputTextField.sendKeys(listdetails);
		SAVE_BTN.click();
		
	}
	
	public void EditList(String Updatedlistdetails) {
		longPressAction(Addeditem);
		EDIT.click();
		InputTextField.click();
		driver.hideKeyboard();
		InputTextField.sendKeys(Updatedlistdetails);
		SAVE_BTN.click();
	}
	
	public void DeleteList() {
		longPressAction(Addeditem);
		DELETE.click();
		YES.click();
	}

}
