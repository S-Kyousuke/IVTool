/*
 * Copyright 2016 Surasek Nusati <surasek@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
