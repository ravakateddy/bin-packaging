package com.company.generator;

import com.company.Bin;
import com.company.Item;
import java.util.ArrayList;
import java.util.List;

public class OneItemOneBinGeneratorStrategy implements GeneratorStrategy {
    @Override
    public List<Bin> generate(List<Item> items, int sizeOfBin) {

        List<Bin> bins = new ArrayList<>();
        for(int i=0; i<items.size(); i++){
            bins.add(new Bin(sizeOfBin));
            bins.get(i).addItem(items.get(i));
        }
        return bins;
    }
}
