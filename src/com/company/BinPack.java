package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
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
        String file = "src/com/company/data/binpack1d_31.txt";

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

        //génération de 10 voisins
        for(int i = 0; i < 10; i++) {
//            boolean neighboorNew = moveOneItem(sizes, bins);
            boolean neighboorNew = echangeItem(sizes, bins);
            if(neighboorNew) {
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

        int random;
        if(binOfItemSelect == 0){
            random = 1;
        }else if(binOfItemSelect == bins.size()-1){
            random = 0;
        } else {
            random = (Math.random() > 0.5) ? 1 : 0;
        }

        int i = 1;
        int neighboorBin;
        if(random == 1) {
            while(bins.get(binOfItemSelect+i) == 9 && binOfItemSelect+i <= bins.size()){
                i++;
            }

            neighboorBin = binOfItemSelect+i;
        }else {
            while(bins.get(binOfItemSelect-i) == 9 && binOfItemSelect-i > 0){
                i++;
            }

            neighboorBin = binOfItemSelect-i;
        }

        List<Integer> indexOfItemsOfNeighboorBin = new ArrayList<>();
        List<Integer> valueItemsOfNeighboorBin = new ArrayList<>();

        //récupération des items qui sont dans le bin voisin
        for(int j = 0; j < assignedBin.length; j++){
            if(assignedBin[j] == neighboorBin){

                //on vérifie si l'item est supérieur à la taille de l'item que l'on cherche à déplacer
                //et si la taille de l'item du voisin peut rentrer dans le bin initial
                if(bins.get(neighboorBin)+sizes.get(j) >= sizeOfItemSelect){
                    //L'espace dispo dans le bin voisin en retirant l'item présent permet d'accueillir l'item selectionné
                    if(sizes.get(j) != sizeOfItemSelect) {
                        //L'item j est différent de l'item sélectionné");
                        if(bins.get(binOfItemSelect)+sizeOfItemSelect >= sizes.get(j)){
                            //L'item select rentre dans le bin init
                            //récupération de l'index de l'item
                            indexOfItemsOfNeighboorBin.add(j);
                            //récupération valeur de l'index
                            valueItemsOfNeighboorBin.add(sizes.get(j));
                        }
                    }
                }
            }
        }

        if(indexOfItemsOfNeighboorBin.size() != 0){
            moveOK = true;
            if(indexOfItemsOfNeighboorBin.size() == 1){
                //retrait des items des bins respectifs
                bins.set(binOfItemSelect,bins.get(binOfItemSelect)+sizeOfItemSelect);
                bins.set(neighboorBin, bins.get(neighboorBin)+valueItemsOfNeighboorBin.get(0));
                //ajout des items dans leurs nouveaux bins
                bins.set(binOfItemSelect, bins.get(binOfItemSelect)-valueItemsOfNeighboorBin.get(0));
                bins.set(neighboorBin, bins.get(neighboorBin)-sizeOfItemSelect);
                //enregistrement des nouveaux emplacements
                assignedBin[itemSelect] = neighboorBin;
                assignedBin[indexOfItemsOfNeighboorBin.get(0)] = binOfItemSelect;
            }else {
                int randomItem = (int)(Math.random() * (indexOfItemsOfNeighboorBin.size()-1));

                //retrait des items des bins respectifs
                bins.set(binOfItemSelect,bins.get(binOfItemSelect)+sizeOfItemSelect);
                bins.set(neighboorBin, bins.get(neighboorBin)+valueItemsOfNeighboorBin.get(randomItem));
                //ajout des items dans leurs nouveaux bins
                bins.set(binOfItemSelect, bins.get(binOfItemSelect)-valueItemsOfNeighboorBin.get(randomItem));
                bins.set(neighboorBin, bins.get(neighboorBin)-sizeOfItemSelect);
                //enregistrement des nouveaux emplacements
                assignedBin[itemSelect] = neighboorBin;
                assignedBin[indexOfItemsOfNeighboorBin.get(randomItem)] = binOfItemSelect;
            }
        }

        return moveOK;
    }

}
