package rainfallanalyser.cp2406assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.*;

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
        System.out.println("The files available are:\n");

        File f = new File("./rainfalldata");
        String[] pathNames = f.list();

        assert pathNames != null;
        for (int i = 0; i < pathNames.length; i++) {
            System.out.println((i+1) + ": " + pathNames[i]);
        }

        System.out.println("\nWhich file would you like to analyse? Enter the corresponding number");

        int fileNumber;
        String fileName;
        while (true) {
            // Check that the selected file is valid. TextIO handles a non-Int input
            try {
                fileNumber = TextIO.getInt();
                fileName = pathNames[fileNumber - 1];
                break;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("That is outside of the range of available data files to analyse.");
                System.out.println("Please choose another one");
            }
        }

        try {
            Reader reader = new FileReader("./rainfalldata/" + fileName);
//            System.out.println(pathNames[fileNumber - 1]);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}
