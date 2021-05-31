package com.company;


import com.company.generator.GeneratorStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private int capacity;
    private ArrayList<Integer> listItems;
    private int[] assignedBin;
    private List<Integer> listBins;
    GeneratorStrategy generatorStrategy;
    private int[] neighbour;

    public Solution(){
        this.listItems = new ArrayList<>();
        listBins = new ArrayList<>();
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

    public ArrayList<Integer> getListItems() {
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

    public void setListItems(ArrayList<Integer> listItems) {
        this.listItems = listItems;
    }

    public void setGeneratorStrategy(GeneratorStrategy generatorStrategy) {
        this.generatorStrategy = generatorStrategy;
        setAssignedBin(this.generatorStrategy.generate(listItems, capacity, listBins));

    }

    public List<Integer> getListBins(){
        return listBins;
    }




}
