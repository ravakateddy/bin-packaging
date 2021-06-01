package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.GeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;
import com.company.order.DecreasingOrderStrategy;
import com.company.order.SimpleOrderStrategy;
import com.company.solver.RecuitSimuleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BinPack {

    static int capacity, binUsed = 0;
    static int[] assignedBin, solInit;

    public static void main(String[] args) {

        int numItems;
        List<Integer> sizes = new ArrayList<>(); //list d'item
        List<Integer> bins = new ArrayList<>();

        //Récupération information fichier
//        String file = "src/com/company/test2.txt";
        Solution init = new Solution();
        init.getSolutionFromFile("src/com/company/test2.txt");

        // Order
        DecreasingOrderStrategy decreasingOrderStrategy = new DecreasingOrderStrategy();
        SimpleOrderStrategy simpleOrderStrategy = new SimpleOrderStrategy();

        // Generator
        OneItemOneBinGeneratorStrategy oneItemOneBinGeneratorStrategy = new OneItemOneBinGeneratorStrategy();
        FirstFitGeneratorStrategy firstFitGeneratorStrategy = new FirstFitGeneratorStrategy();

        // Neighbour
        MoveOneItemStrategy moveOneItemStrategy = new MoveOneItemStrategy();
        EchangeOneItemStrategy echangeOneItemStrategy = new EchangeOneItemStrategy();

        RecuitSimuleSolver recuitSimuleSolver = new RecuitSimuleSolver(init);
        recuitSimuleSolver.setListItemsOrderStrategy(simpleOrderStrategy);
        recuitSimuleSolver.setGeneratorStrategy(firstFitGeneratorStrategy);
        recuitSimuleSolver.setNeighbourStrategy(moveOneItemStrategy);
        recuitSimuleSolver.solve(0.5);
        System.exit(0);
    }

}
