import React from "react";
import {Navigate} from 'react-router-dom';
import classes from './Home.module.css';

const Home = () => {
    return (
        <>
            <Navigate to='/login'/>
            <div>Home</div>
        </>
    );
};

export default Home;