import * as React from 'react';
import './App.css';
import {PersonList} from './app/components/person/PersonList';

class App extends React.Component {
    render() {
        return (
            <PersonList/>
        );
    }
}

export default App;
