package com.progressoft.tools;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static com.progressoft.tools.Services.isNumeric;

public class CSVUtil {

    public static ArrayList<String[]> readCSVFile(Path csvPath) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        String row;
        String[] temp;
        String[] temporary = new String[2];
        int index = 0;
        ArrayList<String[]> numericData = new ArrayList<>();
        File csvFile = new File(String.valueOf(csvPath));
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(csvPath)));

            while ((row = csvReader.readLine()) != null) {
                data.add( row.split(","));
                // do something with the data
            }
            for (int i = 0; i < data.size(); i++) {
                temp = data.get(i);
                for (int j = 0; j < temp.length; j++) {
                    if (isNumeric(temp[j])) {
                        index = j;
                        if (j == index && j > 0) {
                            temporary[0] = String.valueOf(index);
                            temporary[1] = temp[j];
                            numericData.add(temporary);
                            temporary = new String[2];
                        }
                    }

                }

            }
            csvReader.close();
        } else{
            throw new IllegalArgumentException("source file not found");

        }
        return numericData;
    }


    public static String[] readHeader(Path csvPath,String colToStandardize) throws IOException {
        String row = "";
        BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(csvPath)));
        row = csvReader.readLine();
        String[] data = row.split(",") ;


        csvReader.close();
        if(!(Arrays.asList(data).contains(colToStandardize))){
            throw new IllegalArgumentException("column "+ colToStandardize +" not found");
        }
//        dataPlus[data.length] = colToStandardize + "_scaled";
        return data;
    }
    public  static void writeToCsvFile(String value , Path destPath) throws IOException {
        FileWriter csvWriter = new FileWriter(String.valueOf(destPath),true);
        File csvFile = new File(String.valueOf(destPath));

        if (csvFile.isFile() ) {
            csvWriter.append(String.valueOf(value));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } else {
            throw new IllegalArgumentException("the destination file does not exists");
        }
//        if (!(destPath.toString().contains(".csv")))
    }

    public static ArrayList<String > readAllCsvFile(Path csvPath) throws IOException{
        //data contain all data in csv file
        ArrayList<String> data = new ArrayList<>();
        String row ;
        String[] temp;
        String[] temporary = new String[2];
        int index = 0;
        ArrayList<String[]> numericData = new ArrayList<>();
        File csvFile = new File(String.valueOf(csvPath));
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(csvPath)));

            while ((row = csvReader.readLine()) != null) {
    //                data.add( row.split(","));
                data.add(row);
            }
        }

        return data;

    }
    public static String[] addValue(String[] a, int index, String value) {
        String[] result = new String[a.length+1];
        String temp = a[a.length-1];
        System.arraycopy(a, 0, result, 0, index);
        result[index] = value;
        if(index % 2 == 0){
            result[result.length-1]=temp;
        }
        return result;
    }
    public static String arrayOfStringToString(String[] stringArray){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < stringArray.length; i++) {
            if(i== stringArray.length-1)
                sb.append(stringArray[i]);
            else
                sb.append(stringArray[i]+",");

        }
        String str = sb.toString();
        return str;
    }


}
