package com.truong.mobile.quanlythuchi.adapters;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.units.ItemMenu;

public class MenuAdapter extends BaseAdapter {
	private Context mContext;
	private Vector<ItemMenu> mVector = new Vector<ItemMenu>();

	public MenuAdapter(Context ctx, Vector<ItemMenu> lst) {
		this.mContext = ctx;
		this.mVector = lst;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mVector.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mVector.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mVector.indexOf(getItem(arg0));
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			arg1 = mInflater.inflate(R.layout.list_menu_item, null);
		}

		switch (arg0) {
		case 0:
			arg1.setBackgroundColor(Color.parseColor("#ff8a3c"));
			break;
		case 1:
			arg1.setBackgroundColor(Color.parseColor("#1975a3"));
			break;
		case 2:
			arg1.setBackgroundColor(Color.parseColor("#06bf06"));
			break;
		case 3:
			arg1.setBackgroundColor(Color.parseColor("#b324fd"));
			break;
		case 4:
			arg1.setBackgroundColor(Color.parseColor("#36f4a6"));
			break;
		case 5:
			arg1.setBackgroundColor(Color.parseColor("#19c0f2"));
			break;
		case 6:
			arg1.setBackgroundColor(Color.parseColor("#fcff01"));
			break;
		default:
			break;
		}

		ImageView icon = (ImageView) arg1.findViewById(R.id.icon);
		TextView title = (TextView) arg1.findViewById(R.id.title);
		ItemMenu Item_pos = mVector.get(arg0);
		// Set content
		icon.setImageResource(Item_pos.getIcon());
		title.setText(Item_pos.getTitle());
		return arg1;
	}

}
