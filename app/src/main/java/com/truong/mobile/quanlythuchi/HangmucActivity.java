package com.truong.mobile.quanlythuchi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.truong.mobile.quanlythuchi.sqlitehelper.Mysqlhelper;
import com.truong.mobile.quanlythuchi.units.Hangmuc;

public class HangmucActivity extends Activity {
	private EditText txt_Tenhangmuc, txt_Ghichu;
	private String sTenhangmuc, sGhichu;
	private Button btn_luu, btn_huy;
	private int iLoaihangmuc = 0; // 0- chi, 1 - thu
	private Hangmuc hm_tmp = new Hangmuc();
	private boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_hangmuc);
		iLoaihangmuc = this.getIntent().getIntExtra("iLoaihangmuc", 0);
		isEdit = this.getIntent().getBooleanExtra("isEdit", false);

		//this.getActionBar().setSubtitle("FITHOU-truong.mobile Traing");
		// Toast.makeText(HangmucActivity.this, iLoaihangmuc + "",
		// Toast.LENGTH_SHORT).show();
		String title = (iLoaihangmuc == 0) ? "Hạng mục chi" : "Hạng mục thu";
		// isEdit = ()
		this.setTitle(title);
		getWidgetControl();
		if (isEdit) {
			hm_tmp = (Hangmuc) this.getIntent().getSerializableExtra("Hangmuc");
			initEdit();
		}
		registryEvent();
	}

	public void getWidgetControl() {
		txt_Tenhangmuc = (EditText) findViewById(R.id.editText_tenhangmuc);
		txt_Ghichu = (EditText) findViewById(R.id.editText_ghichu);
		btn_huy = (Button) findViewById(R.id.button_huybohangmuc);
		btn_luu = (Button) findViewById(R.id.button_luuhangmuc);
	}

	public void registryEvent() {
		btn_huy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_luu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isEdit) {
					if (validateData()) {
						hm_tmp.setsTenhangmuc(sTenhangmuc);
						hm_tmp.setsGhichu(sGhichu);
						hm_tmp.setiLoaihangmuc(iLoaihangmuc);
						Mysqlhelper sql = new Mysqlhelper(HangmucActivity.this,
								1);
						try{
							sql.updateHangmuc(hm_tmp);
						}catch(Exception ex){
							
						}
						sendBack(117);
					}
				} else {
					// insert
					if (validateData()) {
						hm_tmp.setsTenhangmuc(sTenhangmuc);
						hm_tmp.setsGhichu(sGhichu);
						hm_tmp.setiLoaihangmuc(iLoaihangmuc);
						Mysqlhelper sql = new Mysqlhelper(HangmucActivity.this,
								1);
						try{
							sql.insertHangmuc(hm_tmp);
						}catch(Exception ex){
							
						}
						sendBack(117);
					}
				}
			}
		});
	}

	public void initEdit() {
		txt_Tenhangmuc.setText(hm_tmp.getsTenhangmuc());
		txt_Ghichu.setText(hm_tmp.getsGhichu());
	}

	public boolean validateData() {
		sTenhangmuc = txt_Tenhangmuc.getText() + "";
		sGhichu = txt_Ghichu.getText() + "";
		if (sTenhangmuc.isEmpty()) {
			return false;
		}
		return true;
	}

	public void sendBack(int code) {
		Intent mIntent = this.getIntent();
		setResult(code, mIntent);
		finish();
	}
}
