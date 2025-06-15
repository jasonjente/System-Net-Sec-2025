// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register';
import DigitalDiceGame from './components/DigitalDiceGame';
import GuessDice from './components/GuessDice'; // Import the new GuessDice page

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/digital-dice-game" element={<DigitalDiceGame />} />
                <Route path="/guess-dice" element={<GuessDice />} /> {/* Add the route for GuessDice */}
            </Routes>
        </Router>
    );
}

export default App;