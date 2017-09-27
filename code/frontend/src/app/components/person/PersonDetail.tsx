import * as React from 'react';
import axios from 'axios';

/**
 * Render a Person in Detail View
 */
export class PersonDetail extends React.Component<any, any> {

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
            <div>
                <span>{this.state.person.id}</span>
                <span>{this.state.person.name}</span>
            </div>
        );
    }
}
