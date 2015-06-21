package com.truong.mobile.quanlythuchi.units;

import java.io.Serializable;

public class Ghichep implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_PK_ghichep;
	private float fSotien;
	private Khoanquy taikhoan;
	private Hangmuc hangmuc;
	private String sThoigian;
	private String sGhichu;

	public Ghichep() {
		this.fSotien = 0;
	}

	public Ghichep(int id, float fSotien, Khoanquy taikhoan, Hangmuc hangmuc,
			String thoigian, String ghichu) {
		super();
		this.id_PK_ghichep = id;
		this.fSotien = fSotien;
		this.taikhoan = taikhoan;
		this.hangmuc = hangmuc;
		this.sThoigian = thoigian;
		this.sGhichu = ghichu;
	}

	public int getId() {
		return id_PK_ghichep;
	}

	public void setId(int id) {
		this.id_PK_ghichep = id;
	}

	public float getfSotien() {
		return fSotien;
	}

	public void setfSotien(float fSotien) {
		this.fSotien = fSotien;
	}

	public Khoanquy getKhoanquy() {
		return taikhoan;
	}

	public void setKhoanquy(Khoanquy taikhoan) {
		this.taikhoan = taikhoan;
	}

	public Hangmuc getHangmuc() {
		return hangmuc;
	}

	public void setHangmuc(Hangmuc hangmuc) {
		this.hangmuc = hangmuc;
	}

	public String getThoigian() {
		return sThoigian;
	}

	public void setThoigian(String thoigian) {
		this.sThoigian = thoigian;
	}

	public String getGhichu() {
		return sGhichu;
	}

	public void setGhichu(String ghichu) {
		this.sGhichu = ghichu;
	}
	

}
