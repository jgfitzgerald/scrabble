package com.compmta.scrabble;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class ScrabbleApplicationTests {
	GameStateControllerTest gsct = new GameStateControllerTest(); 
	
	@Test
	void contextLoads() {
	}

	@Test
	void myServiceTest() {

		gsct.testJoinGame();

		gsct.testJoinGameWhenGameIsFull();

		gsct.testJoinGameWithSameName();

		gsct.testSetUpGameWithLessThanMinPlayers();

		gsct.testSetUpGame();

		gsct.testVoteToEnd();
	}

}
