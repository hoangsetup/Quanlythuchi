package com.truong.mobile.quanlythuchi.units;

import java.util.HashMap;

import com.truong.mobile.quanlythuchi.R;

public class Variable {
	public static float fSum = 0;
	
	
	public static String[] TITLE = { "Ghi chép", "Nhóm giao dịch", "Báo cáo",
			"Tài khoản", "Tỉ giá ngoại tệ" };
	public static int[] ICON = { R.drawable.ghichep, R.drawable.nhomgd,
			R.drawable.baocao, R.drawable.wallet, R.drawable.ic_tygia };

	// Loại tiền tệ
	public static String[] DONVI_LOAITIENTE = { "VND", "USD", "JPY", "EUR",
			"CAD", "AUD" };
	// public static String[] MOTA_LOAITIENTE = { "Việt Nam Đồng", "Đô La Mỹ" };

	// Loại tài khoản
	public static String[] LOAITAIKHOAN = { "Tiền mặt", "Thẻ ngân hàng" };

	public static HashMap<String, Integer> FLAG = new HashMap<String, Integer>();
	public static HashMap<String, Float> TYGIA = new HashMap<String, Float>();
	static {
		FLAG.put("AUD", R.drawable.flag_aud);
		FLAG.put("CAD", R.drawable.flag_cad);
		FLAG.put("EUR", R.drawable.flag_eur);
		FLAG.put("GBP", R.drawable.flag_gbp);
		FLAG.put("GOD", R.drawable.flag_gold);
		FLAG.put("JPY", R.drawable.flag_jpy);
		FLAG.put("SGD", R.drawable.flag_sgd);
		FLAG.put("USD", R.drawable.flag_usd);

		TYGIA.put("AUD", (float) 18655);
		TYGIA.put("CAD", (float) 19204);
		TYGIA.put("EUR", (float) 27122);
		TYGIA.put("GBP", (float) 34667);
		TYGIA.put("GOD", (float) 3646000);
		TYGIA.put("JPY", (float) 195.41);
		TYGIA.put("SGD", (float) 16827);
		TYGIA.put("USD", (float) 21258);
		TYGIA.put("VND", (float) 1);
	}

}
