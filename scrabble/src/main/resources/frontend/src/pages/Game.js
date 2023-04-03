import './Game.css';
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axios from 'axios';
import Board from '../components/board.js';
import Tile from '../components/tile.js';
import { Client } from '@stomp/stompjs';
import { DropTarget } from 'react-drag-drop-container';
import Lobby from './Lobby';
import Rules from '../components/Rules';


const Game = (props) => {

  useEffect(() => {
    let onConnected = () => {
      console.log("connected");
      client.subscribe("/game/gameState/"+state.gameId, (response) => {
        if (response.body) {
          let data = JSON.parse(response.body);
          setGameState(data);
          
          axios.get('/currPlayer', { params: {
            gameId: state.gameId
          }}).then((response) => {
            if (response.status === axios.HttpStatusCode.Ok) {
              let data = response.data;
              setCurrPlayer(data);
            }
          }).catch((error) => {
            showPopup("Something went wrong :(");
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

  const name = sessionStorage.getItem('name');
  const [currPlayer, setCurrPlayer] = useState({});
  const [placedThisTurn, updatePlaced] = useState({});
  const [toExchange, setToExchange] = useState([]);
  const [entering, setEntering] = useState(false);
  let enteringLetter = false;
  const [blanks, setBlanks] = useState([]);
  const [inputLetter, setInputLetter] = useState('');
  const [isOpen, setIsOpen] = useState(false);

  const { state } = useLocation();
  const [gameState, setGameState] = useState(state);

  let showingVar = false;
  const [popupText, setPopupText] = useState('');

  const voteToEnd = () => {
    axios.patch('/vote', {
      gameId: gameState.id ?? gameState.gameId,
      playerId: name
    }).then((response) => {
    }).catch((error) => {
      showPopup("Something went wrong :(");
      console.log(error);
    });
  }

  const getState = () => {
    axios.get('/gamestate', { params: {
      gameId: gameState.id ?? gameState.gameId
    }}).then((response) => {
      if (response.status === axios.HttpStatusCode.Ok) {
        let data = response.data;
        setGameState(data);
      }
    }).catch((error) => {
      showPopup("Something went wrong :(");
      console.log(error);
    });
  }

  const makeMove = () => {
    let placement = checkPlacement();
    if (placement === {}) {
      showPopup("Invalid Tile Placement");
      return;
    }
    // create turn state info
    let word = [];
    let wordParser = {};


    if (placement.isHorizontal) {
      for (const [key, value] of Object.entries(placedThisTurn)) {
        wordParser[key.split('/')[1]] = value;
      }
      word = Object.values(wordParser);
      for (let c = 0; c < word.length; c++) {
        word[c] = word[c].charAt(0);
      }
      // get sorted array of cols
      let cols = Object.keys(placedThisTurn).reduce(function(acc, val, index) {
        acc.push(val.split('/')[1]);
        return acc;
      }, []).sort((a,b) => compare(a,b));
      
      for (let c = cols.length -1; c > 0; c--) {
        let diff = cols[c] - cols[c-1];
        // check if column difference is 1
        if (diff === 1) continue;
        // if it's greater than 1, insert null the appropriate number of times
        else do {
          word.splice(c, 0, null);
          diff--;
        } while (diff > 1);
      }
    } else {
      for (const [key, value] of Object.entries(placedThisTurn)) {
        wordParser[key.split('/')[0]] = value;
      }
      word = Object.values(wordParser);
      for (let c = 0; c < word.length; c++) {
        word[c] = word[c].charAt(0);
      }
      // get sorted array of rows
      let rows = Object.keys(placedThisTurn).reduce(function(acc, val, index) {
        acc.push(val.split('/')[0]);
        return acc;
      }, []).sort((a,b) => compare(a,b));
      
      for (let r = rows.length -1; r > 0; r--) {
        let diff = rows[r] - rows[r-1];
        // check if column difference is 1
        if (diff === 1) continue;
        // if it's greater than 1, insert null the appropriate number of times
        else do {
          word.splice(r, 0, null);
          diff--;
        } while (diff > 1);
      }
    }

    let blankIndices = Object.values(placedThisTurn).reduce(
      function(total, val, index) {
        if (val === ' ') total.push(index);
        return total;
      }, []
    );
    blankIndices = typeof blankIndices !== undefined ? blankIndices : [];
    axios.post('/move', {
      gameId: gameState.id ?? gameState.gameId,
      playerId: name.toString(),
      word: word,
      row: parseInt(placement['x']),
      column: parseInt(placement['y']),
      isHorizontal: placement['isHorizontal'],
      blankIndexes: blankIndices // List<Integer>
    }).then((response)=> {
      if (response.status === axios.HttpStatusCode.Ok) {
        // empty placedThisTurn and blanks
        updatePlaced({});
        setBlanks([]);
      }
    }).catch((error) => {
      showPopup("Something went wrong :(");
      console.log(error);
    })
  }

  const checkPlacement = () => {
    // check if all in same row OR column
    let rows = {};
    let cols = {};
    for (let key in placedThisTurn) {
      let coords = key.split('/');
      rows[coords[0]] = 1;
      cols[coords[1]] = 1;
    }
    let rowKeys = Object.keys(rows).sort((a, b) => compare(a, b));
    let colKeys = Object.keys(cols).sort((a, b) => compare(a, b));

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
      gameId: gameState.id ?? gameState.gameId,
      playerId: name
    }).then((response)=> {
      setToExchange([]);
    }).catch((error) => {
      showPopup("It's not your turn!");
      console.log(error);
    })
  }

  const challengeMove = () => {
    axios.post('/challenge', {
      gameId: gameState.id ?? gameState.gameId,
      challengerId: name
    }).then((response)=> {
      getState();
    }).catch((error) => {
      showPopup("Something went wrong :(");
      console.log(error);
    })
  }


  async function placeTile(e){

    if (e.dropData.name === 'exchange') {
      exchangeDrop(e);
      return;
    }

    let isBlank = false;

    if (e.dragData.letter === " ") {
      enteringLetter = true;
      setEntering(true);
      isBlank = true;
      await new Promise(function(resolve, reject) {
        let counter = 0;
        let checker = () => {
          setTimeout(() => {
            counter++;
            let checkEntering;
            setEntering(current => {
              checkEntering = current;
              return current;
            });
            if (inputLetter.length === 1 || (!checkEntering)) resolve();
            else if (counter > 600) {
              enteringLetter = false;
              setEntering(false);
              reject();
            } else {
              checker();
            };
          }, 100);
        }
        checker();
      }).then((value) => {
        enteringLetter = false;
        setEntering(false);
        let blanksCopy = [...blanks];
        blanksCopy.push(e.dropData.name);
        setBlanks(blanksCopy);

        let stateCopy = {...gameState};
        // keep track of where tiles are being placed in a turn
        let newPlaced = {...placedThisTurn};
        newPlaced[e.dropData.name] = inputLetter;
        updatePlaced(newPlaced);
        // remove tile from rack
        stateCopy.playerMap[name].rack.splice(stateCopy.playerMap[name].rack.indexOf(e.dragData.letter), 1);
        // place tile on board
        let coords = e.dropData.name.split('/');
        stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = isBlank ? inputLetter : e.dragData.letter;
        // save new state
        setGameState(stateCopy);
      }).catch((error) => {
        showPopup("Something went wrong :(");
      });
    } else {
      let stateCopy = {...gameState};
      // keep track of where tiles are being placed in a turn
      let newPlaced = {...placedThisTurn};
      newPlaced[e.dropData.name] = e.dragData.letter;
      updatePlaced(newPlaced);
      // remove tile from rack
      stateCopy.playerMap[name].rack.splice(stateCopy.playerMap[name].rack.indexOf(e.dragData.letter), 1);
      // place tile on board
      let coords = e.dropData.name.split('/');
      stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = e.dragData.letter;
      // save new state
      setGameState(stateCopy);
    }
  }

  function returnToRack(e, coords) {
    let stateCopy = {...gameState};
    // update tiles placed this turn
    let char = placedThisTurn[coords];
    let newPlaced = {...placedThisTurn};
    delete newPlaced[coords];
    updatePlaced(newPlaced);
    // remove tile from board
    coords = coords.split('/');
    stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = "\u0000";
    // add tile to rack
    if (blanks.includes(coords)) {
      stateCopy.playerMap[name].rack.push(' ');
      let blanksCopy = [...blanks];
      blanksCopy.splice(blanksCopy.indexOf(coords), 1);
      setBlanks(blanksCopy);
    } else {
      stateCopy.playerMap[name].rack.push(char);
    }
    // save new state
    setGameState(stateCopy);
  }

  function shuffleRack() {
    let rackCopy = [...gameState.playerMap[name].rack];
    for (let i = 0; i < 50; i++) {
      rackCopy.splice(
        Math.floor(Math.random() * rackCopy.length),
        0,
        rackCopy.splice(Math.floor(Math.random() * rackCopy.length), 1)[0]
      );
    }
    let newState = {...gameState};
    newState.playerMap[name].rack = rackCopy;
    setGameState(newState);
  }

  function exchangeDrop(e) {
    if (!gameState.playerMap[name].rack.includes(e.dragData.letter)) return;
    // Remove tile from rack
    let stateCopy = {...gameState};
    stateCopy.playerMap[name].rack.splice(stateCopy.playerMap[name].rack.indexOf(e.dragData.letter), 1);
    setGameState(stateCopy);
    // and add it to the exchange pile
    let exCopy = [...toExchange];
    exCopy.push(e.dragData.letter);
    setToExchange(exCopy);
  }
  
  function returnFromExchange(e, char) {
    let exCopy = [...toExchange];
    exCopy.splice(exCopy.indexOf(char), 1);
    setToExchange(exCopy);

    let stateCopy = {...gameState};
    stateCopy.playerMap[name].rack.push(char);
    setGameState(gameState);
  }

  function exchange() {
    if (toExchange.length === 0) return;
    axios.patch('/exchange', {
      gameId: gameState.id ?? gameState.gameId,
      playerId: name,
      letters: toExchange
    }).then((response)=> {
      if (response.status === axios.HttpStatusCode.Ok) {
        setToExchange([]);
      }
      getState();
    }).catch((error) => {
      showPopup("Something went wrong :(");
      console.log(error);
    })
  }
  
  const handleKey = (e) => {
    if (e.keyCode === 13 && inputLetter.length === 1) {
      enteringLetter = false;
      setEntering(false);
    }
  }

  const showPopup = (text) => {
    showingVar = true;
    setPopupText(text);
    setTimeout(() => {
      showingVar = false;
      setPopupText('');
    }, 5050);
  }

  const toggleRules = () => {
    setIsOpen(!isOpen);
  }

  return <div className="gamePage">
    <div className='texture'></div>
    <div className="header">
      <h1>Lobby {gameState.id ?? gameState.gameId}</h1>
    </div>
    { popupText !== '' || showingVar?
      <div className={"popUp"}>
        <p>{popupText}</p>
      </div> : <></>
    }
    {gameState.status === 'PENDING' ? <Lobby state={gameState} popup={showPopup}/> : <></>}
    {entering || enteringLetter ? <div className="blankPopup">
      <h2>Enter the letter you would like to play:</h2>
      <TextField
        className='letterInput'
        id='filled-basic'
        variant='filled'
        onChange={(event) => setInputLetter(event.target.value)}
        onKeyUp={handleKey}
      />
      <Button
        variant='contained'
        onClick={() => {
          if (inputLetter.length === 1 && new RegExp("[a-z]").exec(inputLetter)) {
            enteringLetter = false;
            setEntering(false);
          }
        }}>
        Enter
      </Button>
    </div> : <></>}
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
        <div className="tileRack" style={{gridTemplateColumns: `repeat(${gameState.playerMap[name].rack.length}, 1fr)`}}>
          {gameState.playerMap[name].rack.map( (char) =>
            <Tile char={char} drag={true} onDrop={placeTile}/>
          )}
        </div>
    </div>
    <div className="gameMenu">
      <Rules isOpen={isOpen} toggleDropdown={toggleRules}/>
      <div className="menuButtons">
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
          disabled={gameState.status !== "CHALLENGE" || currPlayer.id === name}
          onClick={() => challengeMove()}>
          Challenge
        </Button>
        <Button
          variant='contained'
          color='primary'
          onClick={() => shuffleRack()}>
          Shuffle Rack
        </Button>
        <Button
          variant='contained'
          color='primary'
          onClick={() => voteToEnd()}>
          Vote to End
        </Button>
      </div>
      <div className="exchange">
        <DropTarget
            targetKey="square"
            dropData={{name: "exchange"}}
        >
          <div className="exDropbox">
            {toExchange.map((char) =>
              <Tile char={char} drag={false} inExchange={true} onClick={(e) => returnFromExchange(e, char)}/>
            )}
          </div>
        </DropTarget>
        <Button
          variant='contained'
          color='primary'
          onClick={() => exchange()}>
          Exchange Tiles
        </Button>
      </div>
    </div>
  </div>
}

export default Game;