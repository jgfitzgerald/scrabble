/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: Dictionary
 */

//package
package com.compmta.scrabble.model;

//imports
import lombok.Getter;
import java.io.*;
import java.util.HashSet;

//generates getters
@Getter

public class Dictionary {

    //Instance variables
    public static HashSet<String> dictionary;

    /**
     * Contructor
     * Calls the setUpDictionary method to initialize the dictionary
     * 
     * @param file Takes file with dictionary words
     * @throws IOException for reading input from file
     */
    public Dictionary(File file) throws IOException {
        this.setUpDictionary(file);
    } //Contructor

    /**
     * setUpDictionary reads through the file and creates the dictionary
     * 
     * @param in File with dictionary words
     */
    private void setUpDictionary(File in) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(in));
            dictionary = new HashSet<String>();
            String line;

            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } //setUpDictionary(File in)
}
