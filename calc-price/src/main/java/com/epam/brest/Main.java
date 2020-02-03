package com.epam.brest;


import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import static com.epam.brest.CoefficientPrice.*;

public class Main {

    private final static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) throws IOException{

        Double[] enteredValues = new Double[2];

        Scanner scanner = new Scanner(System.in);
        String inputValue;
        int i = 0;
        do {
            switch (i){
                case 0:  System.out.println("Please, enter distance or Q for exit: "); break;
                case 1:  System.out.println("Please, enter weight or Q for exit: "); break;
            }

            inputValue = scanner.next();


            if (!isExitValue(inputValue)) {
                if (isCorrectDoubleValue(inputValue)) {
                    switch (i){
                        case 0:  System.out.println(getPriceDistance(Double.parseDouble(inputValue))); break;
                        case 1:  System.out.println(getPriceKilogramm(Double.parseDouble(inputValue))); break;
                    }
                    enteredValues[i] = Double.parseDouble(inputValue);
                    i++;
                }
            }

            if (i == 2) {
                Double calc = enteredValues[0]*getPriceDistance(Double.parseDouble(inputValue))
                        + enteredValues[1]*getPriceKilogramm(Double.parseDouble(inputValue));
                System.out.println("Price: $" + calc);
                i = 0;
            }

        } while (!isExitValue(inputValue));

        logger.info("Finish!");

    }

    private static boolean isExitValue(String value) {
        return value.equalsIgnoreCase("Q");
    }

    private static boolean isCorrectDoubleValue(String value) {
        boolean checkResult;
        try {
            double enteredDoubleValue = Double.parseDouble(value);
            checkResult = enteredDoubleValue >= 0;
        } catch (NumberFormatException ex) {
            checkResult = false;
        }
        return checkResult;
    }

}