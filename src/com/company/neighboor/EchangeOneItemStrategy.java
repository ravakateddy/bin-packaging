package com.company.neighboor;

import com.company.Bin;
import com.company.Item;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EchangeOneItemStrategy implements NeighbourStrategy {

    @Override
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins) {
        //reinitialisation de assignedBin avec la solution initiale
        int[] voisin = new int[assignedBin.length];
        System.arraycopy(assignedBin, 0, voisin, 0, assignedBin.length);

        //Tirage de l'item à déplacer
        int itemSelect = (int)(Math.random() * voisin.length-1);

        //Récupération du bin contenant l'item sélectionné
        int binOfItemSelect = voisin[itemSelect];

        //Récupération de la taille de l'item sélectionné
        int sizeOfItemSelect = items.get(itemSelect);

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
        System.out.println(Arrays.toString(voisin));
//        List<Integer> indexOfItemsOfNeighboorBin = new ArrayList<>();
        HashMap<Integer,Integer> indexOfItemsOfNeighboorBin = new HashMap<>();
        for(int j = 0; j < voisin.length; j++){
            if(voisin[j] == neighboorBin){
                indexOfItemsOfNeighboorBin.put(j, items.get(j));
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

        return voisin;
    }
}
