package com.skyousuke.ivtool.util;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class TextFormat {

    public static String formatDecimal(double value, int places) {
        if (places < 1) return String.valueOf(Math.round(value));
        double roundingFactor = 1;
        for (int i = 0; i < places; i++) {
            roundingFactor *= 10;
        }
        return trimDecimalPlaces(Double.toString(Math.round(value * roundingFactor) / roundingFactor), places);
    }

    private static String trimDecimalPlaces(String decimalString, int places) {
        final int dotIndex = decimalString.indexOf('.');
        if (dotIndex == -1) {
            decimalString += '.';
            for (int i = 0; i < places; ++i) {
                decimalString += '0';
            }
            return decimalString;
        }
        final int endIndex = dotIndex + places + 1;
        final int stringLength = decimalString.length();
        for (int i = 0; i < endIndex - stringLength; ++i) {
            decimalString += '0';
        }
        return decimalString.substring(0, endIndex);
    }
}
