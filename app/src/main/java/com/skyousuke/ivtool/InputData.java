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

import java.util.Arrays;
import java.util.Set;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class InputData {

    public static final int NOT_AVAILABLE = -1;
    public static final int NOT_FOUND = -2;

    private int pokemonOrdinal = NOT_AVAILABLE;
    private int dust = NOT_AVAILABLE;
    private int cp = NOT_AVAILABLE;
    private int hp = NOT_AVAILABLE;
    private boolean poweredUp;

    private Team team;
    private Phrase rangePhrase;
    private Phrase statPhrase;
    private StatType bestStat;
    private StatType equalStat1;
    private StatType equalStat2;

    private Listener listener;

    public Pokemon getPokemon() {
        return (pokemonOrdinal == NOT_AVAILABLE || pokemonOrdinal == NOT_FOUND) ?
                null : Pokemon.values[pokemonOrdinal];
    }

    public void setPokemon(int pokemonOrdinal) {
        this.pokemonOrdinal = pokemonOrdinal;
        onDataChange(true);
    }

    public void setPokemon(Pokemon pokemon) {
        setPokemon((pokemon == null) ? (NOT_FOUND) : pokemon.ordinal());
    }

    public void setPokemon(String pokemon) {
        if (pokemon.equals("")) unsetPokemon();
        else setPokemon(Pokemon.getValue(pokemon));
    }

    public int getDust() {
        return dust;
    }

    public void setDust(int dust) {
        this.dust = dust;
        onDataChange(true);
    }

    public void setDust(String dust) {
        try {
            setDust(Integer.parseInt(dust));
        } catch (NumberFormatException e) {
            unsetDust();
        }
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
        onDataChange();
    }

    public void setCp(String cp) {
        try {
            setCp(Integer.parseInt(cp));
        } catch (NumberFormatException e) {
            unsetCp();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        onDataChange();
    }

    public void setHp(String hp) {
        try {
            setHp(Integer.parseInt(hp));
        } catch (NumberFormatException e) {
            unsetHp();
        }
    }

    public boolean isPoweredUp() {
        return poweredUp;
    }

    public void setPoweredUp(boolean poweredUp) {
        this.poweredUp = poweredUp;
        onDataChange(true);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        onDataChange();
    }

    public Phrase getRangePhrase() {
        return rangePhrase;
    }

    public void setRangePhrase(Phrase rangePhrase) {
        this.rangePhrase = rangePhrase;
        onDataChange();
    }

    public Phrase getStatPhrase() {
        return statPhrase;
    }

    public void setStatPhrase(Phrase statPhrase) {
        this.statPhrase = statPhrase;
        onDataChange();
    }

    public StatType getBestStat() {
        return bestStat;
    }

    public void setBestStat(StatType bestStat) {
        this.bestStat = bestStat;
        validateStat();
        onDataChange();
    }

    public StatType getEqualStat1() {
        return equalStat1;
    }

    public void setEqualStat1(StatType equalStat1) {
        this.equalStat1 = equalStat1;
        validateStat();
        onDataChange();
    }

    public StatType getEqualStat2() {
        return equalStat2;
    }

    public void setEqualStat2(StatType equalStat2) {
        this.equalStat2 = equalStat2;
        validateStat();
        onDataChange();
    }

    public boolean foundPokemon() {
        return !isPokemonUnset() && pokemonOrdinal != NOT_FOUND;
    }

    public boolean foundDust() {
        return !isDustUnset() && Arrays.asList(IVCalculator.dustList).contains(dust);
    }

    public boolean foundCp(Set<Integer> cpSet) {
        return !isCpUnset() && cpSet != null && cpSet.contains(cp);
    }

    public boolean foundHp(Set<Integer> hpSet) {
        return !isHpUnset() && hpSet != null && hpSet.contains(hp);
    }

    public boolean isPokemonUnset() {
        return pokemonOrdinal == NOT_AVAILABLE;
    }

    public boolean isDustUnset() {
        return dust == NOT_AVAILABLE;
    }

    public boolean isCpUnset() {
        return cp == NOT_AVAILABLE;
    }

    public boolean isHpUnset() {
        return hp == NOT_AVAILABLE;
    }

    public void unsetPokemon() {
        setPokemon(NOT_AVAILABLE);
    }

    public void unsetDust() {
        setDust(NOT_AVAILABLE);
    }

    public void unsetCp() {
        setCp(NOT_AVAILABLE);
    }

    public void unsetHp() {
        setHp(NOT_AVAILABLE);
    }

    public void unsetTeam() {
        setTeam(null);
    }

    public void unsetRangePhrase() {
        setRangePhrase(null);
    }

    public void unsetStatPhrase() {
        setStatPhrase(null);
    }

    public void unsetBestStat() {
        setBestStat(null);
    }

    public void unsetEqualStat1() {
        setEqualStat1(null);
    }

    public void unsetEqualStat2() {
        setEqualStat2(null);
    }

    public void validateStat() {
        if (bestStat != null && (bestStat == equalStat1 || bestStat == equalStat2)
                || equalStat1 != null && equalStat1 == equalStat2)
            throw new IllegalArgumentException("Duplicate Stat type!");
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void onDataChange() {
        if (listener != null) listener.onDataChange(false);
    }

    private void onDataChange(boolean needNewSet) {
        if (listener != null) listener.onDataChange(needNewSet);
    }

    public boolean readyToCalculateIV(Set<Integer> cpSet, Set<Integer> hpSet) {
        return foundPokemon() && foundDust() && foundCp(cpSet) && foundHp(hpSet);
    }

    @Override
    public String toString() {
        return "Pokemon : " + ((getPokemon() != null) ? String.valueOf(getPokemon()) : pokemonOrdinal) + '\n'
                + "Dust " + String.valueOf(dust) + '\n'
                + "CP: " + String.valueOf(cp) + '\n'
                + "HP: " + String.valueOf(hp) + '\n'
                + "Powered Up: " + String.valueOf(poweredUp) + '\n'
                + "Team: " + String.valueOf(team) + '\n'
                + "Range Phrase: " + String.valueOf(rangePhrase) + '\n'
                + "Best stat: " + String.valueOf(bestStat) + '\n'
                + "Equal stat 1: " + String.valueOf(equalStat1) + '\n'
                + "Equal stat 2: " + String.valueOf(equalStat2) + '\n'
                + "Stat Phrase: " + String.valueOf(statPhrase);
    }

    public interface Listener {

        void onDataChange(boolean needNewSet);

    }
}
