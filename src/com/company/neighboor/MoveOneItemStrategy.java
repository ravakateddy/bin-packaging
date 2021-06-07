package com.company.neighboor;

import java.util.Arrays;
import java.util.List;

public class MoveOneItemStrategy implements NeighbourStrategy {
    @Override
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins ,int sizeOfBin) {

        int[] voisin = new int[assignedBin.length];
        System.arraycopy(assignedBin, 0, voisin, 0, assignedBin.length);
        int itemSelect = (int)(Math.random() * voisin.length-1);

        int binOfItemSelect = voisin[itemSelect];

        int sizeOfItemSelect = items.get(itemSelect);

        //vérification si un bin à la capacité d'accueillir l'item sélectionné

        //Déplacement d'un item vers un autre bin
        boolean moveOK = false;
        for(int i = 0; i < bins.size(); i++) {
            if(!moveOK) {
                if(bins.get(i) >= sizeOfItemSelect && bins.get(i) < sizeOfBin) {
                    //Mise à jour des capacités restantes des bins
                    bins.set(i, bins.get(i)-sizeOfItemSelect);
                    bins.set(binOfItemSelect, bins.get(binOfItemSelect)+sizeOfItemSelect);
                    //enregistrement nouvel emplacement
                    voisin[itemSelect] = i;
                    moveOK = true;
                }
            }
        }

        if(!moveOK){
            voisin = null;
        }


        return voisin;
    }
}