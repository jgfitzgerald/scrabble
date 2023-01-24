package com.compmta.scrabble;

import com.compmta.scrabble.controllers.GameStateController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrabbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrabbleApplication.class, args);
    }

	/*public static void main(String[] args) {
		ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
		players.add(new PlayerInfo("one"));
		players.add(new PlayerInfo("two"));
		GameState game = new GameState();
	}*/

}
