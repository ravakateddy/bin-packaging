package com.company.solver;

import com.company.Solution;
import com.company.generator.GeneratorStrategy;
import com.company.neighboor.EchangeOneItemStrategy;
import com.company.neighboor.MoveOneItemStrategy;
import com.company.neighboor.NeighbourStrategy;
import com.company.order.ListItemsOrderStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solver {
    protected Solution init;
    protected ListItemsOrderStrategy listItemsOrderStrategy;
    protected GeneratorStrategy generatorStrategy;
    protected NeighbourStrategy neighbourStrategy;
    protected ArrayList<Double> x;
    protected ArrayList<Double> y;
    protected ArrayList<Double> yExplore;
    public Solver(Solution init){
        this.init = init;
        x = new ArrayList<>();
        x.add(0.0);
        y = new ArrayList<>();
        yExplore = new ArrayList<>();
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
        int essai = 0;
        for(int i=0; i<nb; i++){
            Solution solution = new Solution(s.getCapacity());
            solution.setListBins(new ArrayList<>(s.getListBins()));
            solution.setListItems(new ArrayList<>(s.getListItems()));

            int[] voisin = new int[s.getAssignedBin().length];
            System.arraycopy(s.getAssignedBin(), 0, voisin, 0, s.getAssignedBin().length);
            solution.setAssignedBin(voisin);
            solution.setCapacity(init.getCapacity());

            NeighbourStrategy neighbourStrategy;
            if(Math.random() > 0.5) {
                neighbourStrategy = new EchangeOneItemStrategy();
                if(s.getListItems().size() == s.getNumberOfBinUsed()){
//                    System.out.println("Move au lieu d'échange car la liste de bins est égale au nombre d'items");
                    neighbourStrategy = new MoveOneItemStrategy();
                }
            } else {
                neighbourStrategy = new MoveOneItemStrategy();
            }

            int[] action = neighbourStrategy.move(solution.getAssignedBin(), solution.getListItems(), solution.getListBins(), solution.getCapacity());

            //si on a réussi à réaliser l'action, on ajoute le voisin à la liste
            if(action != null) {
                solution.setAssignedBin(action);
                solutions.add(solution);
                essai = 0;
            } else {
//                System.out.println("Aucun déplacement possible: " + essai);
                i--;
                //on incrémente le nombre d'essai
                essai++;
            }

            if(essai == 5 || solutions.size() == nb){
//                if(essai == 5)
//                    System.out.println("Nombre d'essai atteint");
//                else {
//                    System.out.println("Nombre de voisins atteints: " + solutions.size());
//                }
                break;
            }
        }

//        solutions.forEach(solution -> System.out.println(solution.getListBins()));
//        System.out.println("SOLVER Nombre de voisins trouvés: " + solutions.size());

        return solutions;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public ArrayList<Double> getY() {
        return y;
    }

    public List<Double> getYExplore() {
        return yExplore;
    }
}
