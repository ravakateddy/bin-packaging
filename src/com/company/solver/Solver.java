package com.company.solver;

import com.company.Solution;
import com.company.generator.GeneratorStrategy;
import com.company.neighboor.NeighbourStrategy;
import com.company.order.ListItemsOrderStrategy;

import java.util.ArrayList;
import java.util.List;

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

    protected List<Solution> genererVoisins(Solution s, int nb){
        List<Solution> solutions = new ArrayList<>();
//        System.out.println("Solution s (xi): " + Arrays.toString(s.getAssignedBin()));
        for(int i=0; i<nb; i++){
            Solution solution = new Solution(s.getCapacity());
            solution.setListBins(new ArrayList<>(s.getListBins()));
            solution.setListItems(new ArrayList<>(s.getListItems()));

            int[] voisin = new int[s.getAssignedBin().length];
            System.arraycopy(s.getAssignedBin(), 0, voisin, 0, s.getAssignedBin().length);
            solution.setAssignedBin(voisin);
            solution.setAssignedBin(this.neighbourStrategy.move(solution.getAssignedBin(), solution.getListItems(), solution.getListBins(), init.getCapacity()));
//            System.out.println("Solution obtenue: " + Arrays.toString(solution.getAssignedBin()));
//            System.out.println("Etat s (xi): " + Arrays.toString(s.getAssignedBin()));
            solutions.add(solution);
        }
        return solutions;
    }
}
