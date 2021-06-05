package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.GeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;
import com.company.neighboor.NeighbourStrategy;
import com.company.order.DecreasingOrderStrategy;
import com.company.order.ListItemsOrderStrategy;
import com.company.order.SimpleOrderStrategy;
import com.company.solver.RecuitSimuleSolver;
import com.company.solver.Solver;
import com.company.solver.TabouSolver;
import com.company.vue.StackedBarChart;
import org.jfree.ui.RefineryUtilities;

import java.util.Arrays;

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
            String neighboor = args[3];
            String solver = args[4];

            ListItemsOrderStrategy orderStrategy;
            GeneratorStrategy generatorStrategy;
            NeighbourStrategy neighbourStrategy;
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

            //Définition du voisin
            if (neighboor.equals("0")) {
                neighbourStrategy = new MoveOneItemStrategy();
            } else {
                neighbourStrategy = new EchangeOneItemStrategy();
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
            solverFinal.setNeighbourStrategy(neighbourStrategy);

            System.out.println("Bins init: " + Arrays.toString(init.getAssignedBin()));
            System.out.println("Taille list item: " + init.getListItems().size());
            System.out.println("Bins occupation init: " + init.getListBins());
            System.out.println("Nombre bin init: " + init.getNumberOfBinUsed());
            System.out.println("Fitness init: " + init.getFitness());

            Long startExecutionTime = System.nanoTime();

            Solution s1;
            //Définiton du solveur
            if (solver.equals("0")) {
                int t0 = 1000;
                int n1 = 100;
                int n2 = 5;
                int nbVoisins = 500;
                double mu = 0.75;
                System.out.println("Paramètres recuit simulé: t0=" + t0 + ", n1=" + n1 + ", n2=" + n2 + ", nbVoisins=" + nbVoisins + ", mu=" + mu);
                s1 = ((RecuitSimuleSolver) solverFinal).solve(t0, n1, n2, nbVoisins, mu);
            } else {
                int maxIter = 1000;
                int maxT = 10;
                System.out.println("Paramètre tabou: maxIter=" + maxIter + " max= " + maxT);
                s1 = ((TabouSolver) solverFinal).solve(init, maxIter, maxT);
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
