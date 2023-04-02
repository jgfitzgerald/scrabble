import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { ThemeProvider } from '@emotion/react';
import { createTheme } from '@mui/material/styles';
import { BrowserRouter as HashRouter, Route, Routes} from "react-router-dom";

import Home from './pages/Home';
import Game from './pages/Game';
import NotFound from './pages/NotFound';

// https://mui.com/material-ui/customization/default-theme/

const theme = createTheme({
  palette: {
    primary: {
      main: '#b63f4b',
      contrastText: '#bfbc95',
      dark: '#8c2b2b',
    },
    secondary: {
      main: '#b63f4b',
      light: '#ea5462',
      dark: '#8c2b2b',
      contrastText: '#7c7c7c',
    },
    contrastThreshold: 3,
    tonalOffset: 0.2,
  },
  typography: {
    fontFamily: "Interstate-Bold",
  },
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <HashRouter>
        <Routes>
          <Route path='/'         element={<Home/>}     />
          <Route path='/play'     element={<Game/>}     />
          <Route path='*'         element={<NotFound/>} />
        </Routes>
      </HashRouter>
    </ThemeProvider>
  </React.StrictMode>
);
