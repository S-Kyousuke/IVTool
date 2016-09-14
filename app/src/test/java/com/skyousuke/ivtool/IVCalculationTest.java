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

package com.skyousuke.ivtool;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class IVCalculationTest {

    @Test
    public void getCpTest() throws Exception {
        for (Pokemon pokemon : Pokemon.values) {
            for (float level = 1; level <= 40; level += 0.5f) {
                for (int attackIV = 0; attackIV <= 15; attackIV++) {
                    for (int defenseIV = 0; defenseIV <= 15; defenseIV++) {
                        for (int staminaIV = 0; staminaIV <= 15; staminaIV++) {
                            assertTrue("CP less than 10", pokemon.getCp(level, attackIV, defenseIV, staminaIV) >= 10);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void getHpTest() throws Exception {
        for (Pokemon pokemon : Pokemon.values) {
            for (float level = 1; level <= 40; level += 0.5f) {
                for (int staminaIV = 0; staminaIV <= 15; staminaIV++) {
                    assertTrue("HP less than 10", pokemon.getHp(level, staminaIV) >= 10);
                }
            }
        }
    }

    @Test
    public void percentIVTest() throws Exception {
        for (int attackIV = 0; attackIV <= 15; attackIV++) {
            for (int defenseIV = 0; defenseIV <= 15; defenseIV++) {
                for (int staminaIV = 0; staminaIV <= 15;staminaIV++) {
                    float percent = new IVResult(null, 0, attackIV, defenseIV, staminaIV).getPercent();
                    assertTrue("IV less than 0", percent >= 0);
                    assertTrue("IV more than 100", percent <= 100);
                }
            }
        }
    }

    @Test
    public void levelToDustTest() throws Exception {
        for (float level = 1; level <= 40; level += 0.5f) {
            assertNotEquals("No dust found", IVCalculator.levelToDust(level), 0);
        }
    }

    @Test
    public void dustToLevelTest() throws Exception {
        for (int dust : IVCalculator.dustList) {
            assertNotEquals("No level found", IVCalculator.dustToLevel(dust), null);
            IVCalculator.dustToLevel(dust, true);
        }
    }

}