import com.compmta.scrabble.controllers.GameStateController;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.GameStatus;
import com.compmta.scrabble.model.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;


class GameStateControllerTest {
//    @Autowired
    private GameStateController gameStateController;
    private int MAX_PLAYERS;

    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo player3;
    private PlayerInfo player4;
    private GameState gameState;
    private String gameID;

    @Test
    void testSetUpGameWithOnePlayer(){
        MockitoAnnotations.openMocks(this);
        gameStateController = new GameStateController();
        gameState = gameStateController.newGame();
        gameID = gameState.getId();
        player1 = gameStateController.joinGame(gameID,"player1");
        assertThrows(IllegalStateException.class, () -> gameStateController.setUpGame(gameID));
    }

    @Test
    void testSetUpGameWithMaxPlayer(){
        MockitoAnnotations.openMocks(this);
        gameStateController = new GameStateController();
        gameState = gameStateController.newGame();
        gameID = gameState.getId();

        player1 = gameStateController.joinGame(gameID,"player1");
        player2 = gameStateController.joinGame(gameID,"player2");
        player3 = gameStateController.joinGame(gameID,"player3");
        player4 = gameStateController.joinGame(gameID,"player4");

        gameStateController.setUpGame(gameID);
        assertEquals(GameStatus.IN_PROGRESS, gameStateController.getGameDatabase().get(gameID).getStatus());
        assertNotNull(gameStateController.getGameDatabase().get(gameID).getPlayerMap());
        assertEquals(player1, gameStateController.getGameDatabase().get(gameID).getPlayers().get(0));
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameStateController = new GameStateController();
        gameState = gameStateController.newGame();
        gameID = gameState.getId();
        MAX_PLAYERS = (int) ReflectionTestUtils.getField(gameStateController, "MAX_PLAYERS");
    }

    @Test
    void testJoinGame() {
        PlayerInfo player1 = gameStateController.joinGame(gameID,"player1");
        assertNotNull(player1);
        assertEquals("player1", player1.getId());
    }

    @Test
    void testJoinGameWhenGameIsFull() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameStateController.joinGame(gameID,"player" + (i + 1));
        }
        assertThrows(IllegalStateException.class, () -> gameStateController.joinGame(gameID,"player5"));
    }

    @Test
    void testJoinGameWithSameName() {
        PlayerInfo player1 = gameStateController.joinGame(gameID,"player1");
        assertThrows(IllegalArgumentException.class, () -> gameStateController.joinGame(gameID,"player1"));
    }

    @Test
    void testSetUpGameWithLessThanMinPlayers() {
        gameStateController.joinGame(gameID,"player1");
        assertThrows(IllegalStateException.class, () -> gameStateController.setUpGame(gameID));
    }

    @Test
    void testSetUpGame() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameStateController.joinGame(gameID,"player" + (i + 1));
        }
        gameStateController.setUpGame(gameID);
        assertNotNull(gameState);
        assertNotNull(gameState.getBoard());
        assertNotNull(gameState.getPlayers());
    }

}