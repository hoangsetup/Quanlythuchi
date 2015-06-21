package com.truong.mobile.quanlythuchi.adapters;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.units.Ghichep;

@SuppressWarnings("rawtypes")
public class GhichepAdapter extends ArrayAdapter {
	private Context ctx;
	private Vector<Ghichep> lst_ghichep = new Vector<Ghichep>();

	@SuppressWarnings("unchecked")
	public GhichepAdapter(Context context, int resource, Vector<Ghichep> objects) {
		super(context, resource, objects);
		this.ctx = context;
		this.lst_ghichep = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					com.truong.mobile.quanlythuchi.R.layout.list_ghichep_item, null);
		}

		TextView txt_tieude = (TextView) convertView
				.findViewById(com.truong.mobile.quanlythuchi.R.id.textView_tieude);
		TextView txt_sotien = (TextView) convertView
				.findViewById(com.truong.mobile.quanlythuchi.R.id.textView_sotien);
		TextView txt_ngay = (TextView) convertView
				.findViewById(R.id.textView_ngay);
		TextView txt_khoanquy = (TextView) convertView
				.findViewById(R.id.textView_khoanquy);
		ImageView img_loaigd = (ImageView) convertView
				.findViewById(R.id.imageView_loaigd);
		Ghichep a = lst_ghichep.get(position);
		String title = "";
		if (a.getHangmuc().getiLoaihangmuc() == 0) {
			title = "Chi:" + a.getHangmuc().getsTenhangmuc();
			txt_tieude.setTextColor(Color.RED);
			img_loaigd.setImageResource(R.drawable.ic_chi);
		} else {
			title = "Thu:" + a.getHangmuc().getsTenhangmuc();
			txt_tieude.setTextColor(Color.GREEN);
			img_loaigd.setImageResource(R.drawable.ic_thu);
		}
		txt_tieude.setText(title);
		DecimalFormat myFormat = new DecimalFormat("#,###");
		txt_sotien.setText(myFormat.format(a.getfSotien()) + ""
				+ a.getKhoanquy().getsLoaitiente());
		txt_ngay.setText(a.getThoigian().split(" ")[0]);
		txt_khoanquy.setText(a.getKhoanquy().getsTenkhoanquy());

		return convertView;
	}
}
