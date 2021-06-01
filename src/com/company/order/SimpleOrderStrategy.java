package com.company.order;

import com.company.Item;

import java.util.List;

public class SimpleOrderStrategy implements ListItemsOrderStrategy {

    @Override
    public List<Integer> orderList(List<Integer> listItems) {
        return listItems;
    }
}
