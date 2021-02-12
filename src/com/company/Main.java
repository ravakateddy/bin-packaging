package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int sizeOfBin = 0;

    public static void extractBin(String file, ArrayList<Item> listItems){
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 1){
                    sizeOfBin = Integer.parseInt(data.split(" ")[0]);
                }else if(lineCounter > 1){
                    listItems.add(new Item(Integer.parseInt(data)));
                }
                lineCounter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<Item> listItems = new ArrayList<>();
        ArrayList<Bin> listBins = new ArrayList<>();

        extractBin("src/com/company/test.txt", listItems);
        System.out.println("Capacité des bins: "+ sizeOfBin);
        listBins.add(new Bin(sizeOfBin));

        for(int i = 0; i < listItems.size(); i++){
            Item currentItem = listItems.get(i);

            int j = 0;
            Bin currentBin = listBins.get(j);
//            System.out.println(currentItem.size);
//            System.out.println(currentBin.capacity + " " + currentBin.total);
//            System.out.println(currentBin.addItem(currentItem));
            while(!currentBin.addItem(currentItem)){
                if(j+1 == listBins.size()){
//                    System.out.println("Pas d'autre élement après, ajout d'un bin");
                    listBins.add(new Bin(sizeOfBin));
                }
                j++;
            }
        }

        System.out.println(listBins);
    }

}
