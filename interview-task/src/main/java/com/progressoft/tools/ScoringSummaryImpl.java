package com.progressoft.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.progressoft.tools.CSVUtil.*;
import static com.progressoft.tools.Services.numericDataArray;
import static com.progressoft.tools.Services.returnColumnNumber;

public class ScoringSummaryImpl implements ScoringSummary{

    Path csvPath;
    Path destPath;
    String colToStandardize;

    public ScoringSummaryImpl(Path csvPath, Path destPath, String colToStandardize) {
        this.csvPath = csvPath;
        this.destPath = destPath;
        this.colToStandardize = colToStandardize;
    }

    @Override
    public BigDecimal mean() {
        BigDecimal count = new BigDecimal(0) ;
        BigDecimal sum = new BigDecimal(0);
        BigDecimal mean ;
        BigDecimal increaseByOne = new BigDecimal(1);
        try {
            ArrayList<String[]> numericData = readCSVFile(csvPath);
            String[] header = readHeader(csvPath,colToStandardize);
            int columnNumber = returnColumnNumber(colToStandardize, csvPath);
            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    count = count.add(increaseByOne);
                    sum = sum.add( BigDecimal.valueOf((Double.parseDouble(temporary[1]))));
                }

            }
            mean =  sum.divide(count, 0, RoundingMode.HALF_EVEN);


        } catch (IOException e) {
            throw  new IllegalArgumentException("the returned summary is null");
        }

        return (mean).setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal standardDeviation() {
        BigDecimal SD = new BigDecimal(0);
        BigDecimal mean = mean();
        BigDecimal count = new BigDecimal(0);
        BigDecimal increaseByOne = new BigDecimal(1);
        double x;
        try {
            ArrayList<String[]> numericData = readCSVFile(csvPath);
            int columnNumber = returnColumnNumber (colToStandardize, csvPath);
            MathContext mc = new MathContext(4);
            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    count = count.add(increaseByOne);
                    x = Double.parseDouble(temporary[1]); // from string to BigDecimal
                    SD = SD.add(BigDecimal.valueOf(Math.ceil(Math.pow(((BigDecimal.valueOf(x).subtract(mean,mc)).doubleValue()), 2))));
                }

            }
        } catch (IOException e) {
            throw  new IllegalArgumentException("the returned summary is null");
        }
        BigDecimal tmp = SD.divide(count, 2, RoundingMode.HALF_EVEN);
        BigDecimal temp = BigDecimal.valueOf(Math.sqrt((tmp.doubleValue())));
        BigDecimal one = new BigDecimal(1);
        BigDecimal tempSD = temp.divide(one, 2,RoundingMode.CEILING);
        return tempSD;
    }

    @Override
    public BigDecimal variance() {
        // variance = Sd^2
        BigDecimal tmp = BigDecimal.valueOf((Math.pow(standardDeviation().doubleValue(),2)));
        BigDecimal one = new BigDecimal(1);
        tmp = tmp.setScale(0,RoundingMode.HALF_EVEN);
        BigDecimal tempVariance = tmp.divide(one, 2,RoundingMode.HALF_EVEN);
        return  tempVariance;

    }

    @Override
    public BigDecimal median() {
        try {
            ArrayList<String[]> numericData = numericDataArray(csvPath);
            String[] header = readHeader(csvPath,colToStandardize);
            int columnNumber = returnColumnNumber (colToStandardize, csvPath);
            ArrayList<Double> toCalculateMedian =  new ArrayList<>();
            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    toCalculateMedian.add(Double.parseDouble(temporary[1]));
                }

            }
            toCalculateMedian = ascendingSort(toCalculateMedian);
            BigDecimal temporary;

            if (toCalculateMedian.size() % 2 != 0){// odd case
                temporary = BigDecimal.valueOf(toCalculateMedian.get(toCalculateMedian.size()/2));
                BigDecimal one = new BigDecimal(1);
                return temporary.divide(one,2,RoundingMode.HALF_EVEN);
            }
            else{ // even case
                temporary =  BigDecimal.valueOf(toCalculateMedian.get(toCalculateMedian.size()/2) + toCalculateMedian.get((toCalculateMedian.size()-1)/2));
                BigDecimal two = new BigDecimal(2);
                return temporary.divide(two,2,RoundingMode.HALF_EVEN);
            }

        } catch (IOException e) {
            throw  new IllegalArgumentException("the returned summary is null");

        }
    }

    @Override
    public BigDecimal min() {
        try {
            ArrayList<String[]> numericData = readCSVFile(csvPath);
            int columnNumber = returnColumnNumber (colToStandardize, csvPath);
            ArrayList<Double> toCalculateMin =  new ArrayList<>();
            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    toCalculateMin.add(Double.parseDouble(temporary[1]));
                }

            }
            toCalculateMin = ascendingSort(toCalculateMin);
            BigDecimal temp = BigDecimal.valueOf(toCalculateMin.get(0));
            BigDecimal one = new BigDecimal(1);
            return temp.divide(one,2,RoundingMode.HALF_EVEN);
        } catch (IOException e) {
            throw  new IllegalArgumentException("the returned summary is null");
        }
    }

    @Override
    public BigDecimal max() {
        try {
            ArrayList<String[]> numericData = numericDataArray(csvPath);
            int columnNumber = returnColumnNumber (colToStandardize, csvPath);
            ArrayList<Double> toCalculateMax =  new ArrayList<>();
            for (int i = 0; i < numericData.size() ; i++) {
                String[] temporary = numericData.get(i);
                if(temporary[0].equals(Integer.toString(columnNumber))){
                    toCalculateMax.add(Double.parseDouble(temporary[1]));
                }

            }
            toCalculateMax = ascendingSort(toCalculateMax);
            BigDecimal temp = BigDecimal.valueOf(toCalculateMax.get(toCalculateMax.size()-1));
            BigDecimal one = new BigDecimal(1);
            return temp.divide(one,2,RoundingMode.HALF_EVEN);
        } catch (IOException e) {
            throw  new IllegalArgumentException("the returned summary is null");
        }
    }

    public   ArrayList <Double> ascendingSort( ArrayList<Double> arr){
        for (int i = 0; i < arr.size(); i++)
        {
            for (int j = i + 1; j < arr.size(); j++)
            {
                Double tmp;
                if (arr.get(i) > arr.get(j))
                {
                    tmp = arr.get(i);
                    arr.set(i,arr.get(j));
                    arr.set(j,tmp);

                }
            }

    }
        return arr;
}
}
