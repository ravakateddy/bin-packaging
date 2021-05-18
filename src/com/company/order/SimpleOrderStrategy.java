package com.company.order;

import com.company.Item;

import java.util.List;

public class SimpleOrderStrategy implements ListItemsOrderStrategy {

    @Override
    public List<Item> orderList(List<Item> listItems) {
        return listItems;
    }
}
