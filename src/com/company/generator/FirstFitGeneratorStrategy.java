package com.company.generator;

import java.util.List;

public class FirstFitGeneratorStrategy implements GeneratorStrategy{

    /**
     * Return la liste des bins avec la répartition des items
     * Ajoute un bin à chaque fois d'un item rentre dans aucun des bins existants
     * @return ArrayList
     */
    @Override
    public int[] generate(List<Integer> items, int sizeOfBin, List<Integer> listBin) {

        listBin.add(0);
        int[] assignedBin = new int[items.size()];
        for (int item = 0; item < items.size(); item++) {
            for (int i = 0; i < listBin.size(); i++) {
                if (listBin.get(i) >= items.get(item)) {
                    listBin.set(i, listBin.get(i)-items.get(item));
                    assignedBin[item] = i;
                    break;
                } else {
                    if (i+1 >= listBin.size()) {
                        listBin.add(sizeOfBin);
                    }
                }
            }
        }
        return assignedBin;
    }

}
