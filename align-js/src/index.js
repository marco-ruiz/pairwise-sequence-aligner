import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';

import { apiRequestGet } from './api-requests';
import AppPage from './components/AppPage';

const appLauncher = ({ scoringMatrixes }) =>
    ReactDOM.render(
        <AppPage alignUrl={process.env.REACT_APP_ALIGN_API_URL} matrixes={scoringMatrixes} />, 
        document.getElementById('root')
    );

apiRequestGet(process.env.REACT_APP_MATRIXES_API_URL, appLauncher);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
