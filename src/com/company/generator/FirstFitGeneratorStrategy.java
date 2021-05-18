package com.company.generator;

import com.company.Bin;
import com.company.Item;

import java.util.ArrayList;
import java.util.List;

public class FirstFitGeneratorStrategy implements GeneratorStrategy{

    /**
     * Return la liste des bins avec la répartition des items
     * Ajoute un bin à chaque fois d'un item rentre dans aucun des bins existants
     * @return ArrayList
     */
    @Override
    public List<Bin> generate(List<Item> items, int sizeOfBin) {
        ArrayList<Bin> listBins = new ArrayList<>();
        listBins.add(new Bin(sizeOfBin));

        //Parcours des items
        for(int i = 0; i < items.size(); i++){
            //Item courant
            Item currentItem = items.get(i);

            int j=0;
            Bin currentBin = listBins.get(j);

            // tant l'on ne peut pas ajouter l'item au bin courant, on parcourt la liste de bin
            while(!currentBin.addItem(currentItem)){
                //Si on atteint la taille de la liste de bin, on ajoute un nouveau bin
                if(j+1 == listBins.size()) {
                    listBins.add(new Bin(sizeOfBin));
                }
                j++;
                currentBin = listBins.get(j);
            }
        }
        return listBins;
    }
}
