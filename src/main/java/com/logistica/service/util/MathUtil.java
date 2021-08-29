package com.logistica.service.util;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static Float roundUp(Float value) {
        Assert.isTrue(value != null, "value should be set");
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).floatValue();
    }
}
