import {Person} from '../../generated/backend-model';
import * as React from 'react';
import {PersonElement} from './Person';

export class PersonList extends React.Component<any, any> {

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

    render() {
        let persons = this.state.persons.map((person: Person, index: number) => {
            return <PersonElement key={index} person={person}/>;
        });
        return (<ul>{persons}</ul>);
    }
}
