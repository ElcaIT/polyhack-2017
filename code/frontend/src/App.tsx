import * as React from 'react';
import axios from 'axios';
import './App.css';
import {PersonList} from './app/components/person/PersonList';
import {AddPerson} from './app/components/person/AddPerson';

class App extends React.Component<any, any> {

    constructor(props, context) {
        super(props, context);
        this.state = {
            persons: [
                // {name: 'Alex'},
                // {name: 'Martin'},
                // {name: 'Marco'}
            ]
        };
    }

    componentDidMount() {
        axios.get('./api/persons')
            .then(response => {
                this.setState({persons: response.data});
            });
    }

    addPerson(person) {
        axios.post('./api/persons', person).then(response => {
            this.setState(prevState => ({
                persons: [...prevState.persons, response.data]
            }));
        }).catch(function (error: any) {
            console.log(error);
        });
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
