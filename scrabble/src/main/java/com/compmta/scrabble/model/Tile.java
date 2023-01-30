package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Tile {

    //instance variables
    private char letter;

    /**
     * effect returns the score of the word after
     * the necessary effect's have been applied.
     * 
     * @param word the word the player has played
     * @param score the score of the word
     * @param index
     * @return the score after the effect has been applied
     */
    public abstract int effect(String word, int score, int index);

}
