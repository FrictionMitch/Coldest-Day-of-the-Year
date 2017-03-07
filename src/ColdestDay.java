import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;

public class ColdestDay {

//    String fileName = null;

    public CSVRecord hottestHourInFile(CSVParser parser) {
        // start with largestSoFar as nothing
        CSVRecord largestSoFar = null;

        // for each row (currentRow)
        for(CSVRecord currentRow : parser) {

            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();

        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
//            if(largestSoFar == null) {
//                largestSoFar = currentRow;
//            } else {
//                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
//                double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
//                if (currentTemp > largestTemp) {
//                    largestSoFar = currentRow;
//                }
//            }
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        if(largestSoFar == null) {
            largestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));

            if(currentTemp > largestTemp) {
                largestSoFar = currentRow;
            }
        }

        return largestSoFar;
    }

    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord smallestSoFar = null;

        for(CSVRecord currentRow : parser) {
            smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }

    public CSVRecord coldestInManyDays() {
        CSVRecord smallestSoFar = null;
        DirectoryResource dr = new DirectoryResource();

        for(File file : dr.selectedFiles()){
            FileResource fr = new FileResource(file);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
//            fileName = file.getName();
        }
        return smallestSoFar;
    }

    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        if(smallestSoFar == null) {
            smallestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));

            if(currentTemp < smallestTemp && currentTemp > -999) {
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }

    public void testHottestInDay() {
        FileResource fileResource = new FileResource("Temperatures/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fileResource.getCSVParser());
        String time = "TimeEST";
//        if (largest.get("TimeEST") == null)
//        {
//            time = "TimeEDT";
//        }
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
//                            " at " + largest.get(time));
                " at " + largest.get("TimeEST"));
    }

    public void testHottestInManyDays() {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temp was " + largest.get("TemperatureF") +
                " at " + largest.get("DateUTC"));
    }

    public void testColdestInDay() {
        FileResource fileResource = new FileResource("weather/nc_weather/2015/weather-2015-01-01.csv");
        CSVRecord smallest = coldestHourInFile(fileResource.getCSVParser());
        System.out.println("The coldest temperature was " + smallest.get("TemperatureF") +
                           " at " + smallest.get("TimeEST"));
    }


    public void testColdestInManyDays() {
        CSVRecord smallest = coldestInManyDays();
        System.out.println("coldest temp was " + smallest.get("TemperatureF") +
                           " at " + smallest.get("DateUTC"));
    }

//    public String fileWithColdestTemperature() {
    public void fileWithColdestTemperature() {
        CSVRecord smallestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        String fileName = null;

        for(File file : dr.selectedFiles()){
            FileResource fr = new FileResource(file);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
            fileName = file.getName();
        }
//        return fileName;
        return smallestSoFar;
        System.out.println("The coldest day was in file: " + fileName);
        FileResource fileResource = new FileResource(fileName);
        CSVRecord smallestDay = coldestHourInFile(fileResource.getCSVParser());
        System.out.println("The coldest temperature on that day was: " + smallestDay.get("TemperatureF") +
                           " at " + smallestDay.get("DateUTC"));
    }

    public void testColdestFileName() {
//        String name = fileWithColdestTemperature();
//        System.out.println(name);
        CSVRecord smallest = fileWithColdestTemperature();

    }
}