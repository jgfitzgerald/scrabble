import './constants.css';
import './App.css';
import React, {useState} from 'react';
import Home from "./pages/Home.js";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {Link} from 'react-router-dom';
import { useSpring, useTransition, animated } from '@react-spring/web'

function App() {

  let [nickname, setNickname] = useState('');

  // const pages = [<Home/>, <Game/>, <User/>];
  // const [currPage, updateCurrPage] = useState();
  // methods in here
  // updateCurrPage(<Home/>)
  // return <Home/>
  return (
      <div className='navigator'>
        <div className='texture' />
        <div className='loginMenu'>
          <div className='logo'/>
          {/* <TextField className="userInput" id="standard-basic" label="Username" variant='standard' />
          <TextField className="userInput" id="standard-basic" label="Password" variant='standard' />
          <div className='btnRow'>
            <Button variant="contained">Login</Button>
            <Button variant="outlined">Sign up</Button>
          </div>
          <Button variant="text">Play as Guest</Button> */}
          <TextField className="userInput" id="filled-basic" label="Nicholasname" variant='filled' onChange={(event) => setNickname(event.target.value)}/>
          <Link to='/home' state={{name: nickname}} className='link'>
            <Button variant="contained">Enter</Button>
          </Link>
        </div>
      </div>
  );
}

export default App;
