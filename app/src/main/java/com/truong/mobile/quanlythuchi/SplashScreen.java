package com.truong.mobile.quanlythuchi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.truong.mobile.quanlythuchi.sqlitehelper.Dbclass;

public class SplashScreen extends Activity {
	public static int TIME_OUT = 1800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_splash);
		//
		Intent intent = new Intent(this, Dbclass.class);
		startService(intent);
		//
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, TIME_OUT);
	}
}
