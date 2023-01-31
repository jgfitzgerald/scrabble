import React from 'react';
import background from './assets/wood.jpg';

const Tile = (props) => {
    const char = props.char;
    const value = props.val;

    const tileStyle = {
        cursor: 'pointer',
        position: 'relative',
        width: '50px',
        aspectRatio: 1,
        backgroundColor: 'white',
        backgroundImage: `url(${background})`,
        backgroundSize: '100%',
        borderRadius: '5px',
        display: 'grid',
        placeItems: 'center',
        color: 'black',
    };
    const valueStyle = {
        position: 'absolute',
        bottom: 1,
        right: 3,
    };

    return <div style={tileStyle}>
        <h1>{char}</h1>
        <p style={valueStyle}>{value}</p>
    </div>
}

export default Tile;