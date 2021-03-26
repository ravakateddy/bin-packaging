package com.company;

import java.util.ArrayList;

public class SimpleOrder implements ListItemsOrderStrategy {

    @Override
    public ArrayList<Item> orderList(ArrayList<Item> listItems) {
        return listItems;
    }
}
