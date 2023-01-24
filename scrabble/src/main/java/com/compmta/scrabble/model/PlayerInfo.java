package com.compmta.scrabble.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PlayerInfo {

    private String id;
    private ArrayList<Character> rack;

    public PlayerInfo(String id) {
        this.id = id;
        this.rack = new ArrayList<Character>();
    }

}
