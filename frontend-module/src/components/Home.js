import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h1>Welcome!</h1>
            <Link to="/login">
                <button style={{ marginRight: 10 }}>
                    Login
                </button>
            </Link>
            <Link to="/register">
                <button>
                    Register
                </button>
            </Link>
        </div>
    );
}

export default Home;
