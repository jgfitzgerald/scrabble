package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.GameStateController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    public final int BOARD_SIZE = 15;
    public final int EFFECT_TILE = 7;
    public final int NUM_BLANK_LETTERS = 2;
    public final char DEFAULT = '\u0000';

    private Square[][] board;
    private int[][] blankLetterCoords;

    public Board() {
        this.board = this.initialize();
    }

    //This is sort of a logistical nightmare honestly im sorry for this
    private Square[][] initialize() {
        Square[][] temp = new Square[BOARD_SIZE][BOARD_SIZE];
        blankLetterCoords = new int[NUM_BLANK_LETTERS][NUM_BLANK_LETTERS];
        //Arrays.fill(blankLetterCoords, -1);

        // TRIPLE WORD
        temp[0][0] = new TripleWordSquare();
        temp[0][BOARD_SIZE - 1] = new TripleWordSquare();
        temp[BOARD_SIZE - 1][0] = new TripleWordSquare();
        temp[BOARD_SIZE - 1][BOARD_SIZE - 1] = new TripleWordSquare();
        temp[EFFECT_TILE][0] = new TripleWordSquare();
        temp[EFFECT_TILE][BOARD_SIZE - 1] = new TripleWordSquare();

        // DOUBLE WORD
        temp[EFFECT_TILE][EFFECT_TILE] = new DoubleWordSquare();
        for (int i = 1; i < 5; i++) {
            temp[i][i] = new DoubleWordSquare();
            temp[i][(BOARD_SIZE - 1) - i] = new DoubleWordSquare();
            temp[(BOARD_SIZE - 1) - i][i] = new DoubleWordSquare();
            temp[(BOARD_SIZE - 1) - i][(BOARD_SIZE - 1) - i] = new DoubleWordSquare();
        }

        //TRIPLE LETTER
        temp[5][1] = new TripleLetterSquare();
        temp[1][5] = new TripleLetterSquare();
        temp[1][9] = new TripleLetterSquare();
        temp[9][1] = new TripleLetterSquare();
        temp[5][5] = new TripleLetterSquare();
        temp[9][9] = new TripleLetterSquare();
        temp[9][5] = new TripleLetterSquare();
        temp[5][9] = new TripleLetterSquare();
        temp[5][13] = new TripleLetterSquare();
        temp[13][5] = new TripleLetterSquare();
        temp[9][13] = new TripleLetterSquare();
        temp[13][9] = new TripleLetterSquare();

        //DOUBLE LETTER
        temp[0][3] = new DoubleLetterSquare();
        temp[3][0] = new DoubleLetterSquare();
        temp[7][3] = new DoubleLetterSquare();
        temp[3][7] = new DoubleLetterSquare();
        temp[2][6] = new DoubleLetterSquare();
        temp[6][2] = new DoubleLetterSquare();
        temp[8][2] = new DoubleLetterSquare();
        temp[2][8] = new DoubleLetterSquare();
        temp[0][11] = new DoubleLetterSquare();
        temp[11][0] = new DoubleLetterSquare();
        temp[11][14] = new DoubleLetterSquare();
        temp[14][11] = new DoubleLetterSquare();
        temp[14][3] = new DoubleLetterSquare();
        temp[3][14] = new DoubleLetterSquare();
        temp[6][6] = new DoubleLetterSquare();
        temp[8][8] = new DoubleLetterSquare();
        temp[6][8] = new DoubleLetterSquare();
        temp[8][6] = new DoubleLetterSquare();
        temp[7][11] = new DoubleLetterSquare();
        temp[11][7] = new DoubleLetterSquare();
        temp[12][6] = new DoubleLetterSquare();
        temp[6][12] = new DoubleLetterSquare();
        temp[8][12] = new DoubleLetterSquare();
        temp[12][8] = new DoubleLetterSquare();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (temp[i][j] == null) {
                    temp[i][j] = new RegularSquare();
                }
            }
        }
        return temp;
    }

    /**
     * @param i The row
     * @param j The column
     * @return The tile at the ith row and jth column
     */
    public Square getSquare(int i, int j) {
        return board[i][j];
    }

    /**
     * Removes the word at the specified coordinates, leaves the letter at index i
     * @param startCoords the starting coordinates
     * @param endCoords the ending coordinates
     * @param i the index of the word not to remove
     */
    public void removeWord(int[] startCoords, int[] endCoords, int i) {
        int curr = 0;
        if (startCoords[0] == endCoords[0]) { // remove horizontally
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                if (this.getSquare(startCoords[0], col).getLetter() != '!' && curr != i) {
                    this.getSquare(startCoords[0], col).setLetter('!');
                }
                curr++;
            }
        } else { // remove vertically
            for (int row = startCoords[0]; row <= endCoords[0]; row++) {
                if (this.getSquare(row, startCoords[1]).getLetter() == '!' && curr != i) {
                    this.getSquare(row, startCoords[1]).setLetter('!');
                }
                curr++;
            }
        }
    }

    /**
     * placeWord allows the player to place their tiles to make a word
     *
     * @param startCoords beginning of the word
     * @param endCoords the ending of the word
     * @param word the word the player has played
     */
    private void placeWord(int[] startCoords, int[] endCoords, String word) {
        int i = 0;
        if (startCoords[0] == endCoords[0]) { // place horizontally
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                if (this.getSquare(startCoords[0], col).getLetter() == DEFAULT) {
                    this.placeLetter(startCoords[0], col, word.charAt(i));
                }
                i++;
            }
        } else { // place vertically
            for (int row = startCoords[0]; row <= endCoords[0]; row++) {
                if (this.getSquare(row, startCoords[1]).getLetter() != DEFAULT) {
                    this.placeLetter(row, startCoords[1], word.charAt(i));
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
        this.getSquare(i, j).setLetter(letter);
    }

    public void setBlankLetter(int[] coords, char letter) {
        int[][] blanks = this.getBlankLetterCoords();
        if (blanks[0][0] == -1) {
            blanks[0][0] = coords[0];
            blanks[0][1] = coords[1];
        } else {
            blanks[1][0] = coords[0];
            blanks[1][1] = coords[1];
        }
    } //setBlankLetter(int[] coords, char letter)

    public String toString() {
        String str ="";
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                str += this.getSquare(i,j).getLetter() + " ";
            }
            str+="\n";
        }
        return str;
    }

    public void applyTurn(Turn turn) {
        this.placeWord(turn.getStartCoords(),turn.getEndCoords(), turn.getWord());
    }

    public boolean validateMove(int[] start, int[] end, String word) {
        int i = 0;

        boolean adjacent = false;
        boolean intersect = false;

        if (start[0] == end[0]) {
            for (int col = start[1]; col <= end[1]; col++) {
                if (this.getSquare(start[0], col).getLetter() != DEFAULT && this.getSquare(start[0], col).getLetter() != word.charAt(i)) {
                    return false;
                }

                if (this.getSquare(start[0], col).getLetter() == word.charAt(i)) {
                    intersect = true;
                }

                adjacent = this.hasSurroundingTiles(start[0],col);

                i++;
            }
        }
        if (start[1] == end[1]) {
            for (int row = start[0]; row <= end[0]; row++) {
                if (this.getSquare(row,start[1]).getLetter() != DEFAULT && this.getSquare(row, start[1]).getLetter() != word.charAt(i)) {
                    return false;
                }

                if (this.getSquare(row, start[1]).getLetter() == word.charAt(i)) {
                    intersect = true;
                }

                adjacent = this.hasSurroundingTiles(row,start[1]);

                i++;
            }
        }
        return adjacent || intersect;
    }

    public boolean validateInitialMove(int[] start, int[] end, String word) {
        int i = 0;
        if (word.length() < 2) {
            return false;
        }
        if (start[0] == end[0]) {
            for (int col = start[1]; col <= end[1]; col++) {
                if (col == EFFECT_TILE && start[0] == EFFECT_TILE) {
                    return true;
                }
            }
        }
        if (start[1] == end[1]) {
            for (int row = start[0]; row <= end[0]; row++) {
                if (row == EFFECT_TILE && start[1] == EFFECT_TILE) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasSurroundingTiles(int i, int j) {
        if (i-1 >= 0) {
            Square temp = this.getSquare(i-1,j);
            if (temp.getLetter() != DEFAULT) {
                return true;
            }
        }
        if (i+1 < BOARD_SIZE) {
            Square temp = this.getSquare(i+1,j);
            if (temp.getLetter() != DEFAULT) {
                return true;
            }
        }
        if (j+1 < BOARD_SIZE) {
            Square temp = this.getSquare(i,j+1);
            if (temp.getLetter() != DEFAULT) {
                return true;
            }
        }
        if (j-1 >= 0) {
            Square temp = this.getSquare(i,j-1);
            if (temp.getLetter() != DEFAULT) {
                return true;
            }
        }
        return false;
    }

}
