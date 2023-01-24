package com.compmta.scrabble.model;

import lombok.Getter;

import java.io.*;
import java.util.HashSet;

@Getter
public class Dictionary {

    public static HashSet<String> dictionary;

    public Dictionary(File file) throws IOException {
        this.setUpDictionary(file);
    }

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
    }
}
