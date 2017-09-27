import * as React from 'react';
import {Person} from '../../generated/backend-model';
import {Link} from 'react-router-dom';
import {RouteComponentProps, withRouter} from 'react-router';

interface PersonElementProperties {
    person: Person;
}

/**
 * Render a Person
 */
class PersonElementComponent extends React.Component<PersonElementProperties & RouteComponentProps<any>, any> {

    render() {
        return (
            <Link to={`persons/${this.props.person.id}`}>
                <li>{this.props.person.name}</li>
            </Link>
        );
    }
}

export const PersonElement: React.ComponentClass<PersonElementProperties> = withRouter(PersonElementComponent);
