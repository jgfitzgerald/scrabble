package com.compmta.scrabble.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerInfoTest {

    private PlayerInfo player;

    @BeforeEach
    void setUp() {
        player = new PlayerInfo("John");
    }

    @Test
    void testUpdateScore() {
        player.updateScore(10);
        Assertions.assertEquals(10, player.getTotalScore());
    }

    @Test
    void testGetVote() {
        Assertions.assertFalse(player.getVote());
        player.setVote(true);
        Assertions.assertTrue(player.getVote());
    }

    @Test
    void testGetRack() {
        ArrayList<Character> expectedRack = new ArrayList<>();
        Assertions.assertEquals(expectedRack, player.getRack());

        expectedRack.add('A');
        expectedRack.add('B');
        expectedRack.add('C');
        player.setRack(expectedRack);

        Assertions.assertEquals(expectedRack, player.getRack());
    }
}
