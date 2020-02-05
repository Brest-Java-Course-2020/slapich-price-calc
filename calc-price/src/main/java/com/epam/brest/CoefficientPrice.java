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


    private static String readInformationFromFile(String pathToFileCoefficientPerDistance){
        String jsonLine, allString="";
        try(InputStream inputStream = new FileInputStream(pathToFileCoefficientPerDistance))
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            jsonLine = bufferedReader.readLine();
            while (jsonLine != null) {
                allString = allString.concat(jsonLine);
                jsonLine = bufferedReader.readLine();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return allString;
    }


    private static HashMap getPricePerDistance() throws IOException{
        ObjectMapper mapperDistance = new ObjectMapper();
        return mapperDistance.readValue(readInformationFromFile(pathToFileCoefficientPerDistance), HashMap.class);
    }

    private static Double getPricePerKilogramm(Double value) throws IOException{
        ObjectMapper mapperKilogramm = new ObjectMapper();
        TreeMap<String, Double> sortedMap = mapperKilogramm.readValue(readInformationFromFile(pathToFileCoefficientPerKilogramm), TreeMap.class);

// sort
        String stepKey;
        ArrayList<String> employeeByKey = new ArrayList<>(sortedMap.keySet());
        for(int i = 0; i< employeeByKey.size()-1; i++){
            if(parseDouble(employeeByKey.get(i)) > parseDouble(employeeByKey.get(i+1))){
                stepKey = employeeByKey.get(i+1);
                employeeByKey.set(i+1, employeeByKey.get(i));
                employeeByKey.set(i, String.valueOf(stepKey));
                 }
        }

        Double priceValue = 0d;
//get kilogramm in period
        for(int i = 0; i< employeeByKey.size(); i++){
            if(parseDouble(employeeByKey.get(i)) <= value){
                priceValue = Double.parseDouble(employeeByKey.get(i));
            }
        }

//get koefficient by kilogramm
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            if (parseDouble((String) entry.getKey()) == priceValue) {
                priceValue = (Double) entry.getValue();
            }
        }

        return priceValue;
    }


    static Double getPriceDistance(Double valueDistance) throws IOException {
        return getPrice(valueDistance, getPricePerDistance());
    }


     static Double getPriceKilogramm(Double valueKilogramm) throws IOException {

        return getPricePerKilogramm(valueKilogramm);
    }


    private static Double getPrice(Double value, Map<String, Double> mapForSearching){
         Double priceValue = 0d;

         for(Map.Entry itemFromMap : mapForSearching.entrySet()) {

             if(parseDouble((String) itemFromMap.getKey()) <= value){
                 priceValue = (Double) itemFromMap.getValue();
             }
         }
         return priceValue;
     }

}


