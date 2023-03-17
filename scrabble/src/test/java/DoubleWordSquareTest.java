import com.compmta.scrabble.model.DoubleWordSquare;
import com.compmta.scrabble.model.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleWordSquareTest {
    private Square doubleWordSquare = new DoubleWordSquare();
    private int score = 10;
    @Test
    void testEffectWithDefaultLetter(){
        String word = "test";
        int expectedFinalScore = score;
        int actualFinalScore = doubleWordSquare.effect(word, score, 4);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = score;
        actualFinalScore = doubleWordSquare.effect(word, score, 8);
        assertEquals(expectedFinalScore, actualFinalScore);

    }

    @Test
    void testEffectWithoutDefaultLetter(){
        doubleWordSquare.setLetter('s');
        String word = "test";
        int expectedFinalScore = 0;
        int actualFinalScore = doubleWordSquare.effect(word, score, 4);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        actualFinalScore = doubleWordSquare.effect(word, score, 8);
        assertEquals(expectedFinalScore, actualFinalScore);
    }
    @Test
    void testEffectWithInvalidIndex(){
        String word = "test";
        int expectedFinalScore = 1;
        int actualFinalScore = doubleWordSquare.effect(word, score, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 3;
        actualFinalScore = doubleWordSquare.effect(word, score, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

    }
}
