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


    public static void main(String[] args) {

        int numItems;
        List<Integer> sizes = new ArrayList<>(); //list d'item
        List<Integer> bins = new ArrayList<>();

        //Récupération information fichier
//        String file = "src/com/company/test2.txt";
        Solution init = new Solution();
        init.getSolutionFromFile("src/com/company/data/binpack1d_31.txt");

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


        // fixer t0 de manière à avoir 4 chances sur 5 d’accepter ces solutions : exp(-Df/t0) = 0.8 => t0 = -Df/ln(0.8)
        // fixer n1 de manière à avoir 1 chance sur 100 (par exemple) d’accepter la même mauvaise solution que pour fixer t0 : n1 = ln(ln(0.8)/ln(0.01))/ln(μ)
        System.out.println(recuitSimuleSolver.solve(0.5, 5, 5, 500, 0.75).getFitness());
    }

}
