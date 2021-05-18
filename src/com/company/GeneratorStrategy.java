package com.company;

import java.util.List;

public interface GeneratorStrategy {
    public List<Bin> generate(List<Item> items);
}
