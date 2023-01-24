package com.compmta.scrabble.util;

import com.compmta.scrabble.model.Tile;

public class WordJudge {
    public static int scoreWord(Tile[] word) {
        int score = 0;
        for (int i = 0; i < word.length; i++) {
            Tile curr = word[i];
            score = curr.effect(word, score, i);
        }
        int modifier = word.length;
        for (int i = 0; i < word.length; i++) {
            Tile curr = word[i];
            score = curr.effect(word, score, word.length);
        }
        return score;
    }

    public static boolean verifyWord(String word) {
        return com.compmta.scrabble.model.Dictionary.dictionary.contains(word);
    }
}
