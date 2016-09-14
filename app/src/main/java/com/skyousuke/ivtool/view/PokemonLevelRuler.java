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

package com.skyousuke.ivtool.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class PokemonLevelRuler extends ArcRuler {

    private static double[] x = new double[]{
            0.094, 0.1351374, 0.1663979, 0.1926509, 0.2157325, 0.2365727, 0.2557201,
            0.2735304, 0.2902499, 0.3060574, 0.3210876, 0.335445, 0.3492127, 0.3624578,
            0.3752356, 0.3875924, 0.3995673, 0.4111936, 0.4225, 0.4335117, 0.4431076,
            0.45306, 0.4627984, 0.4723361, 0.481685, 0.4908558, 0.4998584, 0.5087018,
            0.517394, 0.5259425, 0.5343543, 0.5426358, 0.5507927, 0.5588306, 0.5667545,
            0.5745692, 0.5822789, 0.5898879, 0.5974, 0.6048188, 0.6121573, 0.6194041,
            0.6265671, 0.6336492, 0.640653, 0.647581, 0.6544356, 0.6612193, 0.667934,
            0.6745819, 0.6811649, 0.6876849, 0.6941437, 0.7005429, 0.7068842, 0.7131691,
            0.7193991, 0.7255756, 0.7317, 0.734741, 0.7377695, 0.7407856, 0.7437894,
            0.7467812, 0.749761, 0.7527291, 0.7556855, 0.7586304, 0.7615638, 0.7644861,
            0.7673972, 0.7702973, 0.7731865, 0.776065, 0.7789328, 0.7817901, 0.784637,
            0.7874736, 0.7903, 0.7931164
    };

    private int trainerLevel = 1;

    private static float angle(int trainerLv, float pokemonLv) {
        if (pokemonLv - 1.5f > trainerLv) {
            return 0;
        }
        double angle = 180 - (x[(int) ((pokemonLv - 1) * 2)] - 0.094) * 202.037116 / x[trainerLv * 2 - 2];
        if (angle < 0) angle = 0;
        return (float) angle;
    }

    public PokemonLevelRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTrainerLevel(int trainerLevel) {
        this.trainerLevel = trainerLevel;
    }

    public void setPokemonLevel(float level) {
        setAngle(angle(trainerLevel, level));
    }
}
