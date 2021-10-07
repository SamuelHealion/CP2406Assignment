package rainfallanalyser.cp2406assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class rainfallAnalyser {

    /**
     * CP2406 Assignment - Samuel Healion
     *
     *
     * This program will get a .csv file containing rainfall data that the user specifies.
     *
     */
    public static void main(String[] args) {

        System.out.println("This program analyses the rainfall data given from various sources like BOM");
        System.out.println("Please pick which file you would like analysed:\n");

        File f = new File("./rainfalldata");
        String[] pathNames = f.list();

        assert pathNames != null;
        for (String pathname : pathNames) {
            System.out.println(pathname);
        }

        System.out.println();

        try {
            Reader reader = new FileReader("./rainfalldata/MountSheridanStationCNS.csv");
            System.out.println(reader);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}
