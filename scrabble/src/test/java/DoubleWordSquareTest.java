import com.compmta.scrabble.model.DoubleWordSquare;
import com.compmta.scrabble.model.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        final String word1 = "test";
        assertThrows(IllegalArgumentException.class, () -> doubleWordSquare.effect(word1, score, 10));

        final String word2 = "scrabble";
        assertThrows(IllegalArgumentException.class, () -> doubleWordSquare.effect(word2, score, 10));

    }
}
