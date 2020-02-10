package com.epam.brest.calc;

import java.math.BigDecimal;

public class CalculatorPriceImpl implements CalculatorPrice {

    public CalculatorPriceImpl() {
        System.out.println("Default constructor");
    }


//    public void init() {
//        System.out.println("Spring constructor");
//    }

    @Override
    public BigDecimal calc(BigDecimal weight, BigDecimal distance, BigDecimal pricePerKg, BigDecimal pricePerKm) {
        return weight.multiply(pricePerKg).add(distance.multiply(pricePerKm));
    }
}

