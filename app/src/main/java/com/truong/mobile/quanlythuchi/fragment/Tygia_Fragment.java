package com.truong.mobile.quanlythuchi.fragment;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.adapters.TygiaAdapter;
import com.truong.mobile.quanlythuchi.units.Tygia;

public class Tygia_Fragment extends Fragment {
	private View rootView;
	private ListView mListView;
	private Vector<Tygia> mTygias = new Vector<Tygia>();
	private TygiaAdapter adp = null;
	public static String LINK = "http://oceanbank.vn/ty-gia-ngoai-te.html";

	// http://www.acb.com.vn/tygia/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_tygia, container, false);
		mListView = (ListView) rootView.findViewById(R.id.listView_tygia);
		new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(getActivity());

			protected void onPreExecute() {
				dialog.setTitle("Đang tải...");
				dialog.setCancelable(true);
				dialog.show();
			};

			@SuppressLint("DefaultLocale")
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				Document docHTML = null;
				try {
					docHTML = Jsoup.connect(LINK).timeout(3000).get();
					Elements ele = docHTML.select("table.tb_tg tr");
					for (int i = 3; i < ele.size(); i++) {
						if (i >= 4 && i <= 6) {
							continue;
						}
						Tygia tmp = new Tygia();
						tmp.setName(ele.eq(i).select("td").eq(0).text()
								.toUpperCase());
						tmp.setSell(ele.eq(i).select("td").eq(4).text());
						mTygias.add(tmp);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("------------", e.toString());
					getLocalData();
					e.printStackTrace();
				}
				// Elements ele = docHTML.select("table.tb_tg tr");
				// for (int i = 3; i < ele.size(); i++) {
				// if(i>=4 && i<=6){
				// continue;
				// }
				// Tygia tmp = new Tygia();
				// tmp.setName(ele.eq(i).select("td").eq(0).text()
				// .toUpperCase());
				// tmp.setSell(ele.eq(i).select("td").eq(4).text());
				// mTygias.add(tmp);
				// }
				return null;
			}

			protected void onPostExecute(Void result) {
				try {
					adp = new TygiaAdapter(getActivity(),
							R.layout.list_tygia_item, mTygias);
					mListView.setAdapter(adp);
				} catch (Exception ex) {
					Toast.makeText(getActivity(), ex.toString(),
							Toast.LENGTH_SHORT).show();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				if (mTygias.size() > 0) {
					setLocalData();
				}
			};

		}.execute();

		return rootView;
	}

	public void getLocalData() {
		SharedPreferences local = getActivity().getSharedPreferences(
				"TYGIA_LOCAL", 0);
		@SuppressWarnings("unchecked")
		Map<String, String> m = (Map<String, String>) local.getAll();
		mTygias.clear();
		for (Map.Entry<String, String> i : m.entrySet()) {
			Tygia a = new Tygia();
			a.setName(i.getKey());
			a.setSell(i.getValue());
			mTygias.add(a);
		}
	}

	public void setLocalData() {
		SharedPreferences local = getActivity().getSharedPreferences(
				"TYGIA_LOCAL", 0);
		SharedPreferences.Editor editor = local.edit();
		for (Tygia i : mTygias) {
			editor.putString(i.getName(), i.getSell());
		}
		editor.commit();
	}
}
