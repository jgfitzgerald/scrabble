package com.compmta.scrabble.model;

//imports
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Turn {

    private String playerId;
    private String word;
    private int[] startCoords;
    private int[] endCoords;
    private int score;

}
