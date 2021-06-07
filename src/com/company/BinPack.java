package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.GeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.order.DecreasingOrderStrategy;
import com.company.order.ListItemsOrderStrategy;
import com.company.order.SimpleOrderStrategy;
import com.company.solver.RecuitSimuleSolver;
import com.company.solver.Solver;
import com.company.solver.TabouSolver;
import com.company.vue.StackedBarChart;
import org.jfree.ui.RefineryUtilities;

public class BinPack {

    public static void main(String[] args) {

        //Récupération des arguments
        String file = args[0];

        System.out.println("file: " + file);

        //Récupération information fichier
        Solution init = new Solution();
        init.getSolutionFromFile("src/com/company/data/" + file);

        if (args[1].equals("2")) {

            LinearProgram linearProgram = new LinearProgram();

            Long startExecutionTime = System.nanoTime();
            linearProgram.solve(init.getListItems(), init.getCapacity());
            Long endExecutionTime = System.nanoTime();

            System.out.println("Temps d'exécution (ms): " + ((endExecutionTime - startExecutionTime) / 1000000));

        } else {

            String order = args[1];
            String generator = args[2];
            String solver = args[3];

            ListItemsOrderStrategy orderStrategy;
            GeneratorStrategy generatorStrategy;
            Solver solverFinal;

            //Définition de l'ordre
            if (order.equals("0")) {
                orderStrategy = new SimpleOrderStrategy();
            } else {
                orderStrategy = new DecreasingOrderStrategy();
            }

            //Définition du générateur
            if (generator.equals("0")) {
                generatorStrategy = new OneItemOneBinGeneratorStrategy();
            } else {
                generatorStrategy = new FirstFitGeneratorStrategy();
            }

            //Définiton du solveur
            if (solver.equals("0")) {
                solverFinal = new RecuitSimuleSolver(init);
            } else {
                solverFinal = new TabouSolver(init);
            }

            //Assemblage
            solverFinal.setListItemsOrderStrategy(orderStrategy);
            solverFinal.setGeneratorStrategy(generatorStrategy);

            System.out.println("Taille list item: " + init.getListItems().size());
            System.out.println("Nombre bin init: " + init.getNumberOfBinUsed());
            System.out.println("Fitness init: " + init.getFitness());

            Long startExecutionTime = System.nanoTime();

            Solution s1;
            //Définiton du solveur
            if (solver.equals("0")) {
                double t0 = Double.parseDouble(args[4]);
                int n1 = Integer.parseInt(args[5]);
                int n2 = 100;
                int nbVoisins = Integer.parseInt(args[6]);
                double mu = Double.parseDouble(args[7]);
                System.out.println("Paramètres recuit simulé: t0=" + t0 + ", n1=" + n1 + ", n2=" + n2 + ", nbVoisins=" + nbVoisins + ", mu=" + mu);
                s1 = ((RecuitSimuleSolver) solverFinal).solve(t0, n1, n2, nbVoisins, mu);
            } else {
                int maxIter = Integer.parseInt(args[4]);
                int maxT = Integer.parseInt(args[5]);
                int nbVoisins = Integer.parseInt(args[6]);
                System.out.println("Paramètre tabou: maxIter=" + maxIter + " max= " + maxT + " nbVoisins=" + nbVoisins);
                s1 = ((TabouSolver) solverFinal).solve(init, maxIter, maxT, nbVoisins);
            }
            Long endExecutionTime = System.nanoTime();



            System.out.println("Temps d'exécution (ms): " + ((endExecutionTime - startExecutionTime) / 1000000));
            System.out.println(s1.getListBins());
            System.out.println("Bin utilisé: " + s1.getNumberOfBinUsed());
            System.out.println("Fitness Final: " + s1.getFitness());

            StackedBarChart chart = new StackedBarChart(
                    "Résultat" ,
                    "Nombre de bins en fonction des itérations", solverFinal.getX(), solverFinal.getY(), solverFinal.getYExplore());

            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );
        }
    }

}
