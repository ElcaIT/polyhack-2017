import * as React from 'react';
import './App.css';
import {PersonList} from './app/components/person/PersonList';
import {AddPerson} from './app/components/person/AddPerson';

class App extends React.Component<any, any> {

    constructor(props, context) {
        super(props, context);
        this.state = {
            persons: [
                {name: 'Alex'},
                {name: 'Martin'},
                {name: 'Marco'}
            ]
        };
    }

    addPerson(person) {
        this.setState(prevState => ({
            persons: [...prevState.persons, person]
        }));
    }

    render() {
        return (
            <div>
                <AddPerson addPerson={(person) => this.addPerson(person)}/>
                <PersonList persons={this.state.persons}/>
            </div>
        );
    }
}

export default App;
