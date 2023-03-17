import com.compmta.scrabble.model.RegularSquare;
import com.compmta.scrabble.model.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String word = "test";
        int expectedFinalScore = 0;
        int actualFinalScore = regularSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

        word = "scrabble";
        expectedFinalScore = 0;
        actualFinalScore = regularSquare.effect(word, 10, 10);
        assertEquals(expectedFinalScore, actualFinalScore);

    }
}
