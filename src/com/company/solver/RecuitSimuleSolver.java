package com.company.solver;

import com.company.Bin;
import com.company.Solution;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;

import java.util.ArrayList;
import java.util.List;

public class RecuitSimuleSolver extends Solver {


    public RecuitSimuleSolver(Solution init) {
        super(init);
    }

    public List<Bin> solve(double t0) {
        System.out.println(init);
        int nbVoisins = 10;
        int n1 = 50;
        int n2=10;
        Solution xmin = init;
        //int fmin = xmin.fitness();
        for(int k=0; k<n1; k++){
            for(int l=1; l<n2; l++){
                // Randomly select y € V(xi)
                List<Solution> voisins = genererVoisins(xmin, nbVoisins);
                Solution y = voisins.get((int)(Math.random() * voisins.size()-1));
                //System.out.println(voisins);
                int deltaF = y.getFitness() - init.getFitness();
                if(deltaF < 0){

                }

            }
        }
        return null;
    }

    protected List<Solution> genererVoisins(Solution s, int nb){
        List<Solution> solutions = new ArrayList<>();
        for(int i=0; i<nb; i++){
            Solution solution = new Solution(s.getCapacity());
            solution.setListBins(new ArrayList<>(s.getListBins()));
            solution.setListItems(new ArrayList<>(s.getListItems()));
            solution.setAssignedBin(s.getAssignedBin());
            solution.setAssignedBin(this.neighbourStrategy.move(solution.getAssignedBin(), solution.getListItems(), solution.getListBins()));
            solutions.add(solution);
        }
        return solutions;
    }
}
