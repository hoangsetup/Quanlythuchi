package com.truong.mobile.quanlythuchi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.truong.mobile.quanlythuchi.R;

public class Chart_Fragment extends Fragment {
	View rootView;
	private WebView mWebView_chi, mWebView_thu;
	private String url_thu, url_chi;

	public Chart_Fragment() {

	}

	public Chart_Fragment(String url_t, String url_chi) {
		this.url_chi = url_chi;
		this.url_thu = url_t;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.activity_chartreport, container,
				false);
		try {
			mWebView_chi = (WebView) rootView
					.findViewById(R.id.webView_chartChi);
			mWebView_chi.setWebViewClient(new myWebClien(0));
			

			mWebView_thu = (WebView) rootView
					.findViewById(R.id.webView_chartThu);
			mWebView_thu.setWebViewClient(new myWebClien(1));
			try {
				mWebView_thu.loadUrl(url_thu);
				mWebView_chi.loadUrl(url_chi);
			} catch (Exception ex) {
				Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception ex) {
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT)
					.show();
		}
		return rootView;
	}

	public class myWebClien extends WebViewClient {
		private int type = 0;

		public myWebClien(int type) {// 0chi, 1thu
			this.type = type;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			int pro = (type == 0) ? R.id.progressBar_loadingchi
					: R.id.progressBar_loadingthu;
			ProgressBar bar = (ProgressBar) rootView.findViewById(pro);
			bar.setVisibility(View.GONE);
			super.onPageFinished(view, url);
		}
	}
}
