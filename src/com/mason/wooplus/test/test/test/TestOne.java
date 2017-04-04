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
			//�ҵ�����activity,��׿�е�activity���൱��MVCģʽ�е�controller
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
		//�ص��򿪵�activity
		solo.finishOpenedActivities();
	}
	
	//������¼
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
		//�������������
		if (getEditTextStringById("com.mason.wooplus:id/edit_text").trim() != null) {
			solo.clearEditText(0);
		}
		solo.enterText(0, "m135@gmail.com");
		solo.enterText(1, "mmmmmm");
		clickView("submit_btn");
		//ͨ��IdString����ȡview�����жϸ�view�Ƿ����
		Assert.assertTrue("The login test is failed.", solo.waitForView(getViewIntIdByStringId("com.mason.wooplus:id/me"), 1, 500));
	}
	
	//���ݿؼ�ID������ؼ�
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
	
	//����edittext��ID����ȡEditText�е����ݲ�����
	private String getEditTextStringById(String idString) {
		TextView view = (TextView)solo.getView(idString);
		return view.getText().toString();
	}
	
	//��view��string id�л�ΪR.id
	private int getViewIntIdByStringId(String idString) {
		return solo.getCurrentActivity().getResources().getIdentifier(idString, "id", PACKAGE_STRING);
	}
}