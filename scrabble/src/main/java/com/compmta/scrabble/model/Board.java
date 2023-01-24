package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    public final int BOARD_SIZE = 15;
    public final int EFFECT_TILE = 7;

    private Tile[][] board;

    public Board() {
        this.board = this.initialize();
    }

    //This is sort of a logistical nightmare honestly im sorry for this
    private Tile[][] initialize() {
        Tile[][] temp = new Tile[BOARD_SIZE][BOARD_SIZE];

        // TRIPLE WORD
        temp[0][0] = new TripleWordTile(0, 0);
        temp[0][BOARD_SIZE - 1] = new TripleWordTile(0, BOARD_SIZE - 1);
        temp[BOARD_SIZE - 1][0] = new TripleWordTile(BOARD_SIZE - 1, 0);
        temp[BOARD_SIZE - 1][BOARD_SIZE - 1] = new TripleWordTile(BOARD_SIZE - 1, BOARD_SIZE - 1);
        temp[(BOARD_SIZE - 1) / 2][0] = new TripleWordTile(0, 0);
        temp[(BOARD_SIZE - 1) / 2][BOARD_SIZE - 1] = new TripleWordTile(BOARD_SIZE, BOARD_SIZE - 1);

        // DOUBLE WORD
        temp[(BOARD_SIZE - 1) / 2][(BOARD_SIZE - 1) / 2] = new DoubleWordTile((BOARD_SIZE - 1) / 2, (BOARD_SIZE - 1) / 2);
        for (int i = 1; i < 5; i++) {
            temp[i][i] = new DoubleWordTile(i, i);
            temp[i][(BOARD_SIZE - 1) - i] = new DoubleWordTile(i, (BOARD_SIZE - 1) - i);
            temp[(BOARD_SIZE - 1) - i][i] = new DoubleWordTile((BOARD_SIZE - 1) - i, i);
            temp[(BOARD_SIZE - 1) - i][(BOARD_SIZE - 1) - i] = new DoubleWordTile((BOARD_SIZE - 1) - i, (BOARD_SIZE - 1) - i);
        }

        //TRIPLE LETTER
        temp[5][1] = new TripleLetterTile(5, 1);
        temp[1][5] = new TripleLetterTile(1, 5);
        temp[1][9] = new TripleLetterTile(1, 9);
        temp[9][1] = new TripleLetterTile(9, 1);
        temp[5][5] = new TripleLetterTile(5, 5);
        temp[9][9] = new TripleLetterTile(9, 9);
        temp[9][5] = new TripleLetterTile(9, 5);
        temp[5][9] = new TripleLetterTile(5, 9);
        temp[5][13] = new TripleLetterTile(5, 13);
        temp[13][5] = new TripleLetterTile(13, 5);
        temp[9][13] = new TripleLetterTile(9, 13);
        temp[13][9] = new TripleLetterTile(13, 9);

        //DOUBLE LETTER
        temp[0][3] = new DoubleLetterTile(0, 3);
        temp[3][0] = new DoubleLetterTile(3, 0);
        temp[7][3] = new DoubleLetterTile(7, 3);
        temp[3][7] = new DoubleLetterTile(3, 7);
        temp[2][6] = new DoubleLetterTile(2, 6);
        temp[6][2] = new DoubleLetterTile(6, 2);
        temp[8][2] = new DoubleLetterTile(8, 2);
        temp[2][8] = new DoubleLetterTile(2, 8);
        temp[0][11] = new DoubleLetterTile(0, 11);
        temp[11][0] = new DoubleLetterTile(11, 0);
        temp[11][14] = new DoubleLetterTile(11, 14);
        temp[14][11] = new DoubleLetterTile(14, 11);
        temp[14][3] = new DoubleLetterTile(14, 2);
        temp[3][14] = new DoubleLetterTile(3, 14);
        temp[6][6] = new DoubleLetterTile(6, 6);
        temp[8][8] = new DoubleLetterTile(8, 8);
        temp[6][8] = new DoubleLetterTile(6, 8);
        temp[8][6] = new DoubleLetterTile(8, 6);
        temp[7][11] = new DoubleLetterTile(7, 11);
        temp[11][7] = new DoubleLetterTile(11, 7);
        temp[12][6] = new DoubleLetterTile(12, 6);
        temp[6][12] = new DoubleLetterTile(6, 12);
        temp[8][12] = new DoubleLetterTile(8, 12);
        temp[12][8] = new DoubleLetterTile(12, 8);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (temp[i][j] == null) {
                    temp[i][j] = new RegularTile(i, j);
                }
            }
        }
        return temp;
    }

    public Tile getTile(int i, int j) {
        return board[i][j];
    }

}
