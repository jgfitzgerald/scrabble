import './Game.css';
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Button from '@mui/material/Button';
import axios from 'axios';
import Board from '../components/board.js';
import Tile from '../components/tile.js';
import Stomp, {Client} from '@stomp/stompjs';
import { DragDropContext, Droppable } from 'react-drag-drop-container';


const Game = (props) => {

  useEffect(() => {
    let onConnected = () => {
      console.log("connected");
      client.subscribe("/game/gameState", (response) => {
        // console.log(response);
        if (response.body) {
          let data = JSON.parse(response.body);
          console.log('DATA:::');
          console.log(data);
          setGameState(data);
          
          axios.get('/currPlayer', {
          }).then((response) => {
            // console.log(response);
            if (response.status === axios.HttpStatusCode.Ok) {
              let data = response.data;
              console.log('curr player:::');
              console.log(data);
              setCurrPlayer(data);
              // console.log('PLAYERS:::');
              // console.log(data.players);
            }
          }).catch((error) => {
            console.log(error);
          });

        }
      });
    }

    let onDisconnected = () => {
      console.log("disconnected");
    }
  
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected
    });
    client.activate();
  }, []);

  const name = localStorage.getItem('name');
  const [currPlayer, setCurrPlayer] = useState({});
  const [placedThisTurn, updatePlaced] = useState({});

  const { state } = useLocation();
  const [gameState, setGameState] = useState(state);

  const voteToStart = () => {
    // console.log(gameState.id);
    // console.log(name);
    axios.patch('/vote', {
      gameId: gameState.id,
      playerId: name
    }).then((response) => {
      console.log('VOTE RESPONSE:::');
      console.log(response);
    }).catch((error) => {
      console.log(error);
    });
  }

  const getState = () => {
    axios.get('/gamestate', {
    }).then((response) => {
      if (response.status === axios.HttpStatusCode.Ok) {
        let data = response.data;
        setGameState(data);
      }
    }).catch((error) => {
      console.log(error);
    });
  }

  const makeMove = () => {
    let placement = checkPlacement();
    // console.log(placement);
    if (placement === {}) return; // show some sort of message to user
    // create turn state info
    let word = Object.values(placedThisTurn);
    // console.log("word:::");
    // console.log(word);

    // questions for julia
    // - [TurnController line 76] can't call isEmpty() on array (need to convert JS Array to Java List<Int>)
    // - js might have a list type??? (https://www.collectionsjs.com/list)
    // - I think I was wrong about the challenging working...
    // -- the turn doesn't change until after the challenge phase so it seems like the turn that's being skipped is the player that just played the invalid word.

    // asked and answered
    // - x/y is first PLACED letter
    // - existing letters should be null (e.g. if h and t are played on existing a, word should be ['h', null, 't'])

    for (let c = 0; c < word.length; c++) {
      word[c] = word[c].charAt(0);
    }

    // word.splice(col, 0, null);

    if (placement.isHorizontal) {
      // get sorted array of cols
      let cols = Object.keys(placedThisTurn).reduce(function(acc, val, index) {
        acc.push(val.split('/')[1]);
        return acc;
      }, []).sort((a,b) => compare(a,b));
      
      for (let c = 1; c < cols.length; c++) {
        let diff = cols[c] - cols[c-1];
        // check if column differnce is 1
        if (diff === 1) continue;
        // if it's greater than 1, insert null the appropriate number of times
        else do {
          word.splice(c, 0, null);
          diff--;
        } while (diff > 1);
      }
    } else {
      // get sorted array of rows
      let rows = Object.keys(placedThisTurn).reduce(function(acc, val, index) {
        acc.push(val.split('/')[0]);
        return acc;
      }, []).sort((a,b) => compare(a,b));
      
      for (let r = 1; r < rows.length; r++) {
        let diff = rows[r] - rows[r-1];
        // check if column differnce is 1
        if (diff === 1) continue;
        // if it's greater than 1, insert null the appropriate number of times
        else do {
          word.splice(r, 0, null);
          diff--;
        } while (diff > 1);
      }
    }

    // console.log('NULLED WORD!!!!!');
    // console.log(word);


    let blankIndices = Object.values(placedThisTurn).reduce(
      function(total, val, index) {
        if (val === ' ') total.push(index);
        return total;
      }, []
    );
    blankIndices = typeof blankIndices !== undefined ? blankIndices : [];
    // console.log(blankIndices);

    axios.post('/move', {
      id: name.toString(), // string // DONE
      word: word, // char[]
      row: parseInt(placement['x']), // int
      column: parseInt(placement['y']), // int
      isHorizontal: placement['isHorizontal'], // bool
      blankIndexes: blankIndices // List<Integer>
    }).then((response)=> {
      console.log('MOVE RESPONSE:::');
      console.log(response);
      if (response.status === axios.HttpStatusCode.Ok) {
        // empty placedThisTurn
        updatePlaced({});
        // probably something else...
      }
    }).catch((error) => {
      console.log(error);
    })
  }

  const checkPlacement = () => {
    // check if all in same row OR column
    let rows = {};
    let cols = {};
    for (let key in placedThisTurn) {
      // console.log(key);
      let coords = key.split('/');
      rows[coords[0]] = 1;
      cols[coords[1]] = 1;
    }
    // console.log(rows);
    // console.log(cols);
    let rowKeys = Object.keys(rows).sort((a, b) => compare(a, b));
    let colKeys = Object.keys(cols).sort((a, b) => compare(a, b));
    // console.log(rowKeys);
    // console.log(colKeys);

    // Return the first row and column along with if it is horizontal or not
    if (rowKeys.length === 1) {
      return {
        x: rowKeys[0],
        y: colKeys[0],
        isHorizontal: true,
      };
    }
    else if (colKeys.length === 1) {
      return {
        x: rowKeys[0],
        y: colKeys[0],
        isHorizontal: false,
      };
    }

    return {};
  }

  const compare = (a, b) => {
    if (parseInt(a) < parseInt(b)) return -1;
    if (parseInt(a) < parseInt(b)) return 1;
    return 0;
  }

  const passTurn = () => {
    axios.post('/pass', {
      id: name
    }).then((response)=> {
      console.log('PASS RESPONSE:::');
      console.log(response);
    }).catch((error) => {
      console.log(error);
    })
  }

  const challengeMove = () => {
    axios.post('/challenge', {
      gameId: gameState.id,
      challengerId: name
    }).then((response)=> {
      console.log('CHALLENGE RESPONSE:::');
      console.log(response);
      getState();
    }).catch((error) => {
      console.log(error);
    })
  }

  function placeTile(e) {
    let stateCopy = {...gameState};
    // keep track of where tiles are being placed in a turn
    let newPlaced = {...placedThisTurn};
    newPlaced[e.dropData.name] = e.dragData.letter;
    updatePlaced(newPlaced);
    // console.log(placedThisTurn);
    // remove tile from rack
    stateCopy.playerMap[name].rack.splice(stateCopy.playerMap[name].rack.indexOf(e.dragData.letter), 1);
    // place tile on board
    let coords = e.dropData.name.split('/');
    stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = e.dragData.letter;
    // save new state
    setGameState(stateCopy);
    // console.log('stateCopy: ', stateCopy);


    // checkPlacement();
  }

  // this needs to have the char as well
  function returnToRack(e, coords) {
    // console.log(e);
    let stateCopy = {...gameState};
    // update tiles placed this turn
    // console.log('placedThisTurn: ', placedThisTurn);
    let char = placedThisTurn[coords];
    let newPlaced = {...placedThisTurn};
    // console.log('name ', coords);
    delete newPlaced[coords];
    // console.log(newPlaced);
    updatePlaced(newPlaced);
    // console.log(placedThisTurn);
    // remove tile from board
    coords = coords.split('/');
    stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = "\u0000";
    // add tile to rack
    stateCopy.playerMap[name].rack.push(char);
    // save new state
    setGameState(stateCopy);
    // console.log('stateCopy: ', stateCopy);
  }

  function shuffleRack() {
    let rackCopy = [...gameState.playerMap[name].rack];
    for (let i = 0; i < 50; i++) {
      rackCopy.splice(
        Math.floor(Math.random() * rackCopy.length),
        0,
        rackCopy.splice(Math.floor(Math.random() * rackCopy.length), 1)[0]
      );
      // reorder(rackCopy, Math.floor(Math.random() * rackCopy.length));
    }
    let newState = {...gameState};
    newState.playerMap[name].rack = rackCopy;
    setGameState(newState);
  }

  return <div className="gamePage">
    <div className='texture'></div>
    <div className="header">
      <h1>Lobby {gameState.id.split('-')[0]}</h1>
    </div>
    {/* <DragDropContext onDragEnd={onDragEnd}> */}
      <div className='game'>
        <Board data={gameState.board} thisTurn={placedThisTurn} tileClick={returnToRack} />
        <div className="players">
          {Object.entries(gameState.playerMap).map( ([key, value]) =>
            <div className={"namePlate" + ((Object.keys(currPlayer).length !== 0 && currPlayer.id === key) ? " playing" : "")}>
              <p>{key}</p>
              <p>({value.totalScore} points)</p>
            </div>
          )}
        </div>
        {/* <Droppable droppableId="tile-rack" type="tile" isDropDisabled={false}> */}
          <div className="tileRack" style={{gridTemplateColumns: `repeat(${gameState.playerMap[name].rack.length}, 1fr)`}}>
            {gameState.playerMap[name].rack.map( (char) =>
              <Tile char={char} drag={true} onDrop={placeTile}/>
            )}
          </div>
        {/* </Droppable> */}
      </div>
    {/* </DragDropContext> */}
    <div className="gameMenu">
      <Button
        variant='contained'
        color='primary'
        onClick={() => voteToStart()}>
        Vote to Start
      </Button>
      <Button
        variant='contained'
        color='primary'
        onClick={() => getState()}>
        Game State
      </Button>
      <Button
        variant='contained'
        color='primary'
        onClick={() => makeMove()}>
        Play Move
      </Button>
      <Button
        variant='contained'
        color='primary'
        onClick={() => passTurn()}>
        Pass Turn
      </Button>
      <Button
        variant='contained'
        color='primary'
        onClick={() => challengeMove()}>
        Challenge
      </Button>
      <Button
        variant='contained'
        color='primary'
        onClick={() => shuffleRack()}>
        Shuffle
      </Button>
    </div>
  </div>
}

export default Game;