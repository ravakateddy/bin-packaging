package com.company.vue;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StackedBarChart extends ApplicationFrame {

    public StackedBarChart(String applicationTitle , String chartTitle, List<Double> x, List<Double> y, List<Double> yExplore) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Itération","Nombre de bins",
                createDataset(x, y, yExplore),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private CategoryTableXYDataset createDataset(List<Double> x, List<Double> y, List<Double> yExplore) {
        CategoryTableXYDataset dataset = new CategoryTableXYDataset( );
        for(int i=0; i<x.size()-1; i++){
            dataset.add(x.get(i), y.get(i), "Solution");
            dataset.add(x.get(i), yExplore.get(i), "Explore");
        }
        return dataset;
    }

    public static void main( String[ ] args ) {
    }
}
