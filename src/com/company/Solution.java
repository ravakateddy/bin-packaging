package com.company;

import com.company.generator.GeneratorStrategy;
import com.company.order.ListItemsOrderStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private  int sizeOfBin;
    private List<Bin> listBins;
    private List<Item> listItems;
    private ListItemsOrderStrategy listItemsOrderStrategy;
    private GeneratorStrategy generatorStrategy;


    public Solution(){
        listBins = new ArrayList();
        listItems = new ArrayList();
    }

    public List<Item> extractItemFromFile(String file){

        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 0){
                    this.setSizeOfBin(Integer.parseInt(data.split(" ")[0]));
                }else if(lineCounter >= 1){
                    listItems.add(new Item(Integer.parseInt(data)));
                }
                lineCounter++;
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erreur sur l'extraction du fichier");
            e.printStackTrace();
        }
        return listItems;
    }
    public List<Bin> getListBins() {
        return listBins;
    }
    public void setListBins(List<Bin> listBins) {
        this.listBins = listBins;
    }
    public List<Item> getListItems() {
        return listItems;
    }
    public void setListItems(List<Item> listItems) {
        this.listItems = listItems;
    }
    public int getSizeOfBin() {
        return sizeOfBin;
    }
    public void setSizeOfBin(int sizeOfBin) {
        this.sizeOfBin = sizeOfBin;
    }
    public void setGeneratorStrategy(GeneratorStrategy generatorStrategy) {
        this.generatorStrategy = generatorStrategy;
        setListBins(generatorStrategy.generate(listItems, sizeOfBin));
    }
    public void setListItemsOrderStrategy(ListItemsOrderStrategy listItemsOrderStrategy) {
        this.listItemsOrderStrategy = listItemsOrderStrategy;
        setListItems(listItemsOrderStrategy.orderList(listItems));
    }
}
