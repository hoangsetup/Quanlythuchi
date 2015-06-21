package com.truong.mobile.quanlythuchi.units;

public class Tygia {
	private String Url_img;
	private String Name;
	private String Sell;

	public Tygia() {
		this.Name = "";
		this.Url_img = "";
		this.Sell = "";
	}

	public String getUrl_img() {
		return Url_img;
	}

	public void setUrl_img(String url_img) {
		Url_img = "http://www.acb.com.vn/tygia/"+url_img;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSell() {
		return Sell;
	}

	public void setSell(String sell) {
		Sell = sell;
	}

}
