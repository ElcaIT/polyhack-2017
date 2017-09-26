import * as React from 'react';

interface PersonElementProperties {
    addPerson: (person: any) => void;
}

/**
 * Render a Person
 */
export class AddPerson extends React.Component<PersonElementProperties, any> {

    constructor(props, context) {
        super(props, context);
        this.state = {name: ''};
    }

    updateName(name: string) {
        this.setState({name: name});
    }

    handleAddPersonClick(e) {
        e.preventDefault();
        this.props.addPerson({name: this.state.name});
        this.setState({name: ''});
    }

    render() {
        return (
            <form>
                <input onChange={(e) => this.updateName(e.target.value)} value={this.state.name}/>
                <button onClick={(e) => this.handleAddPersonClick(e)}>Hinzufügen</button>
            </form>
        );
    }
}
