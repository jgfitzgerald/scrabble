/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: Letter
 */

//Package
package com.compmta.scrabble.util;

//Import statements
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Generates getters
@Getter
@AllArgsConstructor
public class Letter {
    //Instance variables
    private char letter;
    private int baseScore;
    private int initialAmount;

    public static final Map<Character, Letter> map = Collections.unmodifiableMap(initializeMapping());

    private static Map<Character, Letter> initializeMapping() {
        Map<Character, Letter> map = new HashMap<Character, Letter>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("util" + File.separator + "scrabble_letter_data.csv")); 
            try {   
                int numLines = Integer.parseInt(br.readLine());
                for(int i=0; i<numLines; i++) {
                    String[] letterData = br.readLine().split(",");
                    char let = letterData[0].charAt(0);
                    int score = Integer.parseInt(letterData[1]);
                    int amtOfLetters = Integer.parseInt(letterData[2]);

                    map.put(let, new Letter(let, score, amtOfLetters));
                }
                br.close();
            }
            
            catch(IOException e) {
                e.printStackTrace();   
            }
        }
        
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

}
