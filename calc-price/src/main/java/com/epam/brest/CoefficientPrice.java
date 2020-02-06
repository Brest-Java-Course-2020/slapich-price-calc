package com.epam.brest;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;

public class CoefficientPrice {

    private static final String pathToFileCoefficientPerDistance = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerDistance.json";
    private static final String pathToFileCoefficientPerKilogramm = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerKilogramm.json";

    public CoefficientPrice() {
    }

    private static String readInformationFromFile(String pathToFileCoefficientPerDistance) {
        String jsonLine, allString = "";
        try (InputStream inputStream = new FileInputStream(pathToFileCoefficientPerDistance)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            jsonLine = bufferedReader.readLine();
            while (jsonLine != null) {
                allString = allString.concat(jsonLine);
                jsonLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return allString;
    }


    static Double getPriceKilogramm(Double value) throws IOException {
        TreeMap sortedMapKilogramm = getNameSortedMap(pathToFileCoefficientPerKilogramm);
        return getSortMap(sortedMapKilogramm, value, pathToFileCoefficientPerKilogramm);
    }

    static Double getPriceDistance(Double value) throws IOException {
        TreeMap sortedMapDistance = getNameSortedMap(pathToFileCoefficientPerDistance);
        return getSortMap(sortedMapDistance, value, pathToFileCoefficientPerDistance);
    }


    private static TreeMap getNameSortedMap(String pathToFileCoefficient) throws IOException {
        ObjectMapper mapperDistance = new ObjectMapper();
        return mapperDistance.readValue(readInformationFromFile(pathToFileCoefficient), TreeMap.class);
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

        return getRealCoefficienFromtMap(priceNeedValue, getNameSortedMap(pathNameFile));
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




