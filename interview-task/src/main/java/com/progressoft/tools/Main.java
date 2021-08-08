package com.progressoft.tools;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        String sourcePath = args[0];
        String destString = args[1];
        String columnToNormalize = args[2];
        String normalizationMethod = args[3];

        NormalizerImpl normalizer = new NormalizerImpl();
        Path csvPath = Paths.get(sourcePath);
        Path destPath = Paths.get(destString);
        if ("z-score".equals(normalizationMethod)) {
            normalizer.zscore(csvPath, destPath, columnToNormalize);
        }else if ("min-max".equals(normalizationMethod)){
            normalizer.minMaxScaling(csvPath, destPath,columnToNormalize);
        }else {
            throw new IllegalArgumentException("Method not supported");
        }

    }
}
