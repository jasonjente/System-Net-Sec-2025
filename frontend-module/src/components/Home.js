import React from 'react';

function Home({ onChangePage }) {
    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h1>Welcome!</h1>
            <button onClick={() => onChangePage('login')} style={{ marginRight: 10 }}>
                Login
            </button>
            <button onClick={() => onChangePage('register')}>
                Register
            </button>
        </div>
    );
}

export default Home;
