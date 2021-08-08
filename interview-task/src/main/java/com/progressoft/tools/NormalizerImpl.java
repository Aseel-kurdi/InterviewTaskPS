package com.progressoft.tools;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import static com.progressoft.tools.CSVUtil.*;
import static com.progressoft.tools.Services.returnColumnNumber;

public class NormalizerImpl implements Normalizer{

    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize)  {
        BigDecimal mean;
        BigDecimal SD;
        //read data from Csv file*****************
        /** numericData array contains in each index a numeric number in the csv file
        and the number of column the numeric number exists in **/
        ScoringSummary scoringSummary = new ScoringSummaryImpl(csvPath,destPath,colToStandardize);
        try {
            ArrayList<String[]> numericData = readCSVFile(csvPath);
            ArrayList<String> data = readAllCsvFile(csvPath);
            int columnNumber = returnColumnNumber(colToStandardize,csvPath);
          String[] header = readHeader(csvPath,colToStandardize);
          // z-score = (rawScore - mean)/ SD
            ArrayList<BigDecimal> toCalculateZScore =  new ArrayList<>();
            BigDecimal one = new BigDecimal(1);
            BigDecimal b = new BigDecimal(0);
            ArrayList<String> StandardizeColName = new ArrayList<>();
            // update header ***************************
            for (int n = 0; n < header.length; n++) {
                if(n == header.length-1)
                StandardizeColName.add(n,header[n] );
                else
                    StandardizeColName.add(n,header[n] +",");
            }
            if( columnNumber == StandardizeColName.size()-1 )
                StandardizeColName.set(columnNumber,header[columnNumber] + "," + colToStandardize + "_z");
            else
                StandardizeColName.set(columnNumber,header[columnNumber] + "," + colToStandardize + "_z" + ",");
            String tempString = "";
            for (int o = 0; o < StandardizeColName.size(); o++) {
                tempString = tempString + StandardizeColName.get(o);
            }
            writeToCsvFile((tempString),destPath);
            // ****************************************
            mean = scoringSummary.mean();
            SD = scoringSummary.standardDeviation();

            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    toCalculateZScore.add(BigDecimal.valueOf((Double.parseDouble(temporary[1]))));

                }

            }


            ArrayList<String> s = new ArrayList<>();
            ArrayList<BigDecimal> zScore = new ArrayList<>();
            BigDecimal  tmp;
            for ( BigDecimal rawScore: toCalculateZScore) {
                tmp =(rawScore.subtract(mean)).divide(SD,2, RoundingMode.HALF_EVEN);
                zScore.add(tmp);
            }

            String[]  rowsInDestFile;
            String zScoreValue;
            for (int k = 0; k < zScore.size() ; k++) {
                rowsInDestFile = data.get(k+1).split(",");
                zScoreValue = String.valueOf(zScore.get(k));
                rowsInDestFile = addValue(rowsInDestFile,columnNumber+1,zScoreValue);
                writeToCsvFile(arrayOfStringToString(rowsInDestFile),destPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //****************************************
        return scoringSummary;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        //read data from Csv file*****************
        /** numericData array contains in each index a numeric number in the csv file
         and the number of column the numeric number exists in **/
        ScoringSummary scoringSummary = new ScoringSummaryImpl(csvPath,destPath,colToNormalize);

        try {
            ArrayList<String[]> numericData = readCSVFile(csvPath); // contains numeric column data and the index of this column
            ArrayList<String> data = readAllCsvFile(csvPath); // contains all data in csv file each row in an index as a string
            int columnNumber = returnColumnNumber(colToNormalize,csvPath); // the numeric column number
            String[] header = readHeader(csvPath,colToNormalize); // the header of the csv file

            ArrayList<String> NormalizeColName = new ArrayList<>();
            // update header ****************************
            for (int n = 0; n < header.length; n++) {
                if(n == header.length-1)
                    NormalizeColName.add(n,header[n] );
                else
                    NormalizeColName.add(n,header[n] +",");
            }
            if(columnNumber== NormalizeColName.size()-1)
                NormalizeColName.set(columnNumber,header[columnNumber] + "," + colToNormalize + "_mm");
            else
                NormalizeColName.set(columnNumber,header[columnNumber] + "," + colToNormalize + "_mm" + ",");
            String tempString = "";
            for (int o = 0; o < NormalizeColName.size(); o++) {
                tempString = tempString + NormalizeColName.get(o);
            }
            writeToCsvFile((tempString),destPath);
            // *****************************
            BigDecimal max = scoringSummary.max();
            BigDecimal min = scoringSummary.min();

            ArrayList<BigDecimal> toCalculateMinMaxScaling =  new ArrayList<>(); // contains the numeric column numbers
            BigDecimal one = new BigDecimal(1);

            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    toCalculateMinMaxScaling.add(BigDecimal.valueOf((Double.parseDouble(temporary[1]))));

                }

            }
            // min-max normalization = (value - min)/ (max-min)
            BigDecimal temporary =  new BigDecimal(0);
            ArrayList<BigDecimal> minMaxNormalization = new ArrayList<>();
            BigDecimal tempMin = new BigDecimal(0);
            BigDecimal tempMax = new BigDecimal(0);
            for (BigDecimal value : toCalculateMinMaxScaling) {
                tempMin = value.subtract(min);
                tempMax = max.subtract(min);
                temporary = tempMin.divide(tempMax,2,RoundingMode.HALF_EVEN);
                minMaxNormalization.add(temporary);

            }

            String[]  rowsInDestFile;
            String minMaxValue;
            for (int j = 0; j < minMaxNormalization.size() ; j++) {
                rowsInDestFile = data.get(j+1).split(",");
                minMaxValue = String.valueOf(minMaxNormalization.get(j));
                rowsInDestFile = addValue(rowsInDestFile,columnNumber+1,minMaxValue);
                writeToCsvFile(arrayOfStringToString(rowsInDestFile),destPath);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        //****************************************
        return scoringSummary;
    }




}
