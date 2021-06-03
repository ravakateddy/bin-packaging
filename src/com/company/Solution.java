package com.company;


import com.company.generator.GeneratorStrategy;
import com.company.neighboor.NeighbourStrategy;
import com.company.order.ListItemsOrderStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution implements Comparable{
    private int capacity;
    private List<Integer> listItems;
    private int[] assignedBin;
    private List<Integer> listBins;
    private int[] neighbour;

    public Solution(){
        this.listItems = new ArrayList<>();
        listBins = new ArrayList<>();
    }

    public Solution(int capacity){
        this.listItems = new ArrayList<>();
        listBins = new ArrayList<>();
        this.capacity = capacity;
    }

    public void getSolutionFromFile(String file){

        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 0){
                    capacity = Integer.parseInt(data.split(" ")[0]);
                }else if(lineCounter >= 1){
                    listItems.add(Integer.parseInt(data));
                }
                lineCounter++;
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erreur sur l'extraction du fichier");
            e.printStackTrace();
        }
    }

    public List<Integer> getListItems() {
        return listItems;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setAssignedBin(int[] assignedBin) {
        this.assignedBin = assignedBin;
    }

    public int[] getAssignedBin() {
        return assignedBin;
    }

    public void setListItems(List<Integer> listItems) {
        this.listItems = listItems;
    }

    public void setListBins(List<Integer> listBins) {
        this.listBins = listBins;
    }

    public List<Integer> getListBins(){
        return listBins;
    }

    public int getFitness(){
        int sum = 0;
        for(int i=0; i<listBins.size(); i++){
            if(listBins.get(i)!=1000){
                sum+=Math.pow(listBins.get(i),2);
            }

        }
        return sum;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "assignedBin=" + getFitness() +
                '}';
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Object o) {
        Solution s = (Solution) o;
        return Integer.compare( this.getFitness(), s.getFitness());
    }
}
