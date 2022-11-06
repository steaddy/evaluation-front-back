import React from "react";
import Header from "./components/Header";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Login from './pages/Login';
import SignUp from "./pages/SignUp";
import Footer from "./components/Footer";
import LoadData from "./components/LoadData";
import Home from "./pages/Home";
import {Provider} from 'react-redux';
import {store} from "./store";

import './App.css';

function App() {
    return (
        <BrowserRouter>
            <Provider store={store}>
            <div className="App">
                <div className="main-container">
                    <Header/>
                    <div className="content">
                        <Routes>
                            <Route path='/' element={<Home/>}/>
                            <Route path='/login' element={<Login/>}/>
                            <Route path='/sign-up' element={<SignUp/>}/>
                            <Route path='/users' element={<LoadData/>}/>
                        </Routes>
                    </div>
                    <Footer/>
                </div>
            </div>
            </Provider>
        </BrowserRouter>
    );
}

export default App;
