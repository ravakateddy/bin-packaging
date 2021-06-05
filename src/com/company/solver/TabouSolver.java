package com.company.solver;

import com.company.Solution;

import java.awt.desktop.SystemEventListener;
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
            List<Solution> c = genererVoisins(xi, 10);
//            System.out.println("Nombre élément de T: "+ T.size());
//            System.out.println("Nombre voisins before remove T: " + c.size());

//            System.out.println("Nombre de voisins pour itération " + i + ": " + c.size());
            if(c.size() == 0){
//                System.out.println("Aucun voisin");
                yExplore.add(xi.getNumberOfBinUsed() + 0.0);
            }else {

//                System.out.println("Nombre élément de T: "+ T.size());
//                System.out.println("Nombre voisins before remove T: " + c.size());
                T.forEach(solution -> System.out.println(solution.getListBins()));
                for (int sol = 0; sol < c.size(); sol++) {
                    boolean oldNeighboor = false;
                    for (int t = 0; t < T.size(); t++) {
                        if(c.get(sol).getListBins().equals(T.get(t).getListBins())) {
                            oldNeighboor = true;
                            break;
                        }
                    }
                    if(oldNeighboor){
                        c.remove(sol);
                    }
                }

//                System.out.println("Nombre voisins after remove T: " + c.size());

//            System.out.println("Nombre voisins after remove T: " + c.size());
                if(c.size() == 0){
//                    System.out.println("Pas de voisin différent");
                    yExplore.add(xi.getNumberOfBinUsed() + 0.0);
                }else {
                    Solution xi1 = c.stream().min(Solution::compareTo).get();

                    yExplore.add(xi1.getNumberOfBinUsed() + 0.0);
//            System.out.println("Voisin choisi: " + xi1.getListBins());
                    int deltaF = xi1.getFitness() - xi.getFitness();
                    if (deltaF >= 0) {
                        T.add(xi1);
                        if (T.size() == maxT + 1) {
                            T.remove(0);
                        }

                    }
                    if (xi1.getFitness() < xmin.getFitness()) {
                        xmin = xi1;
                    }

                    xi = xi1;
                }
            }

            this.x.add(i + 0.0);
            this.y.add(xi.getNumberOfBinUsed() + 0.0);

        }
        return xmin;
    }
}
