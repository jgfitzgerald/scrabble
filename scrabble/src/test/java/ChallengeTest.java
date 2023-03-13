package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.WordInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ChallengeTest {

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
    void testValidChallenge(){
        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("apple", 7,7, true));
        words.add(new WordInfo("rsktq",7,7,false  ));
        words.add(new WordInfo("xcdv", 8,7, true));
        Challenge challenge = new Challenge(words);
        assertTrue(challenge.isValid());
    }
    @Test
    void testInvalidChallenge(){
        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("apple", 7,7, true));
        words.add(new WordInfo("ant",7,7,false  ));
        words.add(new WordInfo("nurse", 8,7, true));
        Challenge challenge = new Challenge(words);
        assertFalse(challenge.isValid());
    }

}
