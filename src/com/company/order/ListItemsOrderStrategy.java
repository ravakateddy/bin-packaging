package com.company.order;

import com.company.Item;
import java.util.List;

public interface ListItemsOrderStrategy {

    List<Item> orderList(List<Item> listItems);
}
