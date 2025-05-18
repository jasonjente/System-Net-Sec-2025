import React, { useState } from 'react';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register';

function App() {
    const [page, setPage] = useState('home'); // 'home', 'login', or 'register'

    const renderPage = () => {
        if (page === 'login') return <Login onChangePage={setPage} />;
        if (page === 'register') return <Register onChangePage={setPage} />;
        return <Home onChangePage={setPage} />;
    };

    return <div className="App">{renderPage()}</div>;
}

export default App;
