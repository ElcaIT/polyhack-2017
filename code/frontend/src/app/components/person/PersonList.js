import * as React from 'react';
import * as ListGroup from 'react-bootstrap/lib/ListGroup';
import {PersonElement} from './PersonElement';

export class PersonList extends React.Component {

    render() {
        let persons = this.props.persons.map((person, index) => {
            return (
                <PersonElement key={index} person={person} />
            );
        });
        return (<ListGroup>{persons}</ListGroup>);
    }
}
