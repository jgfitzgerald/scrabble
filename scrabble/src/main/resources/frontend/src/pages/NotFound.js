import './NotFound.css';
import Tile from '../components/tile.js';

const NotFound = (props) => {

    return <div className='NotFoundPage'>
        <div className='texture'/>
        <div className='tileRow'>
            <Tile char="4"/>
            <Tile char="0"/>
            <Tile char="4"/>
        </div>
        <h1>Woops, this page couldn't be found.</h1>
    </div>
}

export default NotFound