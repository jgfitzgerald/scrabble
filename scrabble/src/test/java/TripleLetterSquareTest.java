import com.compmta.scrabble.model.Square;
import com.compmta.scrabble.model.TripleLetterSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String word = "test";
        int expectedFinalScore = 0;
        int actualFinalScore = tripleLetterSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 0;
        actualFinalScore = tripleLetterSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

    }
}
