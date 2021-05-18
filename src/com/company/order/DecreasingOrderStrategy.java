package com.company.order;

import com.company.Item;
import java.util.List;
import java.util.stream.Collectors;

public class DecreasingOrderStrategy implements ListItemsOrderStrategy {

    @Override
    public List<Item> orderList(List<Item> listItems) {
        return listItems.stream().sorted((item, item2) -> Integer.compare(item2.getSize(), item.getSize())).collect(Collectors.toList());
    }
}
