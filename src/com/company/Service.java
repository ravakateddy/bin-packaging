package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    private  int sizeOfBin;
    private ArrayList<Bin> listBins;
    private ArrayList<Item> listItems;
    private ListItemsOrderStrategy listItemsOrderStrategy;


    public Service(){
        listBins = new ArrayList();
        listItems = new ArrayList();
    }

    public ArrayList<Bin> fillBins() {
        System.out.println("Capacité des bins: "+ sizeOfBin);
        listBins.add(new Bin(sizeOfBin));

        for(int i = 0; i < listItems.size(); i++){
            Item currentItem = listItems.get(i);

            int j=0;
            Bin currentBin = listBins.get(j);
            while(!currentBin.addItem(currentItem)){
//                System.out.println("Pas de place dans le bin actuel");
                if(j+1 == listBins.size()) {
                    listBins.add(new Bin(sizeOfBin));
//                    System.out.println("Ajout d'un bin");
                }
                j++;
                currentBin = listBins.get(j);
            }

//            System.out.println("Item ajouté");
            System.out.println(listBins);
        }
        return listBins;
    }

    public ArrayList<Item> extractItemFromFile(String file){

        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 0){
                    this.setSizeOfBin(Integer.parseInt(data.split(" ")[0]));
                }else if(lineCounter > 1){
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

    public void setListItemsOrderStrategy(ListItemsOrderStrategy strategy){
        listItems = strategy.orderList(listItems);
    }

    public ArrayList<Bin> getListBins() {
        return listBins;
    }
    public void setListBins(ArrayList<Bin> listBins) {
        this.listBins = listBins;
    }
    public ArrayList<Item> getListItems() {
        return listItems;
    }
    public void setListItems(ArrayList<Item> listItems) {
        this.listItems = listItems;
    }
    public int getSizeOfBin() {
        return sizeOfBin;
    }
    public void setSizeOfBin(int sizeOfBin) {
        this.sizeOfBin = sizeOfBin;
    }
}
