package com.truong.mobile.quanlythuchi.fragment;

import java.util.Vector;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.truong.mobile.quanlythuchi.GhichepActivity;
import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.adapters.GhichepAdapter;
import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Ghichep;

public class Ghichep_Fragment extends Fragment {
	private View rootView;
	private ImageButton btn_add;
	private ListView lv_ghichep;
	private Vector<Ghichep> lst_ghichep = new Vector<Ghichep>();
	private GhichepAdapter adp = null;
	private Ghichep ghichep_tmp = new Ghichep();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater
				.inflate(R.layout.fragment_ghichep, container, false);
		getWidget();
		registryEvent();
		loadData();
		return rootView;
	}

	public void getWidget() {
		btn_add = (ImageButton) rootView
				.findViewById(R.id.imageButton_addghichep);
		lv_ghichep = (ListView) rootView.findViewById(R.id.listView_ghichep);
	}

	public void registryEvent() {
		btn_add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(getActivity(),
						GhichepActivity.class);
				startActivityForResult(mIntent, 118);
			}
		});
		registerForContextMenu(lv_ghichep);

		lv_ghichep.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ghichep_tmp = lst_ghichep.get(arg2);
				return false;
			}
		});
		
		lv_ghichep.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ghichep_tmp = lst_ghichep.get(arg2);
				Intent myIntent = new Intent(getActivity(), GhichepActivity.class);
				myIntent.putExtra("isEdit", true);
				myIntent.putExtra("isView", true);
				myIntent.putExtra("ghichep", ghichep_tmp);
				startActivityForResult(myIntent, 119);
			}
			
		});
	}

	public void loadData() {
		Mysqlhelper mysqlhelper = new Mysqlhelper(getActivity(), 1);
		lst_ghichep = mysqlhelper.getAllGhichep();
		adp = new GhichepAdapter(getActivity(), R.layout.list_ghichep_item,
				lst_ghichep);
		// adp.sort(new Comparator<Ghichep>() {
		// @Override
		// public int compare(Ghichep arg0, Ghichep arg1) {
		// // TODO Auto-generated method stub
		// return 1;
		// // return (Date.parse(arg0.getThoigian().split(" ")[0]) < Date
		// // .parse(arg1.getThoigian().split(" ")[0]) ? 1 : 0);
		// }
		// });
		lv_ghichep.setAdapter(adp);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		loadData();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		this.getActivity().getMenuInflater()
				.inflate(R.menu.context_taikhoan, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Mysqlhelper mysqlhelper = new Mysqlhelper(getActivity(), 1);
		switch (item.getItemId()) {
		case R.id.act_sua:
			Intent myIntent = new Intent(getActivity(), GhichepActivity.class);
			myIntent.putExtra("isEdit", true);
			myIntent.putExtra("ghichep", ghichep_tmp);
			startActivityForResult(myIntent, 119);
			break;
		case R.id.act_xoa:
			mysqlhelper.deleteGhichep(ghichep_tmp.getId());
			loadData();
			break;
		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}
}
