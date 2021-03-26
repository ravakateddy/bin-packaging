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
        Service service = new Service();

        //extraction des items et de la taille d'un bin à partir du fichier
        ArrayList<Item> listItems = service.extractItemFromFile("src/com/company/data/binpack1d_01.txt");

        //répartition des items dans les bins
        ArrayList<Bin> listBins = service.fillBins(listItems);

        System.out.println(listBins);
        System.out.println("Nombre minimum de bins: " + listBins.size());
    }

}
