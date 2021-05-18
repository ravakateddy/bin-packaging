package com.company;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
public class FilesTest{
    @Test
    public void testFiles(){
        Path dir = Paths.get("src/com/company/data");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path file : stream) {
                Service service = new Service();
                //extraction des items et de la taille d'un bin à partir du fichier
                ArrayList<Item> listItems = service.extractItemFromFile(file.toString());
                //répartition des items dans les bins
                ArrayList<Bin> listBins = service.fillBins();
                System.out.println("Nombre minimum de bins pour " + file.toString() + " : " + listBins.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
