package com.company;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    static class DataModel {
        public final double[] weights = {48, 30, 19, 36, 36, 27, 42, 42, 36, 24, 30};
        public final int numItems = weights.length;
        public final int numBins = weights.length;
        public final int binCapacity = 100;
    }

    public static void main(String[] args) {
        Service service = new Service();

        FirstFitDecreasing firstFitDecreasing = new FirstFitDecreasing();
        SimpleOrder simpleOrder = new SimpleOrder();

        //extraction des items et de la taille d'un bin à partir du fichier
        ArrayList<Item> listItems = service.extractItemFromFile("src/com/company/data/binpack1d_01.txt");

//        ArrayList<Item> listItems = service.extractItemFromFile("src/com/company/test2.txt");

//        service.setListItemsOrderStrategy(firstFitDecreasing);
        service.setListItemsOrderStrategy(simpleOrder);

        System.out.println(service.getListItems());

//        répartition des items dans les bins
        ArrayList<Bin> listBins = service.fillBins();

        System.out.println(listBins);
        System.out.println("Nombre minimum de bins: " + listBins.size());

        ArrayList<Double> weights = new ArrayList<>();
        listItems.stream().mapToDouble(Item::getSize).forEach(weights::add);
        Object[] obj = weights.toArray();

        double[] weight = new double[obj.length];
        for(int i=0; i < weight.length; i++) {
            //Convertir les objets en int
            weight[i] = (double) obj[i];
        }
        System.out.println(weight.getClass().getSimpleName());

//-----------------------------------------------------------------------------------//


        Loader.loadNativeLibraries();
        final testOrTools.DataModel data = new testOrTools.DataModel();

        // Create the linear solver with the SCIP backend.
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
            System.out.println("Could not create solver SCIP");
            return;
        }

        MPVariable[][] x = new MPVariable[weight.length][weight.length];
        for (int i = 0; i < weight.length; ++i) {
            for (int j = 0; j < weight.length; ++j) {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }
        MPVariable[] y = new MPVariable[weight.length];
        for (int j = 0; j < weight.length; ++j) {
            y[j] = solver.makeIntVar(0, 1, "");
        }

        double infinity = java.lang.Double.POSITIVE_INFINITY;
        for (int i = 0; i < weight.length; ++i) {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int j = 0; j < weight.length; ++j) {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
        // The bin capacity contraint for bin j is
        //   sum_i w_i x_ij <= C*y_j
        // To define this constraint, first subtract the left side from the right to get
        //   0 <= C*y_j - sum_i w_i x_ij
        //
        // Note: Since sum_i w_i x_ij is positive (and y_j is 0 or 1), the right side must
        // be less than or equal to C. But it's not necessary to add this constraint
        // because it is forced by the other constraints.

        for (int j = 0; j < weight.length; ++j) {
            MPConstraint constraint = solver.makeConstraint(0, infinity, "");
            constraint.setCoefficient(y[j], service.getSizeOfBin());
            for (int i = 0; i < weight.length; ++i) {
                constraint.setCoefficient(x[i][j], -weight[i]);
            }
        }

        MPObjective objective = solver.objective();
        for (int j = 0; j < weight.length; ++j) {
            objective.setCoefficient(y[j], 1);
        }
        objective.setMinimization();

        final MPSolver.ResultStatus resultStatus = solver.solve();

        // Check that the problem has an optimal solution.
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Number of bins used: " + objective.value());
            double totalWeight = 0;
            for (int j = 0; j < weight.length; ++j) {
                if (y[j].solutionValue() == 1) {
                    System.out.println("\nBin " + j + "\n");
                    double binWeight = 0;
                    for (int i = 0; i < weight.length; ++i) {
                        if (x[i][j].solutionValue() == 1) {
                            System.out.println("Item " + i + " - weight: " + weight[i]);
                            binWeight += weight[i];
                        }
                    }
                    System.out.println("Packed bin weight: " + binWeight);
                    totalWeight += binWeight;
                }
            }
            System.out.println("\nTotal packed weight: " + totalWeight);
        } else {
            System.err.println("The problem does not have an optimal solution.");
        }
    }

}
