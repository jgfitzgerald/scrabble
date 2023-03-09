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

    @Test
    @BeforeEach
    void testSetUpGameWithOnePlayer(){
        MockitoAnnotations.openMocks(this);
        gameStateController = new GameStateController();
        MockitoAnnotations.openMocks(this);
        player1 = gameStateController.joinGame("player1");
        assertThrows(IllegalStateException.class, () -> gameStateController.setUpGame());
    }

    @Test
    @BeforeEach
    void testSetUpGameWithMaxPlayer(){
        MockitoAnnotations.openMocks(this);
        gameStateController = new GameStateController();
        MockitoAnnotations.openMocks(this);
        player1 = gameStateController.joinGame("player1");
        player2 = gameStateController.joinGame("player2");
        player3 = gameStateController.joinGame("player3");
        player4 = gameStateController.joinGame("player4");
        gameStateController.setUpGame();
        assertEquals(GameStatus.IN_PROGRESS, gameStateController.getGameState().getStatus());
        assertNotNull(gameStateController.getGameState().getPlayerMap());
        assertEquals(player1, gameStateController.getGameState().getPlayers().get(0));
    }
    @BeforeEach
    void setUp() {
        gameStateController = new GameStateController();
        MAX_PLAYERS = (int) ReflectionTestUtils.getField(gameStateController, "MAX_PLAYERS");
    }

    @Test
    void testJoinGame() {
        PlayerInfo player1 = gameStateController.joinGame("player1");
        assertNotNull(player1);
        assertEquals("player1", player1.getId());
    }

    @Test
    void testJoinGameWhenGameIsFull() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        assertThrows(IllegalStateException.class, () -> gameStateController.joinGame("player5"));
    }

    @Test
    void testJoinGameWithSameName() {
        PlayerInfo player1 = gameStateController.joinGame("player1");
        assertThrows(IllegalArgumentException.class, () -> gameStateController.joinGame("player1"));
    }

    @Test
    void testSetUpGameWithLessThanMinPlayers() {
        gameStateController.joinGame("player1");
        assertThrows(IllegalStateException.class, () -> gameStateController.setUpGame());
    }

    @Test
    void testSetUpGame() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        gameStateController.setUpGame();
        GameState gameState = gameStateController.getGameState();
        assertNotNull(gameState);
        assertNotNull(gameState.getBoard());
        assertNotNull(gameState.getPlayers());
    }

    @Test
    void testVoteToEnd() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        gameStateController.setUpGame();
        assertFalse(gameStateController.getGameState().getPlayers().stream().allMatch(PlayerInfo::getVote));

        for (PlayerInfo p : gameStateController.getGameState().getPlayers()) {
            gameStateController.vote(p.getId());
        }
        assertTrue(gameStateController.getGameState().getPlayers().stream().allMatch(PlayerInfo::getVote));
    }

}