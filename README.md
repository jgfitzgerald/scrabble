# Scrabble: COMP 4271 Term Project

Requirements:
- JDK 17

To run:
- Navigate to the scrabble directory
- You may need to give yourself permission: `chmod +x gradlew`
- Run the command, `./gradlew bootrun`
- In your web browser, the game will be hosted on `localhost:8080`
- Enjoy your scrabble game!

# API Endpoints
Endpoints for Scrabble COMP-4271 (Software Design) Project.

## GET
### /gameState
#### ```localhost:8080/gamestate?gameId=UUID```
Fetches game state information.

<table>
  <tr>
    <td colspan="2">Query Params</td>
    </tr>
  <tr>
    <td>gameId</td>
    <td>UUID</td>
  </tr>
</table>

Response Example
```json
{
    "gameId": "ddeec294-d294-4074-8b5c-d6b88fcba007",
    "status": "PENDING",
    "board": null,
    "players": {}
}
```

### /currPlayer
#### ```localhost:8080/currPlayer?gameId=UUID```
Fetches the player whose turn it is.

<table>
  <tr>
    <td colspan="2">Query Params</td>
    </tr>
  <tr>
    <td>gameId</td>
    <td>UUID</td>
  </tr>
</table>

### /getDatabase
#### ```localhost:8080/getDatabase```
Fetches the game database, for testing purposes.

## POST
### /newGame
#### ```localhost:8080/newGame```
Creates a new Game State object.

Response Example
```json
{
    "gameId": "ddeec294-d294-4074-8b5c-d6b88fcba007",
    "status": "PENDING",
    "board": null,
    "players": {}
}
```

### /join
#### ```localhost:8080/join```
Allows a player to join a game.

Request Body
```json
{
    "gameId": "UUID",
    "playerId":"playerId"
}
```

### /move
#### ```localhost:8080/move```
Allows a player to place a word on the board.

Request Body
```json
{
    "gameId":"UUID",
    "playerId":"playerId",
    "word":["c","h","a","r","s"],
    "row":7,
    "column":7,
    "isHorizontal":true,
    "blankIndexes":[]
}
```

### /pass
#### ```localhost:8080/pass```
Allows a player to pass their turn.

Request Body
```json
{
    "gameId":"UUID",
    "playerId":"playerId"
}
```

### /challenge
#### ```localhost:8080/challenge```
Allows a player to contest a move made by another player.

Request Body
```json
{
    "gameId": "UUID",
    "challengerId": "playerId"
}
```

## PATCH
### /exchange
#### ```localhost:8080/exchange```
Allows a player to exchange letters in their rack.

Request Body
```json
{
    "gameId":"UUID",
    "playerId":"playerId",
    "letters":["c","h","a","r","s"]
}
```

### /vote
#### ```localhost:8080/vote```
Allows a player to vote to begin or end the game.

Request Body
```json
{
    "gameId": "UUID",
    "playerId": "playerId"
}
```
