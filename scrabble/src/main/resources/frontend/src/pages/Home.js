import './constants.css';
import './Home.css';
import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Home() {

  let navigate = useNavigate();
  let [nickname, setNickname] = useState();
  let [gameID, setGameID] = useState();

  const [popupText, setPopupText] = useState('');

  const joinGame = (gameId) => {
    if (nickname){
      axios.post('/join', {
        gameId: gameId,
        playerId: nickname,
      }).then((response) => {
        console.log(response);
        if (response.status === axios.HttpStatusCode.Ok) {
          console.log("JOIN REPONSE:::");
          console.log(response);
          sessionStorage.setItem('name', nickname);
          navigate('/play', {replace: true, state: response.data});
        }
      }).catch((error) => {
        console.log("setting popup");
        showPopup("Something went wrong :(");
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
        console.log(response.data);
        setGameID(response.data.gameId);
        joinGame(response.data.gameId);
      }).catch((error) => {
        console.log("setting popup");
        showPopup("Something went wrong :(");
        console.log(error);
      });
    }
  }

  const showPopup = (text) => {
    setPopupText(text);
    setTimeout(setPopupText(''), 5050);
  }

  return (
      <div className='navigator'>
        <div className={'popUp' + (popupText !== '' ? ' showing' : '')}>
          <p>{popupText}</p>
        </div>
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
  );
}

export default Home;
