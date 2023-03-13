import com.compmta.scrabble.model.Dictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class DictionaryTest {
    public static com.compmta.scrabble.model.Dictionary dictionary;
    @BeforeEach
    void setUp() {
        File dict = new File("docs/Collins Scrabble Words (2019).txt");
        try {
            dictionary = new Dictionary(dict);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void testVerifyWord(){
        String validWord = "scrabble";
        String invalidWord = "xcvt";
        assertTrue(dictionary.verifyWord(validWord));
        assertFalse(dictionary.verifyWord(invalidWord));
    }
}
