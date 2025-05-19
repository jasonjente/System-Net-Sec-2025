import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                setMessage('Login successful!');
                // You can store token or redirect user here //TODO
                // Redirect to home
                navigate('/');
            } else {
                setMessage('Login failed. Check your credentials.');
            }
        } catch (error) {
            setMessage('Error connecting to server.');
        }
    };

    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>
                        Username:
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Password:
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </label>
                </div>
                <button type="submit" style={{ marginTop: 10 }}>Login</button>
            </form>
            <p>{message}</p>
            <Link to="/">Back to Home</Link>
        </div>
    );
}

export default Login;