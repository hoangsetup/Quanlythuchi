package com.truong.mobile.quanlythuchi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Khoanquy;
import com.truong.mobile.quanlythuchi.units.Variable;

import java.util.Arrays;

public class TaikhoanActivity extends Activity {
	private Khoanquy tk_tmp;
	private TextView txt_tentaikhoan, txt_sotienbandau, txt_ghichu;
	private Button btn_xong, btn_huy;
	private Spinner sp_loaitaikhoan, sp_loaitiente;
	private String str_loaitaikhoan, str_loaitiente, str_tentaikhoan,
			str_ghichu;
	private float f_sotienbandau = 0;
	private boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_taikhoan);
		this.setTitle("Thông tin tài khoản");

		getWidgetControl();
		initData();
		registryEvents();
		//this.getActionBar().setSubtitle("FITHOU-truong.mobile Traing");
		//
		isEdit = this.getIntent().getBooleanExtra("isEdit", false);
		if (isEdit) {
			tk_tmp = (Khoanquy) this.getIntent().getSerializableExtra("tk");
			// Toast.makeText(this, tk_tmp.getTentaikhoan(), Toast.LENGTH_SHORT)
			// .show();
			// Toast.makeText(TaikhoanActivity.this, tk_tmp.getId()+"",
			// Toast.LENGTH_SHORT).show();
			initEdit();
		}
		//
	}

	public void getWidgetControl() {
		txt_tentaikhoan = (TextView) findViewById(R.id.editText_tentaikhoan);
		txt_sotienbandau = (TextView) findViewById(R.id.editText_sotienbandau);
		txt_ghichu = (TextView) findViewById(R.id.editText_ghichu);
		btn_xong = (Button) findViewById(R.id.btn_xong);
		btn_huy = (Button) findViewById(R.id.btn_huy);
		sp_loaitaikhoan = (Spinner) findViewById(R.id.spinner_loaitaikhoan);
		sp_loaitiente = (Spinner) findViewById(R.id.spinner_loaitiente);
	}

	public void initData() {
		ArrayAdapter<String> adp_loaitaikhoan = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,
				Variable.LOAITAIKHOAN);
		// adp_loaitaikhoan
		// .setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		sp_loaitaikhoan.setAdapter(adp_loaitaikhoan);
		ArrayAdapter<String> adp_loaitiente = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,
				Variable.DONVI_LOAITIENTE);
		// adp_loaitiente
		// .setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		sp_loaitiente.setAdapter(adp_loaitiente);
	}

	public void registryEvents() {
		btn_huy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_xong.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!validateData()) {
					Toast.makeText(TaikhoanActivity.this,
							"Thông tin không được bỏ trống", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (isEdit) {
					try{
						updateKQ();
					}catch(Exception ex){
						
					}
				} else {
					try{
						insertKQ();
					}catch(Exception ex){
						
					}
				}
				sendBack(111);
			}
		});

		sp_loaitaikhoan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				str_loaitaikhoan = Variable.LOAITAIKHOAN[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		sp_loaitiente.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				str_loaitiente = Variable.DONVI_LOAITIENTE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	public boolean validateData() {
		str_tentaikhoan = txt_tentaikhoan.getText().toString();
		try {
			f_sotienbandau = Float.parseFloat(txt_sotienbandau.getText()
					.toString());
		} catch (Exception ex) {

		}
		str_ghichu = txt_ghichu.getText() + "";
		if (str_tentaikhoan.isEmpty()) {
			return false;
		}
		return true;
	}

	public void insertKQ() {
		tk_tmp = new Khoanquy();
		tk_tmp.setsTenkhoanquy(str_tentaikhoan);
		tk_tmp.setsLoaikhoanquy(str_loaitaikhoan);
		tk_tmp.setsLoaitiente(str_loaitiente);
		tk_tmp.setfSotienbandau(f_sotienbandau);
		tk_tmp.setsGhichu(str_ghichu);

		Mysqlhelper sql = new Mysqlhelper(this, 1);
		sql.insertKhoanquy(tk_tmp);
	}

	public void updateKQ() {
		// tk_tmp = new Taikhoan();
		tk_tmp.setsTenkhoanquy(str_tentaikhoan);
		tk_tmp.setsLoaikhoanquy(str_loaitaikhoan);
		tk_tmp.setsLoaitiente(str_loaitiente);
		tk_tmp.setfSotienbandau(f_sotienbandau);
		tk_tmp.setsGhichu(str_ghichu);
		Mysqlhelper sql = new Mysqlhelper(this, 1);
		sql.updateKhoanquy(tk_tmp);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initEdit() {
		txt_tentaikhoan.setText(tk_tmp.getsTenkhoanquy());
		txt_sotienbandau.setText(tk_tmp.getfSotienbandau() + "");
		txt_ghichu.setText(tk_tmp.getsGhichu());
		ArrayAdapter tadp = (ArrayAdapter) sp_loaitaikhoan.getAdapter();
		sp_loaitaikhoan
				.setSelection(tadp.getPosition(tk_tmp.getsLoaikhoanquy()));
		sp_loaitiente.setSelection(Arrays.asList(Variable.DONVI_LOAITIENTE)
				.indexOf(tk_tmp.getsLoaitiente()));
		// ArrayAdapter tadp2 = (ArrayAdapter<String>)
		// sp_loaitiente.getAdapter();
		// sp_loaitiente.setSelection(tadp2.getPosition(tk_tmp.getLoaitiente()));
	}

	public void sendBack(int code) {
		Intent mIntent = this.getIntent();
		setResult(code, mIntent);
		finish();
	}
}
