package com.company.solver;

import com.company.Bin;
import com.company.Solution;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecuitSimuleSolver extends Solver {


    public RecuitSimuleSolver(Solution init) {
        super(init);
    }

    public Solution solve(double t0, int n1, int n2, int nbVoisins, double mu) {
        System.out.println(init.getFitness());
        Solution xmin = init;
        Solution xi = xmin;
        //int fmin = xmin.fitness();
        for(int k=0; k<n1; k++){
            for(int l=1; l<n2; l++){
                // Randomly select y € V(xi)
                List<Solution> voisins = genererVoisins(xi, nbVoisins);
                int yi = (int)(Math.random() * voisins.size()-1);
                Solution y = voisins.get(yi);
                int deltaF = y.getFitness() - init.getFitness();
                if(deltaF <= 0){
                    xi = y;
                    if(xi.getFitness()<xmin.getFitness()){
                        xmin = xi;
                        System.out.println("change xi bas" + xi);
                    }
                }else{
                    double p = Math.random();

                    if(p<Math.exp(-(deltaF/t0))){
                        System.out.println(p);
                        System.out.println("change xi haut" + xi);
                        xi = y;
                    }
                }

            }
            // System.out.println(Arrays.toString(xi.getAssignedBin()) + " " + xi);
            t0 = mu*t0;
        }
        return xmin;
    }

    protected List<Solution> genererVoisins(Solution s, int nb){
        List<Solution> solutions = new ArrayList<>();
        for(int i=0; i<nb; i++){
            Solution solution = new Solution(s.getCapacity());
            solution.setListBins(new ArrayList<>(s.getListBins()));
            solution.setListItems(new ArrayList<>(s.getListItems()));

            int[] voisin = new int[s.getAssignedBin().length];
            System.arraycopy(s.getAssignedBin(), 0, voisin, 0, s.getAssignedBin().length);
            solution.setAssignedBin(voisin);
            solution.setAssignedBin(this.neighbourStrategy.move(solution.getAssignedBin(), solution.getListItems(), solution.getListBins(), init.getCapacity()));
            solutions.add(solution);
        }
        return solutions;
    }
}
