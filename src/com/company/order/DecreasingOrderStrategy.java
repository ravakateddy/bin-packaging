package com.company.order;

import java.util.List;
import java.util.stream.Collectors;

public class DecreasingOrderStrategy implements ListItemsOrderStrategy {

    @Override
    public List<Integer> orderList(List<Integer> listItems) {
        return listItems.stream().sorted((item, item2) -> Integer.compare(item2, item)).collect(Collectors.toList());
    }
}
