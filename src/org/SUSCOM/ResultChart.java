package org.SUSCOM;


import java.util.Random;

import javax.swing.JFrame;



import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ResultChart extends JFrame{

	private static final int COUNT = 700000;

	XYDataset ScatterPlot() {

		int cols = 20;
		int rows = 20;

		double x = 0;
		double y = 0;
		double[][] values = new double[cols][rows];
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Random");
		Random rand = new Random();
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				x = rand.nextGaussian();
				y = rand.nextGaussian();
				series.add(x, y);
			}
		}
		xySeriesCollection.addSeries(series);
		return xySeriesCollection;
	}
}
