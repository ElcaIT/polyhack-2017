import * as React from 'react';
import * as ListGroupItem from 'react-bootstrap/lib/ListGroupItem';
import LinkContainer from 'react-router-bootstrap/lib/LinkContainer';


/**
 * Render a Person
 */
export class PersonElement extends React.Component {

    render() {
        return (
                <LinkContainer to={`persons/${this.props.person.id}`}>
                    <ListGroupItem header={this.props.person.name}>{this.props.person.id}</ListGroupItem>
                </LinkContainer>
        );
    }
}
