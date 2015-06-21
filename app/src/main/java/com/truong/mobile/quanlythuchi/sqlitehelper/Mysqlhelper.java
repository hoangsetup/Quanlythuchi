package com.truong.mobile.quanlythuchi.sqlitehelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.truong.mobile.quanlythuchi.units.Baocao;
import com.truong.mobile.quanlythuchi.units.Ghichep;
import com.truong.mobile.quanlythuchi.units.Hangmuc;
import com.truong.mobile.quanlythuchi.units.Khoanquy;
import com.truong.mobile.quanlythuchi.units.Variable;

public class Mysqlhelper extends SQLiteOpenHelper {
	private static String DATABASE_NAME = "truong.mobile_qltc";
	private static String CREATE_TABLE_tbl_khoanquy = "CREATE TABLE tbl_khoanquy("
			+ "id_PK_khoanquy INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "sTenkhoanquy VARCHAR NOT NULL,"
			+ "sLoaikhoanquy VARCHAR NOT NULL,"
			+ "sLoaitiente VARCHAR NOT NULL,"
			+ "fSotienbandau FLOAT, sGhichu VARCHAR)";

	private static String CREATE_TABLE_tbl_hangmuc = "CREATE TABLE tbl_hangmuc("
			+ "id_PK_hangmuc INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "sTenhangmuc VARCHAR NOT NULL,"
			+ "sLoaihangmuc INTEGER NOT NULL," + "sGhichu VARCHAR NOT NULL)";

	private static String CREATE_TABLE_tbl_ghichep = "CREATE TABLE tbl_ghichep("
			+ "id_PK_ghichep INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "fSotien FLOAT NOT NULL,"
			+ "id_FK_khoanquy INTEGER NOT NULL,"
			+ "id_FK_hangmuc INTEGER NOT NULL,"
			+ "sThoigian VARCHAR, sGhichu VARCHAR)";

	private static String CREATE_VIEW_chichep_hangmuc = "CREATE VIEW ghichep_hangmuc AS "
			+ "SELECT tbl_ghichep.id_PK_ghichep, tbl_ghichep.fSotien , tbl_ghichep.id_FK_khoanquy,"
			+ "tbl_ghichep.id_FK_hangmuc, tbl_ghichep.sThoigian, tbl_hangmuc.sTenhangmuc,"
			+ "tbl_hangmuc.sLoaihangmuc "
			+ "FROM tbl_ghichep, tbl_hangmuc "
			+ "WHERE tbl_ghichep.id_FK_hangmuc = tbl_hangmuc.id_PK_hangmuc";

	public static String GET_BAOCAO = "SELECT sTenhangmuc, SUM(fSotien) AS fTong, id_FK_khoanquy  FROM ghichep_hangmuc "
			+ "WHERE sLoaihangmuc=? GROUP BY sTenhangmuc";

	public static String GET_TONG_THU_CHI = "SELECT SUM(fSotien) AS fTong "
			+ "FROM ghichep_hangmuc WHERE sLoaihangmuc=?";

	public Mysqlhelper(Context context, int version) {
		super(context, DATABASE_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		db.execSQL("PRAGMA foreign_keys=ON");
		// try {
		// db.execSQL(TRIGGER_ghichep_hangmuc);
		// db.execSQL(TRIGGER_ghichep_khoanquy);
		// Log.d("Create view:::::::", "OKKKKKKK");
		// //db.execSQL(CREATE_VIEW_chichep_hangmuc);
		// } catch (Exception ex) {
		// Log.d("Create view:::::::",ex.toString());
		// }

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(CREATE_TABLE_tbl_khoanquy);
		arg0.execSQL(CREATE_TABLE_tbl_hangmuc);
		arg0.execSQL(CREATE_TABLE_tbl_ghichep);
		try {
			arg0.execSQL(CREATE_VIEW_chichep_hangmuc);
			Log.d("Create view:::::::", "OKKKKKKK");

		} catch (Exception ex) {
			// Log.d("Create view:::::::","FFFFFFFFFF");
		}
		//
		try {
			// arg0.execSQL(TRIGGER_ghichep_hangmuc);
			// arg0.execSQL(TRIGGER_ghichep_khoanquy);
			Log.d("Create view:::::::", "OKKKKKKK2");
			// db.execSQL(CREATE_VIEW_chichep_hangmuc);
		} catch (Exception ex) {
			Log.d("Create trigger:::::::", ex.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS tbl_khoanquy");
		arg0.execSQL("DROP TABLE IF EXISTS tbl_hangmuc");
		arg0.execSQL("DROP TABLE IF EXISTS tbl_ghichep");

	}

	public SQLiteDatabase getDatabase() {
		return this.getWritableDatabase();
	}

	public boolean closeAllDatabasse() {
		try {
			this.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	// tbl_khoanquy
	public long insertKhoanquy(Khoanquy tk) {
		ContentValues vl = new ContentValues();
		vl.put("sTenkhoanquy", tk.getsTenkhoanquy());
		vl.put("sLoaikhoanquy", tk.getsLoaikhoanquy());
		vl.put("sLoaitiente", tk.getsLoaitiente());
		vl.put("fSotienbandau", tk.getfSotienbandau());
		vl.put("sGhichu", tk.getsGhichu());
		SQLiteDatabase db = this.getDatabase();
		long count = db.insert("tbl_khoanquy", null, vl);
		return count;
	}

	//
	public Vector<Khoanquy> getAllKhoanquy() {
		Vector<Khoanquy> tmp = new Vector<Khoanquy>();
		String query = "SELECT * FROM tbl_khoanquy";
		SQLiteDatabase db = this.getDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				Khoanquy a = new Khoanquy();
				a.setId(cursor.getInt(0));
				a.setsTenkhoanquy(cursor.getString(1));
				a.setsLoaikhoanquy(cursor.getString(2));
				a.setsLoaitiente(cursor.getString(3));
				a.setfSotienbandau(cursor.getFloat(4));
				a.setsGhichu(cursor.getString(5));
				// Log.d("Taikhoan", cursor.getString(1));
				a.setfSotienconlai((a.getfSotienbandau() + this.Tongthuchi(a
						.getId_PK_khoanquy())));
				tmp.add(a);
			} while (cursor.moveToNext());
		}
		db.close();
		return tmp;
	}

	public Khoanquy getKhoanquyById(int id) {
		Khoanquy tmp = new Khoanquy();
		SQLiteDatabase db = this.getDatabase();
		String query = "SELECT * FROM tbl_khoanquy WHERE id_PK_khoanquy=?";
		Cursor cursor = db.rawQuery(query, new String[] { id + "" });
		if (cursor.moveToFirst()) {
			tmp.setId(cursor.getInt(0));
			tmp.setsTenkhoanquy(cursor.getString(1));
			tmp.setsLoaikhoanquy(cursor.getString(2));
			tmp.setsLoaitiente(cursor.getString(3));
			tmp.setfSotienbandau(cursor.getFloat(4));
			tmp.setsGhichu(cursor.getString(5));
		}
		db.close();
		return tmp;
	}

	//
	public int updateKhoanquy(Khoanquy tk) {
		ContentValues vl = new ContentValues();
		vl.put("sTenkhoanquy", tk.getsTenkhoanquy());
		vl.put("sLoaikhoanquy", tk.getsLoaikhoanquy());
		vl.put("sLoaitiente", tk.getsLoaitiente());
		vl.put("fSotienbandau", tk.getfSotienbandau());
		vl.put("sGhichu", tk.getsGhichu());
		SQLiteDatabase db = this.getDatabase();
		int count = db.update("tbl_khoanquy", vl, "id_PK_khoanquy=?",
				new String[] { tk.getId_PK_khoanquy() + "" });
		db.close();
		return count;
	}

	//
	public int deleteTaikhoan(Khoanquy kq) {
		SQLiteDatabase db = this.getDatabase();
		int count = db.delete("tbl_khoanquy", "id_PK_khoanquy=?",
				new String[] { kq.getId_PK_khoanquy() + "" });
		db.delete("tbl_ghichep", "id_FK_Khoanquy=?",
				new String[] { kq.getId_PK_khoanquy() + "" });
		db.close();
		return count;
	}

	// public float getSotienconlaiKhoanquy(int id){
	// String qurey
	// ="SELECT SUM(fSotien) AS [fTongchi] FROM tbl_ghichep WHERE id=?";
	// }

	// tbl_hangmuc
	public long insertHangmuc(Hangmuc hm) {
		long count = 0;
		ContentValues vl = new ContentValues();
		vl.put("sTenhangmuc", hm.getsTenhangmuc());
		vl.put("sLoaihangmuc", hm.getiLoaihangmuc());
		vl.put("sGhichu", hm.getsGhichu());
		SQLiteDatabase db = this.getDatabase();
		count = db.insert("tbl_hangmuc", null, vl);
		db.close();
		return count;
	}

	public int updateHangmuc(Hangmuc hm) {
		int count = 0;
		ContentValues vl = new ContentValues();
		vl.put("sTenhangmuc", hm.getsTenhangmuc());
		vl.put("sLoaihangmuc", hm.getiLoaihangmuc());
		vl.put("sGhichu", hm.getsGhichu());
		SQLiteDatabase db = this.getDatabase();
		count = db.update("tbl_hangmuc", vl, "id_PK_hangmuc=?",
				new String[] { hm.getId() + "" });
		db.close();
		return count;
	}

	public int deleteHangmuc(Hangmuc hm) {
		int count = 0;
		SQLiteDatabase db = this.getDatabase();
		count = db.delete("tbl_hangmuc", "id_PK_hangmuc=?",
				new String[] { hm.getId() + "" });
		db.delete("tbl_ghichep", "id_FK_hangmuc=?", new String[] { hm.getId()
				+ "" });
		db.close();
		return count;
	}

	public Vector<Hangmuc> getAllHangmuc() {
		Vector<Hangmuc> tmp = new Vector<Hangmuc>();
		String query = "SELECT * FROM tbl_hangmuc";
		SQLiteDatabase db = this.getDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				Hangmuc a = new Hangmuc();
				a.setId(cursor.getInt(0));
				a.setsTenhangmuc(cursor.getString(1));
				a.setiLoaihangmuc(cursor.getInt(2));
				a.setsGhichu(cursor.getString(3));
				tmp.add(a);
			} while (cursor.moveToNext());
		}
		db.close();
		return tmp;
	}

	public Hangmuc getHangmucById(int id) {
		Hangmuc tmp = new Hangmuc();
		String query = "SELECT * FROM tbl_hangmuc WHERE id_PK_hangmuc=?";
		SQLiteDatabase db = this.getDatabase();
		Cursor cursor = db.rawQuery(query, new String[] { id + "" });
		if (cursor.moveToFirst()) {
			tmp.setId(cursor.getInt(0));
			tmp.setsTenhangmuc(cursor.getString(1));
			tmp.setiLoaihangmuc(cursor.getInt(2));
			tmp.setsGhichu(cursor.getString(3));
		}
		db.close();
		return tmp;
	}

	// tbl_ghichep
	public long insertGhichep(Ghichep gc) {
		long count = 0;
		ContentValues vl = new ContentValues();
		vl.put("fSotien", gc.getfSotien());
		vl.put("id_FK_khoanquy", gc.getKhoanquy().getId_PK_khoanquy());
		vl.put("id_FK_hangmuc", gc.getHangmuc().getId());
		vl.put("sThoigian", gc.getThoigian());
		vl.put("sGhichu", gc.getGhichu());
		SQLiteDatabase db = this.getDatabase();
		db.insert("tbl_ghichep", null, vl);
		db.close();
		return count;
	}

	public int updateGhichep(Ghichep gc) {
		int count = 0;
		ContentValues vl = new ContentValues();
		vl.put("fSotien", gc.getfSotien());
		vl.put("id_FK_khoanquy", gc.getKhoanquy().getId_PK_khoanquy());
		vl.put("id_FK_hangmuc", gc.getHangmuc().getId());
		vl.put("sThoigian", gc.getThoigian());
		vl.put("sGhichu", gc.getGhichu());
		SQLiteDatabase db = this.getDatabase();
		db.update("tbl_ghichep", vl, "id_PK_ghichep=?",
				new String[] { gc.getId() + "" });
		db.close();
		return count;
	}

	public int deleteGhichep(int id) {
		int count = 0;
		SQLiteDatabase db = this.getDatabase();
		count = db.delete("tbl_ghichep", "id_PK_ghichep=?", new String[] { id
				+ "" });
		db.close();
		return count;
	}

	public Vector<Ghichep> getAllGhichep() {
		Vector<Ghichep> tmp = new Vector<Ghichep>();
		String query = "SELECT * FROM tbl_ghichep order by id_PK_ghichep DESC";
		SQLiteDatabase db = this.getDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				Ghichep a = new Ghichep();
				a.setId(cursor.getInt(0));
				a.setfSotien(cursor.getFloat(1));
				a.setKhoanquy(this.getKhoanquyById(cursor.getInt(2)));
				a.setHangmuc(this.getHangmucById(cursor.getInt(3)));
				a.setThoigian(cursor.getString(4));
				tmp.add(a);
			} while (cursor.moveToNext());
		}
		db.close();
		return tmp;
	}

	public float Tongthuchi(int id) {
		float fSum = 0;
		Vector<Ghichep> gc_temp = this.getAllGhichep();
		for (Ghichep ghichep : gc_temp) {
			if (ghichep.getKhoanquy().getId_PK_khoanquy() == id
					&& ghichep.getHangmuc().getiLoaihangmuc() == 0) {
				fSum -= ghichep.getfSotien();
			} else if (ghichep.getKhoanquy().getId_PK_khoanquy() == id
					&& ghichep.getHangmuc().getiLoaihangmuc() == 1) {
				fSum += ghichep.getfSotien();
			}
		}
		return fSum;
	}

	public Vector<Baocao> getBaocao_(int sLoaihangmuc) {
		Variable.fSum = 0;
		Vector<Baocao> tmp = new Vector<Baocao>();
		SQLiteDatabase db = this.getDatabase();
		Cursor cursor = db.rawQuery(GET_BAOCAO, new String[] { sLoaihangmuc
				+ "" });
		if (cursor.moveToFirst()) {
			do {
				Variable.fSum += cursor.getFloat(1);
			} while (cursor.moveToNext());
		}
		//
		if (cursor.moveToFirst()) {
			do {
				Baocao a = new Baocao();
				// String loaitiente = this.getKhoanquyById(cursor.getInt(2))
				// .getsLoaitiente();
				a.setfSotien(cursor.getFloat(1));
				a.setsHangmuc(cursor.getString(0));
				a.setfOverview(Variable.fSum);
				tmp.add(a);
				// Log.d("CGIIIIIIII2", cursor.getString(6)+"");
			} while (cursor.moveToNext());
		}
		db.close();
		return tmp;
	}

	public Vector<Baocao> getBaocao2(int type) {
		Vector<Baocao> tmp = new Vector<Baocao>();
		Vector<Ghichep> gc_tmp = this.getAllGhichep();
		HashMap<String, Float> hashmap_tmp = new HashMap<String, Float>();
		// float sum_all = 0;
		Variable.fSum = 0;
		for (Ghichep i : gc_tmp) {
			if (i.getHangmuc().getiLoaihangmuc() == type) {
				float tien = i.getfSotien()
						* Variable.TYGIA.get(i.getKhoanquy().getsLoaitiente());
				Log.d("-------", i.getHangmuc().getsTenhangmuc() + ":" + tien);
				Variable.fSum += tien;
				float exist = 0;
				try{
					exist = hashmap_tmp.get(i.getHangmuc().getsTenhangmuc());
				}catch(Exception ex){
					
				}
				
				hashmap_tmp.put(i.getHangmuc().getsTenhangmuc(),
						(exist == 0) ? tien : exist + tien);
			}
		}

		for (Map.Entry<String, Float> i : hashmap_tmp.entrySet()) {
			Baocao a = new Baocao();
			a.setfSotien(i.getValue());
			a.setsHangmuc(i.getKey());
			a.setfOverview(Variable.fSum);
			tmp.add(a);
		}
		return tmp;
	}
}
