package com.company.neighboor;

import com.company.Bin;
import com.company.Item;

import java.sql.SQLOutput;
import java.util.List;

public class EchangeOneItemStrategy implements NeighbourStrategy {

    @Override
    public List<Bin> move(List<Bin> listBins) {

        int A = (int)(Math.random() * listBins.size());

        //récupération capacité bin
        int capacityBin = listBins.get(0).getCapacity();

        List<Item> listItemOfBin = listBins.get(A).getListItems();
        Item itemSelectA = listItemOfBin.get((int)(Math.random() * listItemOfBin.size()));
        int placeDispoA = listBins.get(A).getCapacity() - (listBins.get(A).getTotal() - itemSelectA.getSize());

        boolean placement = false;
        for(int i = 0; i < listBins.size(); i++) {

            if(i != A) {
                List<Item> itemsOfBin = listBins.get(i).getListItems();
                int placeDispoB = listBins.get(i).getCapacity() - listBins.get(i).getTotal();
                for (int j = 0; j < itemsOfBin.size(); j++) {
                    if((placeDispoB + itemsOfBin.get(j).getSize()) >= itemSelectA.getSize() && placeDispoA >= itemsOfBin.get(j).getSize()) {

                        System.out.println("Before opé: "+ listBins);

                        Item itemSelectB = itemsOfBin.get(j);
                        System.out.println("ItemSelectB " + itemSelectB);

                        listBins.get(A).remove(itemSelectA);
                        listBins.get(i).remove(itemSelectB);

                        System.out.println("After remove: " + listBins);

                        listBins.get(A).addItem(itemSelectB);
                        listBins.get(i).addItem(itemSelectA);

                        System.out.println("After add: " + listBins);

                        placement = true;
                    }

                    if (placement)
                        break;
                }
            }

            if(placement)
                break;
        }

        return listBins;
    }
}
