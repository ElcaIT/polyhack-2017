import * as React from 'react';
import {Person} from '../../generated/backend-model';
import {RouteComponentProps, withRouter} from 'react-router';
import * as ListGroupItem from 'react-bootstrap/lib/ListGroupItem';
import LinkContainer from 'react-router-bootstrap/lib/LinkContainer';

interface PersonElementProperties {
    person: Person;
}

/**
 * Render a Person
 */
class PersonElementComponent extends React.Component<PersonElementProperties & RouteComponentProps<any>, any> {

    render() {
        return (
                <LinkContainer to={`persons/${this.props.person.id}`}>
                    <ListGroupItem header={this.props.person.name}>{this.props.person.id}</ListGroupItem>
                </LinkContainer>
        );
    }
}

export const PersonElement: React.ComponentClass<PersonElementProperties> = withRouter(PersonElementComponent);
