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

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public enum Pokemon {
    BULBASAUR(1, 126, 126, 90),
    IVYSAUR(2, 156, 158, 120),
    VENUSAUR(3, 198, 200, 160),
    CHARMANDER(4, 128, 108, 78),
    CHARMELEON(5, 160, 140, 116),
    CHARIZARD(6, 212, 182, 156),
    SQUIRTLE(7, 112, 142, 88),
    WARTORTLE(8, 144, 176, 118),
    BLASTOISE(9, 186, 222, 158),
    CATERPIE(10, 62, 66, 90),
    METAPOD(11, 56, 86, 100),
    BUTTERFREE(12, 144, 144, 120),
    WEEDLE(13, 68, 64, 80),
    KAKUNA(14, 62, 82, 90),
    BEEDRILL(15, 144, 130, 130),
    PIDGEY(16, 94, 90, 80),
    PIDGEOTTO(17, 126, 122, 126),
    PIDGEOT(18, 170, 166, 166),
    RATTATA(19, 92, 86, 60),
    RATICATE(20, 146, 150, 110),
    SPEAROW(21, 102, 78, 80),
    FEAROW(22, 168, 146, 130),
    EKANS(23, 112, 112, 70),
    ARBOK(24, 166, 166, 120),
    PIKACHU(25, 124, 108, 70),
    RAICHU(26, 200, 154, 120),
    SANDSHREW(27, 90, 114, 100),
    SANDSLASH(28, 150, 172, 150),
    NIDORAN_FEMALE(29, 100, 104, 110),
    NIDORINA(30, 132, 136, 140),
    NIDOQUEEN(31, 184, 190, 180),
    NIDORAN_MALE(32, 110, 94, 92),
    NIDORINO(33, 142, 128, 122),
    NIDOKING(34, 204, 170, 162),
    CLEFAIRY(35, 116, 124, 140),
    CLEFABLE(36, 178, 178, 190),
    VULPIX(37, 106, 118, 76),
    NINETALES(38, 176, 194, 146),
    JIGGLYPUFF(39, 98, 54, 230),
    WIGGLYTUFF(40, 168, 108, 280),
    ZUBAT(41, 88, 90, 80),
    GOLBAT(42, 164, 164, 150),
    ODDISH(43, 134, 130, 90),
    GLOOM(44, 162, 158, 120),
    VILEPLUME(45, 202, 190, 150),
    PARAS(46, 122, 120, 70),
    PARASECT(47, 162, 170, 120),
    VENONAT(48, 108, 118, 120),
    VENOMOTH(49, 172, 154, 140),
    DIGLETT(50, 108, 86, 20),
    DUGTRIO(51, 148, 140, 70),
    MEOWTH(52, 104, 94, 80),
    PERSIAN(53, 156, 146, 130),
    PSYDUCK(54, 132, 112, 100),
    GOLDUCK(55, 194, 176, 160),
    MANKEY(56, 122, 96, 80),
    PRIMEAPE(57, 178, 150, 130),
    GROWLITHE(58, 156, 110, 110),
    ARCANINE(59, 230, 180, 180),
    POLIWAG(60, 108, 98, 80),
    POLIWHIRL(61, 132, 132, 130),
    POLIWRATH(62, 180, 202, 180),
    ABRA(63, 110, 76, 50),
    KADABRA(64, 150, 112, 80),
    ALAKAZAM(65, 186, 152, 110),
    MACHOP(66, 118, 96, 140),
    MACHOKE(67, 154, 144, 160),
    MACHAMP(68, 198, 180, 180),
    BELLSPROUT(69, 158, 78, 100),
    WEEPINBELL(70, 190, 110, 130),
    VICTREEBEL(71, 222, 152, 160),
    TENTACOOL(72, 106, 136, 80),
    TENTACRUEL(73, 170, 196, 160),
    GEODUDE(74, 106, 118, 80),
    GRAVELER(75, 142, 156, 110),
    GOLEM(76, 176, 198, 160),
    PONYTA(77, 168, 138, 100),
    RAPIDASH(78, 200, 170, 130),
    SLOWPOKE(79, 110, 110, 180),
    SLOWBRO(80, 184, 198, 190),
    MAGNEMITE(81, 128, 138, 50),
    MAGNETON(82, 186, 180, 100),
    FARFETCHD(83, 138, 132, 104),
    DODUO(84, 126, 96, 70),
    DODRIO(85, 182, 150, 120),
    SEEL(86, 104, 138, 130),
    DEWGONG(87, 156, 192, 180),
    GRIMER(88, 124, 110, 160),
    MUK(89, 180, 188, 210),
    SHELLDER(90, 120, 112, 60),
    CLOYSTER(91, 196, 196, 100),
    GASTLY(92, 136, 82, 60),
    HAUNTER(93, 172, 118, 90),
    GENGAR(94, 204, 156, 120),
    ONIX(95, 90, 186, 70),
    DROWZEE(96, 104, 140, 120),
    HYPNO(97, 162, 196, 170),
    KRABBY(98, 116, 110, 60),
    KINGLER(99, 178, 168, 110),
    VOLTORB(100, 102, 124, 80),
    ELECTRODE(101, 150, 174, 120),
    EXEGGCUTE(102, 110, 132, 120),
    EXEGGCUTOR(103, 232, 164, 190),
    CUBONE(104, 102, 150, 100),
    MAROWAK(105, 140, 202, 120),
    HITMONLEE(106, 148, 172, 100),
    HITMONCHAN(107, 138, 204, 100),
    LICKITUNG(108, 126, 160, 180),
    KOFFING(109, 136, 142, 80),
    WEEZING(110, 190, 198, 130),
    RHYHORN(111, 110, 116, 160),
    RHYDON(112, 166, 160, 210),
    CHANSEY(113, 40, 60, 500),
    TANGELA(114, 164, 152, 130),
    KANGASKHAN(115, 142, 178, 210),
    HORSEA(116, 122, 100, 60),
    SEADRA(117, 176, 150, 110),
    GOLDEEN(118, 112, 126, 90),
    SEAKING(119, 172, 160, 160),
    STARYU(120, 130, 128, 60),
    STARMIE(121, 194, 192, 120),
    MR_MIME(122, 154, 196, 80),
    SCYTHER(123, 176, 180, 140),
    JYNX(124, 172, 134, 130),
    ELECTABUZZ(125, 198, 160, 130),
    MAGMAR(126, 214, 158, 130),
    PINSIR(127, 184, 186, 130),
    TAUROS(128, 148, 184, 130),
    MAGIKARP(129, 42, 84, 40),
    GYARADOS(130, 192, 196, 190),
    LAPRAS(131, 186, 190, 260),
    DITTO(132, 110, 110, 96),
    EEVEE(133, 114, 128, 110),
    VAPOREON(134, 186, 168, 260),
    JOLTEON(135, 192, 174, 130),
    FLAREON(136, 238, 178, 130),
    PORYGON(137, 156, 158, 130),
    OMANYTE(138, 132, 160, 70),
    OMASTAR(139, 180, 202, 140),
    KABUTO(140, 148, 142, 60),
    KABUTOPS(141, 190, 190, 120),
    AERODACTYL(142, 182, 162, 160),
    SNORLAX(143, 180, 180, 320),
    ARTICUNO(144, 198, 242, 180),
    ZAPDOS(145, 232, 194, 180),
    MOLTRES(146, 242, 194, 180),
    DRATINI(147, 128, 110, 82),
    DRAGONAIR(148, 170, 152, 122),
    DRAGONITE(149, 250, 212, 182),
    MEWTWO(150, 284, 202, 212),
    MEW(151, 220, 220, 200);

    public static final Pokemon[] values = Pokemon.values();
    public static final int MAX_IV = 15;

    private final int number;
    private final int attack;
    private final int defense;
    private final int stamina;

    Pokemon(int number, int attack, int defense, int stamina) {
        this.number = number;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
    }

    public int getNumber() {
        return number;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getStamina() {
        return stamina;
    }

    public int getHp(float level, int staminaIV) {
        return Math.max((int) ((stamina + staminaIV) * IVCalculator.cpMultiplier[(int) (level * 2 - 2)]), 10);
    }

    public int getCp(float level, int attackIV, int defenseIV, int staminaIV) {
        return Math.max((int) ((attack + attackIV) * Math.sqrt((defense + defenseIV)
                * (stamina + staminaIV)) * IVCalculator.cpMultiplier[(int) (level * 2 - 2)]
                * IVCalculator.cpMultiplier[(int) (level * 2 - 2)] / 10), 10);
    }

    @Override
    public String toString() {
        switch (this) {
            case NIDORAN_FEMALE:
                return "Nidoran ♀";
            case NIDORAN_MALE:
                return "Nidoran ♂";
            case FARFETCHD:
                return "Farfetch'd";
            case MR_MIME:
                return "Mr. Mime";
            default:
                return name().charAt(0) + this.name().toLowerCase().substring(1);
        }
    }

    public static Pokemon getValue(String name) {
        for (Pokemon pokemon : values) {
            if (pokemon.toString().equals(name))
                return pokemon;
        }
        return null;
    }

    public enum StatType {
        NONE,
        ATTACK,
        DEFENSE,
        STAMINA;

        public static final StatType[] values = StatType.values();

        @Override
        public String toString() {
            switch (this) {
                case NONE:
                    return "-";
                case STAMINA:
                    return "HP";
                default:
                    return name().charAt(0) + this.name().toLowerCase().substring(1);
            }
        }

        public StatType[] getOthers() {
            int index = 0;
            StatType[] others = new StatType[2];
            for (StatType type : values) {
                if (type == NONE || type == this) continue;
                others[index] = type;
                ++index;
            }
            return others;
        }
    }
}
