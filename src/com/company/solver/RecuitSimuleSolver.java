package com.company.solver;

import com.company.Bin;
import com.company.Solution;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;

import java.util.ArrayList;
import java.util.List;

public class RecuitSimuleSolver {
    public List<Bin> solve(Solution x0, double t0) {

        int n1 = 50;
        int n2=10;
        Solution xmin = x0;
        //int fmin = xmin.fitness();
        for(int k=0; k<n1; k++){
            for(int l=1; l<n2; l++){
                // Randomly select y € V(xi)
                List<Solution> voisins = genererVoisins(xmin, 2);
                System.out.println(voisins);

            }
        }
        return null;
    }

    protected List<Solution> genererVoisins(Solution s, int nb){
        List<Solution> solutions = new ArrayList<>();
        MoveOneItemStrategy moveOneItemStrategy = new MoveOneItemStrategy();
        EchangeOneItemStrategy echangeOneItemStrategy = new EchangeOneItemStrategy();
        for(int i=0; i<nb; i++){
            Solution solution = new Solution();
            //solution.setListBins(echangeOneItemStrategy.move(s.getListBins()));
            solutions.add(solution);
        }
        return solutions;
    }
}
