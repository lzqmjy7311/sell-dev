package com.imooc.selldev.utils;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 15:43
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class MathUtil {

    public static boolean equals(double d1, double d2) {

        final double result = Math.abs(d1 - d2);
        if (result < 0.01) {
            return true;
        } else {
            return false;
        }
    }
}
