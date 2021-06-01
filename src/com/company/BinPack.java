package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.GeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.neighboor.MoveOneItemStrategy;
import com.company.order.DecreasingOrderStrategy;
import com.company.order.SimpleOrderStrategy;

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
        DecreasingOrderStrategy decreasingOrderStrategy = new DecreasingOrderStrategy();
        SimpleOrderStrategy simpleOrderStrategy = new SimpleOrderStrategy();
        init.setListItemsOrderStrategy(simpleOrderStrategy);
        System.out.println(init.getListItems());

        OneItemOneBinGeneratorStrategy oneItemOneBinGeneratorStrategy = new OneItemOneBinGeneratorStrategy();
        FirstFitGeneratorStrategy firstFitGeneratorStrategy = new FirstFitGeneratorStrategy();
        init.setGeneratorStrategy(firstFitGeneratorStrategy);


        MoveOneItemStrategy moveOneItemStrategy = new MoveOneItemStrategy();
        for (int i = 0; i < 5; i++) {
            /*System.out.println("assignedBin" + Arrays.toString(init.getAssignedBin()));
            System.out.println("listItems" + init.getListItems());
            System.out.println("listBins"  + init.getListBins());*/
            System.out.println(Arrays.toString(moveOneItemStrategy.move(init.getAssignedBin(), init.getListItems(), init.getListBins())));
        }
        System.out.println(init.getListBins());
        System.exit(0);
    }

}
