package com.truong.mobile.quanlythuchi.fragment;

import java.util.Vector;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.HangmucActivity;
import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Hangmuc;

public class Hangmuc_Fragment extends Fragment {
	private View rootView;
	private ListView lv_Chi, lv_Thu;
	private ImageButton btn_addChi, btn_addThu;
	private Vector<Hangmuc> vt_allHangmuc = new Vector<Hangmuc>();
	private Vector<Hangmuc> vt_hangmuc_chi = new Vector<Hangmuc>();
	private Vector<Hangmuc> vt_hangmuc_thu = new Vector<Hangmuc>();
	private ArrayAdapter<Hangmuc> adp_hangmuc_chi = null;
	private ArrayAdapter<Hangmuc> adp_hangmuc_thu = null;



	private Hangmuc hm_temp = new Hangmuc();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater
				.inflate(R.layout.fragment_hangmuc, container, false);
		initTab();
		getWidgetControl();
		registryEvent();
		loadData();
		registerForContextMenu(lv_Chi);
		registerForContextMenu(lv_Thu);
		return rootView;
	}

	public void initTab() {
		final TabHost tab = (TabHost) (rootView.findViewById(R.id.tabHost));
		tab.setup();
		TabHost.TabSpec spec;
		// Config tab1
		spec = tab.newTabSpec("t1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Hạng mục thu");
		tab.addTab(spec);
		// Tab2
		spec = tab.newTabSpec("t2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Hạng mục chi");
		tab.addTab(spec);

		tab.setCurrentTab(0);

		// TabHost tabhost = );
		for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title); // Unselected Tabs
			tv.setTextColor(Color.parseColor("#ffffff"));
			tv.setTextSize(20);
		}
		// TextView tv = (TextView) tab.getCurrentTabView().findViewById(
		// android.R.id.title); // for Selected Tab
		// tv.setTextColor(Color.parseColor("#000000"));
	}

	public void getWidgetControl() {
		btn_addChi = (ImageButton) rootView
				.findViewById(R.id.imageButton_addchi);
		btn_addThu = (ImageButton) rootView
				.findViewById(R.id.imageButton_addthu);
		lv_Chi = (ListView) rootView.findViewById(R.id.listView_chi);
		lv_Thu = (ListView) rootView.findViewById(R.id.listView_thu);


	}

	public void registryEvent() {
		btn_addChi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAddHangmuc(false, 0, null);
			}
		});

		btn_addThu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAddHangmuc(false, 1, null);
			}
		});

		lv_Chi.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				hm_temp = vt_hangmuc_chi.get(arg2);
				return false;
			}
		});
		lv_Thu.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				hm_temp = vt_hangmuc_thu.get(arg2);
				return false;
			}
		});
	}

	public void loadData() {
		vt_allHangmuc.clear();
		vt_hangmuc_chi.clear();
		vt_hangmuc_thu.clear();
		Mysqlhelper mysqlhelper = new Mysqlhelper(getActivity(), 1);
		vt_allHangmuc = mysqlhelper.getAllHangmuc();
		//
		for (Hangmuc i : vt_allHangmuc) {
			if (i.getiLoaihangmuc() == 0) {
				vt_hangmuc_chi.add(i);
			} else {
				vt_hangmuc_thu.add(i);
			}
		}
		adp_hangmuc_chi = new ArrayAdapter<Hangmuc>(getActivity(),
				android.R.layout.simple_expandable_list_item_1, vt_hangmuc_chi);
		adp_hangmuc_thu = new ArrayAdapter<Hangmuc>(getActivity(),
				android.R.layout.simple_expandable_list_item_1, vt_hangmuc_thu);
		lv_Chi.setAdapter(adp_hangmuc_chi);
		lv_Thu.setAdapter(adp_hangmuc_thu);
	}

	public void startAddHangmuc(boolean isEdit, int iLoaihangmuc, Hangmuc hm) { // 0-thu,
																				// 1-chi
		Intent myIntent = new Intent(getActivity(), HangmucActivity.class);
		myIntent.putExtra("iLoaihangmuc", iLoaihangmuc);
		myIntent.putExtra("isEdit", isEdit);
		myIntent.putExtra("Hangmuc", hm);
		startActivityForResult(myIntent, 116);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		loadData();
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		this.getActivity().getMenuInflater()
				.inflate(R.menu.context_taikhoan, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Mysqlhelper mysqlhelper = new Mysqlhelper(getActivity(), 1);
		switch (item.getItemId()) {
		case R.id.act_sua:
			startAddHangmuc(true, hm_temp.getiLoaihangmuc(), hm_temp);
			break;
		case R.id.act_xoa:
			mysqlhelper.deleteHangmuc(hm_temp);
			loadData();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
