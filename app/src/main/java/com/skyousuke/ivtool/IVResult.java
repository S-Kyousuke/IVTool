package com.skyousuke.ivtool;

import com.skyousuke.ivtool.Pokemon.StatType;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public class IVResult {

    private final Pokemon pokemon;
    private final float level;
    private final int attack;
    private final int defense;
    private final int stamina;
    private final float percent;

    public IVResult(Pokemon pokemon, float level, int attack, int defense, int stamina) {
        this.pokemon = pokemon;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        percent = (100.0f * (attack + defense + stamina)) / (Pokemon.MAX_IV * 3);
    }

    public float getPercent() {
        return percent;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public int getStamina() {
        return stamina;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public float getLevel() {
        return level;
    }

    public int getSumIV() {
        return attack + defense + stamina;
    }

    public int getIV(StatType stat) {
        switch (stat) {
            case ATTACK:
                return getAttack();
            case DEFENSE:
                return getDefense();
            case STAMINA:
                return getStamina();
        }
        throw new IllegalArgumentException("Wrong stat type!");
    }

    @Override
    public String toString() {
        return String.valueOf(pokemon) + ' ' + level + ' ' + attack + ' ' + defense + ' ' + stamina;
    }
}
