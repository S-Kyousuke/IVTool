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
public enum Team {
    VALOR,
    INSTINCT,
    MYSTIC;

    public static final Team[] values = Team.values();

    public Phrase[] getIVPhrases() {
        switch (this) {
            case VALOR:
                return new Phrase[] {
                        Phrase.VALOR_IV_FAIR,
                        Phrase.VALOR_IV_AVERAGE,
                        Phrase.VALOR_IV_GOOD,
                        Phrase.VALOR_IV_EXCELLENT
                };
            case INSTINCT:
                return new Phrase[] {
                        Phrase.INSTINCT_IV_FAIR,
                        Phrase.INSTINCT_IV_AVERAGE,
                        Phrase.INSTINCT_IV_GOOD,
                        Phrase.INSTINCT_IV_EXCELLENT
                };
            case MYSTIC:
                return new Phrase[] {
                        Phrase.MYSTIC_IV_FAIR,
                        Phrase.MYSTIC_IV_AVERAGE,
                        Phrase.MYSTIC_IV_GOOD,
                        Phrase.MYSTIC_IV_EXCELLENT
                };
        }
        return null;
    }

    public Phrase[] getStatPhrases() {
        switch (this) {
            case VALOR:
                return new Phrase[] {
                        Phrase.VALOR_STATS_FAIR,
                        Phrase.VALOR_STATS_AVERAGE,
                        Phrase.VALOR_STATS_GOOD,
                        Phrase.VALOR_STATS_EXCELLENT
                };
            case INSTINCT:
                return new Phrase[] {
                        Phrase.INSTINCT_STATS_FAIR,
                        Phrase.INSTINCT_STATS_AVERAGE,
                        Phrase.INSTINCT_STATS_GOOD,
                        Phrase.INSTINCT_STATS_EXCELLENT
                };
            case MYSTIC:
                return new Phrase[] {
                        Phrase.MYSTIC_STATS_FAIR,
                        Phrase.MYSTIC_STATS_AVERAGE,
                        Phrase.MYSTIC_STATS_GOOD,
                        Phrase.MYSTIC_STATS_EXCELLENT
                };
        }
        return null;
    }

    @Override
    public String toString() {
        return name().charAt(0) + this.name().toLowerCase().substring(1);
    }
}
