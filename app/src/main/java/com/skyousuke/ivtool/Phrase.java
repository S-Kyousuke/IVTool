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
public enum Phrase {

    VALOR_IV_FAIR(
            "Overall, your ... may not be great in battle, but I still like it!",
            Team.VALOR,
            PhraseType.IV,
            PhraseRating.FAIR
    ),
    VALOR_IV_AVERAGE(
            "Overall, your ... is a decent Pokemon.",
            Team.VALOR,
            PhraseType.IV,
            PhraseRating.AVERAGE
    ),
    VALOR_IV_GOOD(
            "Overall, your ... is a strong Pokemon. You should be proud!",
            Team.VALOR,
            PhraseType.IV,
            PhraseRating.GOOD
    ),
    VALOR_IV_EXCELLENT(
            "Overall, your ... simply amazes me. It can accomplish anything!",
            Team.VALOR,
            PhraseType.IV,
            PhraseRating.EXCELLENT
    ),
    INSTINCT_IV_FAIR(
            "Overall, your ... has room for improvement as far as battling goes.",
            Team.INSTINCT,
            PhraseType.IV,
            PhraseRating.FAIR
    ),
    INSTINCT_IV_AVERAGE(
            "Overall, your ... is pretty decent!",
            Team.INSTINCT,
            PhraseType.IV,
            PhraseRating.AVERAGE
    ),
    INSTINCT_IV_GOOD(
            "Overall, your ... is really strong!",
            Team.INSTINCT,
            PhraseType.IV,
            PhraseRating.GOOD
    ),
    INSTINCT_IV_EXCELLENT(
            "Overall, your ... looks like it can really battle with the best of them!",
            Team.INSTINCT,
            PhraseType.IV,
            PhraseRating.EXCELLENT
    ),
    MYSTIC_IV_FAIR(
            "Overall, your ... is not likely to make much headway in battle.",
            Team.MYSTIC,
            PhraseType.IV,
            PhraseRating.FAIR
    ),
    MYSTIC_IV_AVERAGE(
            "Overall, your ... is above average.",
            Team.MYSTIC,
            PhraseType.IV,
            PhraseRating.AVERAGE
    ),
    MYSTIC_IV_GOOD(
            "Overall, your ... has certainly caught my attention.",
            Team.MYSTIC,
            PhraseType.IV,
            PhraseRating.GOOD
    ),
    MYSTIC_IV_EXCELLENT(
            "Overall, your ... is a wonder! What a breathtaking Pokemon!",
            Team.MYSTIC,
            PhraseType.IV,
            PhraseRating.EXCELLENT
    ),
    VALOR_STATS_FAIR(
            "Its stats don't point to greatness in battle.",
            Team.VALOR,
            PhraseType.STATS,
            PhraseRating.FAIR
    ),
    VALOR_STATS_AVERAGE(
            "Its stats indicate that in battle, it'll get the job done.",
            Team.VALOR,
            PhraseType.STATS,
            PhraseRating.AVERAGE
    ),
    VALOR_STATS_GOOD(
            "It's got excellent stats! How exciting!",
            Team.VALOR,
            PhraseType.STATS,
            PhraseRating.GOOD
    ),
    VALOR_STATS_EXCELLENT(
            "I'm blown away by its stats. WOW!",
            Team.VALOR,
            PhraseType.STATS,
            PhraseRating.EXCELLENT
    ),
    INSTINCT_STATS_FAIR(
            "Its stats are all right, but kinda basic, as far as I can see.",
            Team.INSTINCT,
            PhraseType.STATS,
            PhraseRating.FAIR
    ),
    INSTINCT_STATS_AVERAGE(
            "It's definitely got some good stats. Definitely!",
            Team.INSTINCT,
            PhraseType.STATS,
            PhraseRating.AVERAGE
    ),
    INSTINCT_STATS_GOOD(
            "Its stats are really strong! Impressive.",
            Team.INSTINCT,
            PhraseType.STATS,
            PhraseRating.GOOD
    ),
    INSTINCT_STATS_EXCELLENT(
            "Its stats are the best I've ever seen! No doubt about it!",
            Team.INSTINCT,
            PhraseType.STATS,
            PhraseRating.EXCELLENT
    ),
    MYSTIC_STATS_FAIR(
            "Its stats are not out of the norm, in my opinion.",
            Team.MYSTIC,
            PhraseType.STATS,
            PhraseRating.FAIR
    ),
    MYSTIC_STATS_AVERAGE(
            "Its stats are noticeably trending to the positive.",
            Team.MYSTIC,
            PhraseType.STATS,
            PhraseRating.AVERAGE
    ),
    MYSTIC_STATS_GOOD(
            "I am certainly impressed by its stats, I must say.",
            Team.MYSTIC,
            PhraseType.STATS,
            PhraseRating.GOOD
    ),
    MYSTIC_STATS_EXCELLENT(
            "Its stats exceed my calculations. It's incredible!",
            Team.MYSTIC,
            PhraseType.STATS,
            PhraseRating.EXCELLENT
    );

    private final String text;
    private final Team team;
    private final int type;
    private final int rating;

    public static class PhraseType {
        public static final int IV = 0;
        public static final int STATS = 1;
    }

    public static class PhraseRating {
        public static final int FAIR = 0;
        public static final int AVERAGE = 1;
        public static final int GOOD = 2;
        public static final int EXCELLENT = 3;
    }

    Phrase(String text, Team team, int type, int rating) {
        this.text = text;
        this.team = team;
        this.type = type;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }

    public Team getTeam() {
        return team;
    }
}
