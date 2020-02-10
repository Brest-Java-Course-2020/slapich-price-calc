package com.epam.brest;


import com.epam.brest.calc.CalculatorPrice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.epam.brest.CoefficientPrice.getPriceDistance;
import static com.epam.brest.CoefficientPrice.getPriceKilogramm;

public class Main {


    private final static Logger logger = Logger.getLogger(String.valueOf(Main.class));


    public static void main(String[] args) throws IOException {

        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("application-config.xml");
        CalculatorPrice calculatorPrice = (CalculatorPrice) applicationContext.getBean("calculator");

            Scanner scanner = new Scanner(System.in);
            String inputValue = null;
            int i = 0;
            BigDecimal  weightValue = null,
                        distanceValue = null,
                        priceWeight = null,
                        priceDistance = null;

        do {



                switch (i){
                    case 0: System.out.println("Enter weight: ");
                            inputValue = scanner.next();
                            if(isCorrectDoubleValue(inputValue)){
                                weightValue = (new BigDecimal(inputValue));
                                priceWeight = BigDecimal.valueOf(getCorrectValue(inputValue, i++));
                            }
                            break;
                    case 1: System.out.println("Enter distance: ");
                            inputValue = scanner.next();
                            if(isCorrectDoubleValue(inputValue)){
                                distanceValue = (new BigDecimal(inputValue));
                                priceDistance = BigDecimal.valueOf(getCorrectValue(inputValue, i++));
                            }
                            break;
                }


                if (i == 2) {


                    BigDecimal calcResult = calculatorPrice.calc(weightValue, distanceValue, priceWeight, priceDistance);
                    System.out.println("Price: $" + calcResult);
                    i = 0;
                }


            } while (!isExitValue(inputValue));

            logger.info("Finish!");

        }


        private static double getCorrectValue(String inputValue, int i) throws IOException {
            double coefficientValue = new Double(inputValue);


                if (isCorrectDoubleValue(inputValue)) {
                    switch (i){
                        case 0: coefficientValue = getPriceDistance(Double.parseDouble(inputValue));
                            System.out.println(coefficientValue);
                            break;
                        case 1:  coefficientValue = getPriceKilogramm(Double.parseDouble(inputValue));
                            System.out.println(coefficientValue);
                            break;
                    }
                }


            return coefficientValue;

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

