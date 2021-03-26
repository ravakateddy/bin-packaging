package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


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
