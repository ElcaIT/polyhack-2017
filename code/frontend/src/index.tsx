import * as React from 'react';
import * as ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import App from './App';
import {PersonDetail} from './app/components/person/PersonDetail';
import {HashRouter} from 'react-router-dom';
import {Route} from 'react-router';

ReactDOM.render(
    <HashRouter>
        <div>
            <Route exact={true} path="/" component={App}/>
            <Route path="/persons/:personId" component={PersonDetail}/>
        </div>
    </HashRouter>,
    document.getElementById('root') as HTMLElement
);
registerServiceWorker();
