package com.company;

import java.util.List;

public class Service {
    
    public List<Bin> cloneListBin(List<Bin> init, List<Bin> copie) {
        
        for (int i = 0; i < init.size(); i++) {
            copie.add(new Bin(init.get(i).getCapacity()));

            for (int j=0; j < init.get(i).getListItems().size(); j++) {
                copie.get(i).addItem(new Item(init.get(i).getListItems().get(j).getSize()));
            }
        }
        
        return copie;
    }
}
