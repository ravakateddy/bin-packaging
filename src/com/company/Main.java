package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;
import com.company.order.DecreasingOrderStrategy;
import com.company.order.SimpleOrderStrategy;
import com.company.vue.StackedBarChart;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static class DataModel {
        public final double[] weights = {48, 30, 19, 36, 36, 27, 42, 42, 36, 24, 30};
        public final int numItems = weights.length;
        public final int numBins = weights.length;
        public final int binCapacity = 100;
    }

    public static void main(String[] args) {
    }

}
