package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Turn {

    private String playerId;
    private String word;
    private int[] startCoords;
    private int[] endCoords;
    private int score;

    public Turn(String playerId) {
        this.playerId = playerId;
        this.word = null;
        this.startCoords = new int[Tile.COORDS];
        this.endCoords = new int[Tile.COORDS];
        this.score = 0;
    }
}
