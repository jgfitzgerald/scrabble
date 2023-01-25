/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: BoardController
 */

//package
package com.compmta.scrabble.controllers;

//imports
import com.compmta.scrabble.model.Board;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component

//generates the setters
@Setter
public class BoardController {

    //instance variable
    static Board board;

    /**
     * Constructor
     * Creates a null instance of the board
     */
    public BoardController() {
        board = null;
    } //Constructor

    /**
     * placeWord allows the player to place their tiles to make a word
     * 
     * @param startCoords beginning of the word
     * @param endCoords the ending of the word
     * @param word the word the player has played
     */
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
    } //placeWord(int[] startCoords, int[] endCoords, String word)

    /**
     * placeLetter allows the player to place one of their letters
     * on a tile on the board. It takes the row and column where it should be
     * placed.
     * 
     * @param i row
     * @param j column
     * @param letter letter on tile
     */
    public void placeLetter(int i, int j, char letter) {
        board.getTile(i, j).setLetter(letter);
    } //placeLetter(int i, int j, char letter)

    /**
     * @param coords
     * @param letter 
     */
    public void setBlankLetter(int[] coords, char letter) {
        //TO-DO: this
    } //setBlankLetter(int[] coords, char letter)

    /**
     * @return 
     */
    private boolean validateMove() {
        return true; //TO-DO: this
    } //validateMove()
}
