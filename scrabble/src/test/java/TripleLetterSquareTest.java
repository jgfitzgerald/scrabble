import com.compmta.scrabble.model.Square;
import com.compmta.scrabble.model.TripleLetterSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripleLetterSquareTest {
    private Square tripleLetterSquare = new TripleLetterSquare();
    @Test
    void testEffectWithDefaultLetter(){
        String word = "test";
        int expectedFinalScore = 3;
        int actualFinalScore = tripleLetterSquare.effect(word, 10, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 9;
        actualFinalScore = tripleLetterSquare.effect(word, 10, 5);
        assertEquals(expectedFinalScore, actualFinalScore);

    }

    @Test
    void testEffectWithoutDefaultLetter(){
        tripleLetterSquare.setLetter('s');
        String word = "test";
        int expectedFinalScore = 1;
        int actualFinalScore = tripleLetterSquare.effect(word, 10, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 3;
        actualFinalScore = tripleLetterSquare.effect(word, 10, 5);
        assertEquals(expectedFinalScore, actualFinalScore);
    }

    @Test
    void testEffectWithInvalidIndex(){
        final String word1 = "test";
        assertThrows(IllegalArgumentException.class, () -> tripleLetterSquare.effect(word1, 10, 10));

        final String word2 = "scrabble";
        assertThrows(IllegalArgumentException.class, () -> tripleLetterSquare.effect(word2, 10, 10));


    }
}
