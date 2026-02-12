package com.tcs.e_commerce_back_end.utils;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CalculatorMinMax{
    private final Integer min;
    private final Integer max;
    public CalculatorMinMax(Integer min, Integer max) {
        if (Objects.isNull(min) && Objects.isNull(max)){
            this.min = 0;
            this.max = 0;
        }else if (Objects.isNull(max)){
            this.max = Integer.MAX_VALUE;
            this.min = min;
        }else if (Objects.isNull(min)){
            this.min = 0;
            this.max = max;
        }else {
            this.min = min;
            this.max = max;
        }
    }
    public CalculatorMinMax(int min, int max) {
        if (min == 0 && max == 0){
            this.min = 0;
            this.max = 0;
        }else if (max == 0){
            this.max = Integer.MAX_VALUE;
            this.min = min;
        }else if (min == 0){
            this.min = 0;
            this.max = max;
        }else {
            this.min = min;
            this.max = max;
        }
    }
}
