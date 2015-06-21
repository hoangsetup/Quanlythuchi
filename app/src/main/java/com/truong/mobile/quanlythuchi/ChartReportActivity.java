package com.truong.mobile.quanlythuchi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChartReportActivity extends Activity {
	private WebView mWebView_chi, mWebView_thu;
	private String url_thu, url_chi;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_chartreport);
		url_chi = this.getIntent().getStringExtra("url_chi");
		url_thu = this.getIntent().getStringExtra("url_thu");
		
		dialog = new ProgressDialog(ChartReportActivity.this);
		dialog.setTitle("Đang tải...");
		dialog.setCancelable(false);
		dialog.show();
		

		mWebView_chi = (WebView) findViewById(R.id.webView_chartChi);
		mWebView_chi.setWebViewClient(new myClient());
		mWebView_chi.loadUrl(url_chi);
		

		mWebView_thu = (WebView) findViewById(R.id.webView_chartThu);
		mWebView_thu.setWebViewClient(new myClient());
		mWebView_thu.loadUrl(url_thu);

		this.getActionBar().setSubtitle("FITHOU-truong.mobile Traing");

	}

	public class myClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			if(dialog != null){
				dialog.dismiss();
			}
			super.onPageFinished(view, url);
		}
	}
}
