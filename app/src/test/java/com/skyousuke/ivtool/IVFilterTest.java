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

import com.skyousuke.ivtool.Pokemon.StatType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class IVFilterTest {

    @Test
    public void filterIVRangeTest() {
        assertEquals(false, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 15, 15, 15), Phrase.INSTINCT_IV_FAIR));
        assertEquals(true, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 0, 0, 0), Phrase.INSTINCT_IV_FAIR));
        assertEquals(false, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 0, 0, 0), Phrase.INSTINCT_IV_AVERAGE));
        assertEquals(true, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 8, 8, 8), Phrase.INSTINCT_IV_AVERAGE));
        assertEquals(false, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 0, 0, 0), Phrase.INSTINCT_IV_GOOD));
        assertEquals(true, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 10, 10, 10), Phrase.INSTINCT_IV_GOOD));
        assertEquals(false, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 0, 0, 0), Phrase.INSTINCT_IV_EXCELLENT));
        assertEquals(true, IVCalculator
                .isCorrectIVRange(new IVResult(null, 1, 13, 13, 13), Phrase.INSTINCT_IV_EXCELLENT));
    }

    @Test
    public void filterBestStatNoEqualTest() {
        assertEquals(true, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 15, 0, 0), StatType.ATTACK));
        assertEquals(true, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 0, 15, 0), StatType.DEFENSE));
        assertEquals(true, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 0, 0, 15), StatType.STAMINA));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 0, 15, 0), StatType.ATTACK));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 0, 0, 15), StatType.DEFENSE));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 15, 0, 0), StatType.STAMINA));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 15, 15, 0), StatType.ATTACK));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 0, 15, 15), StatType.DEFENSE));
        assertEquals(false, IVCalculator
                .isCorrectBestStat(new IVResult(null, 1, 15, 0, 15), StatType.STAMINA));
    }

    @Test
    public void filterBestStatOneEqualTest() {
        Random random = new Random();

        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 15, 14), StatType.ATTACK, StatType.DEFENSE));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 14, 15), StatType.ATTACK, StatType.STAMINA));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 15, 14), StatType.DEFENSE, StatType.ATTACK));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 14, 15, 15), StatType.DEFENSE, StatType.STAMINA));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 14, 15), StatType.STAMINA, StatType.ATTACK));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 14, 15, 15), StatType.STAMINA, StatType.DEFENSE));

        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 15, random.nextInt(14)), StatType.ATTACK, StatType.DEFENSE));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, random.nextInt(14), 15), StatType.ATTACK, StatType.STAMINA));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 15, random.nextInt(14)), StatType.DEFENSE, StatType.ATTACK));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, random.nextInt(14), 15, 15), StatType.DEFENSE, StatType.STAMINA));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, random.nextInt(14), 15), StatType.STAMINA, StatType.ATTACK));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, random.nextInt(14), 15, 15), StatType.STAMINA, StatType.DEFENSE));

        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, random.nextInt(14), 0), StatType.ATTACK, StatType.DEFENSE));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 15, 0, random.nextInt(14)), StatType.ATTACK, StatType.STAMINA));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, random.nextInt(14), 15, 0), StatType.DEFENSE, StatType.ATTACK));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 0, 15, random.nextInt(14)), StatType.DEFENSE, StatType.STAMINA));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, random.nextInt(14), 0, 15), StatType.STAMINA, StatType.ATTACK));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, 0, random.nextInt(14), 15), StatType.STAMINA, StatType.DEFENSE));
    }

    @Test
    public void filterBestStatTwoEqualTest() {
        Random random = new Random();
        int randomStat = random.nextInt(13) + 2;

        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, randomStat, randomStat, randomStat),
                StatType.ATTACK, StatType.DEFENSE, StatType.STAMINA));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, randomStat, randomStat, randomStat),
                StatType.DEFENSE, StatType.STAMINA, StatType.ATTACK));
        assertEquals(true, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, randomStat / 2, randomStat / 2, randomStat / 2),
                StatType.ATTACK, StatType.DEFENSE, StatType.STAMINA));

        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, randomStat, randomStat / 2, randomStat),
                StatType.ATTACK, StatType.DEFENSE, StatType.STAMINA));
        assertEquals(false, IVCalculator.isCorrectBestStat(
                new IVResult(null, 1, randomStat, randomStat, randomStat / 2),
                StatType.ATTACK, StatType.DEFENSE, StatType.STAMINA));
    }

    @Test
    public void filterStatRangeTest() {
        assertEquals(true, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 7, 6, 5),
                Phrase.VALOR_STATS_FAIR
        ));
        assertEquals(true, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 8, 10, 12),
                Phrase.INSTINCT_STATS_AVERAGE
        ));
        assertEquals(true, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 13, 14, 0),
                Phrase.INSTINCT_STATS_GOOD
        ));
        assertEquals(true, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 15, 0, 0),
                Phrase.INSTINCT_STATS_EXCELLENT
        ));

        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 8, 0, 0),
                Phrase.VALOR_STATS_FAIR
        ));
        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 7, 0, 0),
                Phrase.INSTINCT_STATS_AVERAGE
        ));
        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 13, 0, 0),
                Phrase.INSTINCT_STATS_AVERAGE
        ));
        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 12, 0, 0),
                Phrase.INSTINCT_STATS_GOOD
        ));
        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 15, 0, 0),
                Phrase.INSTINCT_STATS_GOOD
        ));
        assertEquals(false, IVCalculator.isCorrectStatRange(
                new IVResult(null, 1, 0, 0, 0),
                Phrase.INSTINCT_STATS_EXCELLENT
        ));
    }


    @Test
    public void filterIVTest() {
        List<IVResult> ivResults = new ArrayList<>();

        ivResults.add(new IVResult(null, 1, 0, 0, 0));
        ivResults.add(new IVResult(null, 1, 15, 15, 15));
        ivResults.add(new IVResult(null, 1, 1, 1, 1));
        ivResults.add(new IVResult(null, 1, 1, 2, 3));
        IVCalculator.filterIV(ivResults, null, null, StatType.ATTACK, StatType.DEFENSE, StatType.STAMINA);
        assertEquals(2, ivResults.size());

        ivResults.clear();

        ivResults.add(new IVResult(null, 1, 0, 0, 0));
        ivResults.add(new IVResult(null, 1, 15, 15, 15));
        ivResults.add(new IVResult(null, 1, 1, 1, 0));
        ivResults.add(new IVResult(null, 1, 1, 2, 3));
        ivResults.add(new IVResult(null, 1, 15, 15, 3));
        IVCalculator.filterIV(ivResults, null, null, StatType.ATTACK, StatType.DEFENSE, null);
        assertEquals(2, ivResults.size());

        ivResults.clear();

        ivResults.add(new IVResult(null, 1, 0, 0, 0));
        ivResults.add(new IVResult(null, 1, 15, 15, 15));
        ivResults.add(new IVResult(null, 1, 15, 0, 8));
        ivResults.add(new IVResult(null, 1, 13, 12, 13));
        ivResults.add(new IVResult(null, 1, 15, 14, 15));
        IVCalculator.filterIV(ivResults, null, null, StatType.ATTACK, null, StatType.STAMINA);
        assertEquals(2, ivResults.size());

        ivResults.clear();

        ivResults.add(new IVResult(null, 1, 0, 0, 0));
        ivResults.add(new IVResult(null, 1, 1, 1, 0));
        ivResults.add(new IVResult(null, 1, 15, 15, 15));
        ivResults.add(new IVResult(null, 1, 15, 14, 14));
        ivResults.add(new IVResult(null, 1, 12, 10, 8));
        IVCalculator.filterIV(ivResults, null, null, StatType.ATTACK, null, null);
        assertEquals(2, ivResults.size());
    }
}
