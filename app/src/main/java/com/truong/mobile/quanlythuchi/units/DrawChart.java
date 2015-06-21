package com.truong.mobile.quanlythuchi.units;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;

public class DrawChart {
	Color[] lst_color = { Color.newColor("CACACA"), Color.newColor("DF7417"),
			Color.newColor("951800"), Color.newColor("01A1DB"),
			Color.newColor("66DD2A"), Color.newColor("DDBC2A"),
			Color.newColor("DD2AC8"), Color.newColor("871479"),
			Color.newColor("37D37D"), Color.newColor("126637"),
			Color.newColor("2BAD1F"), Color.newColor("92D62C"),
			Color.newColor("23E0D6"), Color.newColor("E0DA23"),
			Color.newColor("FCAAA9") };

	public static void setUpBeforeClass() throws Exception {
		Logger.global.setLevel(Level.ALL);
	}

	public String drawrPieChar(Vector<Baocao> lst_baocao, String title) {
		ArrayList<Slice> lst_slice = new ArrayList<Slice>();
		// EXAMPLE CODE START
		// Slice s1 = Slice.newSlice(30, Color.newColor("CACACA"), "Safari",
		// "Apple");
		// Slice s2 = Slice.newSlice(30, Color.newColor("DF7417"), "Firefox",
		// "Mozilla");
		// Slice s3 = Slice.newSlice(30, Color.newColor("951800"), "Chrome",
		// "Google");
		// Slice s4 = Slice.newSlice(10, Color.newColor("01A1DB"),
		// "Internet Explorer", "Microsoft");
		int cl = 0;
		for (Baocao i : lst_baocao) {
			Slice s1 = Slice.newSlice((int) i.getfOverview(), lst_color[cl],
					String.format("%.1f", i.getfOverview())+"%", i.getsHangmuc());
			lst_slice.add(s1);
			cl++;
		}
		PieChart chart = GCharts.newPieChart(lst_slice);

		chart.setTitle(title, Color.newColor("000000"), 16);
		chart.setSize(500, 200);
		chart.setThreeD(true);
		String url = chart.toURLString();
		return url;
	}
}
