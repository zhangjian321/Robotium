package com.mason.wooplus.test.test.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.robotium.solo.Solo;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class TestOne extends ActivityInstrumentationTestCase2 {
	private Solo solo;
	private static final String mainActivity = "com.mason.wooplus.activity.SplashActivity";
	private static final String PACKAGE_STRING = "com.mason.wooplus";
	private static Class launchActivityClass;
	
	static {
		try {
			//找到启动activity,安卓中的activity就相当于MVC模式中的controller
			launchActivityClass = Class.forName(mainActivity);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public TestOne() throws ClassNotFoundException {
		super(launchActivityClass);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@After
	protected void tearDown() throws Exception {
		//关掉打开的activity
		solo.finishOpenedActivities();
	}
	
	//正常登录
	@Test
	public void testLogin() {
		if (solo.waitForView(getViewIntIdByStringId("com.mason.wooplus:id/me"), 1, 5000)) {
			solo.clickOnView(solo.getView(getViewIntIdByStringId("com.mason.wooplus:id/me")));
			solo.clickOnText("Settings");
			solo.clickOnText("Log Out");
			solo.clickOnButton("Log Out");
		}
		clickView("sign_in");
		solo.waitForText("Password");
		//输入邮箱和密码
		if (getEditTextStringById("com.mason.wooplus:id/edit_text").trim() != null) {
			solo.clearEditText(0);
		}
		solo.enterText(0, "m135@gmail.com");
		solo.enterText(1, "mmmmmm");
		clickView("submit_btn");
		//通过IdString来获取view，并判断该view是否出现
		Assert.assertTrue("The login test is failed.", solo.waitForView(getViewIntIdByStringId("com.mason.wooplus:id/me"), 1, 500));
	}
	
	//根据控件ID来点击控件
	private boolean clickView(String idString) {
		if (idString.trim() == "") {
			System.out.println("This idString is null.");
			return false;
		} else {
			try {
				Activity currentActivity = solo.getCurrentActivity();
				int id = currentActivity.getResources().getIdentifier(idString, "id", currentActivity.getPackageName());
				if (solo.waitForView(id)) {
					solo.clickOnView(solo.getView(id));
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	//根据edittext的ID来获取EditText中的内容并返回
	private String getEditTextStringById(String idString) {
		TextView view = (TextView)solo.getView(idString);
		return view.getText().toString();
	}
	
	//将view的string id切换为R.id
	private int getViewIntIdByStringId(String idString) {
		return solo.getCurrentActivity().getResources().getIdentifier(idString, "id", PACKAGE_STRING);
	}
}