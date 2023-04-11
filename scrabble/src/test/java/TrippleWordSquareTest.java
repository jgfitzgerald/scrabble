import com.compmta.scrabble.model.DoubleWordSquare;
import com.compmta.scrabble.model.Square;
import com.compmta.scrabble.model.TripleLetterSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrippleWordSquareTest {
    private Square trippleWordSquare = new TripleLetterSquare();
    private int score = 10;
    @Test
    void testEffectWithDefaultLetter(){
        String word = "test";
        int expectedFinalScore = 3;
        int actualFinalScore = trippleWordSquare.effect(word, score, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 9;
        actualFinalScore = trippleWordSquare.effect(word, score, 5);
        assertEquals(expectedFinalScore, actualFinalScore);

    }

    @Test
    void testEffectWithoutDefaultLetter(){
        trippleWordSquare.setLetter('s');
        String word = "test";
        int expectedFinalScore = 1;
        int actualFinalScore = trippleWordSquare.effect(word, score, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 3;
        actualFinalScore = trippleWordSquare.effect(word, score, 5);
        assertEquals(expectedFinalScore, actualFinalScore);
    }
    @Test
    void testEffectWithInvalidIndex(){
        final String word1 = "test";
        assertThrows(IllegalArgumentException.class, () -> trippleWordSquare.effect(word1, score, 10));

        final String word2 = "scrabble";
        assertThrows(IllegalArgumentException.class, () -> trippleWordSquare.effect(word2, score, 10));

    }
}
