package rainfallanalyser.cp2406assignment;

import java.io.*;

import org.apache.commons.csv.*;

public class RainfallAnalyser {

    /**
     * CP2406 Assignment - Samuel Healion
     *
     *
     * This program will get a .csv file containing rainfall data that the user specifies.
     *
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Rainfall Analyser");
        System.out.println("This program analyses the rainfall data given from various sources like BOM");
        System.out.println("It will then return the extracted Total Monthly Rainfall for the data set");
        System.out.println("as well as the minimum and maximum rainfall that occurred that month.");


//        String filename = getRainfallData();
        String filename = "MountSheridanStationCNS.csv";
        analyseRainfallData(filename);
    }

    private static void analyseRainfallData(String fileName) {
        int currentYear = 0;
        int currentMonth = 0;
        double monthlyTotal = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;

        try {
            Reader reader = new FileReader("./rainfalldata/" + fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Product Code", "Bureau of Meteorology station number", "Year", "Month", "Day",
                    "Rainfall", "Period of Measurement", "Quality").withSkipHeaderRecord().parse(reader);

            for (CSVRecord record : records) {
                // Get the data from each row of the Rainfall Data CSV file
                String yearText = record.get("Year");
                String monthText = record.get("Month");
                String dayText = record.get("Day");
                String rainfallText = record.get("Rainfall");

                // Analyse the data and convert it into the expected output format
                try {
                    int year = Integer.parseInt(yearText);
                    int month = Integer.parseInt(monthText);
                    int day = Integer.parseInt(dayText);
                    double rainfall = Double.parseDouble(rainfallText);

                    if (month != currentMonth) {
//                        System.out.println("Total rainfall for " + currentMonth + "/" + yearText + " is: " + monthlyTotal + " millimeters");
//                        System.out.println("Maximum rainfall was: " + maxRainfall + " and Minimum rainfall was: " + minRainfall);
                        System.out.printf("%d,%d,%1.2f,%1.2f,%1.2f\n", year, month, monthlyTotal, minRainfall, maxRainfall);

                        currentYear = year;
                        currentMonth = month;
                        monthlyTotal = 0;
                        maxRainfall = 0.0;
                        minRainfall = Double.POSITIVE_INFINITY;
                    } else {
                        monthlyTotal += rainfall;
                        if (rainfall > maxRainfall)
                            maxRainfall = rainfall;
                        else if (rainfall < minRainfall)
                            minRainfall = rainfall;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a list of available rainfall data sets to be analysed.
     * Allows the user to pick which data set to analyse from this list.
     * @return Name of the file to be analysed
     */
    private static String getRainfallData() {
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
    }
}
