package com.epam.brest;

import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;

public class CoefficientPrice {

    private static final String pathToFileCoefficientPerDistance = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerDistance.json";
    private static final String pathToFileCoefficientPerKilogramm = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerKilogramm.json";

    public CoefficientPrice() {
    }

    static Double getPriceKilogramm(Double value) throws IOException {
        TreeMap sortedMapKilogramm = FileReader.getNameSortedMap(pathToFileCoefficientPerKilogramm);
        return getSortMap(sortedMapKilogramm, value, pathToFileCoefficientPerKilogramm);
    }

    static Double getPriceDistance(Double value) throws IOException {
        TreeMap sortedMapDistance = FileReader.getNameSortedMap(pathToFileCoefficientPerDistance);
        return getSortMap(sortedMapDistance, value, pathToFileCoefficientPerDistance);
    }


    private static Double getSortMap(TreeMap sortedMap, Double valueFromCompare, String pathNameFile) throws IOException {
        String stepKey;
        ArrayList<String> sortedMapByKey = new ArrayList<>(sortedMap.keySet());
        for (int i = 0; i < sortedMapByKey.size() - 1; i++) {
            if (parseDouble(sortedMapByKey.get(i)) > parseDouble(sortedMapByKey.get(i+1))) {
                stepKey = sortedMapByKey.get(i + 1);
                sortedMapByKey.set(i + 1, sortedMapByKey.get(i));
                sortedMapByKey.set(i, String.valueOf(stepKey));
            }
        }
        return getRealValueFromMap(sortedMapByKey, valueFromCompare, pathNameFile);
    }


    private static Double getRealValueFromMap(List<String> sortedMapByKey, Double valueFromCompare, String pathNameFile) throws IOException { //get kilogramm in period
        double priceNeedValue = 0d;

        for (String s : sortedMapByKey) {
            if (parseDouble(s) <= valueFromCompare) {
                priceNeedValue = parseDouble(s);
            }
        }
        return getRealCoefficienFromtMap(priceNeedValue, FileReader.getNameSortedMap(pathNameFile));
    }


    private static Double getRealCoefficienFromtMap(Double valueRealValue, TreeMap<String, Double> sortedMap) { //get koefficient by kilogramm
        double priceRealCoefficienValue = 0d;

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            if (parseDouble(entry.getKey()) == valueRealValue) {
                priceRealCoefficienValue = entry.getValue();
            }
        }
        return priceRealCoefficienValue;
    }

}




