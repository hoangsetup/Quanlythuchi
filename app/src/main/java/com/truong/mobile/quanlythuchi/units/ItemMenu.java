package com.truong.mobile.quanlythuchi.units;

public class ItemMenu {
	private String Title;
	private int Icon;

	public ItemMenu(String title, int icon) {
		this.Title = title;
		this.Icon = icon;
	}

	public ItemMenu() {

	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getIcon() {
		return Icon;
	}

	public void setIcon(int icon) {
		Icon = icon;
	}
	
}
