package com.truong.mobile.quanlythuchi.adapters;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.units.Khoanquy;

public class TaikhoanAdapter extends ArrayAdapter<Khoanquy> {
	private Context mContext;
	private Vector<Khoanquy> mTaikhoans = new Vector<Khoanquy>();

	public TaikhoanAdapter(Context context, int resource,
			Vector<Khoanquy> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTaikhoans = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_taikhoan_item, null);
		}
		TextView txt_tentaikhoan = (TextView) convertView
				.findViewById(R.id.txt_tentaikhoan);
		TextView txt_sotienconlai = (TextView) convertView
				.findViewById(R.id.txt_sotienconlai);

		Khoanquy tmp = this.mTaikhoans.get(position);
		txt_tentaikhoan.setText(tmp.getsTenkhoanquy());
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		txt_sotienconlai.setText(myFormatter.format(tmp.getfSotienconlai())
				+ tmp.getsLoaitiente());
		return convertView;
	}

}
