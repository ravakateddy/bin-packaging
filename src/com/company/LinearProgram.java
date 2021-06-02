package com.company;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearProgram {

    public void solve(List<Integer> listItems, int capacity){

        //Transformation de nos données en dataModel
        ArrayList<Double> weights = new ArrayList<>();
        listItems.stream().mapToDouble(Integer::intValue).forEach(weights::add);
        System.out.println("weights" + weights);
        Object[] obj = weights.toArray();
        System.out.println(Arrays.toString(obj));

        double[] weight = new double[obj.length];
        for(int i=0; i < weight.length; i++) {
            //Convertir les objets en int
            weight[i] = (double) obj[i];
        }

//-----------------------------------------------------------------------------------//

        try {
            Loader.loadNativeLibraries();
        }catch (Exception e){
            e.printStackTrace();
        }
        //Déclaration du solveur
        // Create the linear solver with the SCIP backend.
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
            System.out.println("Could not create solver SCIP");
            return;
        }

        //Create the variables
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


        //Define the constraints
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
            //definition taille bin
            constraint.setCoefficient(y[j], capacity);
            for (int i = 0; i < weight.length; ++i) {
                constraint.setCoefficient(x[i][j], -weight[i]);
            }
        }

        //Define the objective
        MPObjective objective = solver.objective();
        for (int j = 0; j < weight.length; ++j) {
            objective.setCoefficient(y[j], 1);
        }
        objective.setMinimization();

        System.out.println("Affichage résultat");


        //Call the solver and prit the solution
        final MPSolver.ResultStatus resultStatus = solver.solve();

        System.out.println(resultStatus);

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
