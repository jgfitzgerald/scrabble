package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Tile {

    public final static int COORDS = 2;

    private char letter;
    private int[] coordinates;

    public Tile(int i, int j) {
        this.coordinates = new int[COORDS];
        this.setCoordinates(i, j);
        letter = '!';
    }

    public void setCoordinates(int i, int j) {
        coordinates[0] = i;
        coordinates[1] = j;
    }

    public abstract int effect(Tile[] word, int score, int index);

}
