package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;

public class DoubleWordTile extends Tile {

    public DoubleWordTile(int i, int j) {
        super(i, j);
    }

    @Override
    public int effect(Tile[] word, int score, int index) {
        if (index == word.length) {
            return score * 2;
        } else {
            return score + Letter.map.get(word[index].getLetter()).getBaseScore();
        }
    }

}
