import './constants.css';
import './Home.css';
import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { useSpring, useTransition, animated } from '@react-spring/web'
import axios from 'axios';

function Home() {

  let navigate = useNavigate();
  let [nickname, setNickname] = useState();

  const checkNickname = () => {
    if (nickname){
      axios.post('/join', {
        id: nickname,
      }).then((response) => {
        console.log(response);
        if (response.status === axios.HttpStatusCode.Ok) {
          localStorage.setItem('name', nickname);
          navigate('/play');
        }
      }).catch((error) => {
        console.log(error);
      });
    }
  }

  useEffect(() => {
    let name = localStorage.getItem('name');
    if (name) {
      setNickname(name);
      console.log('name: ' + name);
    }
    console.log('nickname: ' + nickname);
  }, []);

  return (
      <div className='navigator'>
        <div className='texture' />
        <div className='loginMenu'>
          <div className='logo'/>
          <TextField
            className='userInput'
            id='filled-basic'
            label='Nicholasname'
            variant='filled'
            defaultValue={nickname}
            onChange={(event) => setNickname(event.target.value)}
          />
          <Button
            className='homeMenuBtn'
            variant='contained'
            onClick={() => checkNickname()}>
            Enter
          </Button>
        </div>
      </div>
  );
}

export default Home;
