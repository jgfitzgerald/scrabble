package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.Board;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Setter
public class BoardController {

    static Board board;


    public BoardController() {
        board = null;
    }

    /**
     * placeWord allows the player to place their tiles to make a word
     * 
     * @param startCoords beginning of the word
     * @param endCoords the ending of the word
     * @param word the word the player has played
     */
    public static void placeWord(int[] startCoords, int[] endCoords, String word) {
        int i = 0;
        if (startCoords[0] == endCoords[0]) { // place horizontally
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                if (board.getTile(startCoords[0], col).getLetter() == '!') {
                    placeLetter(startCoords[0], col, word.charAt(i));
                }
                System.out.println(i);
            }
        } else { // place vertically
            for (int row = startCoords[0]; row <= endCoords[0]; row++) {
                if (board.getTile(row, startCoords[1]).getLetter() == '!') {
                    placeLetter(row, startCoords[1], word.charAt(i));
                }
                System.out.println(i);
            }
        }
    } //placeWord(int[] startCoords, int[] endCoords, String word)

    public static void removeWord(int[] startCoords, int[] endCoords, int i) {
        int curr = 0;
        if (startCoords[0] == endCoords[0]) { // remove horizontally
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                if (board.getTile(startCoords[0], col).getLetter() != '!' && curr != i) {
                    board.getTile(startCoords[0], col).setLetter('!');
                }
                curr++;
            }
        } else { // remove vertically
            for (int row = startCoords[0]; row <= endCoords[0]; row++) {
                if (board.getTile(row, startCoords[1]).getLetter() == '!' && curr != i) {
                    board.getTile(row, startCoords[1]).setLetter('!');
                }
                curr++;
            }
        }
    }

    /**
     * placeLetter allows the player to place one of their letters
     * on a tile on the board. It takes the row and column where it should be
     * placed.
     * 
     * @param i row
     * @param j column
     * @param letter letter on tile
     */
    public static void placeLetter(int i, int j, char letter) {
        board.getTile(i, j).setLetter(letter);
    }

    public void setBlankLetter(int[] coords, char letter) {
        int[][] blanks = BoardController.board.getBlankLetterCoords();
        if (blanks[0][0] == -1) {
            blanks[0][0] = coords[0];
            blanks[0][1] = coords[1];
        } else {
            blanks[1][0] = coords[0];
            blanks[1][1] = coords[1];
        }
    } //setBlankLetter(int[] coords, char letter)
}
