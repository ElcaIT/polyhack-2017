import * as React from 'react';
import * as FormGroup from 'react-bootstrap/lib/FormGroup';
import * as InputGroup from 'react-bootstrap/lib/InputGroup';
import * as FormControl from 'react-bootstrap/lib/FormControl';
import * as Button from 'react-bootstrap/lib/Button';


/**
 * Render a Person
 */
export class AddPerson extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {name: ''};
    }

    updateName(event) {
        this.setState({name: event.target.value});
    }

    handleAddPersonClick(e) {
        e.preventDefault();
        this.props.addPerson({name: this.state.name});
        this.setState({name: ''});
    }

    render() {
        return (
            <form>
                <FormGroup>
                    <InputGroup>
                        <FormControl onChange={e => this.updateName(e)} value={this.state.name} type="text" />
                        <InputGroup.Button>
                            <Button type="submit" onClick={e => this.handleAddPersonClick(e)}>Hinzufügen</Button>
                        </InputGroup.Button>
                    </InputGroup>
                </FormGroup>
            </form>
        );
    }
}
