package com.truong.mobile.quanlythuchi.units;

import java.io.Serializable;

public class Khoanquy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_PK_khoanquy;
	private String sTenkhoanquy;
	private String sLoaikhoanquy;
	private String sLoaitiente;
	private float fSotienbandau;
	private float fSotienconlai;
	private String sGhichu;

	public Khoanquy() {
		this.fSotienbandau = 0;
		this.fSotienconlai = 0;
	}

	public float getfSotienconlai() {
		return fSotienconlai;
	}

	public void setfSotienconlai(float fSotienconlai) {
		this.fSotienconlai = fSotienconlai;
	}

	public void setId_PK_khoanquy(int id_PK_khoanquy) {
		this.id_PK_khoanquy = id_PK_khoanquy;
	}

	public int getId_PK_khoanquy() {
		return id_PK_khoanquy;
	}

	public void setId(int id) {
		this.id_PK_khoanquy = id;
	}

	public String getsTenkhoanquy() {
		return sTenkhoanquy;
	}

	public void setsTenkhoanquy(String sTenkhoanquy) {
		this.sTenkhoanquy = sTenkhoanquy;
	}

	public String getsLoaikhoanquy() {
		return sLoaikhoanquy;
	}

	public void setsLoaikhoanquy(String sLoaikhoanquy) {
		this.sLoaikhoanquy = sLoaikhoanquy;
	}

	public String getsLoaitiente() {
		return sLoaitiente;
	}

	public void setsLoaitiente(String sLoaitiente) {
		this.sLoaitiente = sLoaitiente;
	}

	public float getfSotienbandau() {
		return fSotienbandau;
	}

	public void setfSotienbandau(float fSotienbandau) {
		this.fSotienbandau = fSotienbandau;
	}

	public String getsGhichu() {
		return sGhichu;
	}

	public void setsGhichu(String sGhichu) {
		this.sGhichu = sGhichu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getsTenkhoanquy() + " : " + this.fSotienconlai
				+ this.sLoaitiente;
	}

}
