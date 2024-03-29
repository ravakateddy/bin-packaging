package com.company.generator;

import java.util.List;

public class OneItemOneBinGeneratorStrategy implements GeneratorStrategy {
    @Override
    public int[] generate(List<Integer> items, int sizeOfBin, List<Integer> listBin) {

        int[] assignedBin = new int[items.size()];
        for (int i = 0; i < items.size(); i++) {
            listBin.add(sizeOfBin - items.get(i));
            assignedBin[i] = i;
        }

        return assignedBin;
    }
}
