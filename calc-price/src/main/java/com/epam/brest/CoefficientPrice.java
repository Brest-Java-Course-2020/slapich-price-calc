package com.epam.brest;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.*;
import java.util.*;

public class CoefficientPrice {

    private static final String pathToFileCoefficientPerDistance = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerDistance.json";
    private static final String pathToFileCoefficientPerKilogramm = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerKilogramm.json";

    public CoefficientPrice() {
    }

    private static HashMap getPricePerDistance() throws IOException{
        ObjectMapper mapperDistance = new ObjectMapper();
        return mapperDistance.readValue(readInformationFromFile(pathToFileCoefficientPerDistance), HashMap.class);
    }

    private static HashMap getPricePerKilogramm() throws IOException{
        ObjectMapper mapperKilogramm = new ObjectMapper();
        return mapperKilogramm.readValue(readInformationFromFile(pathToFileCoefficientPerKilogramm), HashMap.class);
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



    static Double getPriceDistance(Double valueDistance) throws IOException {
        return getPrice(valueDistance, getPricePerDistance());
    }


     static Double getPriceKilogramm(Double valueKilogramm) throws IOException {

        return getPrice(valueKilogramm, getPricePerKilogramm());
    }


    private static Double getPrice(Double value, Map<String, Double> mapForSearching){
         Double priceValue = 0d;

         for(Map.Entry itemFromMap : mapForSearching.entrySet()) {
             if(Double.parseDouble((String) itemFromMap.getKey()) <= value){
                 priceValue = (Double) itemFromMap.getValue();
             }
         }
         return priceValue;
     }

}


