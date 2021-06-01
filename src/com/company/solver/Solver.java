package com.company.solver;

import com.company.Solution;
import com.company.generator.GeneratorStrategy;
import com.company.neighboor.NeighbourStrategy;
import com.company.order.ListItemsOrderStrategy;

public abstract class Solver {
    protected Solution init;
    protected ListItemsOrderStrategy listItemsOrderStrategy;
    protected GeneratorStrategy generatorStrategy;
    protected NeighbourStrategy neighbourStrategy;
    public Solver(Solution init){
        this.init = init;
    }

    public void setInit(Solution init) {
        this.init = init;
    }

    public void setListItemsOrderStrategy(ListItemsOrderStrategy listItemsOrderStrategy) {
        this.listItemsOrderStrategy = listItemsOrderStrategy;
        init.setListItems(listItemsOrderStrategy.orderList(init.getListItems()));
    }

    public void setGeneratorStrategy(GeneratorStrategy generatorStrategy) {
        this.generatorStrategy = generatorStrategy;
        init.setAssignedBin(generatorStrategy.generate(init.getListItems(), init.getCapacity(), init.getListBins()));
    }

    public void setNeighbourStrategy(NeighbourStrategy neighbourStrategy) {
        this.neighbourStrategy = neighbourStrategy;
    }
}
