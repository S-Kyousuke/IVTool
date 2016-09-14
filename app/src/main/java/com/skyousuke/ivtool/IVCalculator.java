package com.skyousuke.ivtool;

import com.skyousuke.ivtool.Phrase.PhraseRating;
import com.skyousuke.ivtool.Phrase.PhraseType;
import com.skyousuke.ivtool.Pokemon.StatType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class IVCalculator {

    public static final Integer[] dustList = new Integer[]{
            200, 400, 600, 800, 1000,
            1300, 1600, 1900, 2200, 2500,
            3000, 3500, 4000, 4500, 5000,
            6000, 7000, 8000, 9000, 10000
    };

    public static final double cpMultiplier[] = new double[]{
            0.094000000000000, 0.135137431784996, 0.166397870000000, 0.192650919047850, 0.215732470000000,
            0.236572661315177, 0.255720050000000, 0.273530381203710, 0.290249880000000, 0.306057377479222,
            0.321087600000000, 0.335445036247676, 0.349212680000000, 0.362457751113460, 0.375235590000000,
            0.387592406365363, 0.399567280000000, 0.411193551403768, 0.422500010000000, 0.433511688330828,
            0.443107550000000, 0.453059959102438, 0.462798390000000, 0.472336083166946, 0.481684950000000,
            0.490855800344055, 0.499858440000000, 0.508701765000018, 0.517393950000000, 0.525942511279990,
            0.534354330000000, 0.542635767034066, 0.550792690000000, 0.558830576321962, 0.566754520000000,
            0.574569153031496, 0.582278910000000, 0.589887917070428, 0.597400010000000, 0.604818813879744,
            0.612157290000000, 0.619404111709588, 0.626567130000000, 0.633649172885941, 0.640652950000000,
            0.647580966556540, 0.654435630000000, 0.661219252378587, 0.667934000000000, 0.674581895887075,
            0.681164920000000, 0.687684923595978, 0.694143650000000, 0.700542870021473, 0.706884210000000,
            0.713169118968232, 0.719399090000000, 0.725575603638538, 0.731700000000000, 0.734741036012009,
            0.737769480000000, 0.740785573840211, 0.743789430000000, 0.746781210911218, 0.749761040000000,
            0.752729086652151, 0.755685510000000, 0.758630378256935, 0.761563840000000, 0.764486064742743,
            0.767397170000000, 0.770297265550326, 0.773186500000000, 0.776064961567361, 0.778932750000000,
            0.781790054818340, 0.784636970000000, 0.787473577639962, 0.790300010000000, 0.793116363843478
    };

    private static final IVResultComparator comparator = new IVResultComparator();

    private static class IVResultComparator implements Comparator<IVResult> {
        @Override
        public int compare(IVResult result1, IVResult result2) {
            if (result1.getLevel() != result2.getLevel()) {
                return result1.getLevel() < result2.getLevel() ? -1
                        : result1.getLevel() > result2.getLevel() ? 1
                        : 0;
            }
            if (result1.getPercent() != result2.getPercent()) {
                return result1.getPercent() < result2.getPercent() ? -1
                        : result1.getPercent() > result2.getPercent() ? 1
                        : 0;
            }
            if (result1.getAttack() != result2.getAttack()) {
                return result1.getAttack() < result2.getAttack() ? -1
                        : result1.getAttack() > result2.getAttack() ? 1
                        : 0;
            }
            if (result1.getDefense() != result2.getDefense()) {
                return result1.getDefense() < result2.getDefense() ? -1
                        : result1.getDefense() > result2.getDefense() ? 1
                        : 0;
            }
            return result1.getStamina() < result2.getStamina() ? -1
                    : result1.getStamina() > result2.getStamina() ? 1
                    : 0;
        }
    }

    private static class BattleIV {

        public final int attackIV;
        public final int defenseIV;

        public BattleIV(int attackIV, int defenseIV) {
            this.attackIV = attackIV;
            this.defenseIV = defenseIV;
        }
    }

    public static float[] dustToLevel(int dust) {
        switch (dust) {
            case 200:
                return new float[]{1, 1.5f, 2, 2.5f};
            case 400:
                return new float[]{3, 3.5f, 4, 4.5f};
            case 600:
                return new float[]{5, 5.5f, 6, 6.5f};
            case 800:
                return new float[]{7, 7.5f, 8, 8.5f};
            case 1000:
                return new float[]{9, 9.5f, 10, 10.5f};
            case 1300:
                return new float[]{11, 11.5f, 12, 12.5f};
            case 1600:
                return new float[]{13, 13.5f, 14, 14.5f};
            case 1900:
                return new float[]{15, 15.5f, 16, 16.5f};
            case 2200:
                return new float[]{17, 17.5f, 18, 18.5f};
            case 2500:
                return new float[]{19, 19.5f, 20, 20.5f};
            case 3000:
                return new float[]{21, 21.5f, 22, 22.5f};
            case 3500:
                return new float[]{23, 23.5f, 24, 24.5f};
            case 4000:
                return new float[]{25, 25.5f, 26, 26.5f};
            case 4500:
                return new float[]{27, 27.5f, 28, 28.5f};
            case 5000:
                return new float[]{29, 29.5f, 30, 30.5f};
            case 6000:
                return new float[]{31, 31.5f, 32, 32.5f};
            case 7000:
                return new float[]{33, 33.5f, 34, 34.5f};
            case 8000:
                return new float[]{35, 35.5f, 36, 36.5f};
            case 9000:
                return new float[]{37, 37.5f, 38, 38.5f};
            case 10000:
                return new float[]{39, 39.5f, 40};
            default:
                return null;
        }
    }

    public static float[] dustToLevel(int dust, boolean halfLevel) {
        if (halfLevel) {
            return dustToLevel(dust);
        } else {
            float[] levels = dustToLevel(dust);
            if (levels != null) {
                float[] noHalfLevels = new float[2];
                noHalfLevels[0] = levels[0];
                noHalfLevels[1] = levels[2];
                return noHalfLevels;
            }
            return null;
        }
    }

    public static int levelToDust(float level) {
        for (int dust : dustList) {
            float[] levels = dustToLevel(dust);
            if (levels != null) {
                for (float eachLevel : levels) {
                    if (level == eachLevel)
                        return dust;
                }
            }
        }
        return 0;
    }

    public static Set<Integer> buildCpSet(Pokemon pokemon, int dust, boolean halfLevel) {
        Set<Integer> cpSet = new TreeSet<>();
        float[] levels = dustToLevel(dust, halfLevel);
        if (levels != null) {
            for (float level : levels) {
                for (int attackIV = 0; attackIV <= 15; attackIV++) {
                    for (int defenseIV = 0; defenseIV <= 15; defenseIV++) {
                        for (int staminaIV = 0; staminaIV <= 15; staminaIV++) {
                            cpSet.add(pokemon.getCp(level, attackIV, defenseIV, staminaIV));
                        }
                    }
                }
            }
        }
        return cpSet;
    }

    public static Set<Integer> buildHpSet(Pokemon pokemon, int dust, boolean halfLevel) {
        Set<Integer> hpSet = new TreeSet<>();
        float[] levels = dustToLevel(dust, halfLevel);
        if (levels != null) {
            for (float level : levels) {
                for (int staminaIV = 0; staminaIV <= 15; staminaIV++) {
                    hpSet.add(pokemon.getHp(level, staminaIV));
                }
            }
        }
        return hpSet;
    }

    public static void sort(List<IVResult> results) {
        Collections.sort(results, comparator);
    }

    /**
     * @param results all iv result.
     * @return min and max iv result (min at index 0).
     */
    public static IVResult[] findMinMax(List<IVResult> results) {
        IVResult[] minMax = new IVResult[]{results.get(0), results.get(0)};
        final int size = results.size();
        for (int i = 1; i < size; ++i) {
            if (results.get(i).getPercent() < minMax[0].getPercent()) minMax[0] = results.get(i);
            else if (results.get(i).getPercent() > minMax[1].getPercent()) minMax[1] = results.get(i);
        }
        return minMax;
    }

    public static void filterIV(List<IVResult> results, Phrase rangePhrase, Phrase statPhrase,
                                StatType bestStat, StatType equalStat1, StatType equalStat2) {
        for (Iterator<IVResult> it = results.iterator(); it.hasNext(); ) {
            IVResult result = it.next();
            if (rangePhrase != null && !isCorrectIVRange(result, rangePhrase)) {
                it.remove();
                continue;
            }
            if (statPhrase != null && !isCorrectStatRange(result, statPhrase)) {
                it.remove();
                continue;
            }
            if (bestStat != null && bestStat != StatType.NONE) {
                if (!isCorrectBestStat(result, bestStat, equalStat1, equalStat2)) {
                    it.remove();
                }
            }
        }
    }

    public static boolean isCorrectIVRange(IVResult result, Phrase rangePhrase) {
        if (rangePhrase == null)
            throw new NullPointerException("Range Phrase is null!");
        if (rangePhrase.getType() == PhraseType.STATS)
            throw new NullPointerException("Wrong Phrase type!");

        int sumIV = result.getSumIV();
        switch (rangePhrase.getRating()) {
            case PhraseRating.FAIR:
                if (sumIV > 22)
                    return false;
                break;
            case PhraseRating.AVERAGE:
                if (sumIV < 23 || sumIV > 29)
                    return false;
                break;
            case PhraseRating.GOOD:
                if (sumIV < 30 || sumIV > 36)
                    return false;
                break;
            case PhraseRating.EXCELLENT:
                if (sumIV < 37)
                    return false;
                break;
        }
        return true;
    }

    public static boolean isCorrectStatRange(IVResult result, Phrase statPhrase) {
        if (statPhrase == null)
            throw new NullPointerException("Stat Phrase is null!");
        if (statPhrase.getType() == PhraseType.IV)
            throw new NullPointerException("Wrong Phrase type!");

        int attackIV = result.getAttack();
        int defenseIV = result.getDefense();
        int staminaIV = result.getStamina();
        int maxIV = 0;
        if (attackIV > maxIV) maxIV = attackIV;
        if (defenseIV > maxIV) maxIV = defenseIV;
        if (staminaIV > maxIV) maxIV = staminaIV;

        switch (statPhrase.getRating()) {
            case PhraseRating.FAIR:
                if (maxIV > 7)
                    return false;
                break;
            case PhraseRating.AVERAGE:
                if (maxIV < 8 || maxIV > 12)
                    return false;
                break;
            case PhraseRating.GOOD:
                if (maxIV != 13 && maxIV != 14)
                    return false;
                break;
            case PhraseRating.EXCELLENT:
                if (maxIV < 15)
                    return false;
                break;
        }
        return true;
    }

    public static boolean isCorrectBestStat(IVResult result, StatType bestStat,
                                            StatType equalStat1, StatType equalStat2) {
        if (bestStat == null || bestStat == StatType.NONE)
            throw new IllegalArgumentException("Wrong Best stat!");

        boolean hasEqualStat1 = (equalStat1 != null && equalStat1 != StatType.NONE);
        boolean hasEqualStat2 = (equalStat2 != null && equalStat2 != StatType.NONE);

        if (hasEqualStat1 && hasEqualStat2)
            return (result.getIV(bestStat) != 0)
                    && (result.getIV(bestStat) == result.getIV(equalStat1))
                    && (result.getIV(bestStat) == result.getIV(equalStat2));
        else if (hasEqualStat1) return isCorrectBestStat(result, bestStat, equalStat1);
        else if (hasEqualStat2) return isCorrectBestStat(result, bestStat, equalStat2);
        return isCorrectBestStat(result, bestStat);
    }

    public static boolean isCorrectBestStat(IVResult result, StatType bestStat, StatType equalStat) {
        if (bestStat == null || bestStat == StatType.NONE)
            throw new IllegalArgumentException("Wrong Best stat!");
        if (equalStat == null || equalStat == StatType.NONE)
            throw new IllegalArgumentException("Wrong Equal stat!");
        if (bestStat == equalStat)
            throw new IllegalArgumentException("Best stats is same as Equal stat!");

        final int attack = result.getAttack();
        final int defense = result.getDefense();
        final int stamina = result.getStamina();

        switch (bestStat) {
            case ATTACK:
                if (attack == 0) return false;
                if (equalStat == StatType.DEFENSE) {
                    if (attack != defense || attack <= stamina)
                        return false;
                } else {
                    if (attack != stamina || attack <= defense)
                        return false;
                }
                break;
            case DEFENSE:
                if (defense == 0) return false;
                if (equalStat == StatType.ATTACK) {
                    if (defense != attack || defense <= stamina)
                        return false;
                } else {
                    if (defense != stamina || defense <= attack)
                        return false;
                }
                break;
            case STAMINA:
                if (stamina == 0) return false;
                if (equalStat == StatType.ATTACK) {
                    if (stamina != attack || stamina <= defense)
                        return false;
                } else {
                    if (stamina != defense || stamina <= attack)
                        return false;
                }
                break;
        }
        return true;
    }

    public static boolean isCorrectBestStat(IVResult result, StatType bestStat) {
        if (bestStat == null || bestStat == StatType.NONE)
            throw new IllegalArgumentException("Wrong Best stat!");

        final int attack = result.getAttack();
        final int defense = result.getDefense();
        final int stamina = result.getStamina();
        switch (bestStat) {
            case ATTACK:
                if (attack <= defense || attack <= stamina || attack == 0)
                    return false;
                break;
            case DEFENSE:
                if (defense <= attack || defense <= stamina || defense == 0)
                    return false;
                break;
            case STAMINA:
                if (stamina <= attack || stamina <= defense || stamina == 0)
                    return false;
                break;
        }
        return true;
    }

    public static List<IVResult> findPossibleIV(Pokemon pokemon, int dust, int cp, int hp, boolean halfLevel) {
        float[] levels = dustToLevel(dust);
        List<IVResult> results = new ArrayList<>();
        if (levels != null) {
            if (!halfLevel) {
                float[] adjustedLevels = new float[2];
                adjustedLevels[0] = levels[0];
                adjustedLevels[1] = levels[2];
                levels = adjustedLevels;
            }
            for (float level : levels) {
                for (int staminaIV : findPossibleStaminaIV(pokemon, level, hp)) {
                    for (BattleIV battleIV : findPossibleBattleIV(pokemon, level, cp, staminaIV)) {
                        results.add(new IVResult(pokemon, level, battleIV.attackIV, battleIV.defenseIV, staminaIV));
                    }
                }
            }
        }
        return results;
    }

    private static List<Integer> findPossibleStaminaIV(Pokemon pokemon, float level, int hp) {
        List<Integer> staminaIVs = new ArrayList<>();
        for (int staminaIV = 0; staminaIV <= 15; staminaIV++) {
            if (pokemon.getHp(level, staminaIV) == hp) {
                staminaIVs.add(staminaIV);
            }
        }
        return staminaIVs;
    }

    private static List<BattleIV> findPossibleBattleIV(Pokemon pokemon, float level, int cp, int staminaIV) {
        List<BattleIV> battleIVs = new ArrayList<>();
        for (int attackIV = 0; attackIV <= 15; attackIV++) {
            for (int defenseIV = 0; defenseIV <= 15; defenseIV++) {
                if (pokemon.getCp(level, attackIV, defenseIV, staminaIV) == cp) {
                    battleIVs.add(new BattleIV(attackIV, defenseIV));
                }
            }
        }
        return battleIVs;
    }

    private IVCalculator() {
    }

}
