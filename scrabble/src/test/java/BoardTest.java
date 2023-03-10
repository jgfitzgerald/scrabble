package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.WordInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


public class BoardTest {
    private Board board = new Board();
    char DEFAULT = board.DEFAULT;
    private Square[][] boardArr = board.getBoard();
    @Test
    void testGetSquare(){
        int row = 1;
        int col = 2;
        assertEquals(boardArr[row][col], board.getSquare(row, col));
    }

    @Test
    void testRemoveWord() {
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();

        // Create a new TurnInfo object to remove the word
        TurnInfo turn = new TurnInfo("player1", testChar, 7,7, true, blankIndexes);

        // Remove the word from the board
        board.removeWord(turn);

        // Assert that the letters have been removed
        assertEquals(DEFAULT, board.getSquare(7, 7).getLetter());
        assertEquals(DEFAULT, board.getSquare(7, 8).getLetter());
        assertEquals(DEFAULT, board.getSquare(7, 9).getLetter());
        assertEquals(DEFAULT, board.getSquare(7, 10).getLetter());
    }

    @Test
    public void testPlaceLetter() {

        // place the test square on the board
        board.placeLetter(7, 7, 'A');

        // verify that the square on the board contains the expected letter
        assertEquals('A', board.getSquare(7, 7).getLetter());

        board.placeLetter(7, 6, 'C');

        // verify that the square on the board does not contain the expected letter
        assertNotEquals('A', board.getSquare(7, 6).getLetter());
    }

    @Test
    void testSetBlankLetter() {
        int row = 1;
        int col = 2;
        board.setBlankLetter(row, col, 'A');
        assertTrue(board.getBlanks().containsKey('A'));
        assertEquals(row, board.getBlanks().get('A').getI());
        assertEquals(col, board.getBlanks().get('A').getJ());

        //test with too much blank letters
        board.setBlankLetter(col, row, 'B');
        assertThrows(IllegalStateException.class, () ->  board.setBlankLetter(col+1, row+1, 'C'));

    }

    @Test
    void testApplyTurn(){
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7,7, true, blankIndexes);
        Turn turn = new Turn(turnInfo);
        board.applyTurn(turn);

        // Assert that the letters have been placed
        assertEquals('t', board.getSquare(7, 7).getLetter());
        assertEquals('e', board.getSquare(7, 8).getLetter());
        assertEquals('s', board.getSquare(7, 9).getLetter());
        assertEquals('t', board.getSquare(7, 10).getLetter());
    }

    @Test
    void testValidateInitialMove(){
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo1 = new TurnInfo("player1", testChar, 7,7, true, blankIndexes);
        assertTrue(board.validateInitialMove(turnInfo1));

        TurnInfo turnInfo2 = new TurnInfo("player1", testChar, 0,0, true, blankIndexes);
        assertFalse(board.validateInitialMove(turnInfo2));
    }

    @Test
    void testValidateMoveWithSurroundingTiles(){
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        char[] testChar = {DEFAULT, DEFAULT, DEFAULT};
        board.getSquare(8, 7).setLetter('a');
        board.getSquare(9, 7).setLetter('k');
        board.getSquare(10, 7).setLetter('e');
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 8,7, false, blankIndexes);
        assertTrue(board.validateMove(turnInfo));
    }
    @Test
    void testValidateMoveWithNoSurroundingTiles(){
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7,7, true, blankIndexes);
        assertFalse(board.validateMove(turnInfo));

    }

    @Test
    void testValidateMoveWithLongWord(){
        char[] testChar = {'p','h','a','m','e','r','c','i','s','t'};
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7,7, true, blankIndexes);
        assertFalse(board.validateMove(turnInfo));

    }

    @Test
    void testScoreMove(){
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        char[] testChar = {DEFAULT, DEFAULT, DEFAULT};
        board.getSquare(8, 7).setLetter('a');
        board.getSquare(9, 7).setLetter('k');
        board.getSquare(10, 7).setLetter('e');
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 8,7, false, blankIndexes);
        assertTrue(board.scoreMove(turnInfo)>0);
    }

    @Test
    void testScoreWord(){
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        assertTrue(board.scoreWord("ake",8,7, false)>0);
    }

    @Test
    void testDetectWord(){
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        char[] testChar = {DEFAULT, DEFAULT, DEFAULT};
        board.getSquare(8, 7).setLetter('a');
        board.getSquare(9, 7).setLetter('k');
        board.getSquare(10, 7).setLetter('e');
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 8,7, false, blankIndexes);
        ArrayList<WordInfo> words = board.detectWords(turnInfo);
        String expectedWord = "take";
        String detectedWord = words.get(0).word();
        assertEquals(expectedWord,detectedWord);

    }
    @Test
    void testDetectWordOutsideOfPlacedWordCoordinate(){
        board.getSquare(7, 7).setLetter('t');
        board.getSquare(7, 8).setLetter('e');
        board.getSquare(7, 9).setLetter('s');
        board.getSquare(7, 10).setLetter('t');

        char[] testChar = {DEFAULT, DEFAULT, DEFAULT};
        board.getSquare(8, 7).setLetter('a');
        board.getSquare(9, 7).setLetter('k');
        board.getSquare(10, 7).setLetter('e');
        List<Integer> blankIndexes = new ArrayList<Integer>();

        TurnInfo turnInfo = new TurnInfo("player1", testChar, 2,8, false, blankIndexes);
        ArrayList<WordInfo> words = board.detectWords(turnInfo);
        String expectedWord = DEFAULT+""+DEFAULT+""+DEFAULT;
        String detectedWord = words.get(0).word();
        assertEquals(expectedWord, detectedWord);

    }


}
