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

        System.out.println("This program analyses the rainfall data given from various sources like BOM");
        System.out.println("The files available are:\n");

        File f = new File("./rainfalldata");
        String[] pathNames = f.list();

        assert pathNames != null;
        for (int i = 0; i < pathNames.length; i++) {
            System.out.println((i+1) + ": " + pathNames[i]);
        }

        System.out.println("\nWhich file would you like to analyse? Enter the corresponding number");

//        int fileNumber;
//        String fileName;
//        while (true) {
//            // Check that the selected file is valid. TextIO handles a non-Int input
//            try {
//                fileNumber = TextIO.getInt();
//                fileName = pathNames[fileNumber - 1];
//                break;
//            }
//            catch (ArrayIndexOutOfBoundsException e) {
//                System.out.println("That is outside of the range of available data files to analyse.");
//                System.out.println("Please choose another one");
//            }
//        }

        int currentYear = 0, currentMonth = 0;

        double monthlyTotal = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;

        try {
//            Reader reader = new FileReader("./rainfalldata/" + fileName);
            Reader reader = new FileReader("./rainfalldata/MountSheridanStationCNS.csv");
//            System.out.println(pathNames[fileNumber - 1]);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Product Code", "Bureau of Meteorology station number", "Year", "Month", "Day",
                    "Rainfall", "Period of Measurement", "Quality").parse(reader);
            for (CSVRecord record : records) {
                // Get the data from each row of the Rainfall Data CSV file
                String yearText = record.get("Year");
                String monthText = record.get("Month");
                String dayText = record.get("Day");
                String rainfallText = record.get("Rainfall");
//                System.out.println(dayText+"/"+monthText+"/"+yearText + " received " + rainfallText + " millimeters of rain");

                try {
                    int year = Integer.parseInt(yearText);
                    int month = Integer.parseInt(monthText);
                    int day = Integer.parseInt(dayText);
                    double rainfall = Double.parseDouble(rainfallText);

                    if (month != currentMonth) {
                        System.out.println("Total rainfall for " + currentMonth + "/" + yearText + " is: " + monthlyTotal + " millimeters");
                        System.out.println("Maximum rainfall was: " + maxRainfall + " and Minimum rainfall was: " + minRainfall);
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
//                    System.out.println("Error");
                }

//                try {
//                    if (currentYear != Integer.parseInt(yearText)) {
//                        currentYear = Integer.parseInt(yearText);
//                        currentMonth = Integer.parseInt(monthText);
//                        monthlyTotal = 0;
//                    } else {
//                        if (currentMonth != Integer.parseInt(monthText)) {
//                            System.out.println("Total rainfall for " + currentMonth +"/"+ yearText + " is: " + monthlyTotal + " millimeters");
//                            currentMonth = Integer.parseInt(monthText);
//                            monthlyTotal = 0;
//                        }
//                        try {
//
//                            monthlyTotal += Double.parseDouble(rainfallText);
////                            System.out.println(Double.parseDouble(rainfallText));
//                        } catch (NumberFormatException e) {
//                            // When there is no number in the data (or is an invalid string) treat it as
//                            // a zero entry and continue with parsing the file
////                                System.out.println("No rainfall data");
//                        }
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Error converting to a number.");
//                    // TODO Work out how to parse the first line without triggering this error
//                    System.out.println(e.getMessage());
//                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
