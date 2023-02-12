package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.WordInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
     *
     * @param word The word to be removed
     * @param row The starting row of the word
     * @param col The starting column of the word
     * @param isHorizontal True if horizontal, false if vertical
     * @param path An ArrayList containing the letters on the path not placed in the current turn
     * @return A list of characters removed
     */
    public ArrayList<Character> removeWord(String word, int row, int col, boolean isHorizontal, ArrayList<Character> path) {
        ArrayList<Character> charsRemoved = new ArrayList<Character>();

        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                char c = this.getSquare(i,col).getLetter();
                if (c != DEFAULT && !path.contains(c)) {
                    this.getSquare(i,col).setLetter(DEFAULT);
                    charsRemoved.add(c);
                } else if (path.contains(c)) {
                    path.remove(path.indexOf(c));
                }
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                char c = this.getSquare(row,j).getLetter();
                if (c != DEFAULT && !path.contains(c)) {
                    this.getSquare(row,j).setLetter(DEFAULT);
                    charsRemoved.add(c);
                }  else if (path.contains(c)) {
                    path.remove(path.indexOf(c));
                }
            }
        }
        return charsRemoved;
    }

    /**
     *
     * @param word The word to be placed
     * @param row The row of the first index of the word
     * @param col The column of the first index of the word
     * @param isHorizontal True if the word is placed horizontally, false if vertical
     */
    private void placeWord(String word, int row, int col, boolean isHorizontal) {
        int index = 0;
        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                if (this.getSquare(i,col).getLetter() == DEFAULT) {
                    this.placeLetter(i,col,word.charAt(index));
                }
                index++;
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                if (this.getSquare(row,j).getLetter() == DEFAULT) {
                    this.placeLetter(row,j,word.charAt(index));
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

    public void applyTurn(Turn turn) {
        this.placeWord(turn.getWord(), turn.getRow(), turn.getColumn(), turn.isHorizontal());
    }

    public boolean validateInitialMove(TurnInfo turnInfo) {

        String word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        if (word.length() < 2) {
            return false;
        }
        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                if (i == EFFECT_TILE && col == EFFECT_TILE) {
                    return true;
                }
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                if (j == EFFECT_TILE && row == EFFECT_TILE) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateMove(TurnInfo turnInfo) {
        String word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        int index = 0;

        if (!isHorizontal) {
            if (row + word.length() > BOARD_SIZE) {
                return false;
            }
            for (int i = row; i < row + word.length(); i++) {
                if (this.getSquare(i, col).getLetter() != DEFAULT && this.getSquare(i, col).getLetter() != word.charAt(index)) {
                    return false;
                }
                index++;
            }
        } else {
            if (col + word.length() > BOARD_SIZE) {
                return false;
            }
            for (int j = col; j < col + word.length(); j++) {
                if (this.getSquare(row, j).getLetter() != DEFAULT && this.getSquare(row, j).getLetter() != word.charAt(index)) {
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

        String word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                char c = this.getSquare(i,col).getLetter();

                if (c != DEFAULT) {
                    return true;
                }
                if (i == row && i > 1) { // check above
                    if (i-1 >= 0 && this.getSquare(i-1,col).getLetter() != DEFAULT) return true;
                }
                if (i == word.length() - 1) { // check below
                    if (i+1 > BOARD_SIZE && this.getSquare(i+1,col).getLetter() != DEFAULT) return true;
                }

                // check left and right
                if (col-1 >= 0 && this.getSquare(i,col-1).getLetter() != DEFAULT) return true;
                if (col+1 > BOARD_SIZE && this.getSquare(i,col+1).getLetter() != DEFAULT) return true;
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                char c = this.getSquare(row,j).getLetter();

                if (c != DEFAULT) {
                    return true;
                }

                if (j == col) { // check left
                    if (j-1 >= 0 && this.getSquare(row,j-1).getLetter() != DEFAULT) return true;
                }
                if (j == word.length() - 1) { // check right
                    if (j+1 >= BOARD_SIZE && this.getSquare(row,j+1).getLetter() != DEFAULT) return true;
                }
                // check above and below
                if (row-1 >= 0 && this.getSquare(row-1,j).getLetter() != DEFAULT) return true;
                if (row+1 >= 0 && this.getSquare(row+1,j).getLetter() != DEFAULT) return true;
            }
        }
        return false;
    }

    public ArrayList<Character> getLettersOnPath(TurnInfo turnInfo) {

        String word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        ArrayList<Character> path = new ArrayList<Character>();

        if (!isHorizontal) {
            for (int i = row; i < row + word.length(); i++) {
                if (this.getSquare(i,col).getLetter() != DEFAULT) {
                    path.add(this.getSquare(i,col).getLetter());
                }
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                if (this.getSquare(row,j).getLetter() != DEFAULT) {
                    path.add(this.getSquare(row,j).getLetter());
                }
            }
        }
        return path;
    }
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
                if (!(blankLetterCoords[0][0] == row && blankLetterCoords[0][1] == col ||
                        blankLetterCoords[1][0] == row && blankLetterCoords[1][1] == col)) {
                    score += this.getSquare(i,col).effect(word,score,index);
                }
                index++;
            }
            for (int i = row; i < row + word.length(); i++) {
                score += this.getSquare(i,col).effect(word,score,word.length());
            }
        } else {
            for (int j = col; j < col + word.length(); j++) {
                if (!(blankLetterCoords[0][0] == row && blankLetterCoords[0][1] == col ||
                        blankLetterCoords[1][0] == row && blankLetterCoords[1][1] == col)) {
                    score += (this.getSquare(row,j).effect(word,score,index));
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
     * @return A list of turn information specifying the words to be scored.
     */
    public ArrayList<WordInfo> detectWords(TurnInfo turnInfo) {

        String word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        ArrayList<WordInfo> words = new ArrayList<>();

        if (!isHorizontal) {
            // check below
            row = turnInfo.row() + turnInfo.word().length() - 1;
            while (row+1 < BOARD_SIZE && this.getSquare(row+1,col).getLetter() != DEFAULT) {
                word = word + this.getSquare(row+1,col).getLetter();
                row++;
            }
            // check above
            row = turnInfo.row();
            while (row-1 > 0 && this.getSquare(row-1,col).getLetter() != DEFAULT) {
                word = this.getSquare(row-1,col).getLetter() + word;
                row--;
            }
            WordInfo totalWord = new WordInfo(word,row,col,false);
            words.add(totalWord);

            // check left to right
            String extras = "";
            int index = 0;
            for (int i = totalWord.row(); i < totalWord.row() + totalWord.word().length(); i++) {
                if (this.getSquare(i,col).getLetter() == DEFAULT) {
                    extras = String.valueOf(totalWord.word().charAt(index));
                    col = totalWord.col();
                    while (col-1 > 0 && this.getSquare(i,col-1).getLetter() != DEFAULT) {
                        extras = this.getSquare(i,col-1).getLetter() + extras;
                        col--;
                    }
                    col = totalWord.col() + totalWord.word().length() - 1;
                    while (col+1 < BOARD_SIZE && this.getSquare(i,col+1).getLetter() != DEFAULT) {
                        extras = extras + this.getSquare(i,col-1).getLetter();
                        col++;
                    }
                    if (extras.length() > 1) {
                        words.add(new WordInfo(extras, i,col,true));
                    }
                }
                index++;
            }
        } else {
            // check right
            col = turnInfo.column() + turnInfo.word().length() - 1;
            while (col+1 < BOARD_SIZE && this.getSquare(row,col+1).getLetter() != DEFAULT) {
                word = word + this.getSquare(row,col+1).getLetter();
                col++;
            }
            // check left
            col = turnInfo.column();
            while (col-1 > 0 && this.getSquare(row,col-1).getLetter() != DEFAULT) {
                word = this.getSquare(row,col).getLetter() + word;
                col--;
            }
            WordInfo totalWord = new WordInfo(word,row,col,true);
            words.add(totalWord);

            // check above to below
            String extras = "";
            int index = 0;
            for (int j = totalWord.col(); j < totalWord.col() + totalWord.word().length(); j++) {
                if (this.getSquare(row,j).getLetter() == DEFAULT) {
                    extras = String.valueOf(totalWord.word().charAt(index));
                    row = totalWord.row();
                    while (row-1 > 0 && this.getSquare(row-1,j).getLetter() != DEFAULT) {
                        extras = this.getSquare(row-1,j).getLetter() + extras;
                        System.out.println(extras);
                        row--;
                    }
                    row = turnInfo.row() + totalWord.word().length() - 1;
                    while (row+1 < BOARD_SIZE && this.getSquare(row+1,j).getLetter() != DEFAULT) {
                        extras = extras + this.getSquare(row+1,j).getLetter();
                        row++;
                    }
                    if (extras.length() > 1) {
                        words.add(new WordInfo(extras, row,j,false));
                    }
                }
                index++;
            }
        }
        return words;
    }
}
