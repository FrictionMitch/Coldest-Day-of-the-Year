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

    public CSVRecord getLowestHumidityOfTwo (CSVRecord currentRow, CSVRecord lowestSoFar) {
        if(lowestSoFar == null) {
            lowestSoFar = currentRow;
        } else {
            double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
            double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));

            if(currentHumidity < lowestHumidity) {
                lowestSoFar = currentRow;
            }
        }
        return lowestSoFar;
    }



//    public String fileWithColdestTemperature() {
    public CSVRecord fileWithColdestTemperature() {
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
        System.out.println("The coldest day was in file: " + fileName);
        String location = "weather/nc_weather/2014/" + fileName;
        FileResource fileResource = new FileResource(location);
        CSVRecord smallestDay = coldestHourInFile(fileResource.getCSVParser());
        System.out.println("The coldest temperature on that day was: " + smallestDay.get("TemperatureF") +
                           " at " + smallestDay.get("DateUTC"));
        System.out.println("All the temperatures on that day (the coldest day) were:");

        for(CSVRecord curentRow : fileResource.getCSVParser()) {
            System.out.println((curentRow.get("DateUTC")) + " : " + (curentRow.get("TemperatureF")));
        }
        return smallestSoFar;
    }



    public CSVRecord lowestHumidityInFile (CSVParser parser) {
        double currentHumidity;
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if(currentRow.get("Humidity").contains("N/A")){
                continue;
            }
            if (lowestSoFar == null) {
                lowestSoFar = currentRow;
            } else {

                currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));

                if (currentHumidity < lowestHumidity ) {
                    lowestSoFar = currentRow;
                }
            }
        }
            return lowestSoFar;
    }

    public CSVRecord lowestHumidityInManyDays() {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();

        for(File file : dr.selectedFiles()){
            FileResource fr = new FileResource(file);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            lowestSoFar = getLowestHumidityOfTwo(currentRow, lowestSoFar);
//            fileName = file.getName();
        }
        return lowestSoFar;
    }

    public double averageTemperatureInFile(CSVParser parser) {
        int numberOfRecords = 0;
        double temperature = 0;
        for (CSVRecord record : parser) {
            temperature += Double.parseDouble(record.get("TemperatureF"));
            numberOfRecords ++;
        }
        double average = temperature / numberOfRecords;
        return average;
    }

    public double averageTemperatureWithHighHumidity(CSVParser parser, int value) {
        int numberOfRecords = 0;
        double temperature = 0;
        double highHumidity;
        double average;
        for (CSVRecord record : parser) {
            if(record.get("Humidity").contains("N/A")) {
                continue;
            }
            highHumidity = Double.parseDouble(record.get("Humidity"));

            if (highHumidity >= value) {
            temperature += Double.parseDouble(record.get("TemperatureF"));
                numberOfRecords++;
            }
//            else {
//                average = 0;
//            }

        }
        average = temperature / numberOfRecords;
        return average;
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
        FileResource fileResource = new FileResource("weather/nc_weather/2014/weather-2014-05-01.csv");
        CSVRecord smallest = coldestHourInFile(fileResource.getCSVParser());
        System.out.println("The coldest temperature was " + smallest.get("TemperatureF") +
                " at " + smallest.get("DateUTC"));
    }


    public void testColdestInManyDays() {
        CSVRecord smallest = coldestInManyDays();
        System.out.println("coldest temp was " + smallest.get("TemperatureF") +
                " at " + smallest.get("DateUTC"));
    }

    public void testColdestFileName() {
//        String name = fileWithColdestTemperature();
//        System.out.println(name);
        CSVRecord smallest = fileWithColdestTemperature();
    }

    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource("weather/nc_weather/2014/weather-2014-07-22.csv");
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("The lowest humidity was: " + csv.get("Humidity")+ " at: " + csv.get("DateUTC"));
    }

    public void testLowestHumidityInManyDays() {
        CSVRecord lowest = lowestHumidityInManyDays();
        System.out.println("Humidity " + lowest.get("Humidity") +
                " at " + lowest.get("DateUTC"));
    }

    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource("weather/nc_weather/2013/weather-2013-08-10.csv");
//        FileResource fr = new FileResource("weather/nc_weather/2014/weather-2014-06-01.csv");
//        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
//        CSVRecord csv = averageTemperatureInFile(parser);
        System.out.println("The average temperature was: " + averageTemperatureInFile(parser));
    }

    public void testAverageTemperatureWithHighHumidityInFile(int value){
        FileResource fr = new FileResource("weather/nc_weather/2013/weather-2013-09-02.csv");
        CSVParser parser = fr.getCSVParser();
//        if(averageTemperatureWithHighHumidity(parser, value) < 1) {
//            System.out.println("No Temperatures with that humidity");
//        } else {
            System.out.println("Average Temperature with High Humidity was: " + averageTemperatureWithHighHumidity(parser, value));
        }

//    }

}