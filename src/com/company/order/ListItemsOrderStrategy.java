package com.company.order;

import com.company.Item;
import java.util.List;

public interface ListItemsOrderStrategy {

    List<Integer> orderList(List<Integer> listItems);
}
