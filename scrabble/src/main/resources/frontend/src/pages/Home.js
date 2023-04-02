import './constants.css';
import './Home.css';
import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Home() {

  let navigate = useNavigate();
  const [nickname, setNickname] = useState();
  const [gameID, setGameID] = useState();

  let showingVar = false;
  const [showing, setShowing] = useState(false);

  const joinGame = (gameId) => {
    if (nickname){
      axios.post('/join', {
        gameId: gameId,
        playerId: nickname,
      }).then((response) => {
        if (response.status === axios.HttpStatusCode.Ok) {
          sessionStorage.setItem('name', nickname);
          navigate('/play', {replace: true, state: response.data});
        }
      }).catch((error) => {
        showPopup();
        console.log(error);
      });
    }
  }

  const checkInput = () => {
    if (gameID) {
      joinGame(gameID);
    } else {
      axios.post('/newGame',  {
      }).then((response) => {
        setGameID(response.data.gameId);
        joinGame(response.data.gameId);
      }).catch((error) => {
        showPopup();
        console.log(error);
      });
    }
  }

  const showPopup = () => {
    showingVar = true;
    setShowing(true);
    setTimeout(() => {
      showingVar = false;
      setShowing(false);
    }, 5050);
  }

  return <div className='navigator'>
    { showing || showingVar ?
      <div className={"popUp"}>
        <p>{"Something went wrong :("}</p>
      </div> : <></>
    }
    <div className='texture' />
    <div className='loginMenu'>
      <div className='logo'/>
      <TextField
        className='userInput'
        id='filled-basic'
        label='Nicholasname'
        variant='filled'
        onChange={(event) => setNickname(event.target.value)}
      />
      <TextField
        className='userInput'
        id='filled-basic'
        label='Game ID'
        variant='filled'
        onChange={(event) => setGameID(event.target.value)}
      />
      <p class="inst">leave game ID blank to create a new game</p>
      <Button
        className='homeMenuBtn'
        variant='contained'
        onClick={() => checkInput()}>
        Enter
      </Button>
    </div>
  </div>
}

export default Home;
