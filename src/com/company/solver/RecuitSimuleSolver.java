package com.company.solver;

import com.company.Solution;

import java.util.List;

public class RecuitSimuleSolver extends Solver {


    public RecuitSimuleSolver(Solution init) {
        super(init);
    }

    public Solution solve(double t0, int n1, int n2, int nbVoisins, double mu) {
//        System.out.println("SOLVE fitness init: " + init.getFitness());
        Solution xmin = init;
//        System.out.println("Xmin debut: " + Arrays.toString(xmin.getAssignedBin()));
        Solution xi = xmin;
//        System.out.println("Xi debut: " + Arrays.toString(xi.getAssignedBin()));
//        System.out.println("Bins debut: " + xi.getListBins());
        //int fmin = xmin.fitness();
        int i=0;
        for(int k=0; k<n1; k++){
//            System.out.println("---------------------------------------- Tour N1 ------------------------------------");
            for(int l=1; l<n2; l++){
                // Randomly select y € V(xi)
//                System.out.println("---------------------------------------- Tour N2 ------------------------------------");
                List<Solution> voisins = genererVoisins(xi, nbVoisins);
//                System.out.println("Fitness voisin 0: " + voisins.get(0).getFitness());
//                System.out.println("Bins voisin 0: " + voisins.get(0).getListBins());
                int yi = (int)(Math.random() * voisins.size()-1);

                Solution y = voisins.get(yi);
                yExplore.add(y.getFitness()+0.0);
                int deltaF = y.getFitness() - xi.getFitness();
                if(deltaF <= 0){
                    xi = y;
                    if(xi.getFitness()<xmin.getFitness()){
                        xmin = xi;

                    }

                }else{
                    double p = Math.random();

                    if(p<=Math.exp(-(deltaF/t0))){
                        xi = y;

                    }
                }
                i++;
                this.x.add(i + 0.0);
                this.y.add(xi.getFitness()+0.0);
                t0 = mu*t0;

            }
            // System.out.println(Arrays.toString(xi.getAssignedBin()) + " " + xi);

        }
        return xmin;
    }


}
