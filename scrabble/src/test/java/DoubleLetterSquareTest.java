package com.compmta.scrabble.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoubleLetterSquareTest {
    private Square doubleLetterSquare = new DoubleLetterSquare();
    @Test
    void testEffectWithDefaultLetter(){
        String word = "test";
        int expectedFinalScore = 2;
        int actualFinalScore = doubleLetterSquare.effect(word, 10, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 6;
        actualFinalScore = doubleLetterSquare.effect(word, 10, 5);
        assertEquals(expectedFinalScore, actualFinalScore);

    }

    @Test
    void testEffectWithoutDefaultLetter(){
        doubleLetterSquare.setLetter('s');
        String word = "test";
        int expectedFinalScore = 1;
        int actualFinalScore = doubleLetterSquare.effect(word, 10, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 3;
        actualFinalScore = doubleLetterSquare.effect(word, 10, 5);
        assertEquals(expectedFinalScore, actualFinalScore);
    }

    @Test
    void testEffectWithInvalidIndex(){
        String word = "test";
        int expectedFinalScore = 0;
        int actualFinalScore = doubleLetterSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 0;
        actualFinalScore = doubleLetterSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

    }
}
