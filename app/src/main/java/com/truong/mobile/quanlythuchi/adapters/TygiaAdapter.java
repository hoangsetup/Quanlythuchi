package com.truong.mobile.quanlythuchi.adapters;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.units.Tygia;
import com.truong.mobile.quanlythuchi.units.Variable;

public class TygiaAdapter extends ArrayAdapter<Tygia> {
	private Context mContext;
	private Vector<Tygia> mTygias = new Vector<Tygia>();

	public TygiaAdapter(Context context, int resource, Vector<Tygia> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTygias = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_tygia_item, null);
		}
		ImageView img = (ImageView) convertView
				.findViewById(R.id.imageView_tygia);
		TextView name = (TextView) convertView
				.findViewById(R.id.textView_tenngoaite);
		TextView giaban = (TextView) convertView
				.findViewById(R.id.textView_banra);
		Tygia a = mTygias.get(position);
		name.setText(a.getName());
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		float f = 0;
		try{
			f = Float.parseFloat(a.getSell());
		}catch(Exception ex){
			
		}
		try{
			img.setImageResource(Variable.FLAG.get(a.getName()));
		}catch(Exception ex){
			
		}
		giaban.setText(myFormatter.format(f));

		return convertView;
	}

}
