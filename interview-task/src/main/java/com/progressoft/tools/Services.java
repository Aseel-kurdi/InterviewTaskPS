package com.progressoft.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.progressoft.tools.CSVUtil.readCSVFile;
import static com.progressoft.tools.CSVUtil.readHeader;

public class Services {
    public  static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int returnColumnNumber (String colToStandardize, Path csvPath) throws IOException {
        String[] header = readHeader(csvPath,colToStandardize);
        int x = 0;
        File csvFile = new File(String.valueOf(csvPath));
        if (csvFile.isFile()) {
            for (int i = 0; i < header.length; i++) {
                if(colToStandardize.equalsIgnoreCase(header[i])){
                    x = i;
                }
            }
        } else{
            throw new IllegalArgumentException("source file not found");

        }

        return x;
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
    public static ArrayList<String[]> numericDataArray(Path csvPath) throws IOException {
        ArrayList<String[]> numericData = readCSVFile(csvPath);
        return  numericData;
    }

}
