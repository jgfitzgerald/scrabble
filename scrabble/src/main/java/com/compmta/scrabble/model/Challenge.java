package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.WordInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class Challenge {
    private List<WordInfo> words;
    private boolean valid;

    public Challenge(List<WordInfo> words) {
        this.words = words;
        valid = this.validate(words);
    }

    private boolean validate(List<WordInfo> words) {
        for (WordInfo word : words) {
            if (!Dictionary.verifyWord(word.word())) {
                return true;
            }
        }
        return false;
    }
}