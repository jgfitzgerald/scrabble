import './constants.css';
import './Home.css';
import React, {useState, useEffect} from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {Link} from 'react-router-dom';
import { useSpring, useTransition, animated } from '@react-spring/web'

function Home() {

  let [nickname, setNickname] = useState('');
  let [nameSet, updateNameSet] = useState(false);

  function updateNickname() {
    localStorage.setItem('name', nickname);
    updateNameSet(true);
  }

  useEffect(() => {
    setNickname(localStorage.getItem('name'));
    updateNameSet(false);
  }, []);

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
          { nameSet ? <>
            <Link to='/play' className='link'>
              <Button className='homeMenuBtn' variant="contained">Play</Button>
            </Link>
            <Link to='/rules' className='link'>
              <Button className='homeMenuBtn' variant="contained">Rules</Button>
            </Link>
            </> : <>
            <TextField
              className="userInput"
              id="filled-basic"
              label="Nicholasname"
              variant='filled'
              defaultValue={nickname}
              onChange={(event) => setNickname(event.target.value)}
            />
            <Button className='homeMenuBtn' variant="contained" onClick={updateNickname()}>Enter</Button> </>
          }
        </div>
      </div>
  );
}

export default Home;
