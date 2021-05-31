package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

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
        sizes = sizes.stream().sorted((integer1, integer2) -> Integer.compare(integer2, integer1)).collect(Collectors.toList());

        bins.add(capacity);

        assignedBin = new int[numItems];
        solInit = new int[numItems];

        //generator
        firstFit(sizes, bins);
//        oneBinByItem(sizes,bins);



        System.out.println(binUsed);

        System.out.println(Arrays.toString(assignedBin));
        System.arraycopy(assignedBin, 0, solInit, 0, sizes.size());
        System.out.println(Arrays.toString(solInit));

        List<int[]> listOfNeighboor = new ArrayList<>();

        for(int i = 0; i<10; i++) {
            echangeItem(sizes, bins);
        }


        //génération de 10 voisins
        /*for(int i = 0; i < 10; i++) {
            if(moveOneItem(sizes, bins)) {
                int[] neighboor = new int[numItems];
                System.arraycopy(assignedBin, 0, neighboor, 0, neighboor.length);
                listOfNeighboor.add(neighboor);
            } else{
                i--;
            }
        }

        System.out.println(listOfNeighboor.size());
        for(int i = 0; i< listOfNeighboor.size(); i++) {
            System.out.println(Arrays.toString(listOfNeighboor.get(i)));
        }*/

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

        //reinitialisation de assignedBin avec la solution initiale
        System.arraycopy(solInit, 0, assignedBin, 0, sizes.size());

        //Tirage de l'item à déplacer
        int itemSelect = (int)(Math.random() * assignedBin.length-1);

        //Récupération du bin contenant l'item sélectionné
        int binOfItemSelect = assignedBin[itemSelect];

        //Récupération de la taille de l'item sélectionné
        int sizeOfItemSelect = sizes.get(itemSelect);

        //Déplacement d'un item vers un autre bin
        boolean moveOK = false;
        for(int i = 0; i < bins.size(); i++) {
            if(!moveOK) {
                //vérification si un bin à la capacité d'accueillir l'item sélectionné
                if(bins.get(i) >= sizeOfItemSelect && bins.get(i) < capacity) {

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

    static boolean echangeItem(List<Integer> sizes, List<Integer> bins) {

        //reinitialisation de assignedBin avec la solution initiale
        System.arraycopy(solInit, 0, assignedBin, 0, sizes.size());

        //Tirage de l'item à déplacer
        int itemSelect = (int)(Math.random() * assignedBin.length-1);

        //Récupération du bin contenant l'item sélectionné
        int binOfItemSelect = assignedBin[itemSelect];

        //Récupération de la taille de l'item sélectionné
        int sizeOfItemSelect = sizes.get(itemSelect);

        //Déplacement d'un item vers un autre bin
        boolean moveOK = false;
        System.out.println(binOfItemSelect + "/" + (bins.size()-1));
        int random;
        if(binOfItemSelect == 0){
            random = 1;
        }else if(binOfItemSelect == bins.size()-1){
            random = 0;
        } else {
            random = (Math.random() > 0.5) ? 1 : 0;
        }

        System.out.println(bins);
        int i = 1;
        int neighboorBin;
        if(random == 1) {
            System.out.println(bins.get(binOfItemSelect+i) == 9 && binOfItemSelect+i <= bins.size());
            while(bins.get(binOfItemSelect+i) == 9 && binOfItemSelect+i <= bins.size()){
                i++;
            }

//            System.out.println("Voisin potentiel en "+ i + " cap dispo " + bins.get(binOfItemSelect+i));
            neighboorBin = binOfItemSelect+i;
        }else {
            System.out.println(bins.get(binOfItemSelect-i) == 9 && binOfItemSelect-i > 0);
            while(bins.get(binOfItemSelect-i) == 9 && binOfItemSelect-i > 0){
                i++;
            }

//            System.out.println("Voisin potentiel en - "+ i + " cap dispo " + bins.get(binOfItemSelect-i));
            neighboorBin = binOfItemSelect-i;
        }

        System.out.println(neighboorBin);
        System.out.println(Arrays.toString(assignedBin));
//        List<Integer> indexOfItemsOfNeighboorBin = new ArrayList<>();
        HashMap<Integer,Integer> indexOfItemsOfNeighboorBin = new HashMap<>();
        for(int j = 0; j < assignedBin.length; j++){
            if(assignedBin[j] == neighboorBin){
                indexOfItemsOfNeighboorBin.put(j, sizes.get(j));
            }
        }

        System.out.println(indexOfItemsOfNeighboorBin);
        System.out.println(indexOfItemsOfNeighboorBin);

//        for(int i = 0; i < bins.size(); i++) {
//            if(!moveOK) {
//                //vérification si un bin à la capacité d'accueillir l'item sélectionné
//                if(bins.get(i) >= sizeOfItemSelect && bins.get(i) < capacity) {
//
//                    //Mise à jour des capacités restantes des bins
//                    bins.set(i, bins.get(i)-sizeOfItemSelect);
//                    bins.set(binOfItemSelect, bins.get(binOfItemSelect)+sizeOfItemSelect);
//                    //enregistrement nouvel emplacement
//                    assignedBin[itemSelect] = i;
//                    moveOK = true;
//                }
//            }
//        }

        return moveOK;
    }

}
