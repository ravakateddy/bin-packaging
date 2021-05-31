package com.company.generator;

import com.company.Bin;
import com.company.Item;

import java.util.List;

public interface GeneratorStrategy {

    /**
     *
     * @param items : La liste des items
     * @param sizeOfBin : La capacité d'un Bin
     * @return
     */
    public int[] generate(List<Integer> items, int sizeOfBin, List<Integer> listBins);
}
