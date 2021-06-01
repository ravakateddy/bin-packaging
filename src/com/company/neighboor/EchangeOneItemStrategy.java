package com.company.neighboor;

import com.company.Bin;
import com.company.Item;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EchangeOneItemStrategy implements NeighbourStrategy {

    @Override
    public int[] move(int[] assignedBin, List<Integer> items, List<Integer> bins, int sizeOfBin) {

        //reinitialisation de assignedBin avec la solution initiale
        int voisin[] = new int[assignedBin.length];
        System.arraycopy(assignedBin, 0, voisin, 0, assignedBin.length);

        //Tirage de l'item à déplacer
        int itemSelect = (int)(Math.random() * assignedBin.length-1);

        //Récupération du bin contenant l'item sélectionné
        int binOfItemSelect = voisin[itemSelect];

        //Récupération de la taille de l'item sélectionné
        int sizeOfItemSelect = items.get(itemSelect);

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
            while(bins.get(binOfItemSelect+i) == sizeOfBin && binOfItemSelect+i <= bins.size()){
                i++;
            }

            neighboorBin = binOfItemSelect+i;
        }else {
            while(bins.get(binOfItemSelect-i) == sizeOfBin && binOfItemSelect-i > 0){
                i++;
            }

            neighboorBin = binOfItemSelect-i;
        }

        List<Integer> indexOfItemsOfNeighboorBin = new ArrayList<>();
        List<Integer> valueItemsOfNeighboorBin = new ArrayList<>();

        //récupération des items qui sont dans le bin voisin
        for(int j = 0; j < voisin.length; j++){
            if(voisin[j] == neighboorBin){

                //on vérifie si l'item est supérieur à la taille de l'item que l'on cherche à déplacer
                //et si la taille de l'item du voisin peut rentrer dans le bin initial
                if(bins.get(neighboorBin)+items.get(j) >= sizeOfItemSelect){
                    //L'espace dispo dans le bin voisin en retirant l'item présent permet d'accueillir l'item selectionné
                    if(items.get(j) != sizeOfItemSelect) {
                        //L'item j est différent de l'item sélectionné");
                        if(bins.get(binOfItemSelect)+sizeOfItemSelect >= items.get(j)){
                            //L'item select rentre dans le bin init
                            //récupération de l'index de l'item
                            indexOfItemsOfNeighboorBin.add(j);
                            //récupération valeur de l'index
                            valueItemsOfNeighboorBin.add(items.get(j));
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
                voisin[itemSelect] = neighboorBin;
                voisin[indexOfItemsOfNeighboorBin.get(0)] = binOfItemSelect;
            }else {
                int randomItem = (int)(Math.random() * (indexOfItemsOfNeighboorBin.size()-1));

                //retrait des items des bins respectifs
                bins.set(binOfItemSelect,bins.get(binOfItemSelect)+sizeOfItemSelect);
                bins.set(neighboorBin, bins.get(neighboorBin)+valueItemsOfNeighboorBin.get(randomItem));
                //ajout des items dans leurs nouveaux bins
                bins.set(binOfItemSelect, bins.get(binOfItemSelect)-valueItemsOfNeighboorBin.get(randomItem));
                bins.set(neighboorBin, bins.get(neighboorBin)-sizeOfItemSelect);
                //enregistrement des nouveaux emplacements
                voisin[itemSelect] = neighboorBin;
                voisin[indexOfItemsOfNeighboorBin.get(randomItem)] = binOfItemSelect;
            }
        }

        return voisin;
    }
}
