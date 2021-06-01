package com.company.neighboor;

import com.company.Bin;
import com.company.Item;

import java.util.Arrays;
import java.util.List;

public class MoveOneItemStrategy implements NeighbourStrategy {
    @Override
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins) {

        int[] voisin = new int[assignedBin.length];

        System.out.println("Before: " + Arrays.toString(assignedBin));
        System.arraycopy(assignedBin, 0, voisin, 0, assignedBin.length);
        System.out.println("After: " + Arrays.toString(assignedBin));

        int itemSelect = (int)(Math.random() * voisin.length-1);

        int binOfItemSelect = voisin[itemSelect];

        int sizeOfItemSelect = items.get(itemSelect);

        int capacityOfBinSelect = bins.get(binOfItemSelect);
//        System.out.println(capacityOfBinSelect);

        //vérification si un bin à la capacité d'accueillir l'item sélectionné

        //Déplacement d'un item vers un autre bin
        boolean moveOK = false;
        for(int i = 0; i < bins.size(); i++) {
            if(!moveOK) {
                if(bins.get(i) >= sizeOfItemSelect && bins.get(i) < 9) {
//                    System.out.println("On peut déplacer ici: " + i + " capacité dispo de ce bin: " + bins.get(i));

                    //Mise à jour des capacités restantes des bins
                    bins.set(i, bins.get(i)-sizeOfItemSelect);
                    bins.set(binOfItemSelect, bins.get(binOfItemSelect)+sizeOfItemSelect);
                    //enregistrement nouvel emplacement
                    voisin[itemSelect] = i;
                    moveOK = true;
                }
            }
        }
        return voisin;
    }
}