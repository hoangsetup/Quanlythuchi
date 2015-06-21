package com.truong.mobile.quanlythuchi.fragment;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.adapters.BaocaoAdapter;
import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Baocao;
import com.truong.mobile.quanlythuchi.units.DrawChart;
import com.truong.mobile.quanlythuchi.units.Variable;

public class Baocao_Fragment extends Fragment {
	private View rootView;
	private ListView lv_chi, lv_thu;
	private TextView tv_tongthu, tv_tongchi;
	private BaocaoAdapter adp_chi = null, adp_thu = null;
	private Vector<Baocao> lst_chi = new Vector<Baocao>();
	private Vector<Baocao> lst_thu = new Vector<Baocao>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_baocao, container, false);
		getWidgetCOntrol();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loadData();
			}
		}, 300);
		// loadData();
		myEvent();
		return rootView;
	}

	public void getWidgetCOntrol() {
		lv_chi = (ListView) rootView.findViewById(R.id.Listview_baocao_chi);
		lv_thu = (ListView) rootView.findViewById(R.id.Listview_baocao_thu);
		tv_tongchi = (TextView) rootView.findViewById(R.id.tv_tongchi);
		tv_tongthu = (TextView) rootView.findViewById(R.id.tv_tongthu);
	}

	public void loadData() {

		Mysqlhelper db = new Mysqlhelper(getActivity(), 1);
		lst_chi.clear();
		lst_thu.clear();
		lst_chi = db.getBaocao2(0);
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		tv_tongchi.setText("Tổng chi: " + myFormatter.format(Variable.fSum));
		lst_thu = db.getBaocao2(1);
		tv_tongthu.setText("Tổng thu: " + myFormatter.format(Variable.fSum));

		//
		// String url = (new DrawChart()).drawrPieChar(lst_chi, "Khoản Chi");
		// Log.d("Link Link", url);
		//

		adp_chi = new BaocaoAdapter(getActivity(), R.layout.list_baocao_item,
				lst_chi);
		lv_chi.setAdapter(adp_chi);

		adp_thu = new BaocaoAdapter(getActivity(), R.layout.list_baocao_item,
				lst_thu);
		lv_thu.setAdapter(adp_thu);

	}

	public void myEvent() {
		// lv_chi.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// String url = (new DrawChart()).drawrPieChar(lst_chi,
		// "Khoản Chi");
		// Log.d("Link Link", url);
		// Intent myIntent = new Intent(getActivity(),
		// ChartReportActivity.class);
		// myIntent.putExtra("url", url);
		// startActivity(myIntent);
		// }
		// });
		lv_chi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				draw();
			}
		});
		lv_thu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				draw();
			}
		});
	}

	public void draw() {
		String url_thu = (new DrawChart()).drawrPieChar(lst_thu, "Khoản Thu");
		String url_chi = (new DrawChart()).drawrPieChar(lst_chi, "Khoản Chi");
		// Intent myIntent = new Intent(getActivity(),
		// ChartReportActivity.class);
		// myIntent.putExtra("url_thu", url_thu);
		// myIntent.putExtra("url_chi", url_chi);
		// startActivity(myIntent);
		Fragment fragment = new Chart_Fragment(url_thu, url_chi);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		// update selected item and title, then close the drawer
		getActivity().getActionBar().setTitle("Cán cân");

	}
}
