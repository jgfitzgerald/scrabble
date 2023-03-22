import com.compmta.scrabble.model.RegularSquare;
import com.compmta.scrabble.model.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegularSquareTest {
    private Square regularSquare = new RegularSquare();
    @Test
    void testEffectWithDefaultLetter(){
        String word = "test";
        int expectedFinalScore = 1;
        int actualFinalScore = regularSquare.effect(word, 10, 2);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 3;
        actualFinalScore = regularSquare.effect(word, 10, 5);
        assertEquals(expectedFinalScore, actualFinalScore);

    }

    @Test
    void testEffectWithInvalidIndex(){
        final String word1 = "test";
        assertThrows(IllegalArgumentException.class, () -> regularSquare.effect(word1, 10, 10));

        final String word2 = "scrabble";
        assertThrows(IllegalArgumentException.class, () -> regularSquare.effect(word2, 10, 10));

    }
}
