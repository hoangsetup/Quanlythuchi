package com.truong.mobile.quanlythuchi.units;

import java.io.Serializable;

public class Hangmuc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String sTenhangmuc;
	private int iLoaihangmuc;
	private String sGhichu;

	public Hangmuc() {
	}

	public Hangmuc(int id, String sTenhangmuc, int iLoaihangmuc,
			String sGhichu) {
		super();
		this.id = id;
		this.sTenhangmuc = sTenhangmuc;
		this.iLoaihangmuc = iLoaihangmuc;
		this.sGhichu = sGhichu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getsTenhangmuc() {
		return sTenhangmuc;
	}

	public void setsTenhangmuc(String sTenhangmuc) {
		this.sTenhangmuc = sTenhangmuc;
	}

	public int getiLoaihangmuc() {
		return iLoaihangmuc;
	}

	public void setiLoaihangmuc(int iLoaihangmuc) {
		this.iLoaihangmuc = iLoaihangmuc;
	}

	public String getsGhichu() {
		return sGhichu;
	}

	public void setsGhichu(String sGhichu) {
		this.sGhichu = sGhichu;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getsTenhangmuc();
	}

}
