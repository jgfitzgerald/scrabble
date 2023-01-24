package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.Board;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class BoardController {

    static Board board;

    public BoardController() {
        board = null;
    }

    public void placeWord(int[] startCoords, int[] endCoords, String word) {
        int i = 0;
        if (startCoords[0] == endCoords[0]) { // place horizontally
            for (int col = startCoords[1]; col < endCoords[1]; col++) {
                if (board.getTile(startCoords[0], col).getLetter() != '!') {
                    placeLetter(startCoords[0], col, word.charAt(i));
                }
                i++;
            }
        } else { // place vertically
            for (int row = startCoords[0]; row < endCoords[0]; row++) {
                if (board.getTile(row, startCoords[1]).getLetter() != '!') {
                    placeLetter(row, startCoords[1], word.charAt(i));
                }
                i++;
            }
        }
    }

    public void placeLetter(int i, int j, char letter) {
        board.getTile(i, j).setLetter(letter);
    }

    public void setBlankLetter(int[] coords, char letter) {
        //TO-DO: this
    }

    private boolean validateMove() {
        return true; //TO-DO: this
    }
}
