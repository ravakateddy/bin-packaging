package com.company;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        Service service = new Service();

        FirstFitDecreasing firstFitDecreasing = new FirstFitDecreasing();
        SimpleOrder simpleOrder = new SimpleOrder();

        //extraction des items et de la taille d'un bin à partir du fichier
        ArrayList<Item> listItems = service.extractItemFromFile("src/com/company/data/binpack1d_01.txt");

//        ArrayList<Item> listItems = service.extractItemFromFile("src/com/company/test2.txt");

//        service.setListItemsOrderStrategy(firstFitDecreasing);
        service.setListItemsOrderStrategy(simpleOrder);

        System.out.println(service.getListItems());

//        répartition des items dans les bins
        ArrayList<Bin> listBins = service.fillBins();

        System.out.println(listBins);
        System.out.println("Nombre minimum de bins: " + listBins.size());
    }

}
