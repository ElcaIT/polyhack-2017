import * as React from 'react';
import {Person} from '../../generated/backend-model';

interface PersonElementProperties {
    person: Person;
}

/**
 * Render a Person
 */
export class PersonElement extends React.Component<PersonElementProperties, any> {

    render() {
        return (<li>{this.props.person.name}</li>);
    }
}
