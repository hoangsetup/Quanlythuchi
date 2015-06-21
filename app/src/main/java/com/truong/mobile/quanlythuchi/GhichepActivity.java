package com.truong.mobile.quanlythuchi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Ghichep;
import com.truong.mobile.quanlythuchi.units.Hangmuc;
import com.truong.mobile.quanlythuchi.units.Khoanquy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

@SuppressLint("SimpleDateFormat")
public class GhichepActivity extends Activity {
	private EditText txt_sotien, txt_ghichu, txt_ngay, txt_gio;
	private TextView tv_donvitiente;
	private String sGhichu, sNgay, sGio;
	private float fSotien = 0;
	private Spinner sp_loaighichep;
	private String[] sLoaighichep = { "Chi", "Thu" };
	private ArrayAdapter<String> adp_loaighichep = null;
	private Spinner sp_hangmuc, sp_khoanquy;
	private Vector<Hangmuc> lst_hmChi = new Vector<Hangmuc>();
	private Vector<Hangmuc> lst_hmThu = new Vector<Hangmuc>();
	private ArrayAdapter<Hangmuc> adp_sp_hangmuc = null;
	private Vector<Khoanquy> lst_khoanquyghichep = new Vector<Khoanquy>();
	private ArrayAdapter<Khoanquy> adp_sp_khoanquy = null;
	private Hangmuc hm_temp = new Hangmuc();
	private int iLoaihangmuc = 0;

	private Ghichep ghichep_temp = new Ghichep();

	private Calendar Cal = Calendar.getInstance();

	private Khoanquy kq_tmp = new Khoanquy();

	private Button btn_luu, btn_huy;

	private boolean isEdit = false, isView = false;

	private ImageButton btn_addHangmuc, btn_addkhoanquy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_ghichep);
		getWidGetControl();
		initData();
		registryEvent();

		this.getActionBar().setSubtitle("FITHOU-truong.mobile Traing");
		isEdit = this.getIntent().getBooleanExtra("isEdit", false);
		isView = this.getIntent().getBooleanExtra("isView", false);
		if (isEdit) {
			// init edit
			ghichep_temp = (Ghichep) this.getIntent().getSerializableExtra(
					"ghichep");
			initEdit();
		}
		if (isView) {
			btn_huy.setText("Trở về");
			btn_luu.setVisibility(View.GONE);
			sp_hangmuc.setEnabled(false);
			sp_khoanquy.setEnabled(false);
			btn_addHangmuc.setVisibility(View.GONE);
			btn_addkhoanquy.setVisibility(View.GONE);
		}
	}

	public void getWidGetControl() {
		txt_sotien = (EditText) findViewById(R.id.editText_sotien);
		txt_ghichu = (EditText) findViewById(R.id.editText_ghichughichep);
		txt_ngay = (EditText) findViewById(R.id.editText_ngayghichep);
		txt_gio = (EditText) findViewById(R.id.editText_gioghichep);
		sp_loaighichep = (Spinner) findViewById(R.id.spinner_loaighichep);
		sp_khoanquy = (Spinner) findViewById(R.id.Spinner_khoanquyghichep);
		sp_hangmuc = (Spinner) findViewById(R.id.spinner_hangmuc);
		btn_luu = (Button) findViewById(R.id.button_luughichep);
		btn_huy = (Button) findViewById(R.id.button_huyghichep);
		tv_donvitiente = (TextView) findViewById(R.id.TextView_donvitiente);

		btn_addHangmuc = (ImageButton) findViewById(R.id.imageButton_addHangmuc);
		btn_addkhoanquy = (ImageButton) findViewById(R.id.imageButton_addkhoanquy);
	}

	public void initData() {
		//
		Vector<Hangmuc> tmp = new Vector<Hangmuc>();
		Mysqlhelper mysqlhelper = new Mysqlhelper(GhichepActivity.this, 1);
		tmp = mysqlhelper.getAllHangmuc();
		for (Hangmuc i : tmp) {
			if (i.getiLoaihangmuc() == 0) {
				lst_hmChi.add(i);
			} else {
				lst_hmThu.add(i);
			}
		}
		adp_loaighichep = new ArrayAdapter<String>(GhichepActivity.this,
				android.R.layout.simple_expandable_list_item_1,
				this.sLoaighichep);
		sp_loaighichep.setAdapter(adp_loaighichep);

		lst_khoanquyghichep = mysqlhelper.getAllKhoanquy();
		adp_sp_khoanquy = new ArrayAdapter<Khoanquy>(GhichepActivity.this,
				android.R.layout.simple_expandable_list_item_1,
				lst_khoanquyghichep);
		sp_khoanquy.setAdapter(adp_sp_khoanquy);

		// Set date time
		SimpleDateFormat fm = null;
		String simpleTimeFormat12 = "hh:mm a";
		String simpleTimeFormat24 = "HH:mm";
		String simpleDateFormat = "dd/MM/yyyy";
		fm = new SimpleDateFormat(simpleTimeFormat12);
		txt_gio.setText(fm.format(Cal.getTime()));
		fm = new SimpleDateFormat(simpleTimeFormat24);
		txt_gio.setTag(fm.format(Cal.getTime()));
		fm = new SimpleDateFormat(simpleDateFormat);
		txt_ngay.setText(fm.format(Cal.getTime()));
	}

	//
	public void loadDataSpinerHangmuc(int type) { // 0- chi, 1- thu
		if (type == 0) {
			adp_sp_hangmuc = new ArrayAdapter<Hangmuc>(GhichepActivity.this,
					android.R.layout.simple_expandable_list_item_1, lst_hmChi);
		} else if (type == 1) {
			adp_sp_hangmuc = new ArrayAdapter<Hangmuc>(GhichepActivity.this,
					android.R.layout.simple_expandable_list_item_1, lst_hmThu);
		}
		sp_hangmuc.setAdapter(adp_sp_hangmuc);
	}

	public void registryEvent() {
		sp_loaighichep.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				iLoaihangmuc = arg2;
				if (arg2 == 0) {
					sp_loaighichep.setBackgroundColor(getResources().getColor(
							android.R.color.holo_blue_dark));
				}else if(arg2 == 1){
					sp_loaighichep.setBackgroundColor(getResources().getColor(
							android.R.color.holo_green_dark));
				}
				((TextView) arg0.getChildAt(0)).setTextSize(30);
				loadDataSpinerHangmuc(iLoaihangmuc);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_hangmuc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (iLoaihangmuc == 0) {
					hm_temp = lst_hmChi.get(arg2);
				} else if (iLoaihangmuc == 1) {
					hm_temp = lst_hmThu.get(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_khoanquy.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				kq_tmp = lst_khoanquyghichep.get(arg2);
				tv_donvitiente.setText(kq_tmp.getsLoaitiente());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		btn_luu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if(TextUtils.isEmpty(txt_sotien.getText())){
					txt_sotien.setError("Không được bỏ trống thông tin!");
				}
				if (TextUtils.isEmpty(hm_temp.getsTenhangmuc())
						|| TextUtils.isEmpty(kq_tmp.getsTenkhoanquy())
						|| !validateData()) {
					Toast.makeText(GhichepActivity.this, "Không đủ thông tin!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//fix 16/dec/2014 - case:GhichepAdd004

				ghichep_temp.setfSotien(fSotien);
				ghichep_temp.setGhichu(sGhichu);
				ghichep_temp.setHangmuc(hm_temp);
				ghichep_temp.setKhoanquy(kq_tmp);
				ghichep_temp.setThoigian(sNgay + " " + sGio);

				Mysqlhelper sql = new Mysqlhelper(GhichepActivity.this, 1);
				try {
					if (isEdit) {
						sql.updateGhichep(ghichep_temp);
					} else {
						sql.insertGhichep(ghichep_temp);
					}
				} catch (Exception ex) {

				}
				sendBack();
			}
		});
		btn_huy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		txt_ngay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Pickdate
				showDataPickerDialog();
			}
		});

		txt_gio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Picktime
				showTimePickerDialog();
			}
		});

		btn_addHangmuc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(GhichepActivity.this,
						com.truong.mobile.quanlythuchi.HangmucActivity.class);
				myIntent.putExtra("iLoaihangmuc", iLoaihangmuc);
				startActivityForResult(myIntent, 116);
			}
		});
		btn_addkhoanquy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(GhichepActivity.this,
						com.truong.mobile.quanlythuchi.TaikhoanActivity.class);
				startActivityForResult(mIntent, 113);
			}
		});
	}

	public void initEdit() {
		if (ghichep_temp.getHangmuc().getiLoaihangmuc() == 0) {
			sp_loaighichep.setSelection(0);
		} else {
			sp_loaighichep.setSelection(1);
		}
		txt_sotien.setText(ghichep_temp.getfSotien() + "");
		tv_donvitiente.setText(ghichep_temp.getKhoanquy().getsLoaitiente());
		Log.d("Test", ghichep_temp.getHangmuc().getsTenhangmuc());
		Log.d("TestTentaikhoan", ghichep_temp.getKhoanquy().getsTenkhoanquy());

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				sp_hangmuc.setSelection(getIndexSpinner(sp_hangmuc,
						ghichep_temp.getHangmuc().getsTenhangmuc()));
				sp_khoanquy.setSelection(getIndexSpinner(sp_khoanquy,
						ghichep_temp.getKhoanquy().getsTenkhoanquy()));
			}
		}, 500);
		txt_ghichu.setText(ghichep_temp.getGhichu());
		String st[] = ghichep_temp.getThoigian().split(" ");
		txt_ngay.setText(st[0]);
		txt_gio.setText(st[1] + " " + st[2]);
	}

	public boolean validateData() {
		try {
			this.fSotien = Float.parseFloat(txt_sotien.getText().toString());
		} catch (Exception e) {			
			return false;
		}
		this.sGhichu = txt_ghichu.getText() + "";
		this.sNgay = txt_ngay.getText() + "";
		this.sGio = txt_gio.getText() + "";
		return true;
	}

	public void sendBack() {
		Intent myIntent = this.getIntent();
		setResult(118, myIntent);
		finish();
	}

	public void showDataPickerDialog() {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				txt_ngay.setText(arg3 + "/" + (arg2 + 1) + "/" + arg1);
				Cal.set(arg1, arg2, arg3);

			}
		};
		String s = txt_ngay.getText().toString();
		String arr_s[] = s.split("/");
		int d = Integer.parseInt(arr_s[0]);
		int m = Integer.parseInt(arr_s[1]) - 1;
		int y = Integer.parseInt(arr_s[2]);
		DatePickerDialog dpd = new DatePickerDialog(GhichepActivity.this,
				callback, y, m, d);
		dpd.setTitle("Ngày giao dịch");
		dpd.show();
	}

	public void showTimePickerDialog() {
		final OnTimeSetListener callback = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				String time = arg1 + ":" + arg2;
				int htemp = arg1;
				if (htemp > 12) {
					htemp -= 12;
				}

				// luu gioi that vao tag
				txt_gio.setTag(time);
				txt_gio.setText(htemp + ":" + arg2 + " "
						+ ((arg1 > 12) ? "PM" : "AM"));
				Cal.set(Calendar.HOUR_OF_DAY, arg1);
				Cal.set(Calendar.MINUTE, arg2);

			}
		};
		String s = txt_gio.getTag() + "";
		String[] StrArr = s.split(":");
		int h = Integer.parseInt(StrArr[0]);
		int m = Integer.parseInt(StrArr[1]);
		TimePickerDialog pic = new TimePickerDialog(this, callback, h, m, true);
		pic.setTitle("Giờ giao dịch");
		pic.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		this.initData();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// sp_hangmuc.setSelection(sp_hangmuc.getCount() - 1);
				// sp_khoanquy.setSelection(sp_khoanquy.getCount() - 1);
			}
		}, 100);
	}

	private int getIndexSpinner(Spinner spinner, String myString) {
		int index = 0;
		// Log.d("Cate", spinner.getCount() + "");
		for (int i = 0; i < spinner.getCount(); i++) {
			// Log.d("Cate", spinner.getItemAtPosition(i).toString().trim());
			if (spinner.getItemAtPosition(i).toString().split(" : ")[0].trim()
					.equalsIgnoreCase(myString.trim())) {
				index = i;
				break;// will stop the loop, kind of break, by
						// making condition false
			}
		}
		return index;
	}

}
