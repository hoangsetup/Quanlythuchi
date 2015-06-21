package com.truong.mobile.quanlythuchi.units;

public class Baocao {
	private String sHangmuc;
	private float fSotien;
	private float fOverview;

	public Baocao() {
		fOverview = 0;
		fSotien = 0;
	}

	public String getsHangmuc() {
		return sHangmuc;
	}

	public void setsHangmuc(String sHangmuc) {
		this.sHangmuc = sHangmuc;
	}

	public float getfSotien() {
		return fSotien;
	}

	public void setfSotien(float fSotien) {
		this.fSotien = fSotien;
	}

	//

	public float getfOverview() {
		return fOverview;
	}

	public void setfOverview(float fSum) {
		try{
			this.fOverview = (this.fSotien / fSum) * 100;
		}catch(Exception ex){
			this.fOverview = 0;
		}
	}
}
