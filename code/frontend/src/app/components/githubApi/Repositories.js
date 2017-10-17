import * as React from 'react';
import axios from 'axios';
import PageHeader from "react-bootstrap/es/PageHeader";
import Pager from "react-bootstrap/es/Pager";

export class Repositories extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            repos: [],
            nextPage: ''
        };
    }

    componentDidMount = () => {
        this.loadData('https://api.github.com/repositories');
    };

    loadData = (url) => {
        axios.get(url)
            .then(response => {
                this.setState({repos: response.data});
                var link = response.headers.link;
                var parts = link.split(',');
                if (parts.length === 2) {
                    var nextPage = parts[0].split(';')[0].trim().replace('<', '').replace('>', '');
                    this.setState({nextPage: nextPage});
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    nextPage = () => {
        this.loadData(this.state.nextPage)
    }

    render() {
        return (
            <div className="container">
                <PageHeader>Github Repos
                    <small>https://api.github.com/repositories</small>
                </PageHeader>
                <Pager>
                    <Pager.Item next onSelect={this.nextPage} href="#">Next</Pager.Item>
                </Pager>
                <ul>
                    {this.state.repos.map(function (repo) {
                        return (<li key={repo.id}>
                            {repo.full_name}
                        </li>)
                    })}
                </ul>
            </div>
        );
    }
}
