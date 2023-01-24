package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;

public class RegularTile extends Tile {

    private char letter;
    private int[] coordinates;

    public RegularTile(int i, int j) {
        super(i, j);
    }

    @Override
    public int effect(Tile[] word, int score, int index) {
        return score + Letter.map.get(word[index].getLetter()).getBaseScore();
    }

}
