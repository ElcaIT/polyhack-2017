import * as React from 'react';
import * as ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import App from './App';
import {PersonDetail} from './app/components/person/PersonDetail';
import {HashRouter} from 'react-router-dom';
import {Route} from 'react-router';
import 'bootstrap/dist/css/bootstrap.css';
import {Repositories} from "./app/components/githubApi/Repositories";

ReactDOM.render(
    <HashRouter>
        <div>
            <Route exact={true} path="/" component={App}/>
            <Route path="/persons/:personId" component={PersonDetail}/>
            <Route path="/github/repositories" component={Repositories} />
        </div>
    </HashRouter>,
    document.getElementById('root')
);
registerServiceWorker();
