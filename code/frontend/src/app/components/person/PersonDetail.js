import * as React from 'react';
import axios from 'axios';
import * as PageHeader from 'react-bootstrap/lib/PageHeader';
import * as Jumbotron from 'react-bootstrap/lib/Jumbotron';

/**
 * Render a Person in Detail View
 */
export class PersonDetail extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = { person: {}};
    }

    componentDidMount() {
        axios.get('./api/persons/' + this.props.match.params.personId)
            .then(response => {
                this.setState({person: response.data});
            });
    }

    render() {
        return (
            <div className="container">
                <PageHeader>Person Detail <small>Reachable in persons/:personId</small></PageHeader>
                <Jumbotron>
                    <h2>id: {this.state.person.id}</h2>
                    <h1>name: {this.state.person.name}</h1>
                </Jumbotron>
            </div>
        );
    }
}
