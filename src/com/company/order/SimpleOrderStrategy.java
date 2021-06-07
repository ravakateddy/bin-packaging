package com.company.order;

import java.util.List;

public class SimpleOrderStrategy implements ListItemsOrderStrategy {

    @Override
    public List<Integer> orderList(List<Integer> listItems) {
        return listItems;
    }
}
