package com.shubham.projects.AppTest;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.shubham.projects.AppTest.utility.AndroidAction;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AddListTest extends BaseTest {
	
	
	@Test(priority = 1)
	public void AddListtest() throws InterruptedException {
		applist.AddList("Test");
		System.out.println("List added successfully!");
		Thread.sleep(2000);
	}
	
	@Test(priority = 2)
	public void EditListtest() throws InterruptedException {
		applist.EditList("Updated Test");
		System.out.println("List updated successfully!");
		Thread.sleep(2000);
	}
	
	@Test(priority = 3)
	public void DeleteListtest() {
		applist.DeleteList();
		System.out.println("List deleted successfully");
	}
	
		
	}
