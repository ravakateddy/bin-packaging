package com.company;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinPack {

    static int capacity, binUsed = 0;
    static int[] assignedBin, sol;

    public static void main(String[] args) {

        int numItems;
        List<Integer> sizes = new ArrayList<>();
        List<Integer> bins = new ArrayList<>();

        //Récupération information fichier
//        String file = "src/com/company/test2.txt";
        String file = "src/com/company/data/binpack1d_001.txt";

        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int lineCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(lineCounter == 0){
                    capacity = Integer.parseInt(data.split(" ")[0]);
                }else if(lineCounter >= 1){
                    sizes.add(Integer.parseInt(data));
                }
                lineCounter++;
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erreur sur l'extraction du fichier");
            e.printStackTrace();
        }

        numItems = sizes.size();

        //order
        sizes = sizes.stream().sorted((integer1, integer2) -> Integer.compare(integer2, integer1)).collect(Collectors.toList());

        bins.add(capacity);

        assignedBin = new int[numItems];
        sol = new int[numItems];

        //generator
        firstFit(sizes, bins);
        oneBinByItem(sizes,bins);



        System.out.println(binUsed);

        System.out.println(Arrays.toString(assignedBin));
        System.arraycopy(assignedBin, 0, sol, 0, sizes.size());
        System.out.println(Arrays.toString(sol));
    }

    static void oneBinByItem(List<Integer> sizes, List<Integer> bins) {

        bins.remove(0);
        for (int i = 0; i < sizes.size(); i++) {
            bins.add(capacity- sizes.get(i));
            assignedBin[i] = i;
        }

        binUsed = bins.size();
    }

    static void firstFit(List<Integer> sizes, List<Integer> bins) {

        for (int item = 0; item < sizes.size(); item++) {
            for (int i = 0; i < bins.size(); i++) {
                if (bins.get(i) >= sizes.get(item)) {
                    bins.set(i, bins.get(i)-sizes.get(item));
                    assignedBin[item] = i;
                    break;
                } else {
                    if (i+1 >= bins.size()) {
                        bins.add(capacity);
                    }
                }
            }
        }

        binUsed = bins.size();

    }

    static void moveOneItem() {
        System.out.println(Arrays.toString(sol));
        System.out.println(Arrays.toString(assignedBin));


    }

}
