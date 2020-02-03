package com.epam.brest;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.*;
import java.util.*;

public class CoefficientPrice {

    private static final String pathToFileCoefficientPerDistance = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerDistance.json";
    private static final String pathToFileCoefficientPerKilogramm = "/home/svlapich/development/slapich-price-calc/calc-price/src/main/resources/coefficientPerKilogramm.json";

    public CoefficientPrice() {
    }

    public static Map<String,Double> getPricePerDistance() throws IOException{
        ObjectMapper mapperDistance = new ObjectMapper();
        return mapperDistance.readValue(readInformationFromFile(pathToFileCoefficientPerDistance), HashMap.class);
    }

    public static Map<String,Double> getPricePerKilogramm() throws IOException{
        ObjectMapper mapperKilogramm = new ObjectMapper();
        return mapperKilogramm.readValue(readInformationFromFile(pathToFileCoefficientPerKilogramm), HashMap.class);
    }

    public static String readInformationFromFile(String pathToFileCoefficientPerDistance){
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



    public static Double getPriceDistance(Double valueDistance) throws IOException {
        Map<String , Double> mapForSearchingDistance = getPricePerDistance();
        return getPrice(valueDistance, mapForSearchingDistance);
    }


    public static Double getPriceKilogramm(Double valueKilogramm) throws IOException {
        Map<String , Double> mapForSearchingKilogramm = getPricePerKilogramm();
        return getPrice(valueKilogramm, mapForSearchingKilogramm);
    }


     public static Double getPrice(Double value, Map<String, Double> mapForSearching){
         Double priceValue = 0d;


         for(Map.Entry itemFromMap : mapForSearching.entrySet()) {
             System.out.println(itemFromMap.getKey());
             if(Double.parseDouble((String) itemFromMap.getKey()) <= value){
                 priceValue = (Double) itemFromMap.getValue();
             }
         }
         return priceValue;
     }


}


