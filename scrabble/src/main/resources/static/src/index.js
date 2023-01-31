import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { ThemeProvider } from '@emotion/react';
import { createTheme } from '@mui/material/styles';
import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import App from './App';
import Home from './pages/Home';

// https://mui.com/material-ui/customization/default-theme/

const theme = createTheme({
  palette: {
    primary: {
      main: '#b63f4b',
      contrastText: '#000000',
    },
    secondary: {
      light: '#ea5462',
      main: '#8c2b2b',
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
      <Router>
        <Routes>
          <Route path='/' element={<App/>} />
          <Route path='/home' element={<Home/>} />
        </Routes>
      </Router>
    </ThemeProvider>
  </React.StrictMode>
);
