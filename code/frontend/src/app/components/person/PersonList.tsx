import {Person} from '../../generated/backend-model';
import * as React from 'react';
import {PersonElement} from './PersonElement';

export class PersonList extends React.Component<any, any> {

    render() {
        let persons = this.props.persons.map((person: Person, index: number) => {
            return <PersonElement key={index} person={person}/>;
        });
        return (<ul>{persons}</ul>);
    }
}
