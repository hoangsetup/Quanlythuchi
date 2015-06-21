package com.truong.mobile.quanlythuchi.fragment;

import java.util.Vector;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.truong.mobile.quanlythuchi.R;
import com.truong.mobile.quanlythuchi.TaikhoanActivity;
import com.truong.mobile.quanlythuchi.adapters.TaikhoanAdapter;
import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Khoanquy;

public class Taikhoan_Fragment extends Fragment {
	private Button btn_themtaikhoan;
	// private TextView txt_tongtien;
	private ListView lv_taikhoan;
	private TaikhoanAdapter adp = null;
	private Vector<Khoanquy> mTaikhoans = new Vector<Khoanquy>();
	private Mysqlhelper sqlite;
	private Khoanquy tk_tmp = new Khoanquy();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		sqlite = new Mysqlhelper(getActivity(), 1);
		// Taikhoan tk = new Taikhoan();
		// tk.setTentaikhoan("HoangWallet3");
		// tk.setLoaitaikhoan("Tiền Mặt");
		// tk.setLoaitiente("đ");
		// tk.setSotienbandau(2000000);
		// tk.setGhichu("Không có ghi chú");
		View rootView = inflater.inflate(R.layout.fragment_taikhoan, container,
				false);
		// sqlite.insertTaikhoan(tk);
		lv_taikhoan = (ListView) rootView.findViewById(R.id.lv_taikhoan);
		btn_themtaikhoan = (Button) rootView.findViewById(R.id.btn_addtaikhoan);
		// txt_tongtien = (TextView) rootView.findViewById(R.id.txt_tongtien);
		// mTaikhoans = sqlite.getAllTaikhoan();
		// adp = new TaikhoanAdapter(getActivity(), R.layout.list_taikhoan_item,
		// mTaikhoans);
		// lv_taikhoan.setAdapter(adp);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				reLoadData();
			}
		}, 300);
		// reLoadData();
		// float s = 0;
		// for (Taikhoan i : mTaikhoans) {
		// s += i.getSotienconlai();
		// }
		// txt_tongtien.setText("Tổng còn: " + s + "");

		//
		btn_themtaikhoan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(getActivity(),
						TaikhoanActivity.class);
				startActivityForResult(mIntent, 113);
			}
		});
		lv_taikhoan
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						tk_tmp = mTaikhoans.get(arg2);
						return false;
					}
				});
		lv_taikhoan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tk_tmp = mTaikhoans.get(arg2);
				AlertDialog.Builder dialog = new Builder(getActivity());
				dialog.setTitle(tk_tmp.getsTenkhoanquy());
				String infor = "Số tiền: " + tk_tmp.getfSotienconlai()
						+ tk_tmp.getsLoaitiente() + "\nLoại tài khoản: "
						+ tk_tmp.getsLoaikhoanquy() + "\n" + "Ghi chú: "
						+ tk_tmp.getsGhichu();
				dialog.setMessage(infor);
				dialog.create().show();

			}
		});

		registerForContextMenu(lv_taikhoan);
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getActivity().getMenuInflater().inflate(R.menu.context_taikhoan, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.act_sua:
			// Toast.makeText(getActivity(), tk_tmp.getTentaikhoan(),
			// Toast.LENGTH_SHORT).show();
			Intent mIntent = new Intent(getActivity(), TaikhoanActivity.class);
			mIntent.putExtra("isEdit", true);
			mIntent.putExtra("tk", tk_tmp);
			// Toast.makeText(getActivity(), tk_tmp.getId()+"",
			// Toast.LENGTH_SHORT).show();
			startActivityForResult(mIntent, 114);
			break;
		case R.id.act_xoa:
			deleteTK();
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	public void reLoadData() {
		mTaikhoans.clear();
		mTaikhoans = sqlite.getAllKhoanquy();
		for (Khoanquy i : mTaikhoans) {
			i.setfSotienconlai(i.getfSotienbandau()
					+ sqlite.Tongthuchi(i.getId_PK_khoanquy()));
		}

		adp = new TaikhoanAdapter(getActivity(), R.layout.list_taikhoan_item,
				mTaikhoans);
		lv_taikhoan.setAdapter(adp);
		// float s = 0;
		// for (Khoanquy i : mTaikhoans) {
		// s += i.getSotienconlai();
		// }
		// txt_tongtien.setText("Tổng còn: " + s + "");
	}

	public void deleteTK() {
		AlertDialog.Builder ab = new Builder(getActivity());
		ab.setTitle("Bạn muốn xóa?");
		ab.setMessage("Tài khoản: " + tk_tmp.getsTenkhoanquy() + "\n"
				+ "Số tiền còn lại: " + tk_tmp.getfSotienconlai()
				+ tk_tmp.getsLoaitiente());
		ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), tk_tmp.getId()+"",
				// Toast.LENGTH_SHORT).show();
				if (sqlite.deleteTaikhoan(tk_tmp) > 0) {
					Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getActivity(), "Không xóa được",
							Toast.LENGTH_SHORT).show();
				}
				reLoadData();
			}
		});
		ab.setNegativeButton("Không", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
		ab.create().show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 111) {
			reLoadData();
		}
	}

}
