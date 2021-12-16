package com.example.javaclicker.lib;

import java.text.DecimalFormat;

public class Formatter {

    public static String formatBigNum(int value){
        String arr[] = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), arr[index]);
    }
}
