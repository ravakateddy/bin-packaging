package com.company.solver;

import com.company.Solution;

import java.util.ArrayList;
import java.util.List;

public class TabouSolver extends Solver{
    public TabouSolver(Solution init) {
        super(init);
    }

    public Solution solve(Solution x0, int maxIter, int maxT){

        Solution xmin = x0;
        Solution xi = x0;
        List<Solution> T = new ArrayList<>();
        for (int i=0; i<maxIter; i++){
            List<Solution> c = genererVoisins(xi, 100);
            System.out.println("Nombre élément de T: "+ T.size());
            System.out.println("Nombre voisins before remove T: " + c.size());
            c.removeAll(T);
            System.out.println("Nombre voisins after remove T: " + c.size());

            Solution xi1 = c.stream().min(Solution::compareTo).get();

            System.out.println("Voisin choisi: " + xi1.getListBins());
            int deltaF = xi1.getFitness() - xi.getFitness();
            if(deltaF>=0){
                T.add(xi1);
                if(T.size()==maxT+1){
                    T.remove(0);
                }

            }
            if(xi1.getFitness() < xmin.getFitness()){
                xmin = xi1;
            }

            xi = xi1;

        }
        return xmin;
    }
}
