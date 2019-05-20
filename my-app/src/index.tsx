import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './User/App';
import Project from './Project/Project';
import Login from './Login/Login';
import Home from './Home/Home';
import * as serviceWorker from './serviceWorker';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';
import Profile from './Profile/Profile';
import SignUp from './SignUp/SignUp'
ReactDOM.render(<Router>
    <div>
    <Route exact path="/" component={Home}/>
    <Route path="/Projects/:projectId" component={Project} />
    <Route path="/Users/:userId" component={App}/>
    <Route path="/Login" component={Login} />
    <Route path="/Home" component={Home} />
    <Route path="/Profile" component={Profile}/>
    <Route path="/SignUp" component={SignUp}/>
    </div>
    </Router>
    , document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
