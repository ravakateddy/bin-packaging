package com.company.generator;

import com.company.Bin;
import com.company.Item;

import java.util.List;

public interface GeneratorStrategy {

    public List<Bin> generate(List<Item> items, int sizeOfBin);
}
