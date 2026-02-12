package com.tcs.e_commerce_back_end.utils;

public class PriceFormat {
    public static Double format(double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
