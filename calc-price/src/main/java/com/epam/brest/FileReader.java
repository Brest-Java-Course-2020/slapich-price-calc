package com.epam.brest;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.TreeMap;

class FileReader {

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

    static TreeMap getNameSortedMap(String pathToFileCoefficient) throws IOException {
        ObjectMapper mapperDistance = new ObjectMapper();
        return mapperDistance.readValue(readInformationFromFile(pathToFileCoefficient), TreeMap.class);
    }

}
