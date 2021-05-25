package com.company.neighboor;

import com.company.Bin;
import com.company.Item;

import java.util.List;

public class MoveOneItemStrategy implements NeighbourStrategy {
    @Override
    public List<Bin> move(List<Bin> listBins) {

        int A = (int)(Math.random() * listBins.size());

        //récupération capacité bin
        int capacityBin = listBins.get(0).getCapacity();

        List<Item> listItemOfBin = listBins.get(A).getListItems();
        Item itemSelect = listItemOfBin.get((int)(Math.random() * listItemOfBin.size()));

        boolean placement = false;
        for(int i=0; i < listBins.size(); i++){
            if(i != A){
                if(listBins.get(i).addItem(itemSelect)){
                    listBins.get(A).remove(itemSelect);

                    if(listBins.get(A).getTotal() == 0) {
                        listBins.remove(A);
                    }

                    placement = true;
                }
            }

            if(placement){
                System.out.println("Element placé " + i + " break for");
                break;
            }
        }

        if(!placement) {

            listBins.get(A).remove(itemSelect);

            if((listBins.get(A).getTotal() - itemSelect.getSize()) == 0) {
                listBins.remove(A);
            }

            listBins.add(new Bin(capacityBin));
            listBins.get(listBins.size()-1).addItem(itemSelect);
        }

        return listBins;
    }
}