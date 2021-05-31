package com.company;

import com.company.generator.FirstFitGeneratorStrategy;
import com.company.generator.GeneratorStrategy;
import com.company.generator.OneItemOneBinGeneratorStrategy;
import com.company.neighboor.MoveOneItemStrategy;

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
        System.out.println(init.getAssignedBin());
        OneItemOneBinGeneratorStrategy oneItemOneBinGeneratorStrategy = new OneItemOneBinGeneratorStrategy();
        FirstFitGeneratorStrategy firstFitGeneratorStrategy = new FirstFitGeneratorStrategy();
        init.setGeneratorStrategy(oneItemOneBinGeneratorStrategy);



        MoveOneItemStrategy moveOneItemStrategy = new MoveOneItemStrategy();
        for(int i=0; i<5; i++){
            /*System.out.println("assignedBin" + Arrays.toString(init.getAssignedBin()));
            System.out.println("listItems" + init.getListItems());
            System.out.println("listBins"  + init.getListBins());*/
            System.out.println(Arrays.toString(moveOneItemStrategy.move(init.getAssignedBin(), init.getListItems(), init.getListBins())));
        }
        System.out.println(init.getListBins());
        System.exit(0);

        numItems = sizes.size();

        //order
//        sizes = sizes.stream().sorted((integer1, integer2) -> Integer.compare(integer2, integer1)).collect(Collectors.toList());

        bins.add(capacity);

        assignedBin = new int[numItems]; // Numéro du bin à chaque position de l'item
        solInit = new int[numItems];

        //generator
//        firstFit(sizes, bins);
        oneBinByItem(sizes,bins);



        System.out.println(binUsed);

        System.out.println(Arrays.toString(assignedBin));
        System.arraycopy(assignedBin, 0, solInit, 0, sizes.size());
        System.out.println(Arrays.toString(solInit));

        List<int[]> listOfNeighboor = new ArrayList<>();

        //génération de 10 voisins
        for(int i = 0; i < 10; i++) {
            if(moveOneItem(sizes, bins)) {
                int[] neighboor = new int[numItems];
                System.arraycopy(assignedBin, 0, neighboor, 0, neighboor.length);
                listOfNeighboor.add(neighboor);
            } else{
                i--;
            }
        }

//        System.out.println(Arrays.toString(listOfNeighboor.get(0)));
        System.out.println(listOfNeighboor.size());
        for(int i = 0; i< listOfNeighboor.size(); i++) {
            System.out.println(Arrays.toString(listOfNeighboor.get(i)));
        }

    }

    static void oneBinByItem(List<Integer> sizes, List<Integer> bins) {

        bins.remove(0);
        for (int i = 0; i < sizes.size(); i++) {
            bins.add(capacity- sizes.get(i));
            assignedBin[i] = i;
        }

        binUsed = bins.size();
    }

    static void firstFit(List<Integer> sizes, List<Integer> bins) {


        for (int item = 0; item < sizes.size(); item++) {
            for (int i = 0; i < bins.size(); i++) {
                if (bins.get(i) >= sizes.get(item)) {
                    bins.set(i, bins.get(i)-sizes.get(item));
                    assignedBin[item] = i;
                    break;
                } else {
                    if (i+1 >= bins.size()) {
                        bins.add(capacity);
                    }
                }
            }
        }

        binUsed = bins.size();

    }

    static boolean moveOneItem(List<Integer> sizes, List<Integer> bins) {

        System.out.println("Before: " + Arrays.toString(assignedBin));
        System.arraycopy(solInit, 0, assignedBin, 0, sizes.size());
        System.out.println("After: " + Arrays.toString(assignedBin));

//        System.out.println(sizes.toString());
//        System.out.println("List bins: " + bins.toString());
//        System.out.println(Arrays.toString(solInit));
//        System.out.println(Arrays.toString(assignedBin));

        int itemSelect = (int)(Math.random() * assignedBin.length-1);
//        System.out.println(itemSelect);

        int binOfItemSelect = assignedBin[itemSelect];
//        System.out.println(binOfItemSelect);

        int sizeOfItemSelect = sizes.get(itemSelect);
//        System.out.println("Taille de l'item: " + sizeOfItemSelect);

        int capacityOfBinSelect = bins.get(binOfItemSelect);
//        System.out.println(capacityOfBinSelect);

        //vérification si un bin à la capacité d'accueillir l'item sélectionné

        //Déplacement d'un item vers un autre bin
        boolean moveOK = false;
        for(int i = 0; i < bins.size(); i++) {
            if(!moveOK) {
                if(bins.get(i) >= sizeOfItemSelect && bins.get(i) < capacity) {
//                    System.out.println("On peut déplacer ici: " + i + " capacité dispo de ce bin: " + bins.get(i));

                    //Mise à jour des capacités restantes des bins
                    bins.set(i, bins.get(i)-sizeOfItemSelect);
                    bins.set(binOfItemSelect, bins.get(binOfItemSelect)+sizeOfItemSelect);
                    //enregistrement nouvel emplacement
                    assignedBin[itemSelect] = i;
                    moveOK = true;
                }
            }
        }

        return moveOK;

    }

}
