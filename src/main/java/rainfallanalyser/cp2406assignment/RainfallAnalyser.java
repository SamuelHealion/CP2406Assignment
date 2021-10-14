package rainfallanalyser.cp2406assignment;

import java.io.*;
import java.util.Objects;

import org.apache.commons.csv.*;

public class RainfallAnalyser {

    /**
     * CP2406 Assignment - Samuel Healion
     * This program will get a .csv file containing rainfall data that the user specifies.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Rainfall Analyser");
        System.out.println("This program analyses the rainfall data given from various sources like BOM");
        System.out.println("It will then return the extracted Total Monthly Rainfall for the data set");
        System.out.println("as well as the minimum and maximum rainfall that occurred that month.");

        try {
//        String filename = getFileName();
            String filename = "MountSheridanStationCNS.csv";
            analyseRainfallData(filename);
        } catch (Exception e) {
            System.out.println("Error: there was an issue");
            System.out.println(e.getMessage());
        }
    }

    private static String getSavePath(String filename) {
        String[] filenameElements = filename.trim().split("\\.");
        return "./rainfalldata_analysed/" + filenameElements[0] + "_analysed.csv";
    }

    private static void analyseRainfallData(String fileName) throws Exception {


        Reader reader = new FileReader("./rainfalldata/" + fileName);
//        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Product Code", "Bureau of Meteorology station number", "Year", "Month", "Day",
//                "Rainfall amount (millimetres)", "Period over which rainfall was measured (days)", "Quality").parse(reader);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

        // Set the output file based on the file being analysed
        TextIO.writeFile(getSavePath(fileName));
        TextIO.putln("year,month,total,minimum,maximum");

        int year, month, day;
        double rainfall;
        int currentYear = 0;
        int currentMonth = 1;
        double monthlyTotal = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;

        for (CSVRecord record : records) {
            // Get the data from each row of the Rainfall Data CSV file
            String yearText = record.get("Year");
            String monthText = record.get("Month");
            String dayText = record.get("Day");
            String rainfallText = record.get("Rainfall amount (millimetres)");

            // Analyse the data and convert it into the expected output format
            year = Integer.parseInt(yearText);
            month = Integer.parseInt(monthText);
            day = Integer.parseInt(dayText);

            // Check if there is data for rainfall, otherwise assume zero
            rainfall = Objects.equals(rainfallText, "") ? 0 : Double.parseDouble(rainfallText);

            // Check to see if it's the next month
            if (month != currentMonth) {
                writeMonthsData(monthlyTotal, minRainfall, maxRainfall, currentYear, currentMonth);
                currentYear = year;
                currentMonth = month;
                monthlyTotal = 0;
                maxRainfall = 0.0;
                minRainfall = Double.POSITIVE_INFINITY;
            }

            // Update the total for the month
            monthlyTotal += rainfall;
            if (rainfall > maxRainfall)
                maxRainfall = rainfall;
            if (rainfall < minRainfall)
                minRainfall = rainfall;
        }
    }

    private static void writeMonthsData(double monthlyTotal, double minRainfall, double maxRainfall, int year, int month) {
        TextIO.putf("%d,%d,%1.2f,%1.2f,%1.2f\n", year, month, monthlyTotal, minRainfall, maxRainfall);
    }

    /**
     * Get a list of available rainfall data sets to be analysed.
     * Allows the user to pick which data set to analyse from this list.
     * @return Name of the file to be analysed
     */
    private static String getFileName() {
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
        return fileName;
    } // End getFileName
}
