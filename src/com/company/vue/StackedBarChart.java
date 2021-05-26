package com.company.vue;
import java.awt.Color;
import java.util.List;

import com.company.Bin;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
    public class StackedBarChart extends ApplicationFrame {

        CategoryDataset dataset;
        public StackedBarChart(String titel, List<Bin> listBin) {
            super(titel);
            final CategoryDataset dataset = getDatasetFromListBin(listBin);
            final JFreeChart chart = createChart(dataset);

            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 350));
            setContentPane(chartPanel);
        }

        private CategoryDataset getDatasetFromListBin(List<Bin> listBin) {
            double[][] data = new double[10][10];
            for(int i=0; i<10; i++){
                for(int j=0; j<listBin.size(); j++){
                    if(i<listBin.get(j).getListItems().size()){
                        data[i][j] = listBin.get(j).getListItems().get(i).getSize();
                    }

                }
            }
            return DatasetUtilities.createCategoryDataset("Item ", "Bin", data);
        }
        private JFreeChart createChart(CategoryDataset dataset) {
            final JFreeChart chart = ChartFactory.createStackedBarChart("Stacked Bar Chart ", "",
                    "Score", dataset, PlotOrientation.VERTICAL, true, true, false);
            chart.setBackgroundPaint(new Color(249, 231, 236));
            CategoryPlot plot = chart.getCategoryPlot();
            plot.getRenderer().setSeriesPaint(0, new Color(128, 0, 0));
            plot.getRenderer().setSeriesPaint(1, new Color(0, 0, 255));
            return chart;
        }
        public static void main(final String[] args) {

        }
    }

