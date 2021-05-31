package com.company;

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
        String file = "src/com/company/data/binpack1d_00.txt";

        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 0){
                    capacity = Integer.parseInt(data.split(" ")[0]);
                }else if(lineCounter >= 1){
                    sizes.add(Integer.parseInt(data));
                }
                lineCounter++;
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erreur sur l'extraction du fichier");
            e.printStackTrace();
        }

        numItems = sizes.size();

        //order
//        sizes = sizes.stream().sorted((integer1, integer2) -> Integer.compare(integer2, integer1)).collect(Collectors.toList());

        bins.add(capacity);

        assignedBin = new int[numItems];
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
                listOfNeighboor.add(assignedBin);
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
        assignedBin = solInit;
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

        if(!moveOK) {
//            System.out.println("pas de déplacement possible");
            return false;
        }else {
            return true;
        }

    }

}
