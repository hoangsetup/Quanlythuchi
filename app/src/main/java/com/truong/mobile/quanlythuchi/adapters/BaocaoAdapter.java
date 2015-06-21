package com.truong.mobile.quanlythuchi.adapters;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.units.Baocao;

@SuppressWarnings("rawtypes")
public class BaocaoAdapter extends ArrayAdapter {
	private Context ctx;
	private Vector<Baocao> lst_baocao = new Vector<Baocao>();

	@SuppressWarnings("unchecked")
	public BaocaoAdapter(Context context, int resource, Vector<Baocao> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.lst_baocao = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_baocao_item, null);
		}
		TextView tv_hangmuc = (TextView) convertView
				.findViewById(R.id.TextView_hangmuc);
		ProgressBar bar = (ProgressBar) convertView
				.findViewById(R.id.progressBar_overview);
		TextView tv_tongtien = (TextView) convertView
				.findViewById(R.id.TextView_tongtien_hangmuc);
		Baocao a = lst_baocao.get(position);
		tv_hangmuc.setText(a.getsHangmuc());
		// tv_tongtien.setText(a.getfSotien() + "");
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		// String output = myFormatter.format(value);
		// System.out.println(output);
		tv_tongtien.setText(myFormatter.format(a.getfSotien()));
		bar.setProgress((int) a.getfOverview());

		return convertView;
	}

}
