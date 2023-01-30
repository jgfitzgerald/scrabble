package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Board {

    public final int BOARD_SIZE = 15;
    public final int EFFECT_TILE = 7;
    public final int NUM_BLANK_LETTERS = 2;

    private Tile[][] board;
    private int[][] blankLetterCoords;

    public Board() {
        this.board = this.initialize();
    }

    //This is sort of a logistical nightmare honestly im sorry for this
    private Tile[][] initialize() {
        Tile[][] temp = new Tile[BOARD_SIZE][BOARD_SIZE];
        blankLetterCoords = new int[NUM_BLANK_LETTERS][NUM_BLANK_LETTERS];
        //Arrays.fill(blankLetterCoords, -1);

        // TRIPLE WORD
        temp[0][0] = new TripleWordTile();
        temp[0][BOARD_SIZE - 1] = new TripleWordTile();
        temp[BOARD_SIZE - 1][0] = new TripleWordTile();
        temp[BOARD_SIZE - 1][BOARD_SIZE - 1] = new TripleWordTile();
        temp[EFFECT_TILE][0] = new TripleWordTile();
        temp[EFFECT_TILE][BOARD_SIZE - 1] = new TripleWordTile();

        // DOUBLE WORD
        temp[EFFECT_TILE][EFFECT_TILE] = new DoubleWordTile();
        for (int i = 1; i < 5; i++) {
            temp[i][i] = new DoubleWordTile();
            temp[i][(BOARD_SIZE - 1) - i] = new DoubleWordTile();
            temp[(BOARD_SIZE - 1) - i][i] = new DoubleWordTile();
            temp[(BOARD_SIZE - 1) - i][(BOARD_SIZE - 1) - i] = new DoubleWordTile();
        }

        //TRIPLE LETTER
        temp[5][1] = new TripleLetterTile();
        temp[1][5] = new TripleLetterTile();
        temp[1][9] = new TripleLetterTile();
        temp[9][1] = new TripleLetterTile();
        temp[5][5] = new TripleLetterTile();
        temp[9][9] = new TripleLetterTile();
        temp[9][5] = new TripleLetterTile();
        temp[5][9] = new TripleLetterTile();
        temp[5][13] = new TripleLetterTile();
        temp[13][5] = new TripleLetterTile();
        temp[9][13] = new TripleLetterTile();
        temp[13][9] = new TripleLetterTile();

        //DOUBLE LETTER
        temp[0][3] = new DoubleLetterTile();
        temp[3][0] = new DoubleLetterTile();
        temp[7][3] = new DoubleLetterTile();
        temp[3][7] = new DoubleLetterTile();
        temp[2][6] = new DoubleLetterTile();
        temp[6][2] = new DoubleLetterTile();
        temp[8][2] = new DoubleLetterTile();
        temp[2][8] = new DoubleLetterTile();
        temp[0][11] = new DoubleLetterTile();
        temp[11][0] = new DoubleLetterTile();
        temp[11][14] = new DoubleLetterTile();
        temp[14][11] = new DoubleLetterTile();
        temp[14][3] = new DoubleLetterTile();
        temp[3][14] = new DoubleLetterTile();
        temp[6][6] = new DoubleLetterTile();
        temp[8][8] = new DoubleLetterTile();
        temp[6][8] = new DoubleLetterTile();
        temp[8][6] = new DoubleLetterTile();
        temp[7][11] = new DoubleLetterTile();
        temp[11][7] = new DoubleLetterTile();
        temp[12][6] = new DoubleLetterTile();
        temp[6][12] = new DoubleLetterTile();
        temp[8][12] = new DoubleLetterTile();
        temp[12][8] = new DoubleLetterTile();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (temp[i][j] == null) {
                    temp[i][j] = new RegularTile();
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
    public Tile getTile(int i, int j) {
        return board[i][j];
    }

}
