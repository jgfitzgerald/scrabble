package com.compmta.scrabble.model;

//imports

import com.compmta.scrabble.controllers.DTO.TurnInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Turn {

    private String playerId;
    private String word;
    private int row;
    private int column;

    private boolean isHorizontal;
    private int score;

    public Turn(TurnInfo turn) {
        playerId = turn.id();
        word = turn.word();
        row = turn.row();
        column = turn.column();
        isHorizontal = turn.isHorizontal();
    }

}
