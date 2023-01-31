import React from 'react';

const Board = (props) => {

    const boardStyle = {
        display: 'grid',
        gridTemplateColumns: 'repeat(15, 1fr)',
        gridTemplateRows: 'repeat(15, 1fr)',
        gap: '2px',
        width: '100%',
        height: '100%',
        borderRadius: '5px',
        backgroundColor: '#bfbc95',
        padding: '2px',
    };
    const normal= {
        borderRadius: '5px',
        backgroundColor: 'grey',
    };
    const center= {
        borderRadius: '5px',
        backgroundColor: '#831399',
    };
    const doubleWord = {
        borderRadius: '5px',
        backgroundColor: '#c526ab',
    };
    const doubleLetter = {
        borderRadius: '5px',
        backgroundColor: '#4bcae0',
    };
    const tripleWord = {
        borderRadius: '5px',
        backgroundColor: '#e49631',
    };
    const tripleLetter = {
        borderRadius: '5px',
        backgroundColor: '#2718af',
    };

    return <div style={boardStyle}>
        <div style={tripleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleWord}></div>

        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={tripleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={center}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleWord}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>

        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>

        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleWord}></div>
        <div style={normal}></div>

        <div style={tripleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleWord}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={doubleLetter}></div>
        <div style={normal}></div>
        <div style={normal}></div>
        <div style={tripleWord}></div>
    </div>
}

export default Board;