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
                console.log("Login was successful. Response status: ", response.status);
                console.log("Getting JWT Token...: ");
                const data = await response.json();
                const token = data.token;
                console.log("Successfully got token. Token is: ", token);
                localStorage.setItem('jwtToken', token);
                setMessage('Login successful!');
                navigate('/digital-dice-game');
            } else {
                console.log("Login failed. Response status: ", response.status);
                setMessage('Login failed. Check your credentials.');
            }
        } catch (error) {
            console.error("Error connecting to server")
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