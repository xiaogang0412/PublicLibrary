package com.polyclinic.library.utils;

/**
 * @author Lxg
 * @create 2020/3/3
 * @Describe
 */
public class MoneyUtils {
    public static String twoWei(float data) {
        return String.format("%10.2f", data).trim();
    }
}
