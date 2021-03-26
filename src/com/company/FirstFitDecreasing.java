package com.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FirstFitDecreasing implements ListItemsOrderStrategy{

    @Override
    public ArrayList<Item> orderList(ArrayList<Item> listItems) {
        return (ArrayList<Item>) listItems.stream().sorted((item, item2) -> Integer.compare(item2.getSize(), item.getSize())).collect(Collectors.toList());
    }
}
