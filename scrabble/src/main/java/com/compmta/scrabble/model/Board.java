package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.WordInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Board {

    public final int BOARD_SIZE = 15;
    public final int EFFECT_TILE = 7;
    public static final int NUM_BLANK_LETTERS = 2;
    public static final char DEFAULT = '\u0000';

    private Square[][] board;
    private Map<Character, Coordinates> blanks;

    @Setter
    @Getter
    @AllArgsConstructor
    static class Coordinates {
        private int i;
        private int j;

        public boolean equals(int i, int j) {
            return this.i == i && this.j == j;
        }
    }
    public Board() {
        this.board = this.initialize();
    }

    /**
     * Initializes the default board.
     * @return The board array.
     */
    private Square[][] initialize() {
        Square[][] temp = new Square[BOARD_SIZE][BOARD_SIZE];
        blanks = new HashMap<>();
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
     * @param turn the turn to cancel
     * @return A list of characters removed
     */
    public void removeWord(TurnInfo turn) {

        int row = turn.row();
        int col = turn.column();
        char[] word = turn.word();
        int index = 0;

        if (!turn.isHorizontal()) {
            for (int i = row; i < row + turn.word().length; i++) {
                if (this.getSquare(i,col).getLetter() == word[index]) {
                    this.getSquare(i,col).setLetter(DEFAULT);
                }
                index++;
            }
        } else {
            for (int j = col; j < col + word.length; j++) {
                if (this.getSquare(row,j).getLetter() == word[index]) {
                    this.getSquare(row,j).setLetter(DEFAULT);
                }
                index++;
            }
        }
    }

    /**
     *
     * @param word The word to be placed
     * @param row The row of the first index of the word
     * @param col The column of the first index of the word
     * @param isHorizontal True if the word is placed horizontally, false if vertical
     */
    private void placeWord(char[] word, int row, int col, boolean isHorizontal) {
        int index = 0;
        if (!isHorizontal) {
            for (int i = row; i < row + word.length; i++) {
                if (this.getSquare(i,col).getLetter() == DEFAULT) {
                    this.placeLetter(i,col,word[index]);
                }
                index++;
            }
        } else {
            for (int j = col; j < col + word.length; j++) {
                if (this.getSquare(row,j).getLetter() == DEFAULT) {
                    this.placeLetter(row,j,word[index]);
                }
                index++;
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
    public void placeLetter(int i, int j, char letter) {
        this.getSquare(i, j).setLetter(letter);
    }

    public void setBlankLetter(int i, int j, char letter) {
        if (blanks.size() < NUM_BLANK_LETTERS) {
            blanks.put(letter, new Coordinates(i,j));
        } else {
            throw new IllegalStateException("Blank letters have already been used.");
        }
    } //setBlankLetter(int[] coords, char letter)

    /**
     * Applies the turn's effects.
     * @param turn The turn
     */
    public void applyTurn(Turn turn) {
        this.placeWord(turn.getWord(), turn.getRow(), turn.getColumn(), turn.isHorizontal());
    }

    /**
     * Validates the first scrabble move (one coordinate must be [7,7])
     * @param turnInfo The initial move
     * @return True if the move is valid, false if illegal
     */
    public boolean validateInitialMove(TurnInfo turnInfo) {

        char[] word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        if (word.length < 2 || Arrays.asList(word).contains(DEFAULT)) {
            return false;
        }
        if (!isHorizontal) {
            for (int i = row; i < row + word.length; i++) {
                if (i == EFFECT_TILE && col == EFFECT_TILE) {
                    return true;
                }
            }
        } else {
            for (int j = col; j < col + word.length; j++) {
                if (j == EFFECT_TILE && row == EFFECT_TILE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Validates a move.
     * @param turnInfo The requested move.
     * @return True if the word is legal, false otherwise.
     */
    public boolean validateMove(TurnInfo turnInfo) {
        char[] word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        int index = 0;

        if (!isHorizontal) {
            if (row + word.length > BOARD_SIZE) {
                return false;
            }
            for (int i = row; i < row + word.length; i++) {
                if (this.getSquare(i, col).getLetter() != DEFAULT && word[index] != DEFAULT) {
                    return false;
                }
                index++;
            }
        } else {
            if (col + word.length > BOARD_SIZE) {
                return false;
            }
            for (int j = col; j < col + word.length; j++) {
                if (this.getSquare(row, j).getLetter() != DEFAULT && word[index] != DEFAULT) {
                    return false;
                }
                index++;
            }
        }
        return this.hasSurroundingTiles(turnInfo);
    }

    /**
     * Checks if a requested move builds off another previous move on the current board.
     * @param turnInfo The requested move.
     * @return True if the move has surrounding tiles, false otherwise.
     */
    private boolean hasSurroundingTiles(TurnInfo turnInfo) {

        char[] word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        if (!isHorizontal) {
            for (int i = row; i < row + word.length; i++) {
                char c = this.getSquare(i,col).getLetter();

                if (c != DEFAULT) {
                    return true;
                }
                if (i == row) { // check above
                    if (i-1 >= 0 && this.getSquare(i-1,col).getLetter() != DEFAULT) return true;
                }
                if (i == row + word.length - 1) { // check below
                    if (i+1 < BOARD_SIZE && this.getSquare(i+1,col).getLetter() != DEFAULT) return true;
                }

                // check left and right
                if (col-1 >= 0 && this.getSquare(i,col-1).getLetter() != DEFAULT) return true;
                if (col+1 < BOARD_SIZE && this.getSquare(i,col+1).getLetter() != DEFAULT) return true;
            }
        } else {
            for (int j = col; j < col + word.length; j++) {
                char c = this.getSquare(row,j).getLetter();

                if (c != DEFAULT) {
                    return true;
                }

                if (j == col) { // check left
                    if (j-1 >= 0 && this.getSquare(row,j-1).getLetter() != DEFAULT) return true;
                }
                if (j == col + word.length - 1) { // check right
                    if (j+1 < BOARD_SIZE && this.getSquare(row,j+1).getLetter() != DEFAULT) return true;
                }
                // check above and below
                if (row-1 >= 0 && this.getSquare(row-1,j).getLetter() != DEFAULT) return true;
                if (row+1 < BOARD_SIZE && this.getSquare(row+1,j).getLetter() != DEFAULT) return true;
            }
        }
        System.out.println("has surrounding tiles");
        return false;
    }

    /**
     * Scores an entire move.
     * @param move The requested move.
     * @return The total score of all words formed by the move.
     */
    public int scoreMove(TurnInfo move) {

        int score = 0;

        ArrayList<WordInfo> words = this.detectWords(move);

        for (WordInfo w : words) {
            score += this.scoreWord(w.word(), w.row(), w.col(), w.isHorizontal());
        }

        return score;
    }

    /**
     * Scores a singular word on the board.
     * @param word The word
     * @param row The staring row
     * @param col The starting column
     * @param isHorizontal True if horizontal
     * @return The score of the word.
     */
    public int scoreWord(String word, int row, int col, boolean isHorizontal) {
        int score = 0;
        int index = 0;
        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                if (!blanks.containsKey(word.charAt(index))) {
                    score += this.getSquare(i,col).effect(word,score,index);
                } else if (!blanks.get(word.charAt(index)).equals(i,col)) {
                    score += this.getSquare(i,col).effect(word,score,index);
                }
                index++;
            }
            for (int i = row; i < row + word.length(); i++) {
                score += this.getSquare(i,col).effect(word,score,word.length());
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                if (blanks.get(word.charAt(index)) == null) {
                    score += (this.getSquare(row,j).effect(word,score,index));
                } else if (!blanks.get(word.charAt(index)).equals(row,j)) {
                    score += this.getSquare(row,j).effect(word,score,index);
                }
                index++;
            }
            for (int j = col; j < col + word.length(); j++) {
                score += this.getSquare(row,j).effect(word,score,word.length());
            }
        }
        return score;
    }

    /**
     * Detects words created by the placement of a new move.
     * @param turnInfo The word to be placed.
     * @return A list of word information specifying the words to be scored.
     */
    public ArrayList<WordInfo> detectWords(TurnInfo turnInfo) {
        ArrayList<WordInfo> words = new ArrayList<>();
        int row = turnInfo.row();
        int col = turnInfo.column();
        char[] word = turnInfo.word().clone();
        boolean isHorizontal = turnInfo.isHorizontal();

        int index = 0;
        for (char c : word) {
            if (c == DEFAULT) {
                word[index] = this.getSquare(row,col).getLetter();
            }
            if (isHorizontal) {
                col++;
            } else {
                row++;
            }
            index++;
        }

        row = turnInfo.row();
        col = turnInfo.column();
        index = 0;
        String totalWord = new String(word);
        if (!isHorizontal) {
            // check above & below
            while (row+1 < BOARD_SIZE && this.getSquare(row+1,col).getLetter() != DEFAULT) {
                totalWord = totalWord +  this.getSquare(row+1,col).getLetter();
                row++;
            }
            row = turnInfo.row();
            while (row-1 > 0 && this.getSquare(row-1,col).getLetter() != DEFAULT) {
                totalWord = this.getSquare(row-1,col).getLetter() + totalWord;
                row--;
            }
            if (totalWord.length() > 1) {
                words.add(new WordInfo(totalWord, row, col, false));
            }
            row = turnInfo.row();

            // check left to right
            String extraWord = "";
            for (int i = row; i < row + turnInfo.word().length; i++) {
                if (turnInfo.word()[index] != DEFAULT) {
                    extraWord = String.valueOf(turnInfo.word()[index]);
                    while (col + 1 < BOARD_SIZE && this.getSquare(i, col + 1).getLetter() != DEFAULT) {
                        extraWord = extraWord + this.getSquare(i, col + 1).getLetter();
                        col++;
                    }
                    col = turnInfo.column();
                    while (col - 1 < BOARD_SIZE && this.getSquare(i, col - 1).getLetter() != DEFAULT) {
                        extraWord = this.getSquare(i, col - 1).getLetter() + extraWord;
                        col--;
                    }
                    if (extraWord.length() > 1) {
                        words.add(new WordInfo(extraWord, i, col, true));
                    }
                }
                index++;
            }
        } else {
            // check above & below
            while (col+1 < BOARD_SIZE && this.getSquare(row,col+1).getLetter() != DEFAULT) {
                totalWord = totalWord +  this.getSquare(row,col+1).getLetter();
                col++;
            }
            col = turnInfo.column();
            while (col-1 > 0 && this.getSquare(row,col-1).getLetter() != DEFAULT) {
                totalWord = this.getSquare(row,col-1).getLetter() + totalWord;
                col--;
            }
            if (totalWord.length() > 1) {
                words.add(new WordInfo(totalWord, row, col, true));
            }
            col = turnInfo.column();

            // check left to right
            String extraWord = "";
            for (int j = col; j < col + turnInfo.word().length; j++) {
                if (turnInfo.word()[index] != DEFAULT) {
                    extraWord = String.valueOf(turnInfo.word()[index]);
                    while (row + 1 < BOARD_SIZE && this.getSquare(row + 1, j).getLetter() != DEFAULT) {
                        extraWord = extraWord + this.getSquare(row + 1, j).getLetter();
                        row++;
                    }
                    row = turnInfo.row();
                    while (row - 1 < BOARD_SIZE && this.getSquare(row - 1, j).getLetter() != DEFAULT) {
                        extraWord = this.getSquare(row - 1, j).getLetter() + extraWord;
                        row--;
                    }
                    if (extraWord.length() > 1) {
                        words.add(new WordInfo(extraWord, row, j, false));
                    }
                }
                index++;
            }
        }
        return words;
    }
}
